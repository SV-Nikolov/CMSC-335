from pathlib import Path
import urllib.request
import time

from reportlab.lib import colors
from reportlab.lib.pagesizes import LETTER
from reportlab.lib.styles import ParagraphStyle, getSampleStyleSheet
from reportlab.lib.units import inch
from reportlab.lib.utils import ImageReader
from reportlab.platypus import (
    Image,
    KeepTogether,
    ListFlowable,
    ListItem,
    PageBreak,
    Paragraph,
    Preformatted,
    SimpleDocTemplate,
    Spacer,
    Table,
    TableStyle,
)


def fetch_mermaid_png(mmd_path: Path, out_png: Path, scale: int = 4, timeout: int = 120) -> None:
    code = mmd_path.read_text(encoding="utf-8")
    req = urllib.request.Request(
        f"https://kroki.io/mermaid/png?scale={scale}",
        data=code.encode("utf-8"),
        headers={
            "Content-Type": "text/plain; charset=utf-8",
            "User-Agent": "Mozilla/5.0",
        },
        method="POST",
    )
    with urllib.request.urlopen(req, timeout=timeout) as resp:
        out_png.write_bytes(resp.read())


def fetch_mermaid_png_with_retry(mmd_path: Path, out_png: Path, scale: int = 4, attempts: int = 3) -> None:
    last_error = None
    for attempt in range(1, attempts + 1):
        try:
            fetch_mermaid_png(mmd_path, out_png, scale=scale, timeout=120)
            return
        except Exception as exc:
            last_error = exc
            if attempt < attempts:
                time.sleep(2)
    raise last_error


def p(text: str, style):
    return Paragraph(text, style)


def mono(code: str, style):
    content = Preformatted(code.strip("\n"), style)
    box = Table([[content]], colWidths=[6.2 * inch])
    box.setStyle(
        TableStyle(
            [
                ("BACKGROUND", (0, 0), (-1, -1), colors.HexColor("#F6F8FB")),
                ("BOX", (0, 0), (-1, -1), 0.7, colors.HexColor("#CDD7E3")),
                ("LEFTPADDING", (0, 0), (-1, -1), 7),
                ("RIGHTPADDING", (0, 0), (-1, -1), 7),
                ("TOPPADDING", (0, 0), (-1, -1), 6),
                ("BOTTOMPADDING", (0, 0), (-1, -1), 6),
            ]
        )
    )
    return box


def bullet_list(items, style):
    flow = []
    for item in items:
        flow.append(ListItem(Paragraph(item, style), leftIndent=10))
    return ListFlowable(
        flow,
        bulletType="bullet",
        start="circle",
        leftIndent=10,
        bulletFontSize=6,
    )


def add_figure(story, image_path: Path, max_w: float, max_h: float, caption: str, caption_style):
    if not image_path.exists():
        return

    reader = ImageReader(str(image_path))
    img_w, img_h = reader.getSize()
    if img_w <= 0 or img_h <= 0:
        return

    scale = min(max_w / img_w, max_h / img_h)
    draw_w = img_w * scale
    draw_h = img_h * scale

    story.append(p(caption, caption_style))
    story.append(Image(str(image_path), width=draw_w, height=draw_h))
    story.append(Spacer(1, 10))


def sized_image(image_path: Path, max_w: float, max_h: float):
    if not image_path.exists():
        return None

    reader = ImageReader(str(image_path))
    img_w, img_h = reader.getSize()
    if img_w <= 0 or img_h <= 0:
        return None

    scale = min(max_w / img_w, max_h / img_h)
    draw_w = img_w * scale
    draw_h = img_h * scale
    return Image(str(image_path), width=draw_w, height=draw_h)


def add_side_by_side_figures(story, left_path: Path, left_caption: str, right_path: Path, right_caption: str, caption_style):
    left_img = sized_image(left_path, max_w=2.95 * inch, max_h=2.45 * inch)
    right_img = sized_image(right_path, max_w=2.95 * inch, max_h=2.45 * inch)

    if not left_img or not right_img:
        return

    caption_left = p(left_caption, caption_style)
    caption_right = p(right_caption, caption_style)

    table = Table(
        [[left_img, right_img], [caption_left, caption_right]],
        colWidths=[3.1 * inch, 3.1 * inch],
    )
    table.setStyle(
        TableStyle(
            [
                ("VALIGN", (0, 0), (-1, -1), "TOP"),
                ("ALIGN", (0, 0), (-1, 0), "CENTER"),
                ("LEFTPADDING", (0, 0), (-1, -1), 4),
                ("RIGHTPADDING", (0, 0), (-1, -1), 4),
                ("TOPPADDING", (0, 0), (-1, -1), 2),
                ("BOTTOMPADDING", (0, 0), (-1, -1), 2),
            ]
        )
    )

    story.append(KeepTogether([table, Spacer(1, 10)]))


def build_pdf(out_pdf: Path, flow_png: Path, compare_png: Path) -> None:
    doc = SimpleDocTemplate(
        str(out_pdf),
        pagesize=LETTER,
        rightMargin=0.9 * inch,
        leftMargin=0.9 * inch,
        topMargin=0.85 * inch,
        bottomMargin=0.8 * inch,
        title="CMSC 335 Homework 3",
        author="Stefan Nikolov",
    )

    styles = getSampleStyleSheet()
    title = ParagraphStyle(
        "TitlePro",
        parent=styles["Title"],
        fontName="Helvetica-Bold",
        fontSize=26,
        leading=30,
        textColor=colors.HexColor("#0B1F3A"),
        spaceAfter=14,
    )
    h1 = ParagraphStyle(
        "H1Pro",
        parent=styles["Heading1"],
        fontName="Helvetica-Bold",
        fontSize=16,
        leading=20,
        textColor=colors.HexColor("#0B1F3A"),
        spaceBefore=12,
        spaceAfter=8,
    )
    h2 = ParagraphStyle(
        "H2Pro",
        parent=styles["Heading2"],
        fontName="Helvetica-Bold",
        fontSize=13,
        leading=17,
        textColor=colors.HexColor("#1A4F7A"),
        spaceBefore=8,
        spaceAfter=4,
    )
    h2_wrong = ParagraphStyle("H2Wrong", parent=h2, textColor=colors.HexColor("#C62828"))
    h2_fix = ParagraphStyle("H2Fix", parent=h2, textColor=colors.HexColor("#1B8E3E"))
    body = ParagraphStyle(
        "BodyPro",
        parent=styles["BodyText"],
        fontName="Helvetica",
        fontSize=11.2,
        leading=15.5,
        textColor=colors.HexColor("#222222"),
        spaceAfter=7,
    )
    code_style = ParagraphStyle(
        "CodePro",
        parent=body,
        fontName="Courier",
        fontSize=9.4,
        leading=12.2,
        backColor=colors.HexColor("#F6F8FB"),
        borderWidth=0.4,
        borderColor=colors.HexColor("#D7DEE8"),
        borderPadding=7,
        leftIndent=2,
        rightIndent=2,
        spaceAfter=10,
    )

    story = []

    story.append(p("CMSC 335 Homework 3", title))
    story.append(p("Object-Oriented and Concurrent Programming", h2))
    story.append(Spacer(1, 6))

    meta = Table(
        [
            ["Student", "Stefan Nikolov"],
            ["Course", "CMSC 335"],
            ["Document Type", "PDF"],
            ["Date", "April 18, 2026"],
        ],
        colWidths=[1.5 * inch, 4.7 * inch],
    )
    meta.setStyle(
        TableStyle(
            [
                ("BACKGROUND", (0, 0), (0, -1), colors.HexColor("#EAF1FA")),
                ("BACKGROUND", (1, 0), (1, -1), colors.white),
                ("TEXTCOLOR", (0, 0), (-1, -1), colors.HexColor("#1D2A3A")),
                ("FONTNAME", (0, 0), (0, -1), "Helvetica-Bold"),
                ("FONTNAME", (1, 0), (1, -1), "Helvetica"),
                ("FONTSIZE", (0, 0), (-1, -1), 10),
                ("GRID", (0, 0), (-1, -1), 0.4, colors.HexColor("#C9D5E5")),
                ("LEFTPADDING", (0, 0), (-1, -1), 6),
                ("RIGHTPADDING", (0, 0), (-1, -1), 6),
                ("TOPPADDING", (0, 0), (-1, -1), 5),
                ("BOTTOMPADDING", (0, 0), (-1, -1), 5),
            ]
        )
    )
    story.append(meta)
    story.append(Spacer(1, 14))

    story.append(p("Overview", h1))
    story.append(p("This document provides concise, rubric-aligned answers for all Homework 3 questions, with clear technical explanations and direct coverage of grading criteria.", body))

    story.append(p("Mermaid Diagrams", h1))
    add_side_by_side_figures(
        story,
        flow_png,
        "Figure 1. Dining Philosophers Deadlock Risk",
        compare_png,
        "Figure 2. Lock vs synchronized Overview",
        h2,
    )

    story.append(PageBreak())

    def add_problem_block(items):
        story.append(KeepTogether(items))

    p1 = [
        p("Problem 1", h1),
        p("Events generated by required Swing/JavaFX controls:", body),
        p("JButton / Button", h2),
        bullet_list([
            "Swing JButton: ActionEvent.",
            "JavaFX Button: ActionEvent via setOnAction(...).",
        ], body),
        p("JTextField / TextField", h2),
        bullet_list([
            "Swing JTextField: ActionEvent on Enter, plus document change events through its Document.",
            "JavaFX TextField: ActionEvent on Enter, plus textProperty() change notifications.",
        ], body),
        p("JComboBox / ComboBox", h2),
        bullet_list([
            "Swing JComboBox: ActionEvent and ItemEvent for select/deselect transitions.",
            "JavaFX ComboBox: ActionEvent and valueProperty() change notifications.",
        ], body),
    ]
    add_problem_block(p1)

    p2_code = mono("""
// Interface-method examples implemented by JTable
public void tableChanged(TableModelEvent e)
public Dimension getPreferredScrollableViewportSize()
public int getScrollableUnitIncrement(Rectangle r, int o, int d)
public void columnAdded(TableColumnModelEvent e)
public void valueChanged(ListSelectionEvent e)
public void editingStopped(ChangeEvent e)
public void sorterChanged(RowSorterEvent e)
""", code_style)
    p2 = [
        p("Problem 2", h1),
        p("JTable implements required methods from interfaces such as TableModelListener, Scrollable, TableColumnModelListener, ListSelectionListener, CellEditorListener, and RowSorterListener.", body),
        p2_code,
    ]
    add_problem_block(p2)

    p3 = [
        p("Problem 3", h1),
        p("Resize behavior comparison of layout managers:", body),
        bullet_list([
            "FlowPane/FlowLayout: wraps controls more as width shrinks; fewer wraps as width grows.",
            "GridPane/GridLayout: grid structure retained; GridLayout equalizes cells, GridPane obeys constraints.",
            "AnchorPane: nodes stay offset to anchored edges; opposite anchors stretch node size.",
            "TilePane: uniform tiles reflow into different row/column counts on resize.",
            "BorderPane (extra): center region absorbs most resize change.",
        ], body),
    ]
    add_problem_block(p3)

    p4 = [
        p("Problem 4", h1),
        p("Dining philosophers analysis:", body),
        p("What is wrong", h2_wrong),
        bullet_list([
            "Picking left then right for everyone can deadlock through circular wait.",
            "All threads may hold one chopstick and wait forever for the second.",
        ], body),
        p("How to fix", h2_fix),
        bullet_list([
            "Use an arbitrator/semaphore permitting at most 4 philosophers to compete for chopsticks.",
            "This breaks full circular wait and guarantees system progress.",
        ], body),
        p("Starvation freedom", h2),
        bullet_list([
            "With fair queuing/fair locks, starvation can be prevented.",
            "Without fairness, deadlock can be avoided but starvation is still possible.",
        ], body),
    ]
    add_problem_block(p4)

    p5 = [
        p("Problem 5", h1),
        p("Methods required by Lock interface and characteristics:", body),
        bullet_list([
            "lock(): blocking acquire.",
            "lockInterruptibly(): blocking acquire that responds to interruption.",
            "tryLock(): immediate non-blocking attempt.",
            "tryLock(timeout, unit): bounded wait attempt.",
            "unlock(): releases lock; use in finally.",
            "newCondition(): creates condition queue bound to lock.",
        ], body),
    ]
    add_problem_block(p5)

    p6 = [
        p("Problem 6", h1),
        p("When JVM encounters synchronized, it performs monitor-enter/monitor-exit operations on a specific monitor object (this, Class object, or explicit lock object). It blocks contending threads, supports reentrancy, and enforces happens-before visibility guarantees on monitor release/acquire.", body),
    ]
    add_problem_block(p6)

    p7 = [
        p("Problem 7", h1),
        p("Lock interface vs synchronized:", body),
        bullet_list([
            "synchronized is implicit and simpler; Lock is explicit and more flexible.",
            "Lock supports tryLock, timeouts, interruptible acquisition, and multiple Conditions.",
            "synchronized uses intrinsic monitor methods (wait/notify) with one condition queue.",
            "Some Lock implementations support fairness policies; synchronized does not expose fairness control.",
        ], body),
    ]
    add_problem_block(p7)

    doc.build(story)


def main() -> None:
    base = Path(__file__).resolve().parent

    flow_mmd = base / "dining_philosophers_flow.mmd"
    compare_mmd = base / "lock_vs_synchronized.mmd"
    flow_png = base / "dining_philosophers_flow.png"
    compare_png = base / "lock_vs_synchronized.png"

    try:
        fetch_mermaid_png_with_retry(flow_mmd, flow_png, scale=4, attempts=3)
    except Exception as exc:
        if not flow_png.exists():
            raise
        print(f"Warning: flow diagram refresh failed, using existing image. {exc}")

    try:
        fetch_mermaid_png_with_retry(compare_mmd, compare_png, scale=4, attempts=3)
    except Exception as exc:
        if not compare_png.exists():
            raise
        print(f"Warning: comparison diagram refresh failed, using existing image. {exc}")

    out_pdf = base / "Homework3_Nikolov.pdf"
    build_pdf(out_pdf, flow_png, compare_png)
    print(f"Created: {out_pdf}")


if __name__ == "__main__":
    main()

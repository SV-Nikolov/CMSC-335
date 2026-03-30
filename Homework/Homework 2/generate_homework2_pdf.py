from pathlib import Path
import urllib.request
import time
from datetime import datetime

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
        except Exception as exc:  # Network/transient render failures
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


def build_pdf(out_pdf: Path, class_png: Path, seq_png: Path) -> None:
    doc = SimpleDocTemplate(
        str(out_pdf),
        pagesize=LETTER,
        rightMargin=0.9 * inch,
        leftMargin=0.9 * inch,
        topMargin=0.85 * inch,
        bottomMargin=0.8 * inch,
        title="CMSC 335 Homework 2",
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
    h2_wrong = ParagraphStyle(
        "H2Wrong",
        parent=h2,
        textColor=colors.HexColor("#C62828"),
    )
    h2_fix = ParagraphStyle(
        "H2Fix",
        parent=h2,
        textColor=colors.HexColor("#1B8E3E"),
    )
    body = ParagraphStyle(
        "BodyPro",
        parent=styles["BodyText"],
        fontName="Helvetica",
        fontSize=11.2,
        leading=15.5,
        textColor=colors.HexColor("#222222"),
        spaceAfter=7,
    )
    small = ParagraphStyle(
        "SmallPro",
        parent=body,
        fontSize=9.25,
        leading=12,
        textColor=colors.HexColor("#3C4A5B"),
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

    story.append(p("CMSC 335 Homework 2", title))
    story.append(p("Object-Oriented and Concurrent Programming", h2))
    story.append(Spacer(1, 6))

    meta = Table(
        [
            ["Student", "Stefan Nikolov"],
            ["Course", "CMSC 335"],
            ["Document Type", "PDF"],
            ["Date", "March 28, 2026"],
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

    story.append(p("Executive Summary", h1))
    story.append(
        p(
            "This document presents complete, clearly structured answers for Homework 2. Each response explains the issue and provides a practical correction, with multi-solution items fully covered where required.",
            body,
        )
    )

    story.append(p("Mermaid UML Diagrams", h1))
    story.append(p("The following diagrams were authored in Mermaid and rendered as UML assets for this submission.", body))

    add_figure(
        story,
        class_png,
        max_w=6.5 * inch,
        max_h=4.0 * inch,
        caption="Figure 1. UML Class Hierarchy",
        caption_style=h2,
    )
    add_figure(
        story,
        seq_png,
        max_w=6.5 * inch,
        max_h=3.8 * inch,
        caption="Figure 2. Sequence Diagram for Problem 2 toString Flow",
        caption_style=h2,
    )

    story.append(PageBreak())

    def add_problem_block(block_items):
        story.append(KeepTogether(block_items))

    p1_items = [
        p("Problem 1", h1),
        p("UML standard diagrams include 14 diagrams in UML 2.x, grouped into Structural and Behavioral categories.", body),
        p("Structural Diagrams", h2),
    ]
    structural = [
        "Class Diagram: static model of classes, attributes, operations, and relationships.",
        "Object Diagram: runtime snapshot of instances and links.",
        "Component Diagram: software components and provided/required interfaces.",
        "Composite Structure Diagram: internal parts and connectors of a classifier.",
        "Package Diagram: package organization and dependencies.",
        "Deployment Diagram: deployment of artifacts to nodes/environments.",
        "Profile Diagram: UML customization with stereotypes and constraints.",
    ]
    p1_items.append(bullet_list(structural, body))

    p1_items.append(p("Behavioral Diagrams", h2))
    behavioral = [
        "Use Case Diagram: user-facing functionality with actors and use cases.",
        "Activity Diagram: workflows, branches, and concurrency.",
        "State Machine Diagram: event-driven state changes over time.",
        "Sequence Diagram: time-ordered message exchanges between participants.",
        "Communication Diagram: object collaboration with focus on links.",
        "Interaction Overview Diagram: high-level control flow connecting interactions.",
        "Timing Diagram: state/value changes on a time axis with constraints.",
    ]
    p1_items.append(bullet_list(behavioral, body))
    add_problem_block(p1_items)

    p2_items = [
        p("Problem 2", h1),
        p("Requirement: produce output 4 3 7 5 99 3 while leveraging H2ClassB natural toString.", body),
        p("What is wrong", h2_wrong),
        bullet_list([
        "Without overriding toString, printing objects yields class-name/hash output.",
        "H2ClassA must join list elements with spaces in insertion order.",
    ], body),
        p("How to fix", h2_fix),
        mono("""
@Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < list.size(); i++) {
        sb.append(list.get(i));
        if (i < list.size() - 1) sb.append(" ");
    }
    return sb.toString();
}

@Override
public String toString() {
    return String.valueOf(x);
}
""", code_style),
    ]
    add_problem_block(p2_items)

    def add_problem(num, wrong_points, fix_points, code=None):
        items = [
            p(f"Problem {num}", h1),
            p("What is wrong", h2_wrong),
            bullet_list(wrong_points, body),
            p("How to fix", h2_fix),
            bullet_list(fix_points, body),
        ]
        if code:
            items.append(mono(code, code_style))
        add_problem_block(items)

    add_problem(
        3,
        [
            "H2ClassD default constructor calls super(), but H2ClassC has only H2ClassC(int).",
        ],
        [
            "Fix 1: define H2ClassD() and call super(0).",
            "Fix 2: add a no-argument constructor to H2ClassC.",
        ],
        """
class H2ClassD extends H2ClassC {
    H2ClassD() { super(0); }
}

// Alternative
public class H2ClassC {
    H2ClassC() {}
    H2ClassC(int a) {}
}
""",
    )

    add_problem(
        4,
        [
            "this(...) is not first statement in constructor.",
        ],
        [
            "Move this(5, 12) to the first line of the constructor before any assignments.",
        ],
    )

    add_problem(
        5,
        [
            "int cannot store decimal literal 17.36.",
        ],
        [
            "Use double for decimals, or change value to integer.",
        ],
        """
public static final double myNumber = 17.36;
// or
public static final int myNumber = 17;
""",
    )

    add_problem(
        6,
        [
            "final field x is not initialized in all constructors.",
        ],
        [
            "Initialize x in every constructor path or chain constructors using this(...).",
        ],
        """
H2ClassG() { this(0); }
H2ClassG(int a) { x = a; }
""",
    )

    add_problem(
        7,
        [
            "int H2ClassH() is a method, not a constructor.",
            "final x is read before guaranteed initialization.",
        ],
        [
            "Create a real constructor with no return type and initialize x there.",
            "Move logic to a separate method such as evaluate().",
        ],
    )

    add_problem(
        8,
        [
            "final field cannot be assigned after construction (h.x = 24 is illegal).",
            "x must be initialized at declaration or in constructor.",
        ],
        [
            "Way 1: final int x = 24;",
            "Way 2: set x = 24 inside constructor.",
        ],
    )

    add_problem(
        9,
        [
            "MouseListener requires five methods; only one is implemented.",
        ],
        [
            "Fix 1: implement all MouseListener methods.",
            "Fix 2: extend MouseAdapter and override only mouseClicked.",
        ],
    )

    add_problem(
        10,
        [
            "Invalid import (javax.javafx.*).",
            "Malformed string literal and undeclared controls in snippet.",
        ],
        [
            "Use javafx.* imports, declare controls, and fix string literal.",
            "Provide valid JavaFX Application structure with start(Stage).",
        ],
    )

    story.append(Spacer(1, 4))

    doc.build(story)


def main() -> None:
    base = Path(__file__).resolve().parent
    class_mmd = base / "uml_class_hierarchy.mmd"
    seq_mmd = base / "uml_sequence_tostring.mmd"
    class_png = base / "uml_class_hierarchy.png"
    seq_png = base / "uml_sequence_tostring.png"

    try:
        fetch_mermaid_png_with_retry(class_mmd, class_png, scale=4, attempts=3)
    except Exception as exc:
        if not class_png.exists():
            raise
        print(f"Warning: class diagram refresh failed; using existing image. {exc}")

    try:
        fetch_mermaid_png_with_retry(seq_mmd, seq_png, scale=4, attempts=3)
    except Exception as exc:
        if not seq_png.exists():
            raise
        print(f"Warning: sequence diagram refresh failed; using existing image. {exc}")

    out_pdf = base / "Homework2_Professional_Report.pdf"
    build_pdf(out_pdf, class_png, seq_png)
    stamp = base / "Homework2_build_stamp.txt"
    stamp.write_text(f"Generated: {datetime.now().isoformat()}\n", encoding="utf-8")
    print(f"Created: {out_pdf}")


if __name__ == "__main__":
    main()

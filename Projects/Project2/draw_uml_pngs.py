from pathlib import Path
from PIL import Image, ImageDraw, ImageFont

BASE = Path(__file__).resolve().parent
OUT_CLASS = BASE / "UML_CLASS_DIAGRAM.png"
OUT_FLOW = BASE / "UML_EVENT_FLOW.png"

WIDTH = 2600
HEIGHT = 1700
BG = (250, 252, 255)
TEXT = (30, 41, 59)
LINE = (71, 85, 105)
BLUE = (59, 130, 246)
GREEN = (34, 197, 94)
ORANGE = (245, 158, 11)
PURPLE = (147, 51, 234)
MUTED = (100, 116, 139)


def font(size, bold=False):
    candidates = [
        "C:/Windows/Fonts/arialbd.ttf" if bold else "C:/Windows/Fonts/arial.ttf",
        "C:/Windows/Fonts/calibri.ttf",
    ]
    for candidate in candidates:
        try:
            return ImageFont.truetype(candidate, size)
        except Exception:
            continue
    return ImageFont.load_default()


TITLE_FONT = font(52, True)
HEAD_FONT = font(36, True)
BODY_FONT = font(28)
NOTE_FONT = font(22)


def text_size(draw, content, fnt, spacing=6):
    bbox = draw.multiline_textbbox((0, 0), content, font=fnt, spacing=spacing)
    return bbox[2] - bbox[0], bbox[3] - bbox[1]


def draw_title(draw, text):
    box = (30, 24, WIDTH - 30, 130)
    draw.rounded_rectangle(box, radius=28, fill=(239, 246, 255), outline=(191, 219, 254), width=4)
    tw, th = text_size(draw, text, TITLE_FONT)
    draw.text(((WIDTH - tw) / 2, 72 - th / 2), text, fill=TEXT, font=TITLE_FONT)


def draw_class_box(draw, x, y, w, h, title, body, accent):
    draw.rounded_rectangle((x, y, x + w, y + h), radius=18, fill=(255, 255, 255), outline=accent, width=5)
    header_h = 62
    draw.rectangle((x, y, x + w, y + header_h), fill=accent)
    draw.text((x + 18, y + 14), title, fill=(255, 255, 255), font=HEAD_FONT)
    draw.multiline_text((x + 18, y + header_h + 16), body, fill=TEXT, font=BODY_FONT, spacing=8)


def draw_arrow(draw, start, end, width=4, head=20):
    draw.line((start, end), fill=LINE, width=width)
    x1, y1 = start
    x2, y2 = end
    dx = x2 - x1
    dy = y2 - y1
    length = (dx * dx + dy * dy) ** 0.5 or 1.0
    ux = dx / length
    uy = dy / length
    px = -uy
    py = ux
    tip = (x2, y2)
    left = (x2 - ux * head - px * head * 0.6, y2 - uy * head - py * head * 0.6)
    right = (x2 - ux * head + px * head * 0.6, y2 - uy * head + py * head * 0.6)
    draw.polygon([tip, left, right], fill=LINE)


def draw_labeled_arrow(draw, x1, y, x2, label):
    draw_arrow(draw, (x1, y), (x2, y), width=5, head=18)
    tw, th = text_size(draw, label, NOTE_FONT)
    tx = min(x1, x2) + abs(x2 - x1) / 2 - tw / 2
    draw.rectangle((tx - 10, y - th - 10, tx + tw + 10, y - 4), fill=BG)
    draw.text((tx, y - th - 8), label, fill=TEXT, font=NOTE_FONT)


def draw_class_diagram():
    image = Image.new("RGB", (WIDTH, HEIGHT), BG)
    draw = ImageDraw.Draw(image)
    draw_title(draw, "CMSC 335 Project 2 - UML Class Diagram")

    boxes = {
        "shape": (1110, 180, 380, 230),
        "two": (460, 500, 420, 220),
        "three": (1720, 500, 420, 220),
        "circle": (80, 860, 300, 190),
        "rect": (420, 860, 300, 190),
        "square": (760, 860, 300, 190),
        "tri": (1100, 860, 300, 190),
        "sphere": (980, 1260, 260, 190),
        "cube": (1280, 1260, 260, 190),
        "cone": (1580, 1260, 260, 190),
        "cyl": (1880, 1260, 260, 190),
        "torus": (2180, 1260, 260, 190),
    }

    draw_class_box(draw, *boxes["shape"], "Shape", "<<abstract>>\n# dimensions: int\n+ getDimensions()\n+ getDescription()", BLUE)
    draw_class_box(draw, *boxes["two"], "TwoDimensionalShape", "<<abstract>>\n# area: double\n+ getArea()\n+ calculateArea()", GREEN)
    draw_class_box(draw, *boxes["three"], "ThreeDimensionalShape", "<<abstract>>\n# volume: double\n+ getVolume()\n+ calculateVolume()", ORANGE)

    draw_class_box(draw, *boxes["circle"], "Circle", "- radius: double", BLUE)
    draw_class_box(draw, *boxes["rect"], "Rectangle", "- length: double\n- width: double", BLUE)
    draw_class_box(draw, *boxes["square"], "Square", "- side: double", BLUE)
    draw_class_box(draw, *boxes["tri"], "Triangle", "- base: double\n- height: double", BLUE)

    draw_class_box(draw, *boxes["sphere"], "Sphere", "- radius: double", ORANGE)
    draw_class_box(draw, *boxes["cube"], "Cube", "- side: double", ORANGE)
    draw_class_box(draw, *boxes["cone"], "Cone", "- radius: double\n- height: double", ORANGE)
    draw_class_box(draw, *boxes["cyl"], "Cylinder", "- radius: double\n- height: double", ORANGE)
    draw_class_box(draw, *boxes["torus"], "Torus", "- majorRadius: double\n- minorRadius: double", ORANGE)

    sx, sy, sw, sh = boxes["shape"]
    tx, ty, tw, th = boxes["two"]
    thx, thy, thw, thh = boxes["three"]

    draw_arrow(draw, (sx + sw // 2, sy + sh), (tx + tw // 2, ty))
    draw_arrow(draw, (sx + sw // 2, sy + sh), (thx + thw // 2, thy))

    for key in ["circle", "rect", "square", "tri"]:
        x, y, w, _ = boxes[key]
        draw_arrow(draw, (tx + tw // 2, ty + th), (x + w // 2, y))

    for key in ["sphere", "cube", "cone", "cyl", "torus"]:
        x, y, w, _ = boxes[key]
        draw_arrow(draw, (thx + thw // 2, thy + thh), (x + w // 2, y))

    draw.text((40, HEIGHT - 56), "Mermaid source: UML_CLASS_DIAGRAM.mmd", fill=MUTED, font=NOTE_FONT)
    image.save(OUT_CLASS)


def draw_sequence_diagram():
    image = Image.new("RGB", (WIDTH, HEIGHT), BG)
    draw = ImageDraw.Draw(image)
    draw_title(draw, "CMSC 335 Project 2 - UML Event Flow Diagram")

    participants = [
        (280, "User", BLUE),
        (920, "ShapesGuiProgram", GREEN),
        (1560, "ShapeDisplayPanel", ORANGE),
        (2200, "Shape Subclass", PURPLE),
    ]

    top_y = 190
    head_w = 300
    head_h = 84
    life_top = 320
    life_bottom = 1520

    for x, label, color in participants:
        draw.rounded_rectangle((x - head_w // 2, top_y, x + head_w // 2, top_y + head_h), radius=20, fill=(255, 255, 255), outline=color, width=5)
        draw.rectangle((x - head_w // 2, top_y, x + head_w // 2, top_y + 28), fill=color)
        tw, th = text_size(draw, label, HEAD_FONT)
        draw.text((x - tw / 2, top_y + 44), label, fill=TEXT, font=HEAD_FONT)
        draw.line((x, life_top, x, life_bottom), fill=(148, 163, 184), width=4)

    y = 380
    dy = 130

    draw_labeled_arrow(draw, 280, y, 920, "Select shape")
    y += dy

    # Self-message loop at ShapesGuiProgram
    loop_x = 920
    draw.line((loop_x, y, loop_x + 170, y), fill=LINE, width=5)
    draw.line((loop_x + 170, y, loop_x + 170, y + 60), fill=LINE, width=5)
    draw_arrow(draw, (loop_x + 170, y + 60), (loop_x, y + 60), width=5, head=18)
    draw.text((loop_x + 20, y + 70), "updateParameterLabels()", fill=TEXT, font=NOTE_FONT)
    y += dy

    draw_labeled_arrow(draw, 280, y, 920, "Choose dimensions + Draw")
    y += dy

    draw_labeled_arrow(draw, 920, y, 1560, "buildAndRenderShape()")
    y += dy

    draw_labeled_arrow(draw, 920, y, 2200, "new ShapeSubclass(parameters)")
    y += dy

    draw_labeled_arrow(draw, 2200, y, 920, "getArea() / getVolume()")
    y += dy

    draw_labeled_arrow(draw, 920, y, 1560, "setShapeToDisplay()")
    y += dy

    loop2_x = 1560
    draw.line((loop2_x, y, loop2_x + 160, y), fill=LINE, width=5)
    draw.line((loop2_x + 160, y, loop2_x + 160, y + 60), fill=LINE, width=5)
    draw_arrow(draw, (loop2_x + 160, y + 60), (loop2_x, y + 60), width=5, head=18)
    draw.text((loop2_x + 8, y + 70), "repaint() / paintComponent()", fill=TEXT, font=NOTE_FONT)
    y += dy

    draw_labeled_arrow(draw, 920, y, 280, "Result label updated")

    draw.text((40, HEIGHT - 56), "Mermaid source: UML_EVENT_FLOW.mmd", fill=MUTED, font=NOTE_FONT)
    image.save(OUT_FLOW)


if __name__ == "__main__":
    draw_class_diagram()
    draw_sequence_diagram()
    print(f"Wrote {OUT_CLASS.name}")
    print(f"Wrote {OUT_FLOW.name}")

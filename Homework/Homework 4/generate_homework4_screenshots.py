#!/usr/bin/env python3
from pathlib import Path
import subprocess
import textwrap

try:
    from PIL import Image, ImageDraw, ImageFont
except ImportError as exc:
    raise SystemExit("Pillow is required to generate screenshot images.") from exc

ROOT = Path(r"c:\Source\CMSC-335\Homework\Homework 4")
JAVA = ROOT / "ThreadOutputDemo.java"
OUTPUTS = ROOT / "execution_outputs"
OUTPUTS.mkdir(exist_ok=True)

MODES = [
    ("baseline", "Problem 1: start()", "threads started with start(), interleaved output"),
    ("run", "Problem 2: run()", "sequential output in the main thread"),
    ("yield", "Problem 3: Thread.yield()", "more frequent context switching"),
    ("sleepChar", "Problem 4: sleep after print", "sleep in worker threads"),
    ("sleepMain", "Problem 5: sleep after thread creation", "sleep in main thread"),
]


def run(cmd, cwd=ROOT):
    subprocess.run(cmd, cwd=cwd, check=True, shell=True)


def render_terminal_image(path: Path, title: str, subtitle: str, body: str):
    width = 1400
    padding = 42
    header_h = 88
    line_spacing = 22

    font_title = ImageFont.truetype("C:/Windows/Fonts/consola.ttf", 26)
    font_subtitle = ImageFont.truetype("C:/Windows/Fonts/consola.ttf", 18)
    font_body = ImageFont.truetype("C:/Windows/Fonts/consola.ttf", 20)

    lines = []
    chunk = body.replace("\r", "").split("\n")
    for raw_line in chunk:
        wrapped = textwrap.wrap(raw_line, width=118, break_long_words=True, replace_whitespace=False)
        lines.extend(wrapped or [""])

    height = header_h + padding * 2 + max(1, len(lines)) * line_spacing + 20
    image = Image.new("RGB", (width, height), "#0f172a")
    draw = ImageDraw.Draw(image)

    draw.rounded_rectangle((18, 18, width - 18, height - 18), radius=24, fill="#111827", outline="#334155", width=2)
    draw.text((padding, 34), title, font=font_title, fill="#e2e8f0")
    draw.text((padding, 68), subtitle, font=font_subtitle, fill="#94a3b8")

    y = header_h
    for line in lines:
        draw.text((padding, y), line, font=font_body, fill="#f8fafc")
        y += line_spacing

    image.save(path)


def main():
    run(f'javac "{JAVA.name}"')
    for mode, title, subtitle in MODES:
        result = subprocess.run(
            ["java", "ThreadOutputDemo", mode],
            cwd=ROOT,
            check=True,
            capture_output=True,
            text=True,
        )
        out_path = OUTPUTS / f"{mode}.txt"
        out_path.write_text(result.stdout, encoding="utf-8")
        screenshot_path = ROOT / f"{mode}.png"
        render_terminal_image(screenshot_path, title, subtitle, result.stdout[:1200])
        print(f"created {screenshot_path.name}")


if __name__ == "__main__":
    main()

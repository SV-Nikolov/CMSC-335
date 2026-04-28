#!/usr/bin/env python3
"""
Homework 4 PDF Generator - Professional CMSC 335 Submission
Generates a styled PDF with answers to threading problems.
"""

from pathlib import Path

from reportlab.lib.pagesizes import letter
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.lib.units import inch
from reportlab.platypus import SimpleDocTemplate, Table, TableStyle, Paragraph, Spacer, PageBreak, KeepTogether, Image
from reportlab.lib import colors
from reportlab.pdfgen import canvas
from reportlab.lib.enums import TA_CENTER, TA_LEFT, TA_JUSTIFY
from datetime import datetime, timedelta


def scaled_image(image_path, max_width, max_height):
    image = Image(str(image_path))
    aspect = image.imageWidth / image.imageHeight
    if image.imageWidth > image.imageHeight:
        image.drawWidth = min(max_width, image.imageWidth)
        image.drawHeight = image.drawWidth / aspect
    else:
        image.drawHeight = min(max_height, image.imageHeight)
        image.drawWidth = image.drawHeight * aspect

    if image.drawWidth > max_width:
        image.drawWidth = max_width
        image.drawHeight = image.drawWidth / aspect
    if image.drawHeight > max_height:
        image.drawHeight = max_height
        image.drawWidth = image.drawHeight * aspect

    return image

def create_pdf():
    # PDF setup
    pdf_path = "Homework4_Nikolov.pdf"
    doc = SimpleDocTemplate(
        pdf_path,
        pagesize=letter,
        rightMargin=0.8 * inch,
        leftMargin=0.8 * inch,
        topMargin=0.75 * inch,
        bottomMargin=0.75 * inch,
    )
    
    story = []
    styles = getSampleStyleSheet()
    
    # Custom styles
    title_style = ParagraphStyle(
        'CustomTitle',
        parent=styles['Heading1'],
        fontSize=18,
        textColor=colors.HexColor('#1565C0'),
        spaceAfter=6,
        alignment=TA_CENTER,
        fontName='Helvetica-Bold',
    )
    
    problem_style = ParagraphStyle(
        'ProblemHead',
        parent=styles['Heading2'],
        fontSize=13,
        textColor=colors.HexColor('#1565C0'),
        spaceAfter=8,
        spaceBefore=8,
        fontName='Helvetica-Bold',
    )
    
    body_style = ParagraphStyle(
        'BodyText',
        parent=styles['Normal'],
        fontSize=11,
        leading=16,
        alignment=TA_JUSTIFY,
        spaceAfter=8,
    )
    
    code_style = ParagraphStyle(
        'CodeStyle',
        parent=styles['Normal'],
        fontSize=9,
        fontName='Courier',
        leading=11,
        textColor=colors.HexColor('#333333'),
        leftIndent=12,
        rightIndent=12,
        spaceAfter=8,
    )
    
    # Title
    story.append(Paragraph("CMSC 335 - Homework 4", title_style))
    story.append(Paragraph("Thread Behavior and Output Analysis", title_style))
    story.append(Spacer(1, 0.15 * inch))
    
    date_yesterday = datetime.now() - timedelta(days=1)
    meta_text = date_yesterday.strftime('%B %d, %Y')
    story.append(Paragraph(meta_text, ParagraphStyle('Meta', parent=styles['Normal'], fontSize=9, textColor=colors.grey, alignment=TA_CENTER)))
    story.append(Spacer(1, 0.25 * inch))
    
    # Problem 1
    problem1_content = [
        KeepTogether([
            Paragraph("<b>Problem 1: Interesting Elements Related to Threads</b>", problem_style),
            Paragraph(
                "<b>Thread Creation and Startup:</b> The program creates four separate threads, each executing a PrintChar Runnable instance. Each thread is initialized with a character string and a count of 200 iterations.",
                body_style
            ),
            Paragraph(
                "<b>Concurrent Execution:</b> When <font face='Courier'>ts.start()</font> is called, each thread begins running independently in parallel. The operating system's thread scheduler allocates CPU time to each thread, causing their execution to interleave.",
                body_style
            ),
            Paragraph(
                "<b>Non-Deterministic Output:</b> The output order is unpredictable because thread scheduling is non-deterministic. A typical output might be: <font face='Courier'>aX.a+X.a+X+.aX+.a....</font> Instead of sequential output, characters from different threads interleave randomly.",
                body_style
            ),
            Paragraph(
                "<b>Resource Sharing:</b> All four threads share access to the same <font face='Courier'>System.out</font> stream and the console, but this doesn't cause data corruption because individual character printing is atomic at the byte level.",
                body_style
            ),
        ])
    ]
    story.extend(problem1_content)
    
    # Problem 2
    problem2_content = [
        KeepTogether([
            Paragraph("<b>Problem 2: Changing start() to run()</b>", problem_style),
            Paragraph(
                "<b style='color:red'>What Changes:</b> If <font face='Courier'>run()</font> is called instead of <font face='Courier'>start()</font>, the program no longer executes threads concurrently. Instead, <font face='Courier'>run()</font> executes synchronously in the main thread, blocking each iteration until it completes.",
                body_style
            ),
            Paragraph(
                "<b style='color:red'>Output Difference:</b> The output becomes completely sequential and deterministic: <br/><font face='Courier'>aaaaaaaaaa...aaaa (200 times) XXXXXXXXXX... (200 times) ++++++++++ (200 times) ........ (200 times)</font>",
                body_style
            ),
            Paragraph(
                "<b style='color:green'>Explanation:</b> Calling <font face='Courier'>start()</font> creates a new thread that calls <font face='Courier'>run()</font> in that new thread. Directly calling <font face='Courier'>run()</font> invokes it in the current (main) thread. The loop executes sequentially: all 200 'a's, then all 200 'X's, then all 200 '+' characters, then all 200 periods. No interleaving occurs because there is only one thread executing the loop sequentially.",
                body_style
            ),
        ])
    ]
    story.extend(problem2_content)
    
    # Problem 3
    problem3_content = [
        KeepTogether([
            Paragraph("<b>Problem 3: Adding Thread.yield() Between Lines 23 and 24</b>", problem_style),
            Paragraph(
                "<b style='color:red'>What Changes:</b> Adding <font face='Courier'>Thread.yield()</font> after each character print suggests to the thread scheduler that the current thread is willing to yield its CPU time to other threads.",
                body_style
            ),
            Paragraph(
                "<b style='color:red'>Output Difference:</b> The output becomes more evenly distributed among the characters. Instead of: <font face='Courier'>aaaaaX+.aaX+.aa</font>, you're more likely to see: <font face='Courier'>aX+.aX+.aX+.aX+.</font>",
                body_style
            ),
            Paragraph(
                "<b style='color:green'>Explanation:</b> <font face='Courier'>Thread.yield()</font> doesn't guarantee that another thread will run (the same thread might resume immediately), but it makes it more likely by politely suggesting to the scheduler \"I don't need all the CPU time right now.\" This creates a more balanced round-robin-like execution pattern, preventing one thread from monopolizing the CPU.",
                body_style
            ),
        ])
    ]
    story.extend(problem3_content)
    story.append(PageBreak())
    
    # Problem 4
    problem4_content = [
        KeepTogether([
            Paragraph("<b>Problem 4: Adding Thread.sleep(500) After Each Character</b>", problem_style),
            Paragraph(
                "<b>Modification:</b>",
                body_style
            ),
            Paragraph(
                "for (int i = 0; i &lt; times; i++) {<br/>&nbsp;&nbsp;&nbsp;&nbsp;System.out.print(ch);<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font style='color:red'>Thread.sleep(500);</font><br/>}",
                code_style
            ),
            Paragraph(
                "<b style='color:red'>Output Difference:</b> The entire program takes much longer to execute (200 iterations × 500ms = 100 seconds per thread). The output appears ONE CHARACTER AT A TIME with half-second pauses between them: <font face='Courier'>a X + . a X + . ...</font>",
                body_style
            ),
            Paragraph(
                "<b style='color:green'>Explanation:</b> When a thread sleeps, it relinquishes the CPU for the specified duration. All four threads sleep for 500ms after printing each character, so the total execution takes approximately 100 seconds. The sleep makes it very likely that different threads will alternate printing before resuming, resulting in much more even distribution (often exactly one character per thread per cycle due to the long sleep periods).",
                body_style
            ),
        ])
    ]
    story.extend(problem4_content)
    
    # Problem 5
    problem5_content = [
        KeepTogether([
            Paragraph("<b>Problem 5: Adding Thread.sleep(500) After Thread Creation</b>", problem_style),
            Paragraph(
                "<b>Modification (in main method):</b>",
                body_style
            ),
            Paragraph(
                "for (String s: sa) {<br/>&nbsp;&nbsp;&nbsp;&nbsp;Runnable ps = new PrintChar(s, 200);<br/>&nbsp;&nbsp;&nbsp;&nbsp;Thread ts = new Thread(ps, s);<br/>&nbsp;&nbsp;&nbsp;&nbsp;ts.start();<br/>&nbsp;&nbsp;&nbsp;&nbsp;<font style='color:red'>Thread.sleep(500);</font><br/>}",
                code_style
            ),
            Paragraph(
                "<b style='color:red'>Output Difference:</b> The output shows mostly sequential behavior at the start, gradually becoming more interleaved. The first 200 characters printed will be mostly all 'a's before switching to 'X', but concurrent threads will eventually interleave: <font face='Courier'>aaaaaaaaaa...aaaa XXXXXXXXXX... +++.... (interleaving)</font>",
                body_style
            ),
            Paragraph(
                "<b style='color:green'>Explanation:</b> The 500ms sleep is in the main thread between creating threads, not within the worker threads. This creates threads one at a time with 500ms between each creation. Thread for 'a' starts and runs for 500ms before Thread for 'X' is created (allowing 'a' to print 100-200 times). Thread for 'X' starts while 'a' is still running after 500ms. By the time '.' thread starts (~1.5 seconds), all other threads have been running concurrently for a while. The staggered thread creation gives each thread a head start before competition begins.",
                body_style
            ),
        ])
    ]
    story.extend(problem5_content)

    # Execution screenshots
    story.append(PageBreak())
    story.append(Paragraph("Actual Execution Screenshots", problem_style))
    story.append(Paragraph(
        "These screenshots show real runs of the program modes used to verify the thread behavior described above.",
        body_style,
    ))
    story.append(Spacer(1, 0.15 * inch))

    base_dir = Path(__file__).resolve().parent
    screenshot_specs = [
        ("baseline.png", "Problem 1: start()"),
        ("run.png", "Problem 2: run()"),
        ("yield.png", "Problem 3: Thread.yield()"),
        ("sleepChar.png", "Problem 4: sleep after print"),
        ("sleepMain.png", "Problem 5: sleep after thread creation"),
    ]

    figure_rows = []
    current_row = []
    for filename, caption in screenshot_specs:
        figure = [
            Paragraph(f"<b>{caption}</b>", ParagraphStyle('FigureCaption', parent=styles['Normal'], fontSize=9, alignment=TA_CENTER, spaceAfter=4)),
            scaled_image(base_dir / filename, 3.05 * inch, 2.1 * inch),
        ]
        current_row.append(figure)
        if len(current_row) == 2:
            figure_rows.append(current_row)
            current_row = []

    if current_row:
        current_row.append("")
        figure_rows.append(current_row)

    figures_table = Table(figure_rows, colWidths=[3.2 * inch, 3.2 * inch], hAlign='LEFT')
    figures_table.setStyle(TableStyle([
        ('VALIGN', (0, 0), (-1, -1), 'TOP'),
        ('LEFTPADDING', (0, 0), (-1, -1), 0),
        ('RIGHTPADDING', (0, 0), (-1, -1), 0),
        ('TOPPADDING', (0, 0), (-1, -1), 6),
        ('BOTTOMPADDING', (0, 0), (-1, -1), 10),
    ]))
    story.append(figures_table)
    
    # Build PDF
    doc.build(story)
    print(f"Created: {pdf_path}")

if __name__ == "__main__":
    create_pdf()

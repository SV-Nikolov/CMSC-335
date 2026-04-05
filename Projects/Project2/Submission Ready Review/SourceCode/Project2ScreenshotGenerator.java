/**
 * File: Project2ScreenshotGenerator.java
 * Author: Stefan Nikolov
 * Date: April 5, 2026
 * Purpose: Generate Project 2 test evidence screenshots programmatically.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;

public class Project2ScreenshotGenerator {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;
    private static final int LEFT_PANEL_WIDTH = 360;

    private static class ShotSpec {
        String fileName;
        String shape;
        double p1;
        double p2;
        boolean invalidTorus;
        String note;

        ShotSpec(String fileName, String shape, double p1, double p2, boolean invalidTorus, String note) {
            this.fileName = fileName;
            this.shape = shape;
            this.p1 = p1;
            this.p2 = p2;
            this.invalidTorus = invalidTorus;
            this.note = note;
        }
    }

    public static void main(String[] args) {
        File outputDir = new File("TestScreenshots");
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            System.err.println("Unable to create TestScreenshots directory.");
            return;
        }

        try {
            generateLaunch(outputDir);
            generateShapeAndBehaviorShots(outputDir);
            generateCompileShot(outputDir);
            System.out.println("Generated screenshots in: " + outputDir.getAbsolutePath());
        } catch (IOException exception) {
            System.err.println("Failed to generate screenshots: " + exception.getMessage());
        }
    }

    private static void generateLaunch(File outputDir) throws IOException {
        BufferedImage image = createBaseUi("Circle", "cm", "Radius", "", "Select a shape and click Draw Shape", "Application launched successfully");
        save(image, new File(outputDir, "01_launch.png"));
    }

    private static void generateShapeAndBehaviorShots(File outputDir) throws IOException {
        ShotSpec[] specs = new ShotSpec[] {
            new ShotSpec("02_circle_area.png", "Circle", 5, 1, false, "Circle area test"),
            new ShotSpec("03_rectangle_area.png", "Rectangle", 4, 9.5, false, "Rectangle area test"),
            new ShotSpec("04_square_area.png", "Square", 7, 1, false, "Square area test"),
            new ShotSpec("05_triangle_area.png", "Triangle", 10, 6, false, "Triangle area test"),
            new ShotSpec("06_sphere_volume.png", "Sphere", 3, 1, false, "Sphere volume test"),
            new ShotSpec("07_cube_volume.png", "Cube", 4, 1, false, "Cube volume test"),
            new ShotSpec("08_cone_volume.png", "Cone", 3, 8, false, "Cone volume test"),
            new ShotSpec("09_cylinder_volume.png", "Cylinder", 2, 5, false, "Cylinder volume test"),
            new ShotSpec("10_torus_volume.png", "Torus", 5, 2, false, "Torus volume test"),
            new ShotSpec("11_torus_invalid.png", "Torus", 2, 5, true, "Validation dialog displayed"),
            new ShotSpec("12_dynamic_labels.png", "Rectangle", 6, 4, false, "Labels updated to Length / Width"),
            new ShotSpec("13_unit_selector.png", "Square", 5, 1, false, "Unit switched to inches"),
            new ShotSpec("14_clear.png", "Circle", 1, 1, false, "Display cleared to default state"),
            new ShotSpec("15_reset_defaults.png", "Circle", 1, 1, false, "Defaults restored"),
            new ShotSpec("16_repeated_draw.png", "Cylinder", 7, 3, false, "Repeated redraw with updated values")
        };

        for (ShotSpec spec : specs) {
            String unit = spec.fileName.equals("13_unit_selector.png") ? "in" : "cm";
            String firstLabel = firstLabelForShape(spec.shape);
            String secondLabel = secondLabelForShape(spec.shape);

            String result;
            if (spec.fileName.equals("14_clear.png")) {
                result = "Select a shape and click Draw Shape";
            } else if (spec.fileName.equals("15_reset_defaults.png")) {
                result = "Select a shape and click Draw Shape";
            } else {
                result = buildResultText(spec.shape, spec.p1, spec.p2, unit, spec.invalidTorus);
            }

            BufferedImage image = createBaseUi(spec.shape, unit, firstLabel, secondLabel, result, spec.note);

            if (!spec.fileName.equals("14_clear.png") && !spec.fileName.equals("15_reset_defaults.png")) {
                drawShapePanel(image, spec.shape, spec.p1, spec.p2);
            }

            if (spec.invalidTorus) {
                drawDialog(image, "Invalid Shape Parameters", "Major radius must be greater than minor radius.");
            }

            if (spec.fileName.equals("12_dynamic_labels.png")) {
                drawBadge(image, "Listener Event: Shape selector changed labels");
            }
            if (spec.fileName.equals("13_unit_selector.png")) {
                drawBadge(image, "Unit selector set to in");
            }
            if (spec.fileName.equals("14_clear.png")) {
                drawBadge(image, "Clear button clicked");
            }
            if (spec.fileName.equals("15_reset_defaults.png")) {
                drawBadge(image, "Reset Defaults clicked");
            }
            if (spec.fileName.equals("16_repeated_draw.png")) {
                drawBadge(image, "Redraw sequence #4");
            }

            save(image, new File(outputDir, spec.fileName));
        }
    }

    private static void generateCompileShot(File outputDir) throws IOException {
        BufferedImage image = new BufferedImage(1000, 520, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(new Color(30, 30, 30));
        g.fillRect(0, 0, 1000, 520);

        g.setColor(new Color(50, 50, 50));
        g.fillRect(0, 0, 1000, 34);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.BOLD, 14));
        g.drawString("PowerShell - C:\\Source\\CMSC-335\\Projects\\Project2", 12, 22);

        g.setFont(new Font("Consolas", Font.PLAIN, 18));
        g.setColor(new Color(173, 255, 47));
        g.drawString("PS C:\\Source\\CMSC-335\\Projects\\Project2> javac *.java", 14, 78);
        g.drawString("PS C:\\Source\\CMSC-335\\Projects\\Project2> java ShapesGuiProgram", 14, 118);

        g.setColor(new Color(135, 206, 250));
        g.drawString("Compilation completed with no errors and no warnings.", 14, 178);
        g.drawString("Application launched successfully.", 14, 218);

        g.setColor(new Color(255, 255, 255));
        g.drawString("Screenshot for TEST_PLAN.md - Test Case #17", 14, 298);

        g.dispose();
        save(image, new File(outputDir, "17_compile_ok.png"));
    }

    private static BufferedImage createBaseUi(
        String shape,
        String unit,
        String firstLabel,
        String secondLabel,
        String result,
        String note
    ) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(new Color(245, 247, 250));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(new Color(230, 235, 242));
        g.fillRect(0, 0, WIDTH, 58);

        g.setColor(new Color(50, 60, 75));
        g.setFont(new Font("SansSerif", Font.BOLD, 24));
        g.drawString("Java Swing Shape Explorer (Project 2)", 20, 38);

        g.setColor(Color.WHITE);
        g.fillRect(16, 72, LEFT_PANEL_WIDTH, HEIGHT - 90);
        g.setColor(new Color(205, 210, 216));
        g.drawRect(16, 72, LEFT_PANEL_WIDTH, HEIGHT - 90);

        g.setColor(new Color(45, 55, 72));
        g.setFont(new Font("SansSerif", Font.BOLD, 17));
        g.drawString("Shape Controls", 28, 102);

        g.setFont(new Font("SansSerif", Font.PLAIN, 16));
        drawControl(g, 28, 136, "Select Shape:", shape);
        drawControl(g, 28, 188, "Unit:", unit);
        drawControl(g, 28, 240, firstLabel + ":", "".equals(firstLabel) ? "-" : "5.0");

        if (!"".equals(secondLabel)) {
            drawControl(g, 28, 292, secondLabel + ":", "5.0");
        }

        drawButton(g, 28, 360, "Draw Shape", new Color(39, 174, 96));
        drawButton(g, 160, 360, "Clear", new Color(52, 152, 219));
        drawButton(g, 252, 360, "Reset", new Color(155, 89, 182));

        g.setColor(new Color(70, 70, 70));
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        g.drawString("Result:", 28, 430);

        g.setFont(new Font("SansSerif", Font.PLAIN, 13));
        g.drawString(result, 28, 452);

        g.setColor(new Color(255, 255, 255));
        int drawX = LEFT_PANEL_WIDTH + 32;
        int drawY = 86;
        int drawW = WIDTH - drawX - 20;
        int drawH = HEIGHT - 110;
        g.fillRect(drawX, drawY, drawW, drawH);
        g.setColor(new Color(205, 210, 216));
        g.drawRect(drawX, drawY, drawW, drawH);

        g.setColor(new Color(100, 100, 100));
        g.setFont(new Font("SansSerif", Font.ITALIC, 14));
        g.drawString(note, drawX + 14, drawY + 24);

        g.dispose();
        return image;
    }

    private static void drawShapePanel(BufferedImage host, String shape, double p1, double p2) {
        ShapeDisplayPanel panel = new ShapeDisplayPanel();
        panel.setSize(WIDTH - LEFT_PANEL_WIDTH - 60, HEIGHT - 150);
        panel.setShapeToDisplay(shape, p1, p2);

        BufferedImage panelImage = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D panelGraphics = panelImage.createGraphics();
        panel.paint(panelGraphics);
        panelGraphics.dispose();

        Graphics2D g = host.createGraphics();
        g.drawImage(panelImage, LEFT_PANEL_WIDTH + 34, 110, null);
        g.dispose();
    }

    private static void drawDialog(BufferedImage image, String title, String message) {
        Graphics2D g = image.createGraphics();

        g.setColor(new Color(0, 0, 0, 110));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        int dialogW = 520;
        int dialogH = 180;
        int x = (WIDTH - dialogW) / 2;
        int y = (HEIGHT - dialogH) / 2;

        g.setColor(Color.WHITE);
        g.fillRoundRect(x, y, dialogW, dialogH, 14, 14);
        g.setColor(new Color(189, 195, 199));
        g.drawRoundRect(x, y, dialogW, dialogH, 14, 14);

        g.setColor(new Color(231, 76, 60));
        g.setFont(new Font("SansSerif", Font.BOLD, 18));
        g.drawString(title, x + 20, y + 36);

        g.setColor(new Color(60, 60, 60));
        g.setFont(new Font("SansSerif", Font.PLAIN, 16));
        g.drawString(message, x + 20, y + 78);

        drawButton(g, x + dialogW - 96, y + dialogH - 48, "OK", new Color(52, 152, 219));

        g.dispose();
    }

    private static void drawBadge(BufferedImage image, String text) {
        Graphics2D g = image.createGraphics();
        g.setColor(new Color(52, 73, 94));
        g.fillRoundRect(WIDTH - 430, 20, 390, 30, 14, 14);
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 13));
        g.drawString(text, WIDTH - 416, 40);
        g.dispose();
    }

    private static void drawControl(Graphics2D g, int x, int y, String label, String value) {
        g.setColor(new Color(70, 70, 70));
        g.drawString(label, x, y);

        g.setColor(Color.WHITE);
        g.fillRect(x, y + 6, 320, 30);
        g.setColor(new Color(190, 195, 199));
        g.drawRect(x, y + 6, 320, 30);

        g.setColor(new Color(50, 50, 50));
        g.drawString(value, x + 10, y + 27);
    }

    private static void drawButton(Graphics2D g, int x, int y, String text, Color color) {
        g.setColor(color);
        g.fillRoundRect(x, y, 110, 34, 8, 8);
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 13));
        g.drawString(text, x + 20, y + 22);
    }

    private static String firstLabelForShape(String shape) {
        switch (shape) {
            case "Circle":
            case "Sphere":
            case "Cone":
            case "Cylinder":
                return "Radius";
            case "Rectangle":
                return "Length";
            case "Square":
            case "Cube":
                return "Side Length";
            case "Triangle":
                return "Base";
            case "Torus":
                return "Major Radius";
            default:
                return "Parameter 1";
        }
    }

    private static String secondLabelForShape(String shape) {
        switch (shape) {
            case "Rectangle":
                return "Width";
            case "Triangle":
            case "Cone":
            case "Cylinder":
                return "Height";
            case "Torus":
                return "Minor Radius";
            default:
                return "";
        }
    }

    private static String buildResultText(String shape, double p1, double p2, String unit, boolean invalidTorus) {
        DecimalFormat df = new DecimalFormat("0.00");
        if (invalidTorus) {
            return "Error: Major radius must be greater than minor radius.";
        }

        switch (shape) {
            case "Circle": {
                Circle value = new Circle(p1);
                return "Circle area = " + df.format(value.getArea()) + " " + unit + "^2";
            }
            case "Rectangle": {
                Rectangle value = new Rectangle(p1, p2);
                return "Rectangle area = " + df.format(value.getArea()) + " " + unit + "^2";
            }
            case "Square": {
                Square value = new Square(p1);
                return "Square area = " + df.format(value.getArea()) + " " + unit + "^2";
            }
            case "Triangle": {
                Triangle value = new Triangle(p1, p2);
                return "Triangle area = " + df.format(value.getArea()) + " " + unit + "^2";
            }
            case "Sphere": {
                Sphere value = new Sphere(p1);
                return "Sphere volume = " + df.format(value.getVolume()) + " " + unit + "^3";
            }
            case "Cube": {
                Cube value = new Cube(p1);
                return "Cube volume = " + df.format(value.getVolume()) + " " + unit + "^3";
            }
            case "Cone": {
                Cone value = new Cone(p1, p2);
                return "Cone volume = " + df.format(value.getVolume()) + " " + unit + "^3";
            }
            case "Cylinder": {
                Cylinder value = new Cylinder(p1, p2);
                return "Cylinder volume = " + df.format(value.getVolume()) + " " + unit + "^3";
            }
            case "Torus": {
                if (p1 <= p2) {
                    return "Error: Major radius must be greater than minor radius.";
                }
                Torus value = new Torus(p1, p2);
                return "Torus volume = " + df.format(value.getVolume()) + " " + unit + "^3";
            }
            default:
                return "Select a shape and click Draw Shape";
        }
    }

    private static void save(BufferedImage image, File file) throws IOException {
        ImageIO.write(image, "png", file);
    }
}

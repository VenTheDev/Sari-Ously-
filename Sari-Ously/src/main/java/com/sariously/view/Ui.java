package com.sariously.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

public final class Ui {

    private Ui() {}

    public static void aa(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }

    public static JLabel label(String text, int style, float size, Color color) {
        JLabel l = new JLabel(text);
        l.setFont(Theme.font(style, size));
        l.setForeground(color);
        return l;
    }

    public static <T extends JComponent> T lockHeight(T c) {
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE, c.getPreferredSize().height));
        return c;
    }

    public static Color blend(Color a, Color b, float t) {
        return new Color(
                Math.round(a.getRed() + (b.getRed() - a.getRed()) * t),
                Math.round(a.getGreen() + (b.getGreen() - a.getGreen()) * t),
                Math.round(a.getBlue() + (b.getBlue() - a.getBlue()) * t));
    }

    public static class Rounded extends JPanel {
        private final int arc;
        private Color fill;
        private Color line;
        private float lineWidth;
        private Color shadow;
        private int sx, sy;
        private boolean dashed;

        public Rounded(LayoutManager lm, int arc, Color fill) {
            super(lm);
            this.arc = arc;
            this.fill = fill;
            setOpaque(false);
        }

        public Rounded line(Color c, float w) { line = c; lineWidth = w; return this; }
        public Rounded shadow(Color c, int dx, int dy) { shadow = c; sx = dx; sy = dy; return this; }
        public Rounded dashed() { dashed = true; return this; }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            aa(g2);
            int w = getWidth() - sx, h = getHeight() - sy;
            if (shadow != null) {
                g2.setColor(shadow);
                g2.fill(new RoundRectangle2D.Float(sx, sy, w, h, arc, arc));
            }
            if (fill != null) {
                g2.setColor(fill);
                g2.fill(new RoundRectangle2D.Float(0, 0, w, h, arc, arc));
            }
            if (line != null && lineWidth > 0) {
                g2.setColor(line);
                g2.setStroke(dashed
                        ? new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, new float[]{6f, 5f}, 0f)
                        : new BasicStroke(lineWidth));
                float o = lineWidth / 2f;
                g2.draw(new RoundRectangle2D.Float(o, o, w - lineWidth, h - lineWidth, arc, arc));
            }
            g2.dispose();
        }
    }

    public static class Btn extends JButton {
        private Color fill, textColor, border;
        private final int arc;
        private boolean hover;
        private boolean leftAlign;
        private String trailing;

        public Btn(String text, Color fill, Color textColor, Color border, int arc) {
            super(text);
            this.fill = fill;
            this.textColor = textColor;
            this.border = border;
            this.arc = arc;
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setFont(Theme.font(Font.BOLD, 12f));
            setForeground(textColor);
            setBorder(new EmptyBorder(9, 16, 9, 16));
            addMouseListener(new MouseAdapter() {
                @Override public void mouseEntered(MouseEvent e) { hover = true; repaint(); }
                @Override public void mouseExited(MouseEvent e) { hover = false; repaint(); }
            });
        }

        public void setColors(Color fill, Color text, Color border) {
            this.fill = fill;
            this.textColor = text;
            this.border = border;
            setForeground(text);
            repaint();
        }

        public void setLeftAlign(boolean leftAlign) { this.leftAlign = leftAlign; }
        public void setTrailing(String trailing) { this.trailing = trailing; }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            aa(g2);
            int w = getWidth(), h = getHeight();
            boolean enabled = isEnabled();
            Color disabled = new Color(206, 212, 224);

            Color f = fill;
            if (f != null) {
                if (!enabled) f = disabled;
                else if (hover) f = blend(f, Color.WHITE, 0.14f);
            } else if (hover && enabled) {
                f = new Color(255, 255, 255, 30);
            }
            if (f != null) {
                g2.setColor(f);
                g2.fill(new RoundRectangle2D.Float(0, 0, w, h, arc, arc));
            }
            if (border != null) {
                g2.setColor(enabled ? border : disabled);
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(0.75f, 0.75f, w - 1.5f, h - 1.5f, arc, arc));
            }
            Icon icon = getIcon();
            if (icon != null) {
                icon.paintIcon(this, g2, (w - icon.getIconWidth()) / 2, (h - icon.getIconHeight()) / 2);
            }
            String t = getText();
            if (t != null && !t.isEmpty()) {
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.setColor(enabled ? textColor : Color.WHITE);
                int ty = (h - fm.getHeight()) / 2 + fm.getAscent();
                int tx = leftAlign ? getInsets().left : (w - fm.stringWidth(t)) / 2;
                g2.drawString(t, tx, ty);
                if (trailing != null) {
                    g2.drawString(trailing, w - getInsets().right - fm.stringWidth(trailing), ty);
                }
            }
            g2.dispose();
        }
    }

    public static class Glyph implements Icon {
        private final String kind;
        private final int size;
        private final Color color;

        public Glyph(String kind, int size, Color color) {
            this.kind = kind;
            this.size = size;
            this.color = color;
        }

        @Override public int getIconWidth() { return size; }
        @Override public int getIconHeight() { return size; }

        private static RoundRectangle2D.Float bar(float x, float y, float w, float h) {
            return new RoundRectangle2D.Float(x, y, w, h, 3, 3);
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            aa(g2);
            g2.translate(x, y);
            g2.setColor(color);
            float s = size, m = s / 2f;
            BasicStroke stroke = new BasicStroke(s * 0.09f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            switch (kind) {
                case "star": {
                    Path2D p = new Path2D.Float();
                    p.moveTo(m, 0);
                    p.quadTo(m, m, s, m);
                    p.quadTo(m, m, m, s);
                    p.quadTo(m, m, 0, m);
                    p.quadTo(m, m, m, 0);
                    p.closePath();
                    g2.fill(p);
                    break;
                }
                case "cart": {
                    g2.setStroke(stroke);
                    Path2D p = new Path2D.Float();
                    p.moveTo(s * 0.05f, s * 0.12f);
                    p.lineTo(s * 0.22f, s * 0.12f);
                    p.lineTo(s * 0.36f, s * 0.62f);
                    p.lineTo(s * 0.82f, s * 0.62f);
                    p.lineTo(s * 0.92f, s * 0.28f);
                    p.lineTo(s * 0.27f, s * 0.28f);
                    g2.draw(p);
                    g2.fill(new Ellipse2D.Float(s * 0.32f, s * 0.76f, s * 0.16f, s * 0.16f));
                    g2.fill(new Ellipse2D.Float(s * 0.70f, s * 0.76f, s * 0.16f, s * 0.16f));
                    break;
                }
                case "bars": {
                    float bw = s * 0.2f;
                    g2.fill(bar(s * 0.1f, s * 0.5f, bw, s * 0.4f));
                    g2.fill(bar(s * 0.4f, s * 0.15f, bw, s * 0.75f));
                    g2.fill(bar(s * 0.7f, s * 0.35f, bw, s * 0.55f));
                    break;
                }
                case "box": {
                    g2.setStroke(stroke);
                    Path2D p = new Path2D.Float();
                    p.moveTo(m, s * 0.08f);
                    p.lineTo(s * 0.9f, s * 0.28f);
                    p.lineTo(s * 0.9f, s * 0.72f);
                    p.lineTo(m, s * 0.92f);
                    p.lineTo(s * 0.1f, s * 0.72f);
                    p.lineTo(s * 0.1f, s * 0.28f);
                    p.closePath();
                    p.moveTo(s * 0.1f, s * 0.28f);
                    p.lineTo(m, s * 0.48f);
                    p.lineTo(s * 0.9f, s * 0.28f);
                    p.moveTo(m, s * 0.48f);
                    p.lineTo(m, s * 0.92f);
                    g2.draw(p);
                    break;
                }
                case "coin": {
                    g2.fill(new Ellipse2D.Float(0, 0, s, s));
                    g2.setColor(Color.WHITE);
                    g2.setFont(Theme.font(Font.BOLD, s * 0.62f));
                    FontMetrics fm = g2.getFontMetrics();
                    String t = "\u20B1";
                    g2.drawString(t, (s - fm.stringWidth(t)) / 2f, (s - fm.getHeight()) / 2f + fm.getAscent());
                    break;
                }
                default: { // "dot"
                    g2.fill(new Ellipse2D.Float(s * 0.15f, s * 0.15f, s * 0.7f, s * 0.7f));
                }
            }
            g2.dispose();
        }
    }

    public static class Logo extends JComponent {
        public Logo() {
            Dimension d = new Dimension(46, 46);
            setPreferredSize(d);
            setMinimumSize(d);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            aa(g2);
            g2.setColor(Theme.YELLOW);
            g2.fill(new Ellipse2D.Float(4, 4, 40, 40));
            g2.setColor(Theme.RED);
            g2.fill(new Ellipse2D.Float(0, 0, 40, 40));
            g2.setColor(Color.WHITE);
            g2.setFont(Theme.font(Font.BOLD, 21f));
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString("S", (40 - fm.stringWidth("S")) / 2f, (40 - fm.getHeight()) / 2f + fm.getAscent());
            g2.dispose();
        }
    }

    public static class Awning extends JComponent {
        private final double leftFrac, rightFrac;

        public Awning() { this(0, 0); }

        public Awning(double leftFrac, double rightFrac) {
            this.leftFrac = leftFrac;
            this.rightFrac = rightFrac;
            setPreferredSize(new Dimension(10, 36));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            aa(g2);
            int w = getWidth(), h = getHeight();
            g2.setColor(Theme.CREAM);
            g2.fillRect(0, 0, w, h);
            if (leftFrac > 0) {
                g2.setColor(Theme.YELLOW);
                g2.fillRect(0, 0, (int) (w * leftFrac), h);
            }
            if (rightFrac > 0) {
                int x = (int) (w * (1 - rightFrac));
                g2.setColor(Theme.RIGHT_TOP);
                g2.fillRect(x, 0, w - x, h);
            }
            int n = Math.max(4, Math.round(w / 90f));
            float sw = w / (float) n;
            Color[] colors = {Theme.RED, new Color(250, 247, 238), Theme.AWNING_BLUE};
            for (int i = 0; i < n; i++) {
                RoundRectangle2D.Float stripe = new RoundRectangle2D.Float(i * sw, -30, sw + 0.5f, h + 30, 28, 28);
                g2.setColor(colors[i % 3]);
                g2.fill(stripe);
                g2.setColor(new Color(0, 0, 0, 28));
                g2.setStroke(new BasicStroke(1f));
                g2.draw(stripe);
            }
            g2.dispose();
        }
    }

    public static JPanel header(JComponent center, JComponent east) {
        JPanel bar = new JPanel(new BorderLayout(20, 0));
        bar.setBackground(Theme.NAVY);
        bar.setBorder(new EmptyBorder(14, 28, 14, 28));

        JPanel brand = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        brand.setOpaque(false);
        brand.add(new Logo());
        JPanel texts = new JPanel();
        texts.setLayout(new BoxLayout(texts, BoxLayout.Y_AXIS));
        texts.setOpaque(false);
        texts.add(label("SARI-OUSLY?", Font.BOLD, 15f, Color.WHITE));
        texts.add(label("TINDAHAN NG BARANGAY", Font.BOLD, 9f, new Color(160, 198, 255)));
        brand.add(texts);

        bar.add(brand, BorderLayout.WEST);
        if (center != null) bar.add(center, BorderLayout.CENTER);
        if (east != null) bar.add(east, BorderLayout.EAST);
        return bar;
    }

    public static JPanel topBar(JComponent center, JComponent east, double leftFrac, double rightFrac) {
        JPanel top = new JPanel(new BorderLayout());
        top.add(header(center, east), BorderLayout.NORTH);
        top.add(new Awning(leftFrac, rightFrac), BorderLayout.SOUTH);
        return top;
    }

    public static JPanel topBar() {
        return topBar(null, null, 0, 0);
    }

    public static class TrackWidth extends JPanel implements Scrollable {
        public TrackWidth() { setOpaque(false); }
        @Override public Dimension getPreferredScrollableViewportSize() { return getPreferredSize(); }
        @Override public int getScrollableUnitIncrement(Rectangle r, int o, int d) { return 24; }
        @Override public int getScrollableBlockIncrement(Rectangle r, int o, int d) { return 120; }
        @Override public boolean getScrollableTracksViewportWidth() { return true; }
        @Override public boolean getScrollableTracksViewportHeight() { return false; }
    }
}

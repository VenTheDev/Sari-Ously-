package com.sariously.view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class DashboardPanel extends JPanel {

    private static final double LEFT_FRAC = 0.26;
    private static final double RIGHT_FRAC = 0.27;
    private static final Color GRAY_LINE = new Color(205, 210, 220);

    private final Runnable onLogout;

    // ---- ui
    private final CardLayout pageLayout = new CardLayout();
    private final JPanel pages = new JPanel(pageLayout);
    private final Map<String, Ui.Btn> navButtons = new LinkedHashMap<>();
    private final JPanel productList = new Ui.TrackWidth();
    private final JLabel cashValue = Ui.label(Theme.peso(0), Font.BOLD, 26f, Theme.NAVY);
    private final JLabel salesValue = Ui.label(Theme.peso(0), Font.BOLD, 17f, Theme.INK);
    private final JLabel expenseValue = Ui.label(Theme.peso(0), Font.BOLD, 17f, Theme.INK);
    private final JLabel itemsValue = Ui.label("0 units", Font.BOLD, 14f, Theme.INK);
    private final JLabel inventoryValue = Ui.label(Theme.peso(0), Font.BOLD, 14f, Theme.INK);
    private final JLabel tipLabel = Ui.label("Tip: Add your first product to start tracking.", Font.PLAIN, 11f, Theme.MUTED);
    private final JLabel footerDay = Ui.label("DAY 01 \u00B7 " + LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH)).toUpperCase(Locale.ENGLISH) + " \u00B7 BARANGAY MALIGAYA", Font.BOLD, 9f, new Color(190, 212, 245));
    private final ProfitCard profitCard = new ProfitCard();
    private final StoreFrame store = new StoreFrame();

    public DashboardPanel(Runnable onLogout) {
        super(new BorderLayout());
        this.onLogout = onLogout;
        setBorder(new EmptyBorder(28, 52, 44, 52));

        store.add(Ui.topBar(buildNav(), buildStatus(), LEFT_FRAC, RIGHT_FRAC), BorderLayout.NORTH);

        pages.setOpaque(false);
        pages.add(buildDashboardPage(), "Dashboard");
        for (String name : new String[]{"Inventory", "Sales", "Reports", "Calendar"}) {
            pages.add(buildPlaceholder(name), name);
        }
        store.add(pages, BorderLayout.CENTER);
        store.add(buildFooter(), BorderLayout.SOUTH);
        add(store, BorderLayout.CENTER);

        showPage("Dashboard");
        showEmptyShelf();
    }

    private static class StoreFrame extends JPanel {
        StoreFrame() {
            super(new BorderLayout());
            setBackground(Theme.CREAM);
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            Ui.aa(g2);
            g2.clip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30));
            super.paint(g2);
            g2.dispose();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        Ui.aa(g2);
        int w = getWidth(), h = getHeight();
        int horizon = (int) (h * 0.4);

        g2.setPaint(new GradientPaint(0, 0, new Color(98, 180, 232), 0, horizon, new Color(176, 218, 242)));
        g2.fillRect(0, 0, w, horizon);
        g2.setPaint(new GradientPaint(0, horizon, new Color(238, 180, 70), 0, h, new Color(222, 150, 40)));
        g2.fillRect(0, horizon, w, h - horizon);

        float sunX = w * 0.82f, sunY = horizon * 0.65f;
        g2.setPaint(new RadialGradientPaint(sunX, sunY, 170f, new float[]{0f, 1f},
                new Color[]{new Color(255, 244, 170, 210), new Color(255, 244, 170, 0)}));
        g2.fill(new Ellipse2D.Float(sunX - 170, sunY - 170, 340, 340));

        Rectangle b = store.getBounds();
        for (int i = 0; i < 7; i++) {
            g2.setColor(new Color(70, 40, 5, 16));
            g2.fill(new RoundRectangle2D.Float(b.x - 2 + i, b.y + 8 + i * 2, b.width + 4 - 2 * i, b.height + 6, 34, 34));
        }
        g2.dispose();
    }

    private JComponent buildNav() {
        Ui.Rounded pill = new Ui.Rounded(new FlowLayout(FlowLayout.CENTER, 4, 4), 60, Theme.NAVY_DARK);
        for (String name : new String[]{"Dashboard", "Inventory", "Sales", "Reports", "Calendar"}) {
            Ui.Btn b = new Ui.Btn(name, null, Color.WHITE, null, 60);
            b.setBorder(new EmptyBorder(8, 18, 8, 18));
            b.addActionListener(e -> showPage(name));
            navButtons.put(name, b);
            pill.add(b);
        }
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        center.add(pill);
        return center;
    }

    private JComponent buildStatus() {
        JPanel east = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 0));
        east.setOpaque(false);

        JLabel open = Ui.label("Bukas \u00B7 Store is open", Font.BOLD, 11f, Color.WHITE);
        open.setIcon(new Ui.Glyph("dot", 12, new Color(60, 220, 120)));
        open.setIconTextGap(6);
        east.add(open);

        Ui.Btn cart = new Ui.Btn(null, null, Color.WHITE, new Color(120, 160, 220), 60);
        cart.setIcon(new Ui.Glyph("cart", 18, Color.WHITE));
        cart.setPreferredSize(new Dimension(38, 38));
        cart.setToolTipText("Cart");
        cart.addActionListener(e -> JOptionPane.showMessageDialog(this, "The cart is coming soon."));
        east.add(cart);

        Ui.Btn logout = new Ui.Btn("Log out", null, Color.WHITE, new Color(120, 160, 220), 60);
        logout.setBorder(new EmptyBorder(8, 16, 8, 16));
        logout.addActionListener(e -> onLogout.run());
        east.add(logout);
        return east;
    }

    private void showPage(String name) {
        pageLayout.show(pages, name);
        for (Map.Entry<String, Ui.Btn> e : navButtons.entrySet()) {
            if (e.getKey().equals(name)) e.getValue().setColors(Theme.YELLOW, Theme.NAVY, null);
            else e.getValue().setColors(null, Color.WHITE, null);
        }
    }

    private static class ThreeColumns implements LayoutManager {
        @Override public void addLayoutComponent(String name, Component comp) {}
        @Override public void removeLayoutComponent(Component comp) {}
        @Override public Dimension preferredLayoutSize(Container p) { return new Dimension(1100, 560); }
        @Override public Dimension minimumLayoutSize(Container p) { return new Dimension(900, 520); }

        @Override
        public void layoutContainer(Container p) {
            int w = p.getWidth(), h = p.getHeight();
            int x1 = (int) (w * LEFT_FRAC), x2 = (int) (w * (1 - RIGHT_FRAC));
            Component[] c = p.getComponents();
            c[0].setBounds(0, 0, x1, h);
            c[1].setBounds(x1, 0, x2 - x1, h);
            c[2].setBounds(x2, 0, w - x2, h);
        }
    }

    private JComponent buildDashboardPage() {
        JPanel page = new JPanel(new ThreeColumns());
        page.setOpaque(false);
        page.add(buildLeft());
        page.add(buildMiddle());
        page.add(buildRight());
        return page;
    }

    private JComponent buildPlaceholder(String name) {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Theme.CREAM);
        JPanel box = new JPanel();
        box.setOpaque(false);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        JLabel title = Ui.label(name, Font.BOLD, 30f, Theme.NAVY);
        JLabel sub = Ui.label("Coming soon \u2014 this section is next on the build list.", Font.PLAIN, 13f, Theme.MUTED);
        Ui.Btn back = new Ui.Btn("Back to dashboard", Theme.BLUE, Color.WHITE, null, 22);
        back.addActionListener(e -> showPage("Dashboard"));
        for (JComponent c : new JComponent[]{title, sub, back}) c.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.add(title);
        box.add(Box.createVerticalStrut(8));
        box.add(sub);
        box.add(Box.createVerticalStrut(20));
        box.add(back);
        p.add(box);
        return p;
    }

    private JComponent buildLeft() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Theme.YELLOW);
        p.setBorder(new EmptyBorder(30, 30, 28, 26));

        JLabel kicker = Ui.label("BUKAS NA, MGA SUKI!", Font.BOLD, 11f, Theme.RED);
        kicker.setIcon(new Ui.Glyph("star", 12, Theme.RED));
        kicker.setIconTextGap(7);

        JLabel h1 = Ui.label("Tingi-tingi.", Font.BOLD, 36f, Theme.NAVY);
        JLabel h2 = Ui.label("Kita araw-", Font.BOLD, 36f, Theme.RED);
        JLabel h3 = Ui.label("araw.", Font.BOLD, 36f, Theme.RED);

        JLabel para = Ui.label("<html><div style='width:210px'>Bantayan ang paninda, sukli, at benta\u2014"
                + "parang totoong sari-sari store sa kanto.</div></html>", Font.PLAIN, 12f, new Color(120, 80, 20));

        for (JComponent c : new JComponent[]{kicker, h1, h2, h3, para}) c.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(kicker);
        p.add(Box.createVerticalStrut(14));
        p.add(h1);
        p.add(h2);
        p.add(h3);
        p.add(Box.createVerticalStrut(14));
        p.add(para);
        p.add(Box.createVerticalStrut(20));

        ChipStrip chips = new ChipStrip();
        chips.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(chips);
        p.add(Box.createVerticalGlue());
        p.add(Box.createVerticalStrut(16));

        JComponent cashCard = buildCashCard();
        cashCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(Ui.lockHeight(cashCard));
        p.add(Box.createVerticalStrut(16));

        Storefront front = new Storefront();
        front.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(front);
        return p;
    }

    private JComponent buildCashCard() {
        Ui.Rounded card = new Ui.Rounded(new BorderLayout(8, 0), 18, Color.WHITE).line(Theme.NAVY, 2f);
        card.setBorder(new EmptyBorder(14, 18, 14, 16));
        JPanel texts = new JPanel();
        texts.setOpaque(false);
        texts.setLayout(new BoxLayout(texts, BoxLayout.Y_AXIS));
        texts.add(Ui.label("PUHUNAN AT CASH", Font.PLAIN, 9f, Theme.MUTED));
        texts.add(Box.createVerticalStrut(4));
        texts.add(cashValue);
        card.add(texts, BorderLayout.CENTER);

        JLabel coin = new JLabel(new Ui.Glyph("coin", 36, Theme.YELLOW));
        card.add(coin, BorderLayout.EAST);

        return card;
    }

    private static class ChipStrip extends JComponent {
        private static final String[] LABELS = {"3-in-1", "SHAMPOO", "TOYO", "KAPE"};
        private static final int[] HEIGHTS = {38, 56, 42, 38};
        private static final Color[] COLORS = {
                new Color(225, 58, 48), new Color(30, 100, 210), new Color(30, 130, 90), new Color(240, 130, 40)};

        ChipStrip() {
            setPreferredSize(new Dimension(10, 66));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 66));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            Ui.aa(g2);
            int w = getWidth(), gap = 8;
            g2.setColor(Theme.NAVY);
            g2.fillRect(0, 0, w, 3);
            int cw = (w - gap * 3) / 4;
            g2.setFont(Theme.font(Font.BOLD, 8f));
            FontMetrics fm = g2.getFontMetrics();
            for (int i = 0; i < 4; i++) {
                int x = i * (cw + gap), y = 8;
                g2.setColor(COLORS[i]);
                g2.fill(new RoundRectangle2D.Float(x, y, cw, HEIGHTS[i], 8, 8));
                g2.setColor(Color.WHITE);
                g2.drawString(LABELS[i], x + (cw - fm.stringWidth(LABELS[i])) / 2,
                        y + (HEIGHTS[i] - fm.getHeight()) / 2 + fm.getAscent());
            }
            g2.dispose();
        }
    }

    private static class Storefront extends JComponent {
        Storefront() {
            setPreferredSize(new Dimension(10, 140));
            setMinimumSize(new Dimension(10, 110));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            Ui.aa(g2);
            int w = getWidth(), h = getHeight();
            g2.clip(new RoundRectangle2D.Float(0, 0, w, h, 16, 16));
            g2.setPaint(new GradientPaint(0, 0, new Color(74, 50, 38), 0, h, new Color(34, 26, 30)));
            g2.fillRect(0, 0, w, h);

            Random r = new Random(11);
            int rows = 4, plankH = 4;
            int rowH = (h - 34) / rows;
            int cols = Math.max(6, w / 26);
            int cw = (w - 24) / cols;
            Color[] item = {Theme.RED, Theme.YELLOW, new Color(46, 170, 110), new Color(40, 120, 230),
                    new Color(240, 130, 40), new Color(245, 240, 225), new Color(0, 160, 170)};
            for (int row = 0; row < rows; row++) {
                int top = 10 + row * rowH;
                for (int c = 0; c < cols; c++) {
                    int ih = rowH - 12 - r.nextInt(8);
                    g2.setColor(item[r.nextInt(item.length)]);
                    g2.fill(new RoundRectangle2D.Float(12 + c * cw + 2, top + rowH - plankH - ih, cw - 5, ih, 4, 4));
                }
                g2.setColor(new Color(130, 90, 52));
                g2.fillRect(8, top + rowH - plankH, w - 16, plankH);
            }
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRect(0, h - 30, w, 30);
            g2.setColor(Theme.YELLOW);
            g2.setFont(Theme.font(Font.BOLD, 9f));
            g2.drawString("BUKAS ARAW-ARAW", 12, h - 12);

            g2.setClip(null);
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3.5f));
            g2.draw(new RoundRectangle2D.Float(1.75f, 1.75f, w - 3.5f, h - 3.5f, 16, 16));
            g2.dispose();
        }
    }

    private JComponent buildMiddle() {
        JPanel p = new JPanel(new BorderLayout(0, 14));
        p.setBackground(Theme.CREAM);
        p.setBorder(new EmptyBorder(28, 36, 22, 36));

        JPanel head = new JPanel();
        head.setOpaque(false);
        head.setLayout(new BoxLayout(head, BoxLayout.Y_AXIS));
        JLabel kicker = Ui.label("ON THE SHELF", Font.BOLD, 10f, Theme.RED);
        kicker.setAlignmentX(Component.LEFT_ALIGNMENT);
        head.add(kicker);
        head.add(Box.createVerticalStrut(4));

        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        row.add(Ui.label("Quick inventory", Font.BOLD, 26f, Theme.NAVY), BorderLayout.WEST);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 0));
        actions.setOpaque(false);
        Ui.Btn add = new Ui.Btn("+  Add product", Theme.BLUE, Color.WHITE, null, 24);
        add.addActionListener(e -> showAddProductDialog());
        JLabel viewAll = Ui.label("View all \u2192", Font.PLAIN, 11f, Theme.BLUE);
        viewAll.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        viewAll.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { showPage("Inventory"); }
        });
        actions.add(add);
        actions.add(viewAll);
        row.add(actions, BorderLayout.EAST);
        head.add(row);
        p.add(head, BorderLayout.NORTH);

        productList.setLayout(new BoxLayout(productList, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(productList,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(24);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        p.add(scroll, BorderLayout.CENTER);

        JPanel stats = new JPanel(new GridLayout(1, 2, 14, 0));
        stats.setOpaque(false);
        stats.add(statBox("box", "TOTAL ITEMS", itemsValue, Theme.RED));
        stats.add(statBox("coin", "INVENTORY VALUE", inventoryValue, Theme.RED));
        p.add(stats, BorderLayout.SOUTH);
        return p;
    }

    private JComponent statBox(String glyph, String caption, JLabel value, Color iconColor) {
        Ui.Rounded box = new Ui.Rounded(new BorderLayout(12, 0), 16, new Color(255, 247, 222))
                .line(new Color(232, 200, 120), 1.4f).dashed();
        box.setBorder(new EmptyBorder(12, 16, 12, 16));
        box.add(new JLabel(new Ui.Glyph(glyph, 20, iconColor)), BorderLayout.WEST);
        JPanel t = new JPanel();
        t.setOpaque(false);
        t.setLayout(new BoxLayout(t, BoxLayout.Y_AXIS));
        t.add(Ui.label(caption, Font.PLAIN, 8.5f, Theme.MUTED));
        t.add(value);
        box.add(t, BorderLayout.CENTER);
        return box;
    }

    private void showEmptyShelf() {
        productList.removeAll();
        productList.add(buildEmptyState());
        productList.add(Box.createVerticalGlue());
    }

    private JComponent buildEmptyState() {
        Ui.Rounded box = new Ui.Rounded(new GridBagLayout(), 22, new Color(255, 250, 235))
                .line(new Color(232, 200, 120), 1.6f).dashed();
        box.setBorder(new EmptyBorder(34, 24, 34, 24));
        JPanel inner = new JPanel();
        inner.setOpaque(false);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));

        JLabel icon = new JLabel(new Ui.Glyph("box", 40, Theme.NAVY));
        JLabel title = Ui.label("Wala pang paninda", Font.BOLD, 17f, Theme.NAVY);
        JLabel sub = Ui.label("No products yet. Add your first product to start tracking stock and sales.",
                Font.PLAIN, 12f, Theme.MUTED);
        Ui.Btn add = new Ui.Btn("+  Add product", Theme.BLUE, Color.WHITE, null, 24);
        add.addActionListener(e -> showAddProductDialog());
        for (JComponent c : new JComponent[]{icon, title, sub, add}) c.setAlignmentX(Component.CENTER_ALIGNMENT);
        inner.add(icon);
        inner.add(Box.createVerticalStrut(10));
        inner.add(title);
        inner.add(Box.createVerticalStrut(4));
        inner.add(sub);
        inner.add(Box.createVerticalStrut(16));
        inner.add(add);
        box.add(inner);
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
        return Ui.lockHeight(box);
    }

    private JComponent buildRight() {
        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, Theme.RIGHT_TOP, 0, getHeight(), Theme.RIGHT_BOTTOM));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(28, 32, 22, 32));

        JLabel kicker = Ui.label("TODAY", Font.BOLD, 10f, Theme.RED);
        kicker.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(kicker);
        p.add(Box.createVerticalStrut(4));

        JPanel titleRow = new JPanel(new BorderLayout());
        titleRow.setOpaque(false);
        titleRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleRow.add(Ui.label("Store summary", Font.BOLD, 22f, Theme.NAVY), BorderLayout.WEST);
        Ui.Rounded live = new Ui.Rounded(new FlowLayout(FlowLayout.CENTER, 5, 3), 30, new Color(214, 242, 226));
        JLabel liveText = Ui.label("LIVE", Font.BOLD, 9f, Theme.GREEN);
        liveText.setIcon(new Ui.Glyph("dot", 9, Theme.GREEN));
        liveText.setIconTextGap(4);
        live.add(liveText);
        JPanel liveWrap = new JPanel(new GridBagLayout());
        liveWrap.setOpaque(false);
        liveWrap.add(live);
        titleRow.add(liveWrap, BorderLayout.EAST);
        p.add(Ui.lockHeight(titleRow));
        p.add(Box.createVerticalStrut(18));

        profitCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(profitCard);
        p.add(Box.createVerticalStrut(22));

        JComponent salesRow = summaryRow("bars", new Color(253, 226, 226), Theme.RED, "Today's sales", "Gross income", salesValue);
        JComponent expenseRow = summaryRow("cart", new Color(255, 242, 205), Theme.ORANGE, "Expenses", "Restock & bills", expenseValue);
        p.add(Ui.lockHeight(salesRow));
        p.add(Box.createVerticalStrut(14));
        p.add(Ui.lockHeight(expenseRow));
        p.add(Box.createVerticalStrut(22));

        Ui.Btn close = new Ui.Btn("Review & close day", Theme.BLUE, Color.WHITE, null, 60);
        close.setLeftAlign(true);
        close.setTrailing("\u2192");
        close.setBorder(new EmptyBorder(15, 20, 15, 20));
        close.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(Ui.lockHeight(close));
        p.add(Box.createVerticalStrut(12));

        tipLabel.setIcon(new Ui.Glyph("star", 10, Theme.RED));
        tipLabel.setIconTextGap(6);
        tipLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(tipLabel);
        p.add(Box.createVerticalGlue());
        return p;
    }

    private JComponent summaryRow(String glyph, Color bg, Color fg, String title, String sub, JLabel amount) {
        JPanel row = new JPanel(new BorderLayout(14, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        Ui.Rounded icon = new Ui.Rounded(new GridBagLayout(), 14, bg);
        icon.setPreferredSize(new Dimension(44, 44));
        icon.add(new JLabel(new Ui.Glyph(glyph, 20, fg)));
        row.add(icon, BorderLayout.WEST);

        JPanel t = new JPanel();
        t.setOpaque(false);
        t.setLayout(new BoxLayout(t, BoxLayout.Y_AXIS));
        t.add(Box.createVerticalGlue());
        t.add(Ui.label(title, Font.BOLD, 12f, Theme.INK));
        t.add(Ui.label(sub, Font.PLAIN, 9f, Theme.MUTED));
        t.add(Box.createVerticalGlue());
        row.add(t, BorderLayout.CENTER);
        row.add(amount, BorderLayout.EAST);
        return row;
    }

    private class ProfitCard extends JComponent {
        ProfitCard() {
            setPreferredSize(new Dimension(10, 188));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 188));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            Ui.aa(g2);
            int sh = 6, w = getWidth() - sh, h = getHeight() - sh;

            g2.setColor(Theme.RED);
            g2.fill(new RoundRectangle2D.Float(sh, sh, w, h, 24, 24));
            RoundRectangle2D.Float face = new RoundRectangle2D.Float(0, 0, w, h, 24, 24);
            g2.setColor(Theme.YELLOW);
            g2.fill(face);

            Shape oldClip = g2.getClip();
            g2.clip(face);
            g2.setColor(new Color(255, 170, 40, 110));
            g2.fill(new Ellipse2D.Float(w - 110, -50, 150, 150));
            g2.setClip(oldClip);

            g2.setColor(Theme.NAVY);
            g2.setStroke(new BasicStroke(3f));
            g2.draw(new RoundRectangle2D.Float(1.5f, 1.5f, w - 3, h - 3, 24, 24));

            g2.setFont(Theme.font(Font.BOLD, 9.5f));
            g2.drawString("NET PROFIT", 22, 34);
            g2.setFont(Theme.font(Font.BOLD, 38f));
            g2.setColor(Theme.NAVY);
            g2.drawString(Theme.peso(0), 22, 84);
            g2.setColor(Theme.NAVY);
            g2.setFont(Theme.font(Font.PLAIN, 11f));
            String msg = "Wala pang kita today";
            g2.drawString(msg, 22, 112);

            // empty placeholder bars (real sales data comes later)
            int n = 7, barW = Math.max(10, Math.min(24, (w - 60) / 12)), gap = 6;
            int x0 = w - 20 - (n * barW + (n - 1) * gap), base = h - 18;
            g2.setColor(new Color(20, 84, 190, 70));
            for (int i = 0; i < n; i++) {
                g2.fill(new RoundRectangle2D.Float(x0 + i * (barW + gap), base - 6, barW, 6, 5, 5));
            }
            g2.dispose();
        }
    }

    private JComponent buildFooter() {
        JPanel f = new JPanel(new GridLayout(1, 3));
        f.setBackground(Theme.NAVY);
        f.setBorder(new EmptyBorder(12, 28, 12, 28));

        JLabel left = Ui.label("SARI-OUSLY? STORE MANAGER", Font.BOLD, 9f, new Color(190, 212, 245));
        JLabel mid = Ui.label("PARA SA BAWAT MASIPAG NA TINDERA AT TINDERO.", Font.BOLD, 9f, new Color(190, 212, 245));
        mid.setHorizontalAlignment(SwingConstants.CENTER);
        footerDay.setHorizontalAlignment(SwingConstants.RIGHT);
        f.add(left);
        f.add(mid);
        f.add(footerDay);
        return f;
    }

    private void showAddProductDialog() {
        JTextField name = new JTextField(18);
        JTextField desc = new JTextField(18);
        JTextField price = new JTextField(18);
        JTextField cost = new JTextField(18);
        JTextField stock = new JTextField(18);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.anchor = GridBagConstraints.WEST;
        String[] labels = {"Product name", "Size / description", "Selling price (\u20B1)", "Cost per unit (\u20B1)", "Starting stock"};
        JTextField[] fields = {name, desc, price, cost, stock};
        for (int i = 0; i < labels.length; i++) {
            g.gridx = 0; g.gridy = i; g.fill = GridBagConstraints.NONE;
            form.add(new JLabel(labels[i]), g);
            g.gridx = 1; g.fill = GridBagConstraints.HORIZONTAL;
            form.add(fields[i], g);
        }
        JOptionPane.showConfirmDialog(this, form, "Add product",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    }
}

package iti.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PaintBrush extends JFrame {

    String tool = "Line";
    Color color = Color.RED;
    boolean filled = false;
    boolean dotted = false;
    int x1, y1, x2, y2;

    ArrayList<Drawings> shapes = new ArrayList<>();

    JPanel editPanel;

    public PaintBrush() {
        setTitle("Paint Brush");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel toolbar = new JPanel();

        JButton btnLine = new JButton("Line");
        JButton btnRect = new JButton("Rectangle");
        JButton btnOval = new JButton("Oval");

        btnLine.addActionListener(e -> tool = "Line");
        btnRect.addActionListener(e -> tool = "Rect");
        btnOval.addActionListener(e -> tool = "Oval");

        JButton btnRed = new JButton("Red");
        JButton btnGreen = new JButton("Green");
        JButton btnBlue = new JButton("Blue");

        btnRed.setBackground(Color.RED);
        btnGreen.setBackground(Color.GREEN);
        btnBlue.setBackground(Color.BLUE);

        btnRed.addActionListener(e -> color = Color.RED);
        btnGreen.addActionListener(e -> color = Color.GREEN);
        btnBlue.addActionListener(e -> color = Color.BLUE);
        JButton btnFree = new JButton("Free Hand");
        btnFree.addActionListener(e -> tool = "Free");
        toolbar.add(btnFree);

        JButton btnErase = new JButton("Eraser");
        btnErase.addActionListener(e -> tool = "Eraser");
        toolbar.add(btnErase);

        JButton btnClear = new JButton("Clear All");
        btnClear.addActionListener(e -> {
            shapes.clear();

            editPanel.repaint();
        });

        toolbar.add(btnLine);
        toolbar.add(btnRect);
        toolbar.add(btnOval);
        toolbar.add(new JSeparator());
        toolbar.add(new JSeparator());

        toolbar.add(btnRed);
        toolbar.add(btnGreen);
        toolbar.add(btnBlue);
        toolbar.add(new JSeparator());

        toolbar.add(btnClear);


        JCheckBox chkFilled = new JCheckBox("Filled");
        chkFilled.addActionListener(e -> filled = chkFilled.isSelected());
        toolbar.add(chkFilled);

        JCheckBox chkDotted = new JCheckBox("Dotted");
        chkDotted.addActionListener(e -> dotted = chkDotted.isSelected());
        toolbar.add(chkDotted);

        editPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                for (Drawings s : shapes) {
                    drawShape(g2, s);
                }

                drawShape(g2, new Drawings(x1, y1, x2, y2, tool, color, filled, dotted));
            }
        };

        editPanel.setBackground(Color.WHITE);

        editPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
            }

            public void mouseReleased(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();

                shapes.add(new Drawings(x1, y1, x2, y2, tool, color, filled, dotted));
                editPanel.repaint();
            }
        });

        editPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
                if ((tool.equals("Free")) || tool.equals("Eraser")) {
                    Color tempColor;
                    if (tool.equals("Eraser")) {
                        tempColor = Color.WHITE;
                    }
                    else {
                        tempColor = color;
                    }
                    shapes.add(new Drawings(x1, y1, x2, y2, "Line", tempColor, false, false));

                    x1 = x2;
                    y1 = y2;
                }

                editPanel.repaint();
            }
        });

        add(toolbar, BorderLayout.NORTH);
        add(editPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    void drawShape(Graphics2D g2, Drawings s) {
        g2.setColor(s.color);
        g2.setStroke(new BasicStroke(2));

        if (s.dotted) {
            float[] dash = {10f, 10f};
            g2.setStroke(new BasicStroke(2,1,1,1, dash,2));
        }

        int x = Math.min(s.x1, s.x2);
        int y = Math.min(s.y1, s.y2);
        int w = Math.abs(s.x2 - s.x1);
        int h = Math.abs(s.y2 - s.y1);

        switch (s.type) {
            case "Line":
                g2.drawLine(s.x1, s.y1, s.x2, s.y2);
                break;
            case "Rect":
                if (s.filled)
                    g2.fillRect(x, y, w, h);
                else
                    g2.drawRect(x, y, w, h);
                break;

            case "Oval":
                if (s.filled)
                    g2.fillOval(x, y, w, h);
                else
                    g2.drawOval(x, y, w, h);
                break;
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PaintBrush::new);
    }
}


package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GraphDraw {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Circle Drawing App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            CircleDrawingPanel drawingPanel = new CircleDrawingPanel();
            JButton clearButton = new JButton("Xóa");
           
            // Gắn ActionListener cho nút Xóa
            clearButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    drawingPanel.clearCircles(); // Gọi phương thức để xóa hình tròn
                }
            });
            
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(clearButton);
            
            frame.add(buttonPanel, BorderLayout.NORTH);
            frame.add(drawingPanel);

            frame.setVisible(true);
        });
    }
}

class CircleDrawingPanel extends JPanel {
    private int circleRadius = 15; // Đường kính hình tròn
    private ArrayList<CircleInfo> circles = new ArrayList<>(); // Danh sách các hình tròn đã vẽ

    public CircleDrawingPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                int number = circles.size() + 1;
                CircleInfo circleInfo = new CircleInfo(point, number);
                circles.add(circleInfo);
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLUE);
        for (CircleInfo circleInfo : circles) {
            int x = (int) circleInfo  .getPoint().getX();
            int y = (int) circleInfo.getPoint().getY();

            // Vẽ hình tròn ở vị trí đã nhấn chuột
            g.fillOval(x - circleRadius, y - circleRadius, circleRadius * 2, circleRadius * 2);

            // Hiển thị số đại diện trên hình tròn
            g.setColor(Color.WHITE);
            g.drawString(String.valueOf(circleInfo.getNumber()), x - 5, y + 5);
            g.setColor(Color.BLUE);
        }
    }

    // Phương thức để xóa tất cả các hình tròn
    public void clearCircles() {
        circles.clear();
        repaint();
    }
}

class CircleInfo {
    private Point point;
    private int number;

    public CircleInfo(Point point, int number) {
        this.point = point;
        this.number = number;
    }

    public Point getPoint() {
        return point;
    }

    public int getNumber() {
        return number;
    }
}



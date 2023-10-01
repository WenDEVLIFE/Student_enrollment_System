package admin_Registration;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GRADIENTCOLORS extends JPanel {

    private static final long serialVersionUID = 1L;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        Color color1 = Color.BLUE;
        Color color2 = Color.WHITE;

        GradientPaint gradient = new GradientPaint(600, 0, 
                new Color(49, 71, 85), 0, 0, new Color(38, 160, 218), true);

        g2.setPaint(gradient);
        g2.fillRect(0, 0, panelWidth, panelHeight);

        g2.dispose();
    }
}
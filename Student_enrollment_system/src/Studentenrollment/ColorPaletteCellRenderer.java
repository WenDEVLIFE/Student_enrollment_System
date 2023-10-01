package Studentenrollment;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ColorPaletteCellRenderer extends DefaultTableCellRenderer {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Get the Graphics2D object from the provided Graphics
        Graphics2D g2d = (Graphics2D) getGraphics();

        // Draw the color palette
        if (g2d != null) {
            Rectangle rectangle = new Rectangle(0, 0, getWidth(), getHeight());
            g2d.setColor(Color.RED);
            g2d.fill(rectangle);

            for (int i = 0; i < 10; i++) {
                g2d.setColor(new Color(i * 25, 253 - i * 25, 0));
                g2d.fillRect(rectangle.x + i * rectangle.width / 10, rectangle.y, rectangle.width / 10, rectangle.height);
            }

            // Dispose of the Graphics2D object to release resources
            g2d.dispose();
        }

        return this;
    }
}
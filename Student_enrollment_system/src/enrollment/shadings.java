package enrollment;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class shadings extends JPanel {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  protected void paintComponent(Graphics g) {
		  super.paintComponent(g);

	        Graphics2D g2 = (Graphics2D) g.create();

	        int panelWidth = getWidth();
	        int panelHeight = getHeight();


	        GradientPaint gradient = new GradientPaint(600, 0, 
	                new Color(43, 88, 118), 0, 0, new Color(78, 67, 118), true);

	        g2.setPaint(gradient);
	        g2.fillRect(0, 0, panelWidth, panelHeight);
	        // Create a new BufferedImage object.
	        BufferedImage image = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);

	        // Create a new Graphics2D object from the BufferedImage object.
	        Graphics2D g2d = image.createGraphics();

	        // Set the color of the Graphics2D object to white.
	        g2d.setColor(Color.WHITE);
	    

	        // Fill the BufferedImage object with white.
	        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());

	        // Create a new LinearGradientPaint object.
	      

	        // Fill the BufferedImage object with the LinearGradientPaint object.
	        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());

	        // Create a new DropShadow object.
	        DropShadow dropShadow = new DropShadow();

	        // Set the offset and blurRadius of the DropShadow object to the desired values.
	        dropShadow.setOffset(5, 5);
	        dropShadow.setBlurRadius(5);

	        // Set the DropShadow object to the Graphics2D object.


	        // Draw the BufferedImage object to the desired location.
	        g2d.drawImage(image, 0, 0, null);
	        g2.dispose();
	  }
	
}

package enrollment;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class DropShadow {
	 public static BufferedImage createDropShadow(BufferedImage image, int offset, int blurRadius) {
	        // Create a new BufferedImage object with the desired offset and blurRadius.
	        BufferedImage shadow = new BufferedImage(image.getWidth() + offset * 2, image.getHeight() + offset * 2, BufferedImage.TYPE_INT_ARGB);

	        // Create a new Graphics2D object from the shadow BufferedImage object.
	        Graphics2D g2d = shadow.createGraphics();

	        // Draw the image to the shadow BufferedImage object with the desired offset.
	        g2d.drawImage(image, offset, offset, null);

	        // Create a new Kernel object for the blur operation.
	        float[] data = new float[blurRadius * blurRadius];
	        for (int i = 0; i < data.length; i++) {
	            data[i] = 1.0f / (blurRadius * blurRadius);
	        }
	        Kernel kernel = new Kernel(blurRadius, blurRadius, data);

	        // Create a new ConvolveOp object for the blur operation.
	        ConvolveOp convolveOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

	        // Apply the blur operation to the shadow BufferedImage object.
	        g2d.drawImage(convolveOp.filter(shadow, null), 0, 0, null);

	        // Dispose of the Graphics2D object.
	        g2d.dispose();

	        // Return the shadow BufferedImage object.
	        return shadow;
	    }

	public void setBlurRadius(int i) {
		// TODO Auto-generated method stub
		
	}

	public void setOffset(int i, int j) {
		// TODO Auto-generated method stub
		
	}
}

package Studentenrollment;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ColorPalette {
	 private List<Color> colors;

	    public ColorPalette() {
	        this.colors = new ArrayList<>();
	    }

	    public void addColor(Color color) {
	        this.colors.add(color);
	    }

	    public Color getColor(int index) {
	        return this.colors.get(index);
	    }

	    public Color getRandomColor() {
	        int index = (int) (Math.random() * this.colors.size());
	        return this.colors.get(index);
	    }


}

import javax.swing.ImageIcon;

import java.awt.Graphics;
import java.awt.Image;

public class Gun {
	public int xpos;
	public int ypos;
	public int dx;
	public int delay = 0;
	public Image png_target;
	public String png_path = "images/gun.png";
	
	//constructor
	public Gun(int xpos, int ypos) {
		this.xpos = xpos;
		this.ypos = ypos;
		ImageIcon imic = new ImageIcon(png_path);
		png_target = imic.getImage();
	}
	
	public void move() {
		// MAKE SURE IT DOESN'T GO OUT OF BOUND
		delay++;
		if(delay == 10) {
			delay = 0;
			if ( (dx < 0) && (xpos > 5) ) {
				xpos = xpos + dx;
			}
			if ( (dx > 0) && (xpos < 575) ) {
				xpos = xpos + dx;
			}
			if ( dx == 0 ) {
				xpos = xpos + dx;
			}
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(png_target, xpos, ypos, null);
	}
}

import javax.swing.ImageIcon;

import java.awt.Graphics;
import java.awt.Image;

public class Bullet {
	public int xpos;
	public int ypos;
	public int delay = 0;
	public boolean fired = false;
	public Image png_target;
	public String png_path = "images/bullet.png";
	
	//constructor
	public Bullet(int xpos, int ypos) {
		this.xpos = xpos;
		this.ypos = ypos;
		ImageIcon imic = new ImageIcon(png_path);
		png_target = imic.getImage();
	}
	
	public void move() {
		if(ypos == 50) {
			fired = false;
		}
		delay++;
		if(delay == 8) {
			delay = 0;
			ypos = ypos - 1;
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(png_target, xpos, ypos, null);
	}
}

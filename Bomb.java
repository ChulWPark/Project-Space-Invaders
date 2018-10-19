import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;

public class Bomb {
	public int xpos;
	public int ypos;
	public int delay = 0;
	public boolean dropped = false;
	public Image png_target;
	public String png_path = "images/bomb.png";
	
	//constructor
	public Bomb(int xpos, int ypos) {
		this.xpos = xpos;
		this.ypos = ypos;
		ImageIcon imic = new ImageIcon(png_path);
		png_target = imic.getImage();
	}
	
	public void dropping() {
		if(ypos >= 550) {
			dropped = false;
		}
		delay++;
		if(delay == 10) {
			delay = 0;
			ypos = ypos + 1;
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(png_target, xpos, ypos, null);
	}
}

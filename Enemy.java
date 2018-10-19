import javax.swing.ImageIcon;

import java.awt.Graphics;
import java.awt.Image;

public class Enemy {
	public int xpos;
	public int ypos;
	public int initialx;
	public int initialy;
	public int dx = 1;
	public int dy = 0;
	public Image png_target;
	public int delay = 0;
	public String png_path = "images/enemy.png";
	public Bomb bomb;
	public boolean infront;
	public int gbafRate;
	public int ddRate;
	public int rb;
	
	// constructor
	public Enemy(int xpos, int ypos) {
		this.xpos = xpos;
		this.ypos = ypos;
		this.initialx = xpos;
		this.initialy = ypos;
		this.bomb = new Bomb(xpos,ypos);
		ImageIcon imic = new ImageIcon(png_path);
		png_target = imic.getImage();
	}
	
	public void move() {
		if(xpos == (initialx - 5) ) {
			dx = 1;
			dy = ddRate;
		}
		else if(xpos == (initialx + rb)) {
			dx = -1;
			dy = ddRate;
		}
		else {
			dy = 0;
		}
		delay++;
		if(delay == gbafRate) {
			delay = 0;
			xpos = xpos + dx;
			ypos = ypos + dy;
		}
	}
	public void draw(Graphics g) {
		g.drawImage(png_target, xpos, ypos, null);
	}
	
}

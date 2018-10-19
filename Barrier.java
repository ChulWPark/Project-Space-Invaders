import java.awt.Color;
import java.awt.Graphics;

public class Barrier {
	public int xpos;
	public int ypos;
	public int width = 20;
	public int height = 10;

	public Barrier(int x, int y) {
		this.xpos = x;
		this.ypos = y;
	}

	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(xpos, ypos, width, height);
	}
}
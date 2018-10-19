import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.applet.*;

@SuppressWarnings("serial")
public class LCU extends Canvas {
	public BufferStrategy strategy;
	public JPanel frame;
	// FOR MENU
	public boolean inMenu = true;
	public int invRows = 4;
	public int invCols = 6;
	public int gbafRate = 10;
	public int ddRate = 25;
	public int fRate = 1000;
	public boolean menurefresh = false;
	///////////
	public boolean inGame = true;
	public boolean keyidle = true;
	public boolean left_clicked = false;
	public boolean right_clicked = false;
	public boolean space_clicked = false;
	public boolean victory = false;
	public Gun gun;
	public List<Bullet> bullets = new CopyOnWriteArrayList<Bullet>();
	public List<Enemy> enemies = new CopyOnWriteArrayList<Enemy>();
	public List<Barrier> barriers = new CopyOnWriteArrayList<Barrier>();
	public Barrier b1;
	public Barrier b2;
	public Barrier b3;
	public int score = 0;
	public int life = 3;
	public AudioClip shoot_sound, bomb_sound, BGM;

	public LCU() {
		JFrame window = new JFrame("ECE30862 JAVA PROJECT - CHUL WOO PARK");
		frame = (JPanel) window.getContentPane();
		frame.setLayout(null);
		frame.setPreferredSize(new Dimension(600, 600));
		frame.setBackground(Color.pink);
		frame.setBounds(0,0,600,600);
		frame.add(this);
		setIgnoreRepaint(true);
		window.pack();
		window.setResizable(false);
		window.setVisible(true);

		addKeyListener(new KeyInputHandler());
		requestFocus();
		createBufferStrategy(2);
		strategy = getBufferStrategy();

		shoot_sound = Applet.newAudioClip(getClass().getResource("sounds/shoot_sound.wav"));
		bomb_sound = Applet.newAudioClip(getClass().getResource("sounds/bomb_sound.wav"));
		BGM = Applet.newAudioClip(getClass().getResource("sounds/BGM.wav"));
		BGM.play();

		// MENU LOOP
		selecting();

		// INITIATE THE GAME
		gun = new Gun(300,550);
		for (int i = 0; i < invRows; i++) {
			for (int j = 0; j < invCols; j++) {
				Enemy enemy = new Enemy(10 + 60 * j, 50 + 50 * i); //70
				enemy.gbafRate = gbafRate;
				enemy.ddRate = ddRate;
				enemy.rb = 535 - (60 * (invCols - 1));
				enemies.add(enemy);
			}
		}
		// BARRIER 1
		int detx = 80; // inc by 20
		int dety = 500; // inc by 10
		for (int a = 0; a < 4; a++) {
			for (int b = 0; b < 4; b++) {
				Barrier barrier = new Barrier(detx,dety);
				barriers.add(barrier);
				detx = detx + 20;
			}
			detx = 80;
			dety = dety - 10;
		}
		// BARRIER 2
		detx = 260;
		dety = 500;
		for (int a = 0; a < 4; a++) {
			for (int b = 0; b < 4; b++) {
				Barrier barrier = new Barrier(detx,dety);
				barriers.add(barrier);
				detx = detx + 20;
			}
			detx = 260;
			dety = dety - 10;
		}
		// BARRIER 3
		detx = 440;
		dety = 500;
		for (int a = 0; a < 4; a++) {
			for (int b = 0; b < 4; b++) {
				Barrier barrier = new Barrier(detx,dety);
				barriers.add(barrier);
				detx = detx + 20;
			}
			detx = 440;
			dety = dety - 10;
		}

		// WHILE LOOP
		operating();
	}

	public void selecting() {
		Graphics g = frame.getGraphics();
		g.setColor(Color.pink);
		g.fillRect(0, 0, 600, 600);
		Font pont = new Font("PLAIN", Font.BOLD, 16);
		g.setColor(Color.blue);
		g.setFont(pont);
		String firststr = "(Q+)(W+) (A-)(S-) Rows and columns of invaders: Rows = " + invRows + ", Cols = " + invCols;
		String secondstr = "(E+) (D-) Rate invaders go back and forth: " + gbafRate;
		String thirdstr = "(R+) (F-) Rate invaders drop down: " + ddRate;
		String fourthstr = "(T+) (G-) Rate invaders fire: " + fRate;
		String fifthstr = "(Y+) (H-) Number of defender lives: " + life;
		g.drawString(firststr, 5, 210);
		g.drawString(secondstr, 5, 280);
		g.drawString(thirdstr, 5, 350);
		g.drawString(fourthstr, 5, 420);
		g.drawString(fifthstr, 5, 490);
		pont = new Font("Serif", Font.BOLD,50);
		g.setColor(Color.red);
		g.setFont(pont);
		g.drawString("SELECT MENU", 120, 100);
		g.drawLine(0, 140, 600, 140);
		while(inMenu) {
			System.out.println("MENU RUNNING");
			if (menurefresh == true) {
				g.setColor(Color.pink);
				g.fillRect(0, 0, 600, 600);
				pont = new Font("PLAIN", Font.BOLD, 16);
				g.setColor(Color.blue);
				g.setFont(pont);
				firststr = "(Q+)(W+) (A-)(S-) Rows and columns of invaders: Rows = " + invRows + ", Cols = " + invCols;
				secondstr = "(E+) (D-) Rate invaders go back and forth: " + gbafRate;
				thirdstr = "(R+) (F-) Rate invaders drop down: " + ddRate;
				fourthstr = "(T+) (G-) Rate invaders fire: " + fRate;
				fifthstr = "(Y+) (H-) Number of defender lives: " + life;
				g.drawString(firststr, 5, 210);
				g.drawString(secondstr, 5, 280);
				g.drawString(thirdstr, 5, 350);
				g.drawString(fourthstr, 5, 420);
				g.drawString(fifthstr, 5, 490);
				pont = new Font("Serif", Font.BOLD,50);
				g.setColor(Color.red);
				g.setFont(pont);
				g.drawString("SELECT MENU", 120, 100);
				g.drawLine(0, 140, 600, 140);
				menurefresh = false;
			}
		}
	}

	public void operating() {
		while(inGame) {
			// REFRESH THE FRAME
			Graphics g = frame.getGraphics();
			g.setColor(Color.pink);
			g.fillRect(0, 0, 600, 600);

			// DRAW LINE ON TOP
			g.setColor(Color.blue);
			g.drawLine(0, 50, 600, 50);

			Font pont = new Font("Serif", Font.BOLD, 18);
	        g.setFont(pont);
			// PUT SCORE ON THE TOP LEFT CORNER
			String scoremsg = "Score: " + score;
			g.drawString(scoremsg, 10, 30);

			// PUT MSG ON THE TOP MIDDLE PART
			g.setColor(Color.red);
			g.drawString("SPACE INVADERS", 230, 30);

			// PUT LIVES ON THE TOP RIGHT CORNER
			g.setColor(Color.blue);
			String lifemsg = "Lives: " + life;
			g.drawString(lifemsg, 525, 30);

			// DRAW THE GUN ON THE FRAME
			gun.move();
			gun.draw(g);

			// CHECK IF THE BULLET HITS THE ENEMY
			@SuppressWarnings({ "unused", "rawtypes" })
			Iterator it1 = enemies.iterator();
			for (Enemy enemy1: enemies) {
				enemy1.infront = false;
				for (Enemy enemy2: enemies) {
					if (enemy1.xpos == enemy2.xpos && enemy1.ypos == enemy2.ypos) {
						// JUST IGNORE THEY ARE THE SAME ENEMY
					}
					if (enemy1.xpos == enemy2.xpos && enemy2.ypos > enemy1.ypos) {
						enemy1.infront = true;
					}
				}
			}
			@SuppressWarnings({ "unused", "rawtypes" })
			Iterator it2 = bullets.iterator();
			for (Enemy enemy: enemies) {
				if (enemy.ypos > 600) {
					inGame = false;
				}
				int possibility = (int) (Math.random() * fRate);
				if (possibility == 7 && enemy.infront == false) {
					if(enemy.bomb.dropped == false) {
						bomb_sound.play();
						enemy.bomb.dropped = true;
						enemy.bomb.xpos = enemy.xpos + 20;
						enemy.bomb.ypos = enemy.ypos + 25;
					}
				}
				for (Bullet bullet: bullets) {
					if( ((bullet.xpos + 10) > enemy.xpos) && (bullet.xpos < (enemy.xpos + 60)) && ((bullet.ypos - 31) < enemy.ypos) && (bullet.ypos > (enemy.ypos - 50)) ) {
						bullets.remove(bullet);
						enemies.remove(enemy);
						score++;
					}
				}
				if( ((enemy.xpos + 60) > gun.xpos) && (enemy.xpos < (gun.xpos + 20)) && (enemy.ypos > (gun.ypos - 25)) && ((enemy.ypos - 50) < gun.ypos) ) {
					life--;
					gun.xpos = 300;
					gun.ypos = 550;
					if (life == 0) {
						inGame = false;
					}
				}
			}

			// CHECK IF THE BOMB HITS THE BARRIER
			@SuppressWarnings({ "unused", "rawtypes" })
			Iterator it3 = barriers.iterator();
			for (Barrier barrier: barriers) {
				for(Bullet bullet: bullets) {
					if( ((bullet.xpos + 10) > barrier.xpos) && (bullet.xpos < (barrier.xpos + barrier.width)) && ((bullet.ypos - 31) < (barrier.ypos - barrier.height)) ) {
						bullets.remove(bullet);
					}
				}
				for(Enemy enemy: enemies) {
					if( ((enemy.bomb.xpos + 18) > barrier.xpos) && (enemy.bomb.xpos < (barrier.xpos + barrier.width)) && (enemy.bomb.ypos > (barrier.ypos - 10)) ) {
						barriers.remove(barrier);
						enemy.bomb.dropped = false;
					}
				}
			}

			// DRAW ENEMIES ON THE FRAME
			for (Enemy enemy: enemies) {
				if(enemy.bomb.dropped == true) {
					if( ((enemy.bomb.xpos + 18) > gun.xpos) && (enemy.bomb.xpos < (gun.xpos + 20)) && (enemy.bomb.ypos > (gun.ypos - 25)) ) {
						life--;
						gun.xpos = 300;
						gun.ypos = 550;
						enemy.bomb.dropped = false;
						if (life == 0) {
							inGame = false;
						}
					}
					else {
						enemy.bomb.dropping();
						enemy.bomb.draw(g);
					}
				}
				enemy.move();
				enemy.draw(g);
			}

			// DRAW BARRIERS ON THE FRAME
			for (Barrier barrier: barriers) {
				barrier.draw(g);
			}

			// DRAW BULLETS ON THE FRAME
			for (Bullet bullet: bullets) {
				if(bullet.fired == true) {
					bullet.move();
					bullet.draw(g);
				}
				else {
					bullets.remove(bullet);
				}
			}

			// GAME ENDS IF ALL ANEMIES ARE KILLED
			if(enemies.size() == 0) {
				inGame = false;
				victory = true;
			}

			// REFRESH THE GRAPHICS AND FRAME
			g.dispose();
			strategy.show();
		}
		Graphics g = frame.getGraphics();
		g.setColor(Color.blue);
		g.fillRect(0, 0, 600, 600);
		g.setColor(Color.red);

		Font pont = new Font("Serif", Font.BOLD, 28);
		FontMetrics metr = this.getFontMetrics(pont);
        g.setFont(pont);
		if(victory == true) {
			g.drawString("WINNER!!", (600 - metr.stringWidth("WINNER!!")) / 2, 600 / 2);
		}
		else {
			g.drawString("LOSER!!", (600 - metr.stringWidth("LOSER!!")) / 2,  600 / 2);
		}
	}

	public class KeyInputHandler extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				gun.dx = -1;
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				gun.dx = 1;
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				shoot_sound.play();
				Bullet bullet = new Bullet(gun.xpos + 10, gun.ypos - 20);
				bullet.fired = true;
				bullets.add(bullet);
			}
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				inMenu = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_Q) {
				invRows++;
				menurefresh = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_W) {
				if(invCols < 9) {
					invCols++;
				}
				menurefresh = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_E) {
				gbafRate++;
				menurefresh = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_R) {
				ddRate = ddRate + 3;
				menurefresh = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_T) {
				fRate = fRate + 1000;
				menurefresh = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_Y) {
				life++;
				menurefresh = true;
			}
		}
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				left_clicked = false;
				gun.dx = 0;
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				right_clicked = false;
				gun.dx = 0;
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				space_clicked = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_A) {
				invRows--;
				menurefresh = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_S) {
				invCols--;
				menurefresh = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_D) {
				gbafRate--;
				menurefresh = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_F) {
				ddRate = ddRate - 3;
				menurefresh = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_G) {
				fRate = fRate - 1000;
				menurefresh = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_H) {
				life--;
				menurefresh = true;
			}
		}
		// CLOSE WINDOW WITH ESC
		public void keyTyped(KeyEvent e) {
			if(e.getKeyChar() == 27) {
				System.exit(0);
			}
		}
	}
}

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

/**
 * Runs the game. Manages the animation loop, handles and processes input.
 * 
 */
public class GameRunner extends JPanel implements KeyListener {

	private GameState state;
	private int frames;

	/**
	 * Creates a new game runner, which contains a game state, and an entity.
	 * 
	 * @param state
	 */
	public GameRunner(GameState state) {
		this.state = state;
		this.frames = 0;
	}

	/**
	 * Attaches the display's input to us so we can handle it, and enters the
	 * game loop.
	 * 
	 * DO NOT START THIS FUNCTION IN SWING'S EVENT THREAD, BECAUSE THIS FUNCTION
	 * HANGS.
	 * 
	 * @param d
	 * @return
	 */
	public GameResult play(Display d) {
		d.possess(this, this, null, null, null);
		System.out.println("Game started\n");
		// Intro
		Sound.amazinggrace.play();
		// Main loop
		while (!state.isGameOver()) {

			try {
				Thread.sleep(Settings.FRAME_DELAY_MS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread());
			Entity.frameNumber++;
			repaint();
			if (this.frames < state.ANIMATION_DURATION_FRAMES){
				this.frames++;
			}
		}
		// Outro
		try{
			Sound.amazinggrace.stop();
		} catch(IllegalStateException E){
			// We are trying to stop sound which never started
			// (e.g. on Steele's laptop)
			// fail silently
		}
		
		return state.getResult();
	}

	@Override
	public void paintComponent(Graphics g) {
		final int hud_width = 250;
		
		
		Rectangle bound = g.getClipBounds();
		// Finds a normalized bounding box and draws that.
		// Leave some for the borders.
		
		int heightWithBorder = 2 + state.getHeight();
		int widthWithBorder = 2 + state.getWidth();
		
		int boxSize = Math.min((bound.width - hud_width) / widthWithBorder, bound.height  / heightWithBorder);
				
		// Draw the game state
		int centerx = bound.width / 2;
		int centery = bound.height / 2;
		

		// Prepare a nice border
		g.setColor(Color.BLACK);
		g.fillRect(bound.x, bound.y, bound.width, bound.height);
		
		g.setClip(centerx - boxSize * state.getWidth() / 2 - hud_width/2, centery - boxSize * state.getHeight() / 2, boxSize * state.getWidth(), boxSize * state.getHeight());
		
		state.draw(g);
		
		g.setClip(centerx + boxSize * state.getWidth() / 2 - hud_width/2, centery - boxSize * state.getHeight() / 2, hud_width,  boxSize * state.getHeight());
		drawHUD(g);
		
		g.setClip(bound.x, bound.y, bound.width, bound.height);
		this.paintChildren(g);
	}
	
	/**
	 * Draws the hud
	 * @param g graphic to draw to
	 */
	public void drawHUD(Graphics g) {
		Rectangle r = g.getClipBounds();
		g.setColor(Color.BLACK);
		g.setFont(new Font("Helvetica", Font.PLAIN, 18));
		g.fillRect(r.x, r.y, r.width, r.height);
		g.setColor(Color.WHITE);
		//g.drawString(score, r.x, r.y + 2*r.height/5);
		
		int centery = r.height/2;
		// Draw ASDF controls for Grace
		g.drawImage(Images.images.get(Images.ULDR), r.x + 90, r.y + centery - 250, 160, 160, null);
		g.drawImage(Images.images.get(Images.GRACE_D), r.x + 20, r.y + centery - 200, 70, 70, null);
		

		// Draw spacebar controls for gun and ammo count
		g.drawImage(Images.images.get(Images.SPACEBAR), r.x + 90, r.y + centery - 100, 160, 160, null);
		g.drawImage(Images.images.get(Images.GUN), r.x + 20, r.y + centery - 50, 70, 70, null);
		
		// Draw bullet count
		int bullets = Math.min(20, state.getPlayer().ammo);
		for (int i = 0; i < bullets; i++) {
			g.drawImage(Images.images.get(Images.BULLET), r.x + 120 + 10 * i, r.y + centery + 40, 10, 32, null);
		}

		g.drawString("AMMO:", r.x + 20,  r.y + centery + 70);
		g.drawString("Press ESC to Quit", r.x + 50, r.y + r.height - 20);
	}
	
	public void keyTyped(KeyEvent e) {
		// Do nothing.
	}
	
	/**
	 * Handle key presses
	 */
	public void keyPressed(KeyEvent e) {

		System.out.println("KeyPressed");
		boolean shouldTick = true;
		
		Coord playerLocation = state.getPlayer().currentlocation;
		// Check that animation has finished
		if(this.frames < state.ANIMATION_DURATION_FRAMES - 2){
			shouldTick = false;
		// Check if they are controls.
		} else if (e.getKeyCode() == Settings.CONTROLS_ESC) {
			state.stop();
		} else if (e.getKeyCode() == Settings.CONTROLS_UP || e.getKeyCode() == Settings.CONTROLS_W) {
			state.getPlayer().action = PlayerAction.Up;
		} else if (e.getKeyCode() == Settings.CONTROLS_DOWN || e.getKeyCode() == Settings.CONTROLS_S) {
			state.getPlayer().action = PlayerAction.Down;
		} else if (e.getKeyCode() == Settings.CONTROLS_LEFT || e.getKeyCode() == Settings.CONTROLS_A) {
			state.getPlayer().action = PlayerAction.Left;
		} else if (e.getKeyCode() == Settings.CONTROLS_RIGHT || e.getKeyCode() == Settings.CONTROLS_D) {
			state.getPlayer().action = PlayerAction.Right;
		} else if (e.getKeyCode() == Settings.CONTROLS_USE) {
			state.getPlayer().action = PlayerAction.Use;
		} else {
			shouldTick = false;
			if (Sound.gunshot.isPlaying()){
				Sound.gunshot.stop();
				Sound.click.stop();
			}
		}
		
		if (shouldTick) {
			if (Sound.gunshot.isPlaying()){
				Sound.gunshot.stop();
				Sound.click.stop();
			}
			this.frames = 0;
			state.tick();
			System.out.println("Ticked");
		}
	}

	public void keyReleased(KeyEvent e) {
		// Do nothing.
	}
}

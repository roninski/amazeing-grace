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
	 * Let's create our game panel and shit yo.
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
		Sound.amazinggrace.stop();
		
		
		return state.getResult();
	}

	@Override
	public void paintComponent(Graphics g) {
		final int hud_width = 150;
		
		Rectangle bound = g.getClipBounds();
		// Finds a normalized bounding box and draws that.
		// Leave some for the borders.
		
		int heightWithBorder = 2 + state.getHeight();
		int widthWithBorder = 2 + state.getWidth();
		
		int boxSize = Math.min((bound.width - hud_width) / widthWithBorder, bound.height  / heightWithBorder);
				
		// Draw the game state
		int centerx = bound.width / 2;
		int centery = bound.height / 2;
		g.setClip(centerx - boxSize * state.getWidth() / 2 - hud_width/2, centery - boxSize * state.getHeight() / 2, boxSize * state.getWidth(), boxSize * state.getHeight());

		state.draw(g);
		
		// TODO: Draw hud
		g.setClip(centerx + boxSize * state.getWidth() / 2 - hud_width/2, centery - boxSize * state.getHeight() / 2, hud_width,  boxSize * state.getHeight());
		drawHUD(g);
		
		g.setClip(bound.x, bound.y, bound.width, bound.height);
		this.paintChildren(g);
	}

	public void drawHUD(Graphics g) {
		Rectangle r = g.getClipBounds();
		g.setColor(Color.BLACK);
		g.setFont(new Font("Helvetica", Font.PLAIN, 18));
		g.fillRect(r.x, r.y, r.width, r.height);
		g.setColor(Color.WHITE);
		g.drawString("Score:", r.x, r.y + r.height/2);
	}
	
	public void keyTyped(KeyEvent e) {
		// Do nothing.
	}

	public void keyPressed(KeyEvent e) {

		System.out.println("KeyPressed");
		boolean shouldTick = true;
		
		Coord playerLocation = state.getPlayer().currentlocation;
		// Check that animation has finished
		if(this.frames < state.ANIMATION_DURATION_FRAMES - 2){
			shouldTick = false;
		// Check if they are controls.
		} else if (e.getKeyCode() == Settings.CONTROLS_UP) {
			state.getPlayer().action = PlayerAction.Up;
		} else if (e.getKeyCode() == Settings.CONTROLS_DOWN) {
			state.getPlayer().action = PlayerAction.Down;
		} else if (e.getKeyCode() == Settings.CONTROLS_LEFT) {
			state.getPlayer().action = PlayerAction.Left;
		} else if (e.getKeyCode() == Settings.CONTROLS_RIGHT) {
			state.getPlayer().action = PlayerAction.Right;
		} else if (e.getKeyCode() == Settings.CONTROLS_USE){
			state.getPlayer().action = PlayerAction.Use;
		} else {
			shouldTick = false;
			if (Sound.gunshot.isPlaying()){
				Sound.gunshot.stop();
			}
		}
		
		if (shouldTick) {
			if (Sound.gunshot.isPlaying()){
				Sound.gunshot.stop();
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

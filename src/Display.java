import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


/**
 * When initialized to a jFrame, directs flow of input. This abstracts away java's weird swing input and output mechanisms into something nicer.
 *
 */
public class Display {
	public JFrame frame;
	public JPanel panel = null;
	private KeyListener keyListener = null;
	private MouseListener mouseListener = null;
	private MouseMotionListener mouseMotionListener = null;
	private MouseWheelListener mouseWheelListener = null;
	
	/**
	 * Creates a new GameInterface attached to the specified JFrame.
	 * @param frame
	 */
	public Display(JFrame frame) {
		this.frame = frame;
	}
	
	/**
	 * Switches the JFrame to the specified JPanel, and changes the current JPanel's input listeners.
	 * @param panel
	 */
	public void possess(JPanel panel, KeyListener keyListener, MouseListener mouseListener, MouseMotionListener mouseMotionListener, MouseWheelListener mouseWheelListener) {
		// Remove previous listeners
		frame.removeKeyListener(this.keyListener);
		frame.removeMouseMotionListener(this.mouseMotionListener);
		frame.removeMouseWheelListener(this.mouseWheelListener);
		frame.removeMouseListener(this.mouseListener);
		
		// set current listeners
		this.keyListener = keyListener;
		this.mouseListener = mouseListener;
		this.mouseWheelListener = mouseWheelListener;
		this.mouseMotionListener = mouseMotionListener;
		
		// attach new listeners
		frame.addKeyListener(keyListener);
		frame.addMouseListener(mouseListener);
		frame.addMouseMotionListener(mouseMotionListener);
		frame.addMouseWheelListener(mouseWheelListener);
		final JPanel pane = panel;
		
		// Swap the JPanel
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub

				frame.getContentPane().removeAll();
				frame.validate();
				frame.add(pane);
				frame.validate();
				frame.repaint();
				System.out.println("Panel added");
				frame.requestFocus();
			}
			
		});
	}
	
	/**
	 * Get the width of the frame
	 * @return frame width
	 */
	public int getWidth() {
		return frame.getWidth();
	}
	
	/**
	 * Get the height of the frame
	 * @return frame height
	 */
	public int getHeight() {
		return frame.getHeight();
	}
	
	public static int DEFAULT_WIDTH = 1000;
	public static int DEFAULT_HEIGHT = 800;
	public static int FRAME_DELAY_MS = 20;
}

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Lists player controls for the game. Can be loaded or unloaded.
 * 
 * @author yujinwunz
 * 
 */
public class Settings {
	public static final String filename = "settings.cfg";

	// Settings and their default values loaded

	// Controls
	
	public static int CONTROLS_LEFT = KeyEvent.VK_LEFT;
	public static int CONTROLS_RIGHT = KeyEvent.VK_RIGHT;
	public static int CONTROLS_UP = KeyEvent.VK_UP;
	public static int CONTROLS_DOWN = KeyEvent.VK_DOWN;

	public static int CONTROLS_USE = KeyEvent.VK_SPACE;
	public static int CONTROLS_DROP = KeyEvent.VK_G;
	
	public static int CONTROLS_PASS = KeyEvent.VK_ENTER;

	// Misc
	public static int FRAME_DELAY_MS = 20;

	// TODO: Everything under this.
	// Attempts to load a config file for controls to override all defaults.
	public static void load() {

		try {
			FileInputStream fileIn = new FileInputStream("/tmp/employee.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Settings s = (Settings) in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception e) {
			System.out.println("Settings didn't read properly");
			save();
		}
	}

	// Saves
	public static void save() {
		try {
			FileOutputStream fileOut = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			//out.writeObject(this);
			out.close();
			fileOut.close();
		} catch (Exception e) {
			System.out.println("Settings failed to save");
		}
	}
}

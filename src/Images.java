import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class for all image assets
 * @author Yujin, Luke, Steele, Michael 
 *
 */
public class Images {
	public static ArrayList<BufferedImage> images;
	public static final int LOGO = 0;
	public static final int WASD = 1;
	public static final int ULDR = 2;
	public static final int HERO = 3;
	public static final int WIN = 4;
	public static final int LOSE = 5;
	public static final int NOT_WALL = 6;
	public static final int WALL = 7;
	public static final int ENEMY = 8;
	public static final int DUMBENEMY = 9;
	public static final int EXIT = 10;
	public static final int START = 11;
	public static final int GRACE_L = 12;
	public static final int GRACE_U = 13;	
	public static final int GRACE_D = 14;	
	public static final int GRACE_R = 15;
	public static final int BOMB = 16;
	public static final int BLOOD = 17;

	public static final int GRACE_BLOOD_L = 18;
	public static final int GRACE_BLOOD_U = 19;	
	public static final int GRACE_BLOOD_D = 20;	
	public static final int GRACE_BLOOD_R = 21;
	
	public static final int SPACEBAR = 22;
	public static final int GUN = 23;
	
	public static final int BULLET = 24;
	public static final int AMMO = 25;

	public Images() {
		images = new ArrayList<BufferedImage>();
		try {
			images.add(ImageIO.read(new File("img/logo-2.gif")));
			images.add(ImageIO.read(new File("img/wasd.jpg")));
			images.add(ImageIO.read(new File("img/uldr.jpg")));
			images.add(ImageIO.read(new File("img/grace.gif")));
			images.add(ImageIO.read(new File("img/win.jpg")));
			images.add(ImageIO.read(new File("img/lose.jpg")));
			images.add(ImageIO.read(new File("img/notwall.jpg")));
			images.add(ImageIO.read(new File("img/rock.jpg")));
			images.add(ImageIO.read(new File("img/enemy.gif")));
			images.add(ImageIO.read(new File("img/dumbenemy.gif")));
			images.add(ImageIO.read(new File("img/exit.gif")));			
			images.add(ImageIO.read(new File("img/start.gif")));
			images.add(ImageIO.read(new File("img/gracegun-l.gif")));			
			images.add(ImageIO.read(new File("img/gracegun-u.gif")));			
			images.add(ImageIO.read(new File("img/gracegun-d.gif")));			
			images.add(ImageIO.read(new File("img/gracegun-r.gif")));			
			images.add(ImageIO.read(new File("img/bomb.gif")));		
			images.add(ImageIO.read(new File("img/blood.gif")));

			images.add(ImageIO.read(new File("img/gracegun-blood-l.gif")));			
			images.add(ImageIO.read(new File("img/gracegun-blood-u.gif")));			
			images.add(ImageIO.read(new File("img/gracegun-blood-d.gif")));			
			images.add(ImageIO.read(new File("img/gracegun-blood-r.gif")));
			
			images.add(ImageIO.read(new File("img/spacebar.jpg")));
			images.add(ImageIO.read(new File("img/gun.png")));
			images.add(ImageIO.read(new File("img/bullet.png")));
			images.add(ImageIO.read(new File("img/ammo.png")));
		} catch (IOException ex) {
			System.out.println("missing file");
			System.out.println(images.size());
		}
	}
}

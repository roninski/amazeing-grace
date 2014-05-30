import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Class for all sound assets
 * @author Yujin, Luke, Steele, Michael 
 *
 */
public class Sound {
	public static Sound amazinggrace = new Sound("../sound/AmazingGrace.wav", true);
	public static Sound gunshot = new Sound("../sound/gunshot.wav", false);
	public static Sound click = new Sound("../sound/click.wav", false);
	
	private String filename;
	private AudioInputStream audioInputStream;
	private Clip clip;
	private boolean willLoop;
	private boolean isPlaying = false;
	
	/**
	 * Sound constructor.
	 * @param s The relative file directory for the sound
	 * @param loop Whether the file will loop when it finishes.
	 */
	Sound(String s, boolean loop){
		this.filename = s;
		this.willLoop = loop;
		try{
			this.audioInputStream =AudioSystem.getAudioInputStream(this.getClass().getResource(filename));
			this.clip = AudioSystem.getClip();
			this.clip.open(audioInputStream);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Play the track
	 */
	public void play(){
		try{
			clip.start();
			if (willLoop){
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			isPlaying = true;
		} catch(Exception e){  
			e.printStackTrace();
		}
	}
	
	/**
	 * Stop the track
	 */
	public void stop(){
		clip.stop();
		clip.setFramePosition(0);
		isPlaying = false;
	}
	
	public boolean isPlaying(){
		return isPlaying;
	}
	
}

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	public static Sound amazinggrace = new Sound("../sound/AmazingGrace.wav");
	
	String filename;
	AudioInputStream audioInputStream;
	Clip clip;
	
	Sound(String s){
		this.filename = s;
		try{
			this.audioInputStream =AudioSystem.getAudioInputStream(this.getClass().getResource(filename));
			this.clip = AudioSystem.getClip();
			this.clip.open(audioInputStream);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void play(){
		try{
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch(Exception e){  
			e.printStackTrace();
		}
	}
	
	public void stop(){
		clip.stop();
	}
	
}

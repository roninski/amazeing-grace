
/**
 * Represents the progress made in the current game.
 * @author yujinwunz
 *
 */
public class Progress {
	static final int nHighScores = 10;
	static final String filename = "score.cfg";
	static int levelsCompleted = 0;
	static int levelScore[][] = new int[Levels.nLevels][nHighScores];
	
	
	
	static {
		load();
	}
	
	public static void load() {
		
	}
	
	public static void save() {
		
	}
}

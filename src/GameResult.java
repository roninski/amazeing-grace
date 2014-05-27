/**
 * Represents the result of playing one game.
 * 
 * @author yujinwunz
 * 
 */
public class GameResult {
	public int getPoints() {
		return points;
	}

	public boolean isFinished() {
		return finished;
	}
	
	public boolean isWon() {
		return won;
	}

	public GameResult(int points, boolean finished, boolean won) {
		super();
		this.points = points;
		this.finished = finished;
		this.won = won;
	}

	private boolean finished;
	private int points;
	private boolean won;
}

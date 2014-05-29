import java.awt.Dimension;
import java.util.Random;

import javax.swing.JFrame;

public class Amazeing {
	private static Display gameInterface;
	
	private static void createAndShowGUI() {
		// load images
		Images images = new Images();
		// Create a jframe
		JFrame frame = new JFrame("Amazeing Grace");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(Display.DEFAULT_WIDTH, Display.DEFAULT_HEIGHT);
		frame.setMinimumSize(new Dimension(Display.DEFAULT_WIDTH, Display.DEFAULT_HEIGHT));
		
		// Create a game interface
		gameInterface = new Display(frame);
		frame.setVisible(true);
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				// Music source: https://www.youtube.com/watch?v=DFDdtruytg4
				

				enterMainMenu();
			}
		});
		
		t.start();
	}
	
	private static void enterMainMenu() {
		// Attach a main menu.
	
		while (true) {
			Menu main = new Menu("Amazeing");
			main.addItem("Quick Play", "quick play");
			main.addItem("Level Select", "level select");
			//main.addItem("Leaderboard", "leaderboard");
			main.addItem("Quit", "quit");
			String result = main.getInput(gameInterface);
			
			if (result.equals("quick play")) {
				enterQuickplay();
			} else if (result.equals("level select")) {
				enterLevelSelect();
			} else if (result.equals("leaderboard")) {
				
			} else if (result.equals("quit")) {

			}
		}
	}
	
	private static void leaderboard() {
		// Load the leaderboard, display it in a JPanel.
		
	}
	
	private static void enterQuickplay() {
		while (true) {
			Menu quickplay = new Menu("Select difficulty");
			quickplay.addItem("Easy", "easy");
			quickplay.addItem("Medium", "medium");
			quickplay.addItem("Hard", "hard");
			quickplay.addItem("Back", "back");
			
			String response = quickplay.getInput(gameInterface);
			
			if (response.equals("easy")) {
				startGame(Difficulty.easy);
			} else if (response.equals("medium")) {
				startGame(Difficulty.medium);
			} else if (response.equals("hard")) {
				startGame(Difficulty.hard);
			} else if (response.equals("back")) {
				return;
			}
		}
	}
	
	private static void enterLevelSelect() {
		while (true) {
			Menu options = new Menu("Options");
			
			// Add an option for every level
			for (int i = 0; i < Levels.names.length; i++) {
				final int workaround = i;
				options.addItem(Levels.names[i], String.format("%d", i));
			}
			options.addItem("Back", "back");
			
			String result = options.getInput(gameInterface);
			if (result.equals("back")) {
				break;
			} else {
				int level = Integer.parseInt(result);
				playLevel(level);
			}
		}
	}
	
	/**
	 * Launches a game in the current window.
	 * With the given difficulty
	 */
	private static void startGame(Difficulty difficulty) {
		GameState newGame = null;
		Random r = new Random();
		long seed = r.nextLong();
		
		while (true) {
			if (difficulty == Difficulty.easy) {
				newGame = new GameState(new QuickPlayCreator(11, 11, 10, -1, seed, difficulty));
			} else if (difficulty == Difficulty.medium){
				newGame = new GameState(new QuickPlayCreator(21, 21, 20, -1, seed, difficulty));
			} else {
				newGame = new GameState(new QuickPlayCreator(31, 31, 40, 0, seed, difficulty));
			}
			GameRunner gr = new GameRunner(newGame);
			GameResult result = gr.play(gameInterface);
			
			// Gameover menu, option to replay
			
			final Object lock = new Object();
			Menu menu = null;
			if (result.isWon()) {
				menu = new Menu("You won!");
			} else {
				menu = new Menu("Game over!");
			}
			menu.addItem("Replay level", "replay");
			menu.addItem("Back to main menu", "back");
			
			String menuresult = menu.getInput(gameInterface);

			if (menuresult == "back") {
				break;
			}
		}

		enterMainMenu();
	}
	
	
	
	static int menuSelection = 0;
	/**
	 * Launches a game in the current window.
	 * with the given level
	 */
	private static void playLevel(int level) {
		GameState newGame = new GameState(new LevelLoader(level));
		GameRunner gr = new GameRunner(newGame);
		GameResult result = gr.play(gameInterface);
		
		submitCampaignHighscore ();
	}

	private static void submitCampaignHighscore () {
		// TODO: check if we qualify. If yes, ask for name, enter scoreboard.
		// If no, main menu.
		
		// scoring goes: 1. levels passed 2. total score in all passed levels
		enterMainMenu();
	}
	
	
	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}

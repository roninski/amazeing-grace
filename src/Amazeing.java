import java.awt.Dimension;
import java.util.Random;

import javax.swing.JFrame;

public class Amazeing {
	private static Display gameInterface;
	
	private static void createAndShowGUI() {
		// Create a jframe
		JFrame frame = new JFrame("Amazeing! (the name... it... burns...)");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(Display.DEFAULT_WIDTH, Display.DEFAULT_HEIGHT);
		frame.setMinimumSize(new Dimension(Display.DEFAULT_WIDTH, Display.DEFAULT_HEIGHT));
		
		// Create a game interface
		gameInterface = new Display(frame);

		frame.setVisible(true);
		
		enterMainMenu();
	}
	
	private static void enterMainMenu() {
		// Attach a main menu.
		Menu main = new Menu("Amazeing");
		main.addItem("Quick Play", new Runnable() {
			public void run() {
				enterQuickplay();
			}
		});
		main.addItem("Level Select", new Runnable() {

			public void run() {
				enterLevelSelect();
			}
			
		});
		main.addItem("Leaderboard", new Runnable() {
			public void run() {
				leaderboard();
			}
		});
		main.attach(gameInterface);
		
	}
	
	private static void leaderboard() {
		// Load the leaderboard, display it in a JPanel.
		
	}
	
	private static void enterQuickplay() {
		Menu quickplay = new Menu("Select difficulty");
		quickplay.addItem("Easy", new Runnable() {

			public void run() {
				startGame(Difficulty.easy);
			}
			
		});
		quickplay.addItem("Medium", new Runnable() {

			public void run() {
				startGame(Difficulty.medium);
			}
			
		});
		quickplay.addItem("Hard", new Runnable() {

			public void run() {
				startGame(Difficulty.hard);
			}
			
		});
		quickplay.addItem("Back", new Runnable() {

			public void run() {
				enterMainMenu();
			}
			
		});
		
		quickplay.attach(gameInterface);
	}
	
	private static void enterLevelSelect() {
		Menu options = new Menu("Options");
		
		// Add an option for every level
		for (int i = 0; i < Levels.names.length; i++) {
			final int workaround = i;
			options.addItem(Levels.names[i], new Runnable() {
				public void run() {
					playLevel(workaround);
				}
				
			});
		}
		options.addItem("Back", new Runnable() {
			public void run() {
				enterMainMenu();
			}
			
		});
		
		options.attach(gameInterface);
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
				newGame = new GameState(new PrimsCreator(10, 10, seed));
			} else if (difficulty == Difficulty.medium){
				newGame = new GameState(new PrimsCreator(20, 20, seed));
			} else {
				newGame = new GameState(new PrimsCreator(30, 30, seed));
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
			menu.addItem("Replay level", new Runnable() {
				public void run() {
					// TODO Auto-generated method stub
					menuSelection = 0;
					synchronized (lock) {
						lock.notify();
					}
				}
			});
			
			menu.addItem("Back to main menu", new Runnable() {
				public void run() {
					// TODO Auto-generated method stub
					menuSelection = 1;
					synchronized (lock) {
						lock.notify();
					}
				}
			});
			
			menu.attach(gameInterface);

			// Wait for selection to be made
			synchronized(lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (menuSelection == 0) {
				// replay
			} else {
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
		// TODO: check if we qualify. If yes, ask for name, enter scorebaord.
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

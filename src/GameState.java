import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GameState {
	private boolean stopped = false;
	private boolean won = false;
	private int points = 0;
	private GameMap map;

	private int lives;

	private Player player;
	
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Creates a new game state with the given map creator and stuff.
	 * @param strategy
	 * @param width
	 * @param height
	 */
	public GameState(MapCreator strategy) {
		this.map = new GameMap(strategy);
		
		// Create first life.
		player = new Player(map);
		player.currentlocation = map.getSpawnLocation();
		map.addEntity(player);
		
		lives = 1;
	}
	
	/**
	 * Load saved state from a file please!
	 * @param filename
	 */
	public GameState(String filename) {
		
	}

	/**
	 * Sets the game's isGameOver to true.
	 */
	public void stop() {
		stopped = true;
	}

	public void draw(Graphics g) {
		// for now, a rect
		Rectangle r = g.getClipBounds();
		int boxwidth = r.width / map.getWidth();
		int boxheight = r.height / map.getHeight();
		g.drawImage(Images.images.get(Images.NOT_WALL), r.x, r.y, r.width, r.height, null);
		/*for (int i = 0; i < r.width; i += boxwidth) {
			for (int j = 0; j < r.height; j += boxheight) {
				g.setClip(i, j, boxwidth, boxheight);
				g.drawImage(Images.images.get(Images.NOT_WALL), i + r.width, j + r.height, r.width, r.height, null);
			}
		}*/
		
		// Draw children. Make sure we do it in order
		ArrayList <Entity> entities = map.entityList();
		Collections.sort(entities, new Comparator<Entity>() {

			public int compare(Entity o1, Entity o2) {
				if (o1.drawIndex < o2.drawIndex) return -1;
				if (o1.drawIndex > o2.drawIndex) return 1;
				return 0;
			}
			
		});
		
		for (Entity e : entities) {
			Coord old = e.prevlocation;
			Coord goal = e.currentlocation;
			
			double ratio = Math.min(1.0, ((double)(Entity.frameNumber - e.lastTickFrame))/ ANIMATION_DURATION_FRAMES);
			if (old.equals(goal)) {
				ratio = 1.0; // avoid precision problems
			}
			// Calculate
			double newx = old.getX() * (1-ratio) + goal.getX() * ratio;
			double newy = old.getY() * (1-ratio) + goal.getY() * ratio;
			
			
			g.setClip((int)(r.x + newx * boxwidth), (int)(r.y + newy * boxheight), boxwidth, boxheight);
			e.draw(g);
		}
	}

	/**
	 * Makes the gamestate move by one state.
	 * 
	 */
	public void tick() {
		// Tick everyone.
		ArrayList <Entity> entities = map.entityList();
		Collections.sort(entities, new Comparator<Entity>() {

			public int compare(Entity o1, Entity o2) {
				if (o1.tickIndex < o2.tickIndex) return -1;
				if (o1.tickIndex > o2.tickIndex) return 1;
				return 0;
			}
			
		});
		for (Entity e : entities) {
			e.ontick();
			
			// check that the square can be moved to
			ArrayList <Entity> inDest = map.getInCoord(e.nextlocation);
			
			boolean shouldBump = false;
			for (Entity a : inDest) {
				if (a.shouldBumpInto(e) || e.shouldBumpInto(a)) {
					shouldBump = true;
				}
			}
			if (e.nextlocation.getX() < 0 || e.nextlocation.getX() >= map.getWidth()) {
				shouldBump = true;
			}
			if (e.nextlocation.getY() < 0 || e.nextlocation.getY() >= map.getHeight()) {
				shouldBump = true;
			}
			
			if (shouldBump && !e.nextlocation.equals(e.currentlocation)) {
				// collide with bump, bump, then collide with current location
				for (Entity a : inDest) {
					if (a.shouldBumpInto(e) || e.shouldBumpInto(a)) {
						a.collide(e);
						e.collide(a);
					}
				}
				e.nextlocation = e.currentlocation;
			} 
			for (Entity a : map.getInCoord(e.nextlocation)) {
				a.collide(e);
				e.collide(a);
			}
			
			// Commit this guy
			map.updateEntityLocation(e);
		}
		
		// check for deaths
		if (player.isActive == false) {
			player = new Player(map);
			player.currentlocation = map.getSpawnLocation();
			map.addEntity(player);
			this.lives --;
			
		}
		if (player.currentlocation.equals(map.getExitLocation())) {
			won = true;
		}
	}

	public boolean isGameOver() {
		if (stopped) {
			return true;
		}
		if (lives <= 0) {
			return true;
		}
		if (won) {
			return true;
		}
		return false;
	}

	public GameResult getResult() {
		if (!isGameOver()) {
			return null;
		} else if (stopped) {
			return new GameResult(points, false, won);
		} else {
			return new GameResult(points, true, won);
		}
	}

	/**
	 * Returns the height of this game
	 * 
	 * @return
	 */
	public int getHeight() {
		return map.getHeight();
	}

	/**
	 * Returns the width of this game
	 * 
	 * @return
	 */
	public int getWidth() {
		return map.getWidth();
	}
	
	public final int ANIMATION_DURATION_FRAMES = 10;
}

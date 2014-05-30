import java.util.ArrayList;
import java.util.Random;


public class QuickPlayCreator extends PrimsCreator {
	Difficulty difficulty;
	
	public QuickPlayCreator(int width, int height, int minWallSize,
			int numJumps, long seed, Difficulty difficulty) {
		super(width, height, minWallSize, numJumps, seed);
		this.difficulty = difficulty;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ArrayList<Entity> create() {
		ArrayList<Entity> entities =  super.create();
		int numRandom, numPathfinding, numAmmoBox;
		Random r = new Random();
		if (this.difficulty == Difficulty.hard) {
			numRandom = 20;
			numPathfinding = 15;
			numAmmoBox = 5;
		} else if (this.difficulty == Difficulty.medium){
			numRandom = 10;
			numPathfinding = 5;
			numAmmoBox = 1;
		} else {
			numRandom = 4;
			numPathfinding = 0;
			numAmmoBox = 1;
		}
		
		int x = 0;
		int y = 0;
		while (numPathfinding > 0){
			x = Math.abs(r.nextInt()%width);
			y = Math.abs(r.nextInt()%height);
			boolean unused = true;
			for (Entity e : entities){
				if (e.getCurrentLocation().equals(new Coord(x,y)) ||
						new Coord(x,y).euclidianDistance(new Coord(0,0)) < 12){
					unused = false;
					break;
				}
			}
			if (unused){
				Entity e = new PathfindingEnemy(null);
				e.currentlocation = new Coord(x, y);
				e.nextlocation = new Coord(x,y);
				entities.add(e);
				numPathfinding--;
			}
		}
		while (numRandom > 0){
			x = Math.abs(r.nextInt()%width);
			y = Math.abs(r.nextInt()%height);
			boolean unused = true;
			for (Entity e : entities){
				if (e.getCurrentLocation().equals(new Coord(x,y)) ||
						new Coord(x,y).euclidianDistance(new Coord(0,0)) < 4){
					unused = false;
					break;
				}
			}
			if (unused){
				Entity e = new RandomEnemy(null);
				e.currentlocation = new Coord(x, y);
				e.nextlocation = new Coord(x,y);
				entities.add(e);
				numRandom--;
			}
		}
		while (numAmmoBox > 0){
			x = Math.abs(r.nextInt()%width);
			y = Math.abs(r.nextInt()%height);
			boolean unused = true;
			for (Entity e : entities){
				if (e.getCurrentLocation().equals(new Coord(x,y)) ||
						new Coord(x,y).euclidianDistance(new Coord(0,0)) < 4){
					unused = false;
					break;
				}
			}
			if (unused){
				Entity e = new AmmoBox(null);
				e.currentlocation = new Coord(x, y);
				e.nextlocation = new Coord(x,y);
				entities.add(e);
				numAmmoBox--;
			}
		}
		
		return entities;
	}
}

import java.util.ArrayList;
import java.util.Random;


public class PrimsCreator implements MapCreator {

	private int width, height;
	private long seed;
	
	public PrimsCreator(int width, int height, long seed) {
		this.width = width;
		this.height = height;
		this.seed = seed;
	}
	
	public ArrayList<Entity> create() {
		// random
		ArrayList <Entity> ret = new ArrayList <Entity>();
		Random r = new Random(seed); // Ensures this is deterministic. The same map is reproducable with the same seed.
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (r.nextDouble() < 0.1) {
					Wall w = new Wall(null);
					w.currentlocation = new Coord(i, j);
					ret.add(w);
					
				}
			}
		}
		
		Spawn spawn = new Spawn(null);
		spawn.currentlocation = new Coord(0, 0);
		Exit exit = new Exit(null);
		exit.currentlocation = new Coord(width-1, height-1);
		ret.add(spawn);
		ret.add(exit);
		
		return ret;
	}

	public int getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return width;
	}
	
}

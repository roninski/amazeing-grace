import java.util.ArrayList;

/**
 * A map creator which loads pre-defined levels.
 * @author yujinwunz
 *
 */
public class LevelLoader implements MapCreator{
	public LevelLoader(int level) {
		this.level = level;
		data = Levels.levels[level];
		
	}
	private int level;
	private String[] data;
	
	public ArrayList<Entity> create() {
		
		ArrayList<Entity> ret = new ArrayList<Entity>();
		if (data == null) return ret;
		for (int y = 0; y < data.length; y++) {
			for (int x = 0; x < data[y].length(); x++) {
				Entity toAdd = null;
				if (data[y].charAt(x) == 'S') { // spawn
					toAdd = new Spawn(null);
				} else if (data[y].charAt(x) == 'E') { // exit
					toAdd = new Exit(null);
				} else if (data[y].charAt(x) == '*') { // wall
					toAdd = new Wall(null);
				} else if (data[y].charAt(x) == 'R') { // wall
					toAdd = new RandomEnemy(null);
				} else if (data[y].charAt(x) == 'P'){
					toAdd = new PathfindingEnemy(null);
				}
				
				if (toAdd != null) {
					toAdd.currentlocation = new Coord(x, y);
					ret.add(toAdd);
				}
			}
		}
		return ret;
	}

	public int getHeight() {
		return data.length;
	}

	public int getWidth() {
		return data[0].length();
	}

}

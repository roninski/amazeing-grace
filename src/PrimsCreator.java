import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
/**
 * A map creator for generating maps using Prim's Maze Generation Algorithm
 * @author Yujin, Luke, Steele, Michael 
 *
 */
public class PrimsCreator implements MapCreator {

	int numWallSize, numJumps;
	int width, height;
	long seed;

	public PrimsCreator(int width, int height, int minWallSize, int numJumps,
			long seed) {
		this.numWallSize = minWallSize;
		this.numJumps = numJumps;
		this.width = width;
		this.height = height;
		this.seed = seed;
	}

	@Override
	public ArrayList<Entity> create() {
		// TODO Auto-generated method stub
		GameMap m = new GameMap(width, height);
		int factor = numWallSize * 2 + 3;

		for (int i = 0; i < m.getWidth(); i++) {
			for (int j = 0; j < m.getHeight(); j++) {
				Wall w = new Wall(null);
				w.currentlocation = w.nextlocation = new Coord(i, j);
				m.addEntity(w);
			}
		}

		// generate using prim's
		initUnion(m.getWidth() * m.getHeight());
		ArrayList<Edge> edges = new ArrayList<Edge>();
		for (int i = 0; i < m.getWidth(); i += 2) {
			for (int j = 0; j < m.getHeight(); j += 2) {
				if (i <= m.getWidth() - 3)
					edges.add(new Edge(i, j, i + 2, j));
				if (j <= m.getWidth() - 3)
					edges.add(new Edge(i, j, i, j + 2));
			}
		}

		Collections.shuffle(edges);

		for (Edge i : edges) {
			Integer a = i.x1 + i.y1 * m.getWidth();
			Integer b = i.x2 + i.y2 * m.getWidth();
			if (isSameGroup(a, b)) {
				if (m.shortestPath(new Coord(i.x1, i.y1), new Coord(i.x2, i.y2)).size() < factor) 
					continue;
					
				if (numJumps == 0)
					continue;
				numJumps--;
			}
			join(a, b);
			for (int x = i.x1; x <= i.x2; x++) {
				for (int y = i.y1; y <= i.y2; y++) {
					for (Entity e : m.getInCoord(new Coord(x, y))) {
						e.isActive = false;
					}
				}
			}
		}
		
		ArrayList <Entity> ret = new ArrayList<Entity>();
		Spawn s = new Spawn(null);
		s.currentlocation = new Coord(0, 0);
		Exit e = new Exit(null);
		e.currentlocation = new Coord(width-1, height-1);
		ret.add(s);
		ret.add(e);
		
		for (Entity i : m.entityList()) {
			Wall w = new Wall(null);
			w.currentlocation = i.currentlocation;
			
			ret.add(w);
		}
		return ret;
	}

	// union find
	HashMap<Integer, Integer> parent = new HashMap<Integer, Integer>();
	HashMap<Integer, Integer> rank = new HashMap<Integer, Integer>();

	Integer find(Integer a) {
		if (parent.get(a) != a)
			parent.put(a, find(parent.get(a)));
		return parent.get(a);
	}

	void join(Integer a, Integer b) {
		a = find(a);
		b = find(b);
		if (rank.get(a) < rank.get(b)) {
			parent.put(b, a);
		} else if (rank.get(a) > rank.get(b)) {
			parent.put(a, b);
		} else {
			rank.put(b, rank.get(b) + 1);
			parent.put(b, a);
		}
	}

	boolean isSameGroup(Integer a, Integer b) {
		return find(a) == find(b);
	}

	void initUnion(int size) {
		for (int i = 0; i < size; i++) {
			rank.put(i, 0);
			parent.put(i, i);
		}
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

}

class Edge {
	int x1, y1, x2, y2;
	public Edge(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
}

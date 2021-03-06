import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents a game map.
 * 
 * @author Yujin, Luke, Steele, Michael 
 * 
 */
public class GameMap {
	private HashMap<Coord, ArrayList<Entity>> byCoord = new HashMap<Coord, ArrayList<Entity>>();
	private int width, height;
	private ArrayList <Entity> entities = new ArrayList <Entity>();
	
	private Coord spawn = null;
	private Coord exit = null;
	
	/**
	 * Instantiates a new map! using the given map creating strategy.
	 * 
	 * @param strategy
	 */
	public GameMap(MapCreator strategy) {
		this.width = strategy.getWidth();
		this.height = strategy.getHeight();
		
		for (Entity e : strategy.create()) {
			if (e.getClass() == Spawn.class) {
				spawn = e.currentlocation;
			} else if (e.getClass() == Exit.class) {
				exit = e.currentlocation;
			}
			
			addEntity(e);
		}
	}
	
	public GameMap(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 *  Safely adds an entity into the map
	 * @param e
	 */
	public void addEntity(Entity e) {
		entities.add(e);
		if (!byCoord.containsKey(e.currentlocation)) {
			byCoord.put(e.currentlocation, new ArrayList<Entity>());
		}
		
		byCoord.get(e.currentlocation).add(e);
		
		e.setMap(this);
	}
	
	/** 
	 * Changes the entity's location in the byCoords thing.
	 * This should be the only function which changes coordinates of an entity.
	 * @param e
	 */
	public void updateEntityLocation(Entity e) {
		if (entities.contains(e) == false) {
			System.out.println("Warning: trying to update entity that is nor part of map");
			return;
		}
		
		// remove from old list
		byCoord.get(e.currentlocation).remove(e);
		
		// add to new list
		if (byCoord.containsKey(e.nextlocation) == false) {
			byCoord.put(e.nextlocation, new ArrayList<Entity>());
		}
		byCoord.get(e.nextlocation).add(e);
		
		// update coord
		e.currentlocation = e.nextlocation;
	}
	
	/**
	 * Returns a list of entities on the specified coordinate
	 * @param c the coordinate
	 * @return a list of entities on the coordinate
	 */
	public ArrayList<Entity> getInCoord(Coord c) {
		if (!byCoord.containsKey(c)) {
			byCoord.put(c, new ArrayList<Entity>());
		}
		// Nows a good time to get rid of dead crap
		cleanEntityList(byCoord.get(c));
		return (ArrayList<Entity>)byCoord.get(c).clone();
	}
	
	private void cleanEntityList (ArrayList <Entity> list) {
		Iterator<Entity> i = list.iterator();
		while(i.hasNext()){
			if (i.next().isActive == false) i.remove();
		}
	}
	
	/**
	 * The spawn location is where we start our lives.
	 * @return the spawn location
	 */
	public Coord getSpawnLocation() {
		return spawn;
	}
	
	/**
	 * The exit location is where we win.
	 * @return the exit
	 */
	public Coord getExitLocation() {
		return exit;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public Entity getPlayer(){
		for (Entity entity : this.entities){
			if (entity.getType() == EntityType.Player){
				return entity;
			}
		}
		return null;
	}
	
	/**
	 * Gives shortest path using only walls as collisions.
	 * @param from coordinate from
	 * @param to coordinate to
	 * @return shortest path as a list
	 */
	public ArrayList <Coord> shortestPath(Coord from, Coord to) {
		return shortestPath(from, to, null, new ArrayList<Entity>());
	}
	
	/**
	 * Finds the shortest path from "from" to "to". The path will exclude "from".
	 * 
	 * @param from The starting coordinate
	 * @param to The ending coordinate
	 * @param reference
	 *            The entity we're testing for collisions
	 * @param ignore
	 *            List of entities to ignore when colliding. Include, for
	 *            example, the enemy which is trying to find the path, into this
	 *            list. null ignores no items.
	 * @return A list of coordinates in order, representing the shortest path.
	 *         If there is no path, returns null.
	 */
	public ArrayList<Coord> shortestPath(Coord from, Coord to,
			Entity reference, ArrayList<Entity> ignore) {
		// Start BFS.
		
		Queue <SearchState> bfs = new LinkedList <SearchState>();
		SearchState initial = new SearchState (from, 0, null);
		bfs.add(initial);
		
		HashSet <Coord> closed = new HashSet <Coord>();
		
		int dx[] = {-1, 0, 1, 0};
		int dy[] = {0, 1, 0, -1};
		
		while (bfs.size() != 0) {
			SearchState top = bfs.poll();
			if (closed.contains(top.location)){
				continue;
			}
			closed.add(top.location);
			
			if (top.location.getX() < 0 || top.location.getX() >= width) continue;
			if (top.location.getY() < 0 || top.location.getY() >= height) continue;
			
			if (!canEnter(top.location, reference, ignore)) {
				continue;
			}
			
			if (top.location.equals(to)) {
				// backtrace and return
				ArrayList <Coord> path = new ArrayList <Coord> ();
				SearchState current = top;
				while (current != null) {
					path.add(0, current.location);
					current = current.previous;
				}
				return path;
			}
			
			for (int i = 0; i < 4; i++) {
				bfs.add(new SearchState(new Coord(top.location.getX() + dx[i], top.location.getY() + dy[i])  ,top.distance + 1 ,top));
			}
		}
		// if there was a path we would have found it in the loop
		return null;
	}
	
	private class SearchState {
		Coord location;
		int distance;
		SearchState previous;
		
		public SearchState(Coord location, int distance, SearchState previous) {
			super();
			this.location = location;
			this.distance = distance;
			this.previous = previous;
		}
	}
	
	/**
	 * Check if a square can be entered
	 * @param check The coordinate to check
	 * @param reference Entities to ignore when cehcking
	 * @return True if it can enter, otherwise false
	 */
	public boolean canEnter (Coord check, Entity reference) {
		return canEnter(check, reference, new ArrayList<Entity>());
	}
	
	/**
	 * Checks if a square can be entered
	 * @param check The coordinate to check
	 * @param reference The entity trying to enter
	 * @param ignore Entities to ignore when checking
	 * @return True if it can be entered, otherwise false
	 */
	public boolean canEnter (Coord check, Entity reference, ArrayList <Entity> ignore) {
		if (reference == null) {
			// Special case. Collide only with walls.
			for (Entity e : getInCoord(check)) {
				if (ignore.contains(e)) {
					continue;
				}
				if (e.type == EntityType.Wall) {
					return false; // found collision
				}
			}
		} else {
			for (Entity e : getInCoord(check)) {
				if (ignore.contains(e)) {
					continue;
				}
				if (e.shouldBumpInto(reference) || reference.shouldBumpInto(e)) {
					return false; // found collision
				}
			}
		}
		return true; // no collision
	}
	
	public boolean hasWall (Coord check) {
		return false; // TODO
	}
	
	/**
	 * Get a list of entities
	 * @return list of entities
	 */
	public ArrayList <Entity> entityList() {
		cleanEntityList(entities);
		return (ArrayList <Entity>)entities.clone();
	}
}

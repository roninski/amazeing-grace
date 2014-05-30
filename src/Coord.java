/**
 * A custom coordinate class containing L2 norms useful 
 * in geometry and pathfinding.
 *
 * @author Yujin, Luke, Steele, Michael 
 *
 */
public class Coord {
	
	/**
	 * Access x value of coordinate
	 * @return x value
	 */
	public int getX() {
		return x;
	}

        /**
	 * Access y value of coordinate
	 * @return y value
	 */
	public int getY() {
		return y;
	}

       	/**
	 * Constructs a coordinate from x and y values
	 */
	public Coord(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	private int x, y;
	
	@Override 
	public int hashCode() {
		return x * 100000 + y;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o.getClass().isInstance(this)) {
			Coord c = (Coord)o;
			if (c.x == this.x && c.y == this.y) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Calculate the euclidean distance to another coordinate
	 * @return euclidean distance
	 */
	public double euclidianDistance(Coord c){
		return Math.sqrt((Math.pow(this.getX() - c.getX(),2) 
				+ Math.pow(this.getY() - c.getY(), 2)));
	}
	
	/**
	 * Calculate the manhattan distance to another coordinate
	 * @return manhattan distance
	 */
	public int manhattanDistance(Coord c){
		return Math.abs(this.getX() - c.getX()) + Math.abs(this.getY() - c.getY()); 
	}
}

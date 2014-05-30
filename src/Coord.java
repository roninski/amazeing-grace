/**
 * A coordinate class
 * @author Yujin, Luke, Steele, Michael 
 *
 */
public class Coord {
	/**
	 * Get the x value
	 * @return x value
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get the y value
	 * @return y value
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Set up Coord
	 * @param x the x coordinate
	 * @param y the y coordinate
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
	 * Get the euclidian distance between two coordinates
	 * @param c the coordinate to compare to
	 * @return the distance
	 */
	public double euclidianDistance(Coord c){
		return Math.sqrt((Math.pow(this.getX() - c.getX(),2) 
				+ Math.pow(this.getY() - c.getY(), 2)));
	}
	
	/**
	 * Get the manhattan distance between two coordinates
	 * @param c the coordinate to compare to
	 * @return the distance
	 */
	public int manhattanDistance(Coord c){
		return Math.abs(this.getX() - c.getX()) + Math.abs(this.getY() - c.getY()); 
	}
}

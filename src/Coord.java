
public class Coord {
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

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
	
	public double euclidianDistance(Coord c){
		return Math.sqrt((Math.pow(this.getX() - c.getX(),2) 
				+ Math.pow(this.getY() - c.getY(), 2)));
	}
	
	public int manhattanDistance(Coord c){
		return Math.abs(this.getX() - c.getX()) + Math.abs(this.getY() + c.getY()); 
	}
}

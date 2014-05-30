import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 * An enemy which moves semi-randomly (patrolling with current implemenation)
 * @author Yujin, Luke, Steele, Michael 
 *
 */
public class RandomEnemy extends Entity {
	public RandomEnemy(GameMap map) {
		super(map, DRAW_INDEX_AGENT, TICK_INDEX_AGENT, EntityType.Enemy);

	}

	@Override
	public void tick() {
		// move randomly.
		if (this.direction == Direction.Down) {
			nextlocation = new Coord(currentlocation.getX() + 0,
					currentlocation.getY() + 1);
		} else if (this.direction == Direction.Left) {
			nextlocation = new Coord(currentlocation.getX() + -1,
					currentlocation.getY() + 0);
		} else if (this.direction == Direction.Right) {
			nextlocation = new Coord(currentlocation.getX() + 1,
					currentlocation.getY() + 0);
		} else if (this.direction == Direction.Up) {
			nextlocation = new Coord(currentlocation.getX() + 0,
					currentlocation.getY() - 1);
		}
		hasCollided = false;
	}

	@Override
	public void draw(Graphics g) {
		Rectangle r = g.getClipBounds();
		g.drawImage(Images.images.get(Images.DUMBENEMY), r.x, r.y, r.width,
				r.height, null);
	}
	
	@Override
	public boolean shouldBumpInto(Entity other) {
		if (other == null) {
			return false;
		}
		if (other.type != EntityType.Ground){
			return true;
		}
		return false;
	}
	
	private boolean hasCollided = false;
	@Override
	public void collide(Entity other) {
		if (hasCollided) return;
		hasCollided = true;
		if (other == null || other.type != EntityType.Ground){
			if (this.direction == Direction.Down) {
				this.direction = Direction.Left;
			} else if (this.direction == Direction.Left) {
				this.direction = Direction.Up;
			} else if (this.direction == Direction.Up) {
				this.direction = Direction.Right;
			} else if (this.direction == Direction.Right) {
				this.direction = Direction.Down;
			}
		}
	}
}

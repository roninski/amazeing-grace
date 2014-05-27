
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends Entity {
	public PlayerAction action = PlayerAction.Pass;
	public double opacity;
	
	public Player(GameMap map) {
		super(map, DRAW_INDEX_PLAYER, TICK_INDEX_PLAYER, EntityType.Ground);
		// TODO Auto-generated constructor stub

	}

	@Override
	public void draw(Graphics g) {
		Rectangle r = g.getClipBounds();
		g.setColor(Color.green);
		g.fillRect(r.x, r.y, r.width, r.height);
		
	}

	@Override
	public void tick() {
		switch(this.action) {
		case Down:
			nextlocation = new Coord(currentlocation.getX(), currentlocation.getY()+1);
			break;
		case Drop:
			// Drops the current item ON THE GROUUUNNNND. or swaps if player is on another item.
			
			break;
		case Left:
			nextlocation = new Coord(currentlocation.getX()-1, currentlocation.getY());
			break;
		case Pass:
			// do nothing
			break;
		case Right:
			nextlocation = new Coord(currentlocation.getX()+1, currentlocation.getY());
			break;
		case Up:
			nextlocation = new Coord(currentlocation.getX(), currentlocation.getY()-1);
			break;
		case Use:
			break;
		default:
			break;
		
		}
		
		action = PlayerAction.Unknown;
	}

	@Override
	public boolean shouldBumpInto(Entity other) {
		if (other == null)
			return false;
		if (other.type.equals(EntityType.Ground)
				|| other.type.equals(EntityType.Item))
			return false;
		return true;
	}

	@Override
	public void collide(Entity other) {
		if (other.type.equals(EntityType.Enemy)) {
			this.isActive = false; // die
		}
	}
}

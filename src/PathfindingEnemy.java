import java.awt.Graphics;
import java.awt.Rectangle;


public class PathfindingEnemy extends Entity {

	public PathfindingEnemy(GameMap map) {
		super(map, DRAW_INDEX_AGENT, TICK_INDEX_AGENT, EntityType.Enemy);
	}

	@Override
	public void tick() {
		// pathfind to the player
		Coord playerLocation = map.getPlayer().getCurrentLocation();
		nextlocation = map.shortestPath(this.getCurrentLocation(), playerLocation).get(1);
	}
		
	@Override
	public void draw(Graphics g) {
		Rectangle r = g.getClipBounds();
		g.drawImage(Images.images.get(Images.ENEMY), r.x, r.y, r.width, r.height, null);
	}
	
	@Override
	public boolean shouldBumpInto(Entity other) {
		if (other == null){
			return false;
		}
		if (other.getType() == EntityType.Wall){
			return true;
		}
		return false;
	}
}

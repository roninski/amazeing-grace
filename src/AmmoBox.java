import java.awt.Graphics;
import java.awt.Rectangle;


public class AmmoBox extends Entity{

	public AmmoBox(GameMap map) {
		super(map, Entity.DRAW_INDEX_ITEM, Entity.TICK_INDEX_BACKGROUND, EntityType.Ground);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void draw(Graphics g) {
		Rectangle r = g.getClipBounds();
		g.drawImage(Images.images.get(Images.AMMO), r.x, r.y, r.width, r.height,  null);
	}

	@Override
	public void tick() {
		// Nothing
	}
	
	@Override
	public void collide(Entity e) {
		// We do nothing. It is the player's responsibility to destroy us.
	}
}

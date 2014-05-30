import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * The ammo box entity. Ammo boxes set player ammo to 100% of max.
 * 
 * @author Yujin, Luke, Steele, Michael 
 *
 */
public class AmmoBox extends Entity{

	/**
	 * See Entity constructor
	 */
	public AmmoBox(GameMap map) {
		super(map, Entity.DRAW_INDEX_ITEM, Entity.TICK_INDEX_BACKGROUND, EntityType.Ground);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Paints this object's image onto the next display canvas to be drawn.
	 * @param The canvas to paint on to
	 */
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

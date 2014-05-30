import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Blood signifies the location of a slain enemy.
 * Blood replaces Enemy on Enemy death
 * 
 * @author Yujin, Luke, Steele, Michael 
 *
 */
public class Blood extends Entity{

	public Blood(GameMap map) {
		super(map, Entity.DRAW_INDEX_BACKGROUND, Entity.TICK_INDEX_BACKGROUND, EntityType.Ground);
	}
	
	/**
	 * Paints this object's image onto the next display canvas to be drawn.
	 * @param The canvas to paint on to
	 */
	@Override
	public void draw(Graphics g) {
		// BLOOD PLOX
		Rectangle r = g.getClipBounds();
		g.drawImage(Images.images.get(Images.BLOOD), r.x, r.y, r.width, r.height, null);	
	}

}

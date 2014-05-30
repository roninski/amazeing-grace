import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * The exit point entity - for signifying where the exit is in the maze.
 * 
 * @author Yujin, Michael, Luke, Steele
 */
public class Exit extends Entity{

	public Exit(GameMap map) {
		super(map, DRAW_INDEX_BACKGROUND, TICK_INDEX_BACKGROUND, EntityType.Ground);
		
	}

	/**
	 * Paints this object's image onto the next display canvas to be drawn.
	 * @param The canvas to paint on to
	 */
	@Override
	public void draw(Graphics g) {
		Rectangle r = g.getClipBounds();
		g.drawImage(Images.images.get(Images.EXIT), r.x, r.y, r.width, r.height, null);
	}
}

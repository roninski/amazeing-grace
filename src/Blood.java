import java.awt.Graphics;
import java.awt.Rectangle;


public class Blood extends Entity{

	public Blood(GameMap map) {
		super(map, Entity.DRAW_INDEX_BACKGROUND, Entity.TICK_INDEX_BACKGROUND, EntityType.Ground);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void draw(Graphics g) {
		// BLOOD PLOX
		Rectangle r = g.getClipBounds();
		g.drawImage(Images.images.get(Images.BLOOD), r.x, r.y, r.width, r.height, null);	
	}

}

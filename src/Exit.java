import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Exit extends Entity{

	public Exit(GameMap map) {
		super(map, DRAW_INDEX_BACKGROUND, TICK_INDEX_BACKGROUND, EntityType.Ground);
		
	}


	@Override
	public void draw(Graphics g) {
		Rectangle r = g.getClipBounds();
		g.setColor(Color.ORANGE);
		g.fillRect(r.x, r.y, r.width, r.height);
	}
}

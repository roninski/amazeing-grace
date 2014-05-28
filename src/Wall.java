import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Wall extends Entity {
	public Wall(GameMap map) {
		super(map, DRAW_INDEX_BACKGROUND, TICK_INDEX_BACKGROUND, EntityType.Wall);
	}
	
	@Override
	public void draw(Graphics g) {
		Rectangle r = g.getClipBounds();
		g.drawImage(Images.images.get(Images.WALL), r.x, r.y, r.width, r.height, null);
		//g.setColor(Color.BLACK);
		//g.fillRect(r.x, r.y, r.width, r.height);
	}
	
	@Override
	public boolean shouldBumpInto(Entity other) {
		if (other == null) return false;
		return true;
	}
}

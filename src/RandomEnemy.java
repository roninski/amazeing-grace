import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;


public class RandomEnemy extends Entity{
	public RandomEnemy(GameMap map) {
		super(map, DRAW_INDEX_AGENT, TICK_INDEX_AGENT, EntityType.Enemy);
		
		
	}

	@Override
	public void tick() {
		// move randomly.
		int dx[] = {-1, 0, 1, 0};
		int dy[] = {0, 1, 0, -1};
		Random r = new Random();
		int index = (r.nextInt()%4 + 8) % 4;
		nextlocation = new Coord(currentlocation.getX() + dx[index], currentlocation.getY() + dy[index]);
	}
	
	@Override
	public void draw(Graphics g) {
		Rectangle r = g.getClipBounds();
		g.setColor(Color.RED);
		g.fillRect(r.x, r.y, r.width, r.height);
	}
}

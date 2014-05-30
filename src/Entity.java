import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * An entity within the maze
 * @author Yujin, Luke, Steele, Michael 
 *
 */
public abstract class Entity {
	// The current animation frame number, used in choosing the sprite to draw
	// in animations.
	public static int frameNumber = 0;
	public int lastTickFrame = -100; // Avoid initial animation!

	public Coord currentlocation = new Coord(0, 0);
	public Coord nextlocation = new Coord(0, 0);
	public Coord prevlocation = new Coord(0, 0); // for smooth animations

	// Determines if this entity is to be considered, ever. If this variable is
	// false, then this entity is effectively non existent and should be removed
	// from data structures.
	public boolean isActive = true;
	
	// The entity with the lowest drawindex gets drawn first (background)
	public int drawIndex;

	// The entity with the highest tick index gets ticked first
	public int tickIndex;

	public EntityType type;
	protected GameMap map;
	
	Direction direction = Direction.Down;
	
	
	public Entity (GameMap map, int drawIndex, int tickIndex, EntityType type) {
		this.map = map;
		this.drawIndex = drawIndex;
		this.tickIndex = tickIndex;
		this.type = type;
		
	}
	
	public void setMap(GameMap map) {
		this.map = map;
	}

	/**
	 * A well behaved entity would draw itself scaled into the graphics' clip
	 * rectangle.
	 * 
	 * @param g
	 */
	public void draw(Graphics g) {
		// Please override this.
		// a big red box reminds you that you haven't overriden draw for an entity, instead of just being invisible.
		Rectangle r = g.getClipBounds();
		g.setColor(Color.red);
		g.fillRect(r.x, r.y, r.width, r.height);
	}

	/**
	 * A well behaved entity would change it's next location AND NOT IT'S
	 * CURRENT LOCATION when updating its tick. This lets the collision engine
	 * resolve properly.
	 * 
	 * @param m
	 */
	public final void ontick() {
		this.nextlocation = this.currentlocation;
		this.prevlocation = this.currentlocation;
		tick();
		
		this.lastTickFrame = Entity.frameNumber;
	}
	
	/** 
	 * Do not call this method directly. Use ontick() instead, which will also take into account direction of entity, sanitizing nextlocation and updating previouslocation.
	 */
	public void tick() {
		// Default tick
	}
	
	public Coord getCurrentLocation(){
		return this.currentlocation;
	}
	
	public EntityType getType(){
		return this.type;
	}
	
	/**
	 * If we collide with this entity, should we physically collide?
	 * @return
	 */
	public boolean shouldBumpInto(Entity other) {
		return false;
	}

	/**
	 * This function is called AFTER the necessary blocking mechanisms have been
	 * done by the engine.
	 * 
	 * Every object that occupies our square/was trying to occupy our
	 * square/occupies the square we tried to go to/occupies the square we tried
	 * to occupy will be on this list, even if we don't share a collision layer.
	 * The collision layer is for "blocking" effect only, which is dealt
	 * automatically in the engine. Hence, it is possible to have entities on
	 * this list who share no layers in common and have the same coordinates as
	 * us.
	 * 
	 * @param others
	 */
	public void collide(Entity other) {
		
	}
	
	public static final int DRAW_INDEX_WALL = 1;
	public static final int DRAW_INDEX_BACKGROUND = 0;
	public static final int DRAW_INDEX_ITEM = 2;
	public static final int DRAW_INDEX_AGENT = 3;
	public static final int DRAW_INDEX_PLAYER = 4;
	public static final int DRAW_INDEX_PROJECTILE = 5;
	
	public static final int TICK_INDEX_WALL = 3;
	public static final int TICK_INDEX_BACKGROUND = 4;
	public static final int TICK_INDEX_ITEM = 2;
	public static final int TICK_INDEX_AGENT = 1;
	public static final int TICK_INDEX_PLAYER = 0;
	public static final int TICK_INDEX_PROJECTILE = 5;
	
}

import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends Entity {
	public PlayerAction action = PlayerAction.Pass;
	public double opacity;
	public boolean bloody = false;
	public int moves = 0;
	public int kills = 0;

	public int ammo;

	public Player(GameMap map) {
		super(map, DRAW_INDEX_PLAYER, TICK_INDEX_PLAYER, EntityType.Player);
		// TODO Auto-generated constructor stub
		ammo = Player.INITIAL_AMMO;
	}

	@Override
	public void draw(Graphics g) {
		Rectangle r = g.getClipBounds();

		if (this.bloody) {
			System.out.println("Blood");
			if (this.direction == Direction.Up) {
				g.drawImage(Images.images.get(Images.GRACE_BLOOD_U), r.x, r.y,
						r.width, r.height, null);
			} else if (this.direction == Direction.Left) {
				g.drawImage(Images.images.get(Images.GRACE_BLOOD_L), r.x, r.y,
						r.width, r.height, null);
			} else if (this.direction == Direction.Right) {
				g.drawImage(Images.images.get(Images.GRACE_BLOOD_R), r.x, r.y,
						r.width, r.height, null);
			} else if (this.direction == Direction.Down) {
				g.drawImage(Images.images.get(Images.GRACE_BLOOD_D), r.x, r.y,
						r.width, r.height, null);
			}
		} else {
			if (this.direction == Direction.Up) {
				g.drawImage(Images.images.get(Images.GRACE_U), r.x, r.y,
						r.width, r.height, null);
			} else if (this.direction == Direction.Left) {
				g.drawImage(Images.images.get(Images.GRACE_L), r.x, r.y,
						r.width, r.height, null);
			} else if (this.direction == Direction.Right) {
				g.drawImage(Images.images.get(Images.GRACE_R), r.x, r.y,
						r.width, r.height, null);
			} else if (this.direction == Direction.Down) {
				g.drawImage(Images.images.get(Images.GRACE_D), r.x, r.y,
						r.width, r.height, null);
			}
		}
	}

	@Override
	public void tick() {
		switch (this.action) {
		case Down:
			nextlocation = new Coord(currentlocation.getX(),
					currentlocation.getY() + 1);
			this.direction = Direction.Down;
			break;
		case Drop:
			// Drops the current item ON THE GROUUUNNNND. or swaps if player is
			// on another item.

			break;
		case Left:
			nextlocation = new Coord(currentlocation.getX() - 1,
					currentlocation.getY());
			this.direction = Direction.Left;
			break;
		case Pass:
			// do nothing
			break;
		case Right:
			nextlocation = new Coord(currentlocation.getX() + 1,
					currentlocation.getY());
			this.direction = Direction.Right;
			break;
		case Up:
			nextlocation = new Coord(currentlocation.getX(),
					currentlocation.getY() - 1);
			this.direction = Direction.Up;
			break;
		case Use:
			this.use();
			break;
		default:
			break;

		}

		action = PlayerAction.Unknown;
		moves ++;
	}

	public void use() {

		if (ammo <= 0) {
			Sound.click.stop();
			Sound.click.play();
		} else {
			// This is currently the code for the Gun item (minus ammunition
			// management).
			Entity closest = null;
			if (this.direction == Direction.Down) {
				// x same y higher
				int closestY = 1000000;
				for (Entity e : map.entityList()) {
					if (e.getCurrentLocation().getX() == this
							.getCurrentLocation().getX()
							&& e.getCurrentLocation().getY() > this
									.getCurrentLocation().getY()
							&& e.getCurrentLocation().getY() < closestY
							&& e.getType() != EntityType.Ground) {
						closest = e;
						closestY = e.getCurrentLocation().getY();
					}
				}
			} else if (this.direction == Direction.Left) {
				// x lower y same
				int closestX = -1;
				for (Entity e : map.entityList()) {
					if (e.getCurrentLocation().getY() == this
							.getCurrentLocation().getY()
							&& e.getCurrentLocation().getX() < this
									.getCurrentLocation().getX()
							&& e.getCurrentLocation().getX() > closestX
							&& e.getType() != EntityType.Ground) {
						closest = e;
						closestX = e.getCurrentLocation().getX();
					}
				}
			} else if (this.direction == Direction.Right) {
				// x higher y same
				int closestX = 1000000; // Like a billion ;D
				for (Entity e : map.entityList()) {
					if (e.getCurrentLocation().getY() == this
							.getCurrentLocation().getY()
							&& e.getCurrentLocation().getX() > this
									.getCurrentLocation().getX()
							&& e.getCurrentLocation().getX() < closestX
							&& e.getType() != EntityType.Ground) {
						closest = e;
						closestX = e.getCurrentLocation().getX();
					}
				}
			} else if (this.direction == Direction.Up) {
				// x same y lower
				int closestY = -1;
				for (Entity e : map.entityList()) {
					if (e.getCurrentLocation().getX() == this
							.getCurrentLocation().getX()
							&& e.getCurrentLocation().getY() < this
									.getCurrentLocation().getY()
							&& e.getCurrentLocation().getY() > closestY
							&& e.getType() != EntityType.Ground) {
						closest = e;
						closestY = e.getCurrentLocation().getY();
					}
				}
			} else {
				throw new RuntimeException("Player not facing a direction");
			}

			if (closest != null && closest.getType() == EntityType.Enemy) {
				closest.isActive = false;
				Blood b = new Blood(map);
				b.currentlocation = closest.currentlocation;
				map.addEntity(b);

				// Calculate if we need blood
				if (this.currentlocation.manhattanDistance(b.currentlocation) <= 1) {
					this.bloody = true;
				}
				
				kills ++;
			}
			Sound.gunshot.play();
			ammo --;
		}
	}

	@Override
	public boolean shouldBumpInto(Entity other) {
		if (other == null)
			return false;
		if (other.type.equals(EntityType.Ground)
				|| other.type.equals(EntityType.Item))
			return false;
		return true;
	}

	@Override
	public void collide(Entity other) {
		if (other == null) {
			return;
		}
		if (other.type.equals(EntityType.Enemy)) {
			this.isActive = false; // die
		}
		if (other instanceof AmmoBox && this.ammo < INITIAL_AMMO) {
			// pick it up bitch
			this.ammo = INITIAL_AMMO;
			other.isActive = false;
		}
	}

	public static final int INITIAL_AMMO = 10;
}

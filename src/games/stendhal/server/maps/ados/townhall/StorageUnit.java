package games.stendhal.server.maps.ados.townhall;

import java.awt.geom.Rectangle2D;
import java.util.Set;

import games.stendhal.server.core.engine.ItemLogger;
import games.stendhal.server.core.engine.Spot;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.events.MovementListener;
import games.stendhal.server.core.events.TurnNotifier;
import games.stendhal.server.entity.ActiveEntity;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.mapstuff.area.WalkBlocker;
import games.stendhal.server.entity.mapstuff.chest.PersonalChest;
import games.stendhal.server.entity.mapstuff.portal.Portal;
import games.stendhal.server.entity.mapstuff.portal.Teleporter;
import games.stendhal.server.entity.mapstuff.sign.Sign;
import games.stendhal.server.entity.player.Player;

public class StorageUnit extends StendhalRPZone {
	private PersonalChest chest;
	
	public StorageUnit(final String name, final StendhalRPZone zone,
			final Player player) {
		super(name, zone);

		init(player);

	}

	private void init(final Player player) {
		Portal portal = new Teleporter(new Spot(player.getZone(),
				player.getX(), player.getY()));
		portal.setPosition(4, 8);
		add(portal);

		chest = new PersonalChest();
		chest.setPosition(4, 2);
		add(chest);

		WalkBlocker walkblocker = new WalkBlocker();
		walkblocker.setPosition(2, 5);
		walkblocker
				.setDescription("You see a wastebin, handily placed for items you wish to dispose of.");
		add(walkblocker);
		// Add a sign explaining about equipped items
		final Sign book = new Sign();
		book.setPosition(2, 2);
		book
				.setText("Keep your storage unit tidy- any items left on the floor will be returned to you. Use the bin for anything you don't want to keep, and we'll empty it on your departure.");
		book.setEntityClass("book_blue");
		book.setResistance(0);
		add(book);
		disallowIn();
		this.addMovementListener(new StorageUnitMovementListener());
	}

	private static final class StorageUnitMovementListener implements MovementListener {
		private static final Rectangle2D area = new Rectangle2D.Double(0, 0, 100, 100);

		@Override
		public Rectangle2D getArea() {
			return area;
		}

		@Override
		public void onEntered(final ActiveEntity entity,
				final StendhalRPZone zone, final int newX, final int newY) {
			// ignore
		}

		@Override
		public void onExited(final ActiveEntity entity,
				final StendhalRPZone zone, final int oldX, final int oldY) {
			if (!(entity instanceof Player)) {
				return;
			}
			if (zone.getPlayers().size() == 1) {
				Set<Item> itemsOnGround = zone.getItemsOnGround();
				for (Item item : itemsOnGround) {
					// ignore items which are in the wastebin
					if (!(item.getX() == 2 && item.getY() == 5)) {
						Player player = (Player) entity;
	
						boolean equippedToBag = false;

						// attempt to equip money in pouch first
						if (item.getName().equals("money")) {
							equippedToBag = player.equip("pouch", item);

						}

						if (!equippedToBag) {
							equippedToBag = player.equip("bag", item);
						}

					} else {
						// the timeout method enters the zone and coords of item, this is useful, this is useful we will know it was in wastebin
						new ItemLogger().timeout(item);
					}

				}
				// since we are about to destroy the unit, change the player
				// zoneid to ados townhall so that if they are relogging,
				// they can enter back to the townhall (not the default zone of
				// PlayerRPClass).
				// If they are scrolling out or walking out the portal it works
				// as before.
				entity.put("zoneid", "int_ados_townhall");
				entity.put("x", "9");
				entity.put("y", "27");

				TurnNotifier.get().notifyInTurns(2, new StorageUnitRemover(zone));
			}
		}

		@Override
		public void onMoved(final ActiveEntity entity,
				final StendhalRPZone zone, final int oldX, final int oldY,
				final int newX, final int newY) {
			// ignore
		}

		@Override
		public void beforeMove(ActiveEntity entity, StendhalRPZone zone,
				int oldX, int oldY, int newX, int newY) {
			// does nothing, but is specified in the implemented interface
		}
	}

	@Override
	public void onFinish() throws Exception {
		this.remove(chest);

	}
}

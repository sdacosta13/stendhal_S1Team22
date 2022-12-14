package games.stendhal.server.maps.ados.townhall;

import java.awt.geom.Rectangle2D;
import java.util.Set;

import games.stendhal.common.grammar.Grammar;
import games.stendhal.server.core.engine.GameEvent;
import games.stendhal.server.core.engine.ItemLogger;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.Spot;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.events.GuaranteedDelayedPlayerTextSender;
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
/**
 * StorageUnit.java defines how a single storage unit works, including its layout, contents and behaviours
 * 
 *
 */


public class StorageUnit extends StendhalRPZone {
	// Initialise the four chests in the storage unit
	private PersonalChest chest_1;
	private PersonalChest chest_2;
	private PersonalChest chest_3;
	private PersonalChest chest_4;
	
	public StorageUnit(final String name, final StendhalRPZone zone,
			final Player player) {
		super(name, zone);

		init(player);

	}

	// Adds entities to the room such as chests, portal and bin
	private void init(final Player player) {
		Portal portal = new Teleporter(new Spot(player.getZone(),
				player.getX(), player.getY()));
		// Exit portal
		portal.setPosition(4, 8);
		add(portal);
		
		// Set positions of all chests, add them
		chest_1 = new PersonalChest();
		chest_1.setPosition(4, 2);
		add(chest_1);
		chest_2 = new PersonalChest();
		chest_2.setPosition(6, 4);
		add(chest_2);
		chest_3 = new PersonalChest();
		chest_3.setPosition(6, 6);
		add(chest_3);
		chest_4 = new PersonalChest();
		chest_4.setPosition(2, 6);
		add(chest_4); 
		
		
		// Include a wastebin like in 'Vault.java' where items can be discarded
		WalkBlocker walkblocker = new WalkBlocker();
		walkblocker.setPosition(2, 5);
		walkblocker
				.setDescription("You see a wastebin, handily placed for items you wish to dispose of.");
		add(walkblocker);
		
		// Include a sign explaining about equipped items
		final Sign book = new Sign();
		book.setPosition(2, 2);
		book
				.setText("Keep your storage unit tidy- any items left on the floor will be returned to you. Use the bin for anything you don't want to keep, and we'll empty it on your departure.");
		book.setEntityClass("book_blue");
		book.setResistance(0);
		add(book);
		disallowIn();
		// Calls below function
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

		// Deals with exiting the storage unit.
		// Any items left in the unit that aren't in a chest or bin by returning them to player if possible. Otherwise, discard them.
		// Sends the player a message about what has happened to their items
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
						String message;
						String slotName = "bag";
						boolean equippedToBag = false;

						// attempt to equip money in pouch first
						if (item.getName().equals("money")) {
							equippedToBag = player.equip("pouch", item);
							if (equippedToBag) {
								slotName = "pouch";
							}
						}

						if (!equippedToBag) {
							equippedToBag = player.equip("bag", item);
						}
					
						if (equippedToBag) {
	
							message = Grammar.quantityplnoun(item.getQuantity(), item.getName(), "A")
												+ " which you left on the floor in the storage unit "+ Grammar.hashave(item.getQuantity())+" been automatically "
												+ "returned to your " + slotName + ".";
	
							new GameEvent(player.getName(), "equip", item.getName(), "storage unit", slotName, Integer.toString(item.getQuantity())).raise();
							// Make it look like a normal equip
							new ItemLogger().equipAction(player, item, new String[] {"ground", zone.getName(), item.getX() + " " + item.getY()}, new String[] {"slot", player.getName(), slotName});
						} else {boolean equippedToBank = player.equip("bank", item);
						if (equippedToBank) {
							message =  Grammar.quantityplnoun(item.getQuantity(), item.getName(), "A")
							+ " which you left on the floor in the storage unit "+ Grammar.hashave(item.getQuantity())+" been automatically "
							+ "returned to your bank chest.";

							new GameEvent(player.getName(), "equip", item.getName(), "storage unit", "townhall", Integer.toString(item.getQuantity())).raise();
							// Make it look like the player put it in the chest
							new ItemLogger().equipAction(player, item, new String[] {"ground", zone.getName(), item.getX() + " " + item.getY()}, new String[] {"slot", "a bank chest", "content"});
						} else {
							// the player lost their items
							message = Grammar.quantityplnoun(item.getQuantity(), item.getName(), "A")
												+ " which you left on the floor in the storage unit"+ Grammar.hashave(item.getQuantity())+" been thrown into "
												+ "the void, because there was no space to fit them into either your "
												+ "bank chest or your bag.";

							// the timeout method enters the zone and coords of item, this is useful we will know it was in storage unit
							new ItemLogger().timeout(item);
						}
						}

						// tell the player the message
						notifyPlayer(player.getName(), message);
					} else {
						// the timeout method enters the zone and coords of item, this is useful, this is useful we will know it was in wastebin
						new ItemLogger().timeout(item);
					}

				}
				// Unit will be destroyed, so we return player back to townhall location
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

	/**
	 * Notifies the user of the storage unit
	 *
	 * @param target the player to be notified
	 * @param message the delivered message
	 */
	private static void notifyPlayer(final String target, final String message)  {
		// only uses postman if they logged out. Otherwise, just send the private message.

		final Player player = SingletonRepository.getRuleProcessor().getPlayer(target);

		new GuaranteedDelayedPlayerTextSender("Serena", player, message, 2);

	}
	
	// Removes chests
	@Override
	public void onFinish() throws Exception {
		this.remove(chest_1);
		this.remove(chest_2);
		this.remove(chest_3);
		this.remove(chest_4);

	}
}

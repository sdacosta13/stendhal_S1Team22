package games.stendhal.server.maps.ados.townhall;

import java.util.Map;

import games.stendhal.common.Direction;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.PlaySoundAction;
import games.stendhal.server.entity.npc.condition.QuestCompletedCondition;
import games.stendhal.server.entity.npc.condition.QuestNotCompletedCondition;
import games.stendhal.server.entity.player.Player;
//import games.stendhal.server.maps.ados.townhall.StorageUnit;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.npc.ChatAction;

public class StorageNPC implements ZoneConfigurator {

	private static final class StorageChatAction implements ChatAction {
		
		@Override
		public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
			final StendhalRPZone storagezone = (StendhalRPZone) SingletonRepository
					.getRPWorld().getRPZone("int_storage_unit");
			String zoneName = player.getName() + "_storage_unit";
			
			final StendhalRPZone zone = new StorageUnit(zoneName, storagezone, player);

			SingletonRepository.getRPWorld().addRPZone(zone);
			player.teleport(zone, 4, 5, Direction.UP, player);
			((SpeakerNPC) npc.getEntity()).setDirection(Direction.DOWN);
		}
	}
	
	@Override
	public void configureZone(StendhalRPZone zone, Map<String, String> attributes) {
		buildNPC(zone);
	}

	private void buildNPC(final StendhalRPZone zone) {
		final SpeakerNPC npc = new SpeakerNPC("Serena") {
			@Override
		    protected void createPath() {
		        // NPC does not move
		        setPath(null);
		    }
		    
		    @Override
			public void createDialog() {
				addGreeting("<Greeting?");
				/**addReply("storage", "You can store whatever you like in your storage unit, as long as you pay your rent in #advance!");
				addReply("advance", "If rent isn't paid in advance, your unit will be emptied and your items left out for the public."
			            + " A #small unit can hold three chests and costs 200 money per week,"
					    + " while a #large unit can hold six chests and costs 300 money per week.");
				addReply("small", "You'd like a small storage unit? That costs 200 money per week. Would you like to rent one?");
				addReply("large", "Wow, you must have a lot of stuff! A large storage unit costs 300 money per week. Would you like to rent one?");
				addReply("rent", "Great! How many weeks do you want to rent it for?");*/
				addHelp("<Help Response>");
				addJob("<Job Response?");
				addOffer("<Offer Response>");
				addGoodbye("<Bye Response>");
				add(ConversationStates.ANY, "storage", new QuestCompletedCondition("storage_renting"), ConversationStates.IDLE, null,
						new MultipleActions(new PlaySoundAction("keys-1", true), new StorageChatAction()));

				add(ConversationStates.ANY, "storage", new QuestNotCompletedCondition("storage_renting"), ConversationStates.ATTENDING, "<Quest Started but not Finished Response>", null);

				// remaining behaviour defined in games.stendhal.server.maps.quests.StorageRenting
			}
		
			@Override
			protected void onGoodbye(RPEntity player) {
				setDirection(Direction.LEFT);
			}
		
		};
		
		npc.setHP(95);
		npc.setPosition(35, 14);
		npc.setEntityClass("welcomernpc");
		npc.setDescription("You see Serena. She looks like a trustworthy, hardworking person.");
		npc.setDirection(Direction.LEFT);
		zone.add(npc);
	}

}


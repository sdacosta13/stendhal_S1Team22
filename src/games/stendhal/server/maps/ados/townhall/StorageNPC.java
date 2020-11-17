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
import games.stendhal.server.maps.ados.townhall.StorageUnit;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.npc.ChatAction;

public class StorageNPC implements ZoneConfigurator {

	private static final class StorageChatAction implements ChatAction {
		
		@Override
		public void fire(final Player player, final Sentence sentence, final EventRaiser npc) {
			final StendhalRPZone storagezone = (StendhalRPZone) SingletonRepository
					.getRPWorld().getRPZone("int_storage");
			String zoneName = player.getName() + "_storage";
			// Below line errors- probably because StorageUnit code stub doesn't take these args.
			final StendhalRPZone zone = new StorageUnit(zoneName, storagezone, player);

			SingletonRepository.getRPWorld().addRPZone(zone);
			player.teleport(zone, 4, 5, Direction.UP, player);
			((SpeakerNPC) npc.getEntity()).setDirection(Direction.DOWN);
		}
	}
	
	@Override
	public void configureZone(StendhalRPZone zone, Map<String, String> attributes) {
		final SpeakerNPC npc = new SpeakerNPC("Serena") {
			
			@Override
			public void createDialog() {
				addGreeting("Hey there.");
				addReply("storage", "You can store whatever you like in your storage unit, as long as you pay your rent in #advance!");
				addReply("advance", "If rent isn't paid in advance, your unit will be emptied and your items left out for the public."
			            + " A #small unit can hold three chests and costs 200 money per week,"
					    + " while a #large unit can hold six chests and costs 300 money per week.");
				addReply("small", "You'd like a small storage unit? That costs 200 money per week. Would you like to rent one?");
				addReply("large", "Wow, you must have a lot of stuff! A large storage unit costs 300 money per week. Would you like to rent one?");
				addReply("rent", "Great! How many weeks do you want to rent it for?");
				//addReply("<trigger>", "<response>");
				addJob("I rent out #storage units for people who just have too much stuff!");
				add(ConversationStates.ANY, "storage", new QuestCompletedCondition("StorageRenting"), ConversationStates.IDLE, null,
						new MultipleActions(new PlaySoundAction("keys-1", true), new StorageChatAction()));

				add(ConversationStates.ANY, "storage", new QuestNotCompletedCondition("StorageRenting"), ConversationStates.ATTENDING, "Come see me if you run out of space for all your belongings.", null);

				// remaining behaviour defined in games.stendhal.server.maps.quests.StorageRenting
			}
			
			@Override
			protected void onGoodbye(RPEntity player) {
				setDirection(Direction.DOWN);
			}
		};
		npc.setPosition(9, 23);
		npc.setDirection(Direction.DOWN);
		npc.setDescription("You see Serena. She looks like a trustworthy, hardworking person.");
		npc.setHP(95);
		npc.setEntityClass("youngnpc");
		zone.add(npc);
	}
}

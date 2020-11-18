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
				addGreeting("Hey there.");
				addHelp("If you have too many belongings, I can rent you a storage unit to keep them in.");
				addJob("I rent storage units for people who just have too much stuff!");
				addOffer("I'm happy to let you rent one of my storage units, for a fee.");
				addGoodbye("See you later!");
				add(ConversationStates.ANY, "storage", new QuestCompletedCondition("storage_renting"), ConversationStates.IDLE, null,
						new MultipleActions(new PlaySoundAction("keys-1", true), new StorageChatAction()));

				add(ConversationStates.ANY, "storage", new QuestNotCompletedCondition("storage_renting"), ConversationStates.ATTENDING, "People having too much stuff means that I can buy more stuff!", null);

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
		npc.setDescription("You see Serena. She looks like a savvy business woman.");
		npc.setDirection(Direction.LEFT);
		zone.add(npc);
	}

}


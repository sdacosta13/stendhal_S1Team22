package games.stendhal.server.maps.quests;

import java.util.LinkedList;

import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.quests.houses.HouseBuyingMain;

public class StorageRenting extends AbstractQuest{
	private static final String QUEST_SLOT = "storage";
	private HouseBuyingMain quest;
	
	@Override
	public String getSlotName() {
		return QUEST_SLOT;
	}
	
	@Override
	public void addToWorld() { //##### This section needs changing
		quest = new HouseBuyingMain();
		quest.addToWorld();
		
		fillQuestInfo(
				"Storage Renting",
				"You can rent a storage unit in Ados to hold your belongings.",
				false);
		
	}
	
	@Override
	public LinkedList<String> getHistory(final Player player) {
		return quest.getHistory(player);
	}
	
	@Override
	public String getName() {
		return "StorageRenting";
	}
	
	@Override
	public int getMinLevel() {
		return 0;
	}
	
	@Override
	public boolean isCompleted(final Player player) {
		return quest.isCompleted(player);
	}
	
	@Override
	public String getNPCName() {
		return "Serena";
	}
}

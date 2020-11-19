package games.stendhal.server.maps.quests;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.action.SetQuestAndModifyKarmaAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.GreetingMatchesNameCondition;
import games.stendhal.server.entity.npc.condition.NotCondition;
import games.stendhal.server.entity.npc.condition.OrCondition;
import games.stendhal.server.entity.npc.condition.PlayerHasItemWithHimCondition;
import games.stendhal.server.entity.npc.condition.QuestCompletedCondition;
import games.stendhal.server.entity.npc.condition.QuestInStateCondition;
import games.stendhal.server.entity.npc.condition.QuestNotCompletedCondition;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.Region;
/**
 * This function allows a player to rent a storage unit from StorageNPC 'Serena'
 * Currently, a single payment allows the player unlimited access to the vault via Serena
 * In a future release, we need to make these payments recurring- like real rent rather than buying.
 */

public class StorageRenting extends AbstractQuest{
	
	private static final String QUEST_SLOT = "storage_renting";
	
	// Cost to rent storage unit
	private static final int COST = 800;
	
	// Records 'history' of this quest for the player
	@Override
	public List<String> getHistory(final Player player) {
		final List<String> res = new ArrayList<String>();
		if (!player.hasQuest(QUEST_SLOT)) {
			return res;
		}
		res.add("I have met Serena. She has a storage unit I can pay to use.");
		final String questState = player.getQuest(QUEST_SLOT);
		if ("rejected".equals(questState)) {
			res.add("But I don't need it yet.");
		}
		if (player.isQuestInState(QUEST_SLOT, "start", "done")) {
			res.add("I said I would like to use it.");
		}
		if ("done".equals(questState)) {
			res.add("I paid Serena to use a storage unit.");
		}
		return res;
	}

	/**
	 * This defines the behaviour of requesting/accepting/rejecting the quest
	 */
	private void prepareRequestingStep() {
		final SpeakerNPC npc = npcs.get("Serena");
		//Player asks about quest/task
		npc.add(
			ConversationStates.ATTENDING,
			ConversationPhrases.QUEST_MESSAGES,
			new QuestNotCompletedCondition(QUEST_SLOT),
			ConversationStates.QUEST_OFFERED,
			"If you have too much stuff, you can pay me 800 money to rent a storage unit for a month. Would you like to rent one?",
			null);
		// Quest already completed/player already has a storage unit
		npc.add(
				ConversationStates.ATTENDING,
				ConversationPhrases.QUEST_MESSAGES,
				new QuestCompletedCondition(QUEST_SLOT),
				ConversationStates.ATTENDING,
				"You already have a #storage unit.",
				null);

		// player is willing to help
		npc.add(
			ConversationStates.QUEST_OFFERED,
			ConversationPhrases.YES_MESSAGES,
			null,
			ConversationStates.ATTENDING,
			"Great- it'll take me a while to prepare your unit, so come back later, and make sure you have the money for me.",
			new SetQuestAction(QUEST_SLOT, "start"));

		// player is not willing to help
		npc.add(
			ConversationStates.QUEST_OFFERED,
			ConversationPhrases.NO_MESSAGES, null,
			ConversationStates.ATTENDING,
			"Okay, well let me know if you change your mind.",
			new SetQuestAndModifyKarmaAction(QUEST_SLOT, "rejected", -5.0));
	}

	/**
	 * Defines all behaviours that occur after the quest has been accepted
	 */
	private void prepareBringingStep() {
		final SpeakerNPC npc = npcs.get("Serena");

		// player returns while quest is still active, and player DOES have 800 money
		npc.add(ConversationStates.IDLE, ConversationPhrases.GREETING_MESSAGES,
			new AndCondition(new GreetingMatchesNameCondition(npc.getName()),
				new QuestInStateCondition(QUEST_SLOT, "start"),
				new OrCondition(
					new PlayerHasItemWithHimCondition("money", COST))),
			ConversationStates.QUEST_ITEM_BROUGHT,
			"I see that you have 800 money! Do you want to rent one of my storage units?",
			null);

		// player returns while quest is still active, and player does NOT ave 800 money		
		npc.add(ConversationStates.IDLE, ConversationPhrases.GREETING_MESSAGES,
				new AndCondition(new GreetingMatchesNameCondition(npc.getName()),
					new QuestInStateCondition(QUEST_SLOT, "start"),
					new NotCondition(new OrCondition(
						new PlayerHasItemWithHimCondition("money", COST)))),
				ConversationStates.ATTENDING,
				"If you want to use a storage unit, you'll need to bring me 800 money.",
				null);
		
		// player returns while quest is still active, and gives Serena 800 money
		final List<ChatAction> reward = new LinkedList<ChatAction>();
		reward.add(new SetQuestAction(QUEST_SLOT, "done"));

		npc.add(
				ConversationStates.QUEST_ITEM_BROUGHT,
				ConversationPhrases.YES_MESSAGES,
				// make sure the player isn't cheating by putting the money away and then saying "yes"
				new PlayerHasItemWithHimCondition("money", COST),
				ConversationStates.ATTENDING, "Oh great! When you need to use your #storage unit, let me know.",
				new MultipleActions(reward));

		// player returns while quest is still active, and player DOES have 800 money, but doesn't want to give it to Serena
		npc.add(
				ConversationStates.QUEST_ITEM_BROUGHT,
				ConversationPhrases.NO_MESSAGES,
				null,
				ConversationStates.ATTENDING,
				"Okay, I guess you don't need a storage unit right now. Find me if you change your mind.",
				null);
		
	}

	// Adds quest to the world
	@Override
	public void addToWorld() {
		fillQuestInfo(
				"Storage Renting",
				"Serena can help you store your belongings.",
				false);
		prepareRequestingStep();
		prepareBringingStep();
	}

	
	@Override
	public String getSlotName() {
		return QUEST_SLOT;
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
	public String getRegion() {
		return Region.ADOS_CITY;
	}

	
	@Override
	public String getNPCName() {
		return "Serena";
	}
	
}

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


public class StorageRenting extends AbstractQuest{
	
	private static final String QUEST_SLOT = "storage_renting";
	
	
	
	@Override
	public List<String> getHistory(final Player player) {
		final List<String> res = new ArrayList<String>();
		if (!player.hasQuest(QUEST_SLOT)) {
			return res;
		}
		res.add("I have met Serena. She has a storage unit I can use.");
		final String questState = player.getQuest(QUEST_SLOT);
		if ("rejected".equals(questState)) {
			res.add("But I don't need it yet.");
		}
		if (player.isQuestInState(QUEST_SLOT, "start", "done")) {
			res.add("I said I would user her storage unit.");
		}
		if ("done".equals(questState)) {
			res.add("I have used the storage unit.");
		}
		return res;
	}

	private void prepareRequestingStep() {
		final SpeakerNPC npc = npcs.get("Serena");

		npc.add(
			ConversationStates.ATTENDING,
			ConversationPhrases.QUEST_MESSAGES,
			new QuestNotCompletedCondition(QUEST_SLOT),
			ConversationStates.QUEST_OFFERED,
			"If you have too much stuff, you can pay me 600 money to rent a storage unit. Would you like to rent one? ---# QUEST OFFERED",
			null);
		
		npc.add(
				ConversationStates.ATTENDING,
				ConversationPhrases.QUEST_MESSAGES,
				new QuestCompletedCondition(QUEST_SLOT),
				ConversationStates.ATTENDING,
				"You already have a #storage unit. ---# NO MORE QUESTS",
				null);

		// player is willing to help
		npc.add(
			ConversationStates.QUEST_OFFERED,
			ConversationPhrases.YES_MESSAGES,
			null,
			ConversationStates.ATTENDING,
			"Great- give me a moment to prepare the unit for you, and make sure you have the money for me. ---# <Quest Accepted>",
			new SetQuestAction(QUEST_SLOT, "start"));

		// player is not willing to help
		npc.add(
			ConversationStates.QUEST_OFFERED,
			ConversationPhrases.NO_MESSAGES, null,
			ConversationStates.ATTENDING,
			"Okay, well let me know if you change your mind. ---#<Quest Rejected>",
			new SetQuestAndModifyKarmaAction(QUEST_SLOT, "rejected", -5.0));
	}

	private void prepareBringingStep() {
		final SpeakerNPC npc = npcs.get("Serena");

		// player returns while quest is still active
		npc.add(ConversationStates.IDLE, ConversationPhrases.GREETING_MESSAGES,
			new AndCondition(new GreetingMatchesNameCondition(npc.getName()),
				new QuestInStateCondition(QUEST_SLOT, "start"),
				new OrCondition(
					new PlayerHasItemWithHimCondition("leather cuirass"),
					new PlayerHasItemWithHimCondition("pauldroned leather cuirass"))),
			ConversationStates.QUEST_ITEM_BROUGHT,
			"I see that you have enough money for the storage unit. Is it for me? ---#<Player has item, quest incomplete>",
			null);
		
		npc.add(ConversationStates.IDLE, ConversationPhrases.GREETING_MESSAGES,
				new AndCondition(new GreetingMatchesNameCondition(npc.getName()),
					new QuestInStateCondition(QUEST_SLOT, "start"),
					new NotCondition(new OrCondition(
						new PlayerHasItemWithHimCondition("leather cuirass"),
						new PlayerHasItemWithHimCondition("pauldroned leather cuirass")))),
				ConversationStates.ATTENDING,
				"If you want to use a storage unit, you'll need to bring me 600 money. ---#<Player DOES NOT have item, quest incomplete>",
				null);

		final List<ChatAction> reward = new LinkedList<ChatAction>();
		reward.add(new SetQuestAction(QUEST_SLOT, "done"));

		npc.add(
				ConversationStates.QUEST_ITEM_BROUGHT,
				ConversationPhrases.YES_MESSAGES,
				// make sure the player isn't cheating by putting the armor
				// away and then saying "yes"
				new PlayerHasItemWithHimCondition("leather cuirass"),
				ConversationStates.ATTENDING, "Oh great! Just find me whenever you want to use your unit. ---# <Item brought, item given, Quest Complete>",
				new MultipleActions(reward));
		
		npc.add(
				ConversationStates.QUEST_ITEM_BROUGHT,
				ConversationPhrases.NO_MESSAGES,
				null,
				ConversationStates.ATTENDING,
				"Okay, I guess you don't need me storage unit right now. Find me if you change your mind. ---#<Item brought, item NOT given, Quest Incomplete>",
				null);
		
	}

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

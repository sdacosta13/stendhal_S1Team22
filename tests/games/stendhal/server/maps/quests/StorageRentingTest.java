package games.stendhal.server.maps.quests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.mapstuff.chest.Chest;
import games.stendhal.server.entity.mapstuff.portal.Portal;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.maps.ados.townhall.StorageNPC;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class StorageRentingTest extends ZonePlayerAndNPCTestImpl {
	private static final String ZONE_NAME = "int_adoc_town_hall_3";
	private games.stendhal.server.entity.player.Player player;
	private SpeakerNPC npc;
	private Engine en;
	private AbstractQuest quest;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		Portal.generateRPClass();
		Chest.generateRPClass();
		
		setupZone(ZONE_NAME);
		
	}

	@Override
	@Before
	public void setUp() {
		StendhalRPZone zone = new StendhalRPZone("admin_test");
		new StorageNPC().configureZone(zone, null);

		npc = SingletonRepository.getNPCList().get("Serena");
		quest = new StorageRenting();
		quest.addToWorld();
		en = npc.getEngine();

		player = PlayerTestHelper.createPlayer("player");
	}
	
	
	public StorageRentingTest() {
		super(ZONE_NAME, "Serena");
	}
	/**
	 * Tests for hiAndBye.
	 */
	@Test
	public void testHiAndBye() {
		final SpeakerNPC npc = getNPC("Serena");
		final Engine en = npc.getEngine();

		en.step(player, "hi");
		assertTrue(npc.isTalking());
		assertEquals(
				"Hey there.",
				getReply(npc));
		en.step(player, "bye");
		assertFalse(npc.isTalking());
	}
	/**
	 * Tests for generalStuff
	 */
	@Test
	public void testGeneralStuff() {
		final SpeakerNPC npc = getNPC("Serena");
		final Engine en = npc.getEngine();
				
		en.step(player, "hi");
		assertTrue(npc.isTalking());
		assertEquals(
				"Hey there.",
				getReply(npc));
		en.step(player, "job");
		assertTrue(npc.isTalking());
		assertEquals(
		            "I rent storage units for people who just have too much stuff!",
		            getReply(npc));
		
		en.step(player, "offer");
		assertTrue(npc.isTalking());
		assertEquals(
		            "I'm happy to let you rent one of my storage units, for a fee.",
		            getReply(npc));	
		
		en.step(player, "help");
		assertTrue(npc.isTalking());
		assertEquals(
		            "If you have too many belongings, I can rent you a storage unit to keep them in.",
		            getReply(npc));	
	
	}

	
	/**
	 * Tests for quest.
	 */
	@Test
	public void testQuest() {
		
		// Quest rejected test
		en.step(player, "hi");
		assertEquals("Hey there.", getReply(npc));
		assertTrue(quest.getHistory(player).isEmpty());


		assertTrue(quest.getHistory(player).isEmpty());
		en.step(player, "task");
		assertTrue(quest.getHistory(player).isEmpty());
		assertEquals("If you have too much stuff, you can pay me 800 money to rent a storage unit for a month. Would you like to rent one?",
				getReply(npc));
		en.step(player, "no");
		java.util.List<String> questHistory = new LinkedList<String>();
		questHistory.add("I have met Serena. She has a storage unit I can pay to use.");
		questHistory.add("But I don't need it yet.");
		assertEquals(questHistory, quest.getHistory(player));

		assertEquals("Okay, well let me know if you change your mind.", getReply(npc));
		en.step(player, "bye");
		assertEquals(questHistory, quest.getHistory(player));
		assertEquals("See you later!", getReply(npc));

		// -----------------------------------------------
		// Quest accepted test
		en.step(player, "hi");
		assertEquals("Hey there.", getReply(npc));
		assertEquals(questHistory, quest.getHistory(player));
		en.step(player, "task");
		assertEquals(questHistory, quest.getHistory(player));
		assertEquals("If you have too much stuff, you can pay me 800 money to rent a storage unit for a month. Would you like to rent one?",
				getReply(npc));
		en.step(player, "yes");
		questHistory = new LinkedList<String>();
		questHistory.add("I have met Serena. She has a storage unit I can pay to use.");
		questHistory.add("I said I would like to use it.");
		assertEquals(questHistory, quest.getHistory(player));
		assertEquals(
				"Great- it'll take me a while to prepare your unit, so come back later, and make sure you have the money for me.",
				getReply(npc));
		en.step(player, "bye");
		assertEquals(questHistory, quest.getHistory(player));
		assertEquals("See you later!", getReply(npc));

		// -----------------------------------------------
		// Player has money, doesn't want to pay
		final StackableItem money = (StackableItem)SingletonRepository.getEntityManager().getItem("money");
		money.setQuantity(800);
		player.equipToInventoryOnly(money);

		en.step(player, "hi");
		assertEquals("I see that you have 800 money! Do you want to rent one of my storage units?",
				getReply(npc));
		assertEquals(questHistory, quest.getHistory(player));
		en.step(player, "no");
		assertEquals("Okay, I guess you don't need a storage unit right now. Find me if you change your mind.",
				getReply(npc));
		assertEquals(questHistory, quest.getHistory(player));
		en.step(player, "bye");
		assertEquals("See you later!", getReply(npc));
		assertEquals(questHistory, quest.getHistory(player));

		// -----------------------------------------------
		// Player has money, pays
		player.equipToInventoryOnly(money);
		en.step(player, "hi");
		assertEquals(questHistory, quest.getHistory(player));
		assertEquals("I see that you have 800 money! Do you want to rent one of my storage units?",
				getReply(npc));
		en.step(player, "yes");
		questHistory.add("I paid Serena to use a storage unit.");
		assertEquals(questHistory, quest.getHistory(player));
		assertEquals("Oh great! When you need to use your #storage unit, let me know.", getReply(npc));
		en.step(player, "task");
		assertEquals(questHistory, quest.getHistory(player));
		assertEquals("You already have a #storage unit.", getReply(npc));
	}
	 
}
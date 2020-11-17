package games.stendhal.server.maps.quests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.mapstuff.chest.Chest;
import games.stendhal.server.entity.mapstuff.chest.StoredChest;
import games.stendhal.server.entity.mapstuff.portal.Portal;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;

import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class StorageRentingTest extends ZonePlayerAndNPCTestImpl {
	private static final String ZONE_NAME = "int_adoc_town_hall_3";
	
	private Portal storagePortal;

	private StoredChest chest;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		Portal.generateRPClass();
		Chest.generateRPClass();
		
		setupZone(ZONE_NAME);
		
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	// DO I NEED THIS?
	}
	
	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		SingletonRepository.getNPCList().add(new SpeakerNPC("Serena"));

		new StorageRenting().addToWorld();
	}
	
	@After
	public void clearStored() {
		if (storagePortal != null) {
			StendhalRPZone zone = storagePortal.getZone();
			if (zone != null) {
				zone.remove(storagePortal);
				storagePortal = null;
			}
		}

		if (chest != null) {
			StendhalRPZone zone = chest.getZone();
			if (zone != null) {
				zone.remove(chest);
				chest = null;
			}
		}

		PlayerTestHelper.removeNPC("Serena");

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
		            "I rent out #storage units for people who just have too much stuff!",
		            getReply(npc));
		en.step(player,"storage");
		assertTrue(npc.isTalking());
		assertEquals(
		            "You can store whatever you like in your storage unit, as long as you pay your rent in #advance!",
		            getReply(npc));
		en.step(player, "advance");
		assertTrue(npc.isTalking());
		assertEquals(
		            "If rent isn't paid in advance, your unit will be emptied and your items left out for the public."
		            + " A #small unit can hold three chests and costs 200 money per week,"
				    + " while a #large unit can hold six chests and costs 300 money per week.",
				    getReply(npc));
	}
	/**
	 * Tests for rentStorage
	 */
	@Test
	public void testRentStorage() {
		final SpeakerNPC npc = getNPC("Serena");
		final Engine en = npc.getEngine();
				
		en.step(player, "hi");
		assertTrue(npc.isTalking());
		assertEquals(
				"Hey there.",
				getReply(npc));
		
		en.step(player, "small");
		assertTrue(npc.isTalking());
		assertEquals(
		            "You'd like a small storage unit? That costs 200 money per week. Would you like to rent one?",
		            getReply(npc));
		en.step(player, "rent");
		assertEquals(
				"Great! How many weeks do you want to rent it for?",
				getReply(npc));
		en.step(player, "4");
		assertEquals("You don't have enough money to rent a storage unit!",
				getReply(npc));
		final StackableItem money = (StackableItem)SingletonRepository.getEntityManager().getItem("money");
		money.setQuantity(120000);
		player.equipToInventoryOnly(money);
		
		en.step(player, "small");
		assertTrue(npc.isTalking());
		assertEquals(
		            "You'd like a small storage unit? That costs 200 money per week. Would you like to rent one?",
		            getReply(npc));
		en.step(player, "rent");
		assertEquals(
				"Great! How many weeks do you want to rent it for?",
				getReply(npc));
		en.step(player, "4");
		assertEquals("Here's the key to your small storage unit. Be careful not to lose it, or you won't be able to access your things..."
				+ "but the person who finds your key might.",
				getReply(npc));
		
		Item item = player.getFirstEquipped("player's storage key");
		assertNotNull(item);
		//assertEquals("ados house 50;0;player", item.get("infostring"));
		//assertFalse(item.isBound());
	}
	 
}
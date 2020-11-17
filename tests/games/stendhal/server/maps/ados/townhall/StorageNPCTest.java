package games.stendhal.server.maps.ados.townhall;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

//import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class StorageNPCTest extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_NAME = "int_ados_town_hall_3";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}
	
	public StorageNPCTest() {
		setNpcNames("Serena");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new StorageNPC(), ZONE_NAME);
	}
	
	/**
	 * Tests for hi and job and bye.
	 */
	@Test
	public void testJob() {

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
		            "I rent out storage units for people who just have too much stuff!",
		            getReply(npc));
		en.step(player, "bye");
		assertFalse(npc.isTalking());
	}
	/**
	 * Tests for smaller storage units.
	 */
	@Test
	public void testSmallStorage() {

		final SpeakerNPC npc = getNPC("Serena");
		final Engine en = npc.getEngine();

		en.step(player, "hi");
		assertTrue(npc.isTalking());
		assertEquals(
				"Hey there.",
				getReply(npc));
		en.step(player, "storage");
		assertTrue(npc.isTalking());
		assertEquals(
		            "You can store whatever you like in your storage unit, but don't forget to pay your #rent in advance!",
		            getReply(npc));
		en.step(player, "rent");
		assertTrue(npc.isTalking());
		assertEquals(
		            "If rent isn't paid in advance, your unit will be emptied and your items left out for the public."
		            + " A #small unit can hold three chests and costs 200 money per week,"
				    + " while a #large unit can hold six chests and costs 300 money per week.",		            getReply(npc));
		en.step(player, "small");
		assertTrue(npc.isTalking());
		assertEquals(
		            "You'd like a small storage unit? That costs 200 money per week. Would you like to rent one?",
		            getReply(npc));
		en.step(player, "large");
		assertTrue(npc.isTalking());
		assertEquals(
		            "You'd like a large storage unit? That costs 300 money per week. Would you like to rent one?",
		            getReply(npc));
		en.step(player, "yes");
		assertTrue(npc.isTalking());
		assertEquals(
		            "That will be 800 money. Are you sure you want to rent?",
		            getReply(npc));
		// need to make sure player has money, and set up portal to a storage unit
		
		en.step(player, "rent");
		assertTrue(npc.isTalking());
		assertEquals(
		            "Great! Here's your key."
		            + " Do you want to buy a spare key for 1000 money?",
		            getReply(npc));
		en.step(player, "bye");
		assertFalse(npc.isTalking());
	}
	
}
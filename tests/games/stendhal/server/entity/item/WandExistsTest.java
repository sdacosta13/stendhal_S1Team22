package games.stendhal.server.entity.item;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.rp.StendhalRPAction;
import games.stendhal.server.entity.creature.Creature;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.server.game.db.DatabaseFactory;
import utilities.PlayerTestHelper;

public class WandExistsTest {
	private StendhalRPZone zone;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		new DatabaseFactory().initializeDatabase();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		PlayerTestHelper.removeAllPlayers();
	}

	@Before
	public void setUp() throws Exception {
		zone = new StendhalRPZone("zone", 20, 20);
		zone.protectionMap.init(1, 1);
		MockStendlRPWorld.get().addRPZone(zone);
	}

	@After
	public void tearDown() throws Exception {
		MockStendlRPWorld.get().removeZone(zone);

	}
	@Test
	public void WandCreationDoesNotErrorTest() {
		Item wand = SingletonRepository.getEntityManager().getItem("wand of sluggishness"); //data/conf/items/ranged.xml needs updating
		assertNotNull(wand);
	}
	@Test
	public void AttackSlowsTest() {
		final Player player = PlayerTestHelper.createPlayer("testPlayer");
		Creature victim = SingletonRepository.getEntityManager().getCreature("mouse");
		zone.add(victim);
		Item wand = SingletonRepository.getEntityManager().getItem("wand of sluggishness");
		player.equip("lhand", wand);
		assertSame(player.getWeapon(), wand);
		double moveSpeed = victim.getBaseSpeed();
		StendhalRPAction.startAttack(player, victim);
		assertSame(player.getAttackTarget(), victim);
		boolean hit = false;
		while(!hit) {
			hit = player.attack();
		}
		player.stopAttack();;
		assertTrue(moveSpeed > victim.getBaseSpeed());
		
		
	}
}

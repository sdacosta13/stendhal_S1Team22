package games.stendhal.server.maps.deniran.ship;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.core.engine.StendhalRPZone;
import utilities.PlayerTestHelper;

public class FerryBetweenAdosAndDeniranTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
	}
	
	@Test
	public void testFerryFromAdosToDeniran() {
		DeniranFerry deniranFerry = SingletonRepository.getDeniranFerry();
		
		final Player player = PlayerTestHelper.createPlayer("bob");
		
		StendhalRPZone ferryZone = new StendhalRPZone("0_deniran_ship_w2");
		StendhalRPZone deniranZone = new StendhalRPZone("0_deniran_river_s");
		
		player.onAdded(ferryZone);
		
		try {
			Class<DeniranFerry> ferry = DeniranFerry.class;
			Field current = ferry.getDeclaredField("current");
			current.setAccessible(true);
			current.set(deniranFerry, DeniranFerry.Status.ANCHORED_AT_DENIRAN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(player.getZone());
		assertThat(player.getZone(), is(deniranZone));
	}
}

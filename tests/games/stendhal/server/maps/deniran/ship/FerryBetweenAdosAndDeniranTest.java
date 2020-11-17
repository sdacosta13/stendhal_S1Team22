package games.stendhal.server.maps.deniran.ship;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Field;

//import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.maps.deniran.DeniranFerry;
import games.stendhal.server.maps.deniran.DeniranFerry.Status;
import games.stendhal.server.core.engine.StendhalRPZone;
import utilities.PlayerTestHelper;

public class FerryBetweenAdosAndDeniranTest {
	private static StendhalRPZone shipZone;
	private static StendhalRPZone ados;
	private static StendhalRPZone deniran;
	private static DeniranFerry deniranFerry;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		shipZone = new StendhalRPZone("0_deniran_ship_w2",100,100);
		ados = new StendhalRPZone("0_ados_coast_s_w2",100,100);
		deniran = new StendhalRPZone("0_deniran_river_s",100,100);
		//Player player = PlayerTestHelper.createPlayer("babara");
		SingletonRepository.getRPWorld().addRPZone(shipZone);
		SingletonRepository.getRPWorld().addRPZone(ados);
		SingletonRepository.getRPWorld().addRPZone(deniran);
		deniranFerry = SingletonRepository.getDeniranFerry();
	}

	  @Test 
	  public void testFerryFromAdosToDeniran() { 
		  try {
				Class<DeniranFerry> ferry = DeniranFerry.class;
				Field current = ferry.getDeclaredField("current");
				current.setAccessible(true);
				current.set(deniranFerry, Status.ANCHORED_AT_ADOS);
			}catch(Exception e) {
				e.printStackTrace();
			}
		  final Player player = PlayerTestHelper.createPlayer("babara"); 
		  StendhalRPZone ship = SingletonRepository.getRPWorld().getZone("0_deniran_ship_w2"); StendhalRPZone
		  deniran = SingletonRepository.getRPWorld().getZone("0_deniran_river_s");
	  
		  ship.add(player);
	  
		  deniranFerry.onTurnReached(1);
		  deniranFerry.onTurnReached(2);
		  assertThat(player.getZone(),is(deniran));  
	  
	  }
	 
	@Test
	public void testFerryFromDeniranToAdos() {
		try {
			Class<DeniranFerry> ferry = DeniranFerry.class;
			Field current = ferry.getDeclaredField("current");
			current.setAccessible(true);
			current.set(deniranFerry, Status.ANCHORED_AT_DENIRAN);
		}catch(Exception e) {
			e.printStackTrace();
		}
		final Player player = PlayerTestHelper.createPlayer("sandy");
		StendhalRPZone ship = SingletonRepository.getRPWorld().getZone("0_deniran_ship_w2");
		StendhalRPZone ados = SingletonRepository.getRPWorld().getZone("0_ados_coast_s_w2");
		
		ship.add(player);
		
		deniranFerry.onTurnReached(1);
		deniranFerry.onTurnReached(2);
		assertThat(player.getZone(),is(ados));
	}
	
	}

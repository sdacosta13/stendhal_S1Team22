package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;

public class TeleportActionTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		SlashActionRepository.register();
	}

	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}

	/**
	 * Tests for execute.
	 */
	@Test
	public void testExecute() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction teleport) {
				assertEquals("teleport", teleport.get("type"));
				assertEquals("schnick", teleport.get("target"));
				assertEquals("second", teleport.get("zone"));  //params[1]
				assertEquals("third", teleport.get("x")); //params[2]
				assertEquals("forth", teleport.get("y")); //params[3]
			
				
				
			}
		};
		final SlashAction action = SlashActionRepository.get("teleport");
		assertTrue(action.execute(new String [] {"schnick", "second","third", "forth" },"teleport"));
		
		
		
	}

	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final SlashAction action = SlashActionRepository.get("teleport");
		assertThat(action.getMaximumParameters(), is(4));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final SlashAction action = SlashActionRepository.get("teleport");
		assertThat(action.getMinimumParameters(), is(4));
	}

}

package games.stendhal.client.actions;
//import static games.stendhal.common.constants.Actions.REMOVEDETAIL;
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

public class MessageActionTest {
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
			public void send(final RPAction remove) {
				assertEquals("tell", remove.get("type"));  // action.put("type", REMOVEDETAIL);
				assertEquals("lastPlayerTell", remove.get("target"));
				assertEquals("something", remove.get("text"));
				
				
				
				
			}
		};
		final SlashAction action = SlashActionRepository.get("msg");
		assertTrue(action.execute(new String [] {"lastPlayerTell"}, "something"));
		
		
	}

	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final SlashAction action = SlashActionRepository.get("remove");
		assertThat(action.getMaximumParameters(), is(1));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final SlashAction action = SlashActionRepository.get("remove");
		assertThat(action.getMinimumParameters(), is(1));
	}

}

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

public class AlterKillActionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@After
 	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}

	/**
	 * Tests for execute without creature.
	 */
	@Test
	public void testExecuteWithNoCreature() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("alterkill", action.get("type"));
				assertEquals("target", action.get("target"));
				assertEquals("killtype", action.get("killtype"));
				assertEquals("count", action.get("count"));
				assertEquals(null, action.get("creature"));
			}
		};
		
		final SlashAction action = SlashActionRepository.get("alterkill");
		assertTrue(action.execute(new String []{"target", "killtype", "count"}, null)); //no remainder
	}
 
	/**
	 * Tests for execute with creature.
	 */
	@Test
	public void testExecuteWithCreature() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("alterkill", action.get("type"));
				assertEquals("target", action.get("target"));
				assertEquals("killtype", action.get("killtype"));     
				assertEquals("count", action.get("count"));
				assertEquals("creature", action.get("creature"));
			}
		};
		
		SlashActionRepository.register();
		final SlashAction action = SlashActionRepository.get("alterkill");
		assertTrue(action.execute(new String []{"target", "killtype", "count"}, "creature")); // has remainder
	}
 
 
	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		SlashActionRepository.register();
		final SlashAction action = SlashActionRepository.get("alterkill");
		assertThat(action.getMaximumParameters(), is(3));
	}
	
	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		SlashActionRepository.register();
		final SlashAction action = SlashActionRepository.get("alterkill");
		assertThat(action.getMinimumParameters(), is(3));
	}
}
	
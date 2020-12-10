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

public class InspectKillActionTest {

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
				assertEquals("inspectkill", action.get("type"));
				assertEquals("target", action.get("target"));
				assertEquals(null, action.get("creature"));
			}
		};
		
		SlashActionRepository.register();
		final SlashAction action = SlashActionRepository.get("inspectkill");
		assertTrue(action.execute(new String []{"target"}, null)); // no remainder
	}
 
	/**
	 * Tests for execute with creature.
	 */
	@Test
	public void testExecuteWithCreature() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("inspectkill", action.get("type"));
				assertEquals("target", action.get("target"));
				assertEquals("creature", action.get("creature"));
			}
		};
		
		SlashActionRepository.register();
		final SlashAction action = SlashActionRepository.get("inspectkill");
		assertTrue(action.execute(new String []{"target"}, "creature")); // has remainder
	}
 
 
	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		SlashActionRepository.register();
		final SlashAction action = SlashActionRepository.get("inspectkill");
		assertThat(action.getMaximumParameters(), is(1));
	}
	
	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		SlashActionRepository.register();
		final SlashAction action = SlashActionRepository.get("inspectkill");
		assertThat(action.getMinimumParameters(), is(1));
	}
}
	
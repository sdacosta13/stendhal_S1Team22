package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import games.stendhal.client.StendhalClient;

import games.stendhal.client.MockStendhalClient;
import marauroa.common.game.RPAction;

public class WhereActionTest {
	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}
	
	
	
	@Test
	public void testRemainderNotLengthZero() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("where",action.get("type"));
				assertEquals("test",action.get("target"));
			}
		};
		SlashActionRepository.register();
		final SlashAction wa = SlashActionRepository.get("where");
		assertTrue(wa.execute(new String[] {null}, "test"));
	}
	
	@Test
	public void testRemainderLengthZero() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("where",action.get("type"));
				assertEquals(" ",action.get("target"));
			}
		};
		SlashActionRepository.register();
		final SlashAction wa = SlashActionRepository.get("where");
		assertTrue(wa.execute(new String[] {null}, " "));
	}
	
	
	@Test
	public void testGetMaximumParameters() {
		SlashActionRepository.register();
		final SlashAction action = SlashActionRepository.get("where");
		assertThat(action.getMaximumParameters(), is(0));
	}

	@Test
	public void testGetMinimumParameters() {
		SlashActionRepository.register();
		final SlashAction action = SlashActionRepository.get("where"); 
		assertThat(action.getMinimumParameters(), is(0));
	}

}

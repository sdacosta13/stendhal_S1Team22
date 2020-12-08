package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import games.stendhal.client.StendhalClient;
import games.stendhal.client.MockStendhalClient;
import marauroa.common.game.RPAction;
import static games.stendhal.common.constants.Actions.CASTSPELL;

public class CastSpellActionTest {
	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}
	@Test
	public void testExecute() {
		new MockStendhalClient(){
			@Override
			public void send(final RPAction action) {
				assertEquals(CASTSPELL,action.get("type"));
				try {
					assertEquals("#"+"schnuck",action.get("target"));
				}catch(NumberFormatException e) {
					assertEquals("schnuck",action.get("target"));
				}
				assertEquals("schnick",action.get("baseitem"));
				assertEquals("spells",action.get("baseslot"));
			}
		};
		final CastSpellAction action = new CastSpellAction();
		assertTrue(action.execute(new String[] {"schnick","#"+"schnuck"}, null));
		assertTrue(action.execute(new String[] {"schnick","#"+"schnuck"}, "spells"));
	}
	@Test
	public void testGetMaximumParameters() {
		final CastSpellAction action = new CastSpellAction();
		assertThat(action.getMaximumParameters(), is(2));
	}

	@Test
	public void testGetMinimumParameters() {
		final CastSpellAction action = new CastSpellAction(); 
		assertThat(action.getMinimumParameters(), is(2));
	}
}

/* $Id$ */
package games.stendhal.client.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.StendhalClient;
import games.stendhal.client.util.UserInterfaceTestHelper;
//import marauroa.common.game.RPAction;

public class GMHelpActionTest {
	@BeforeClass
	public static void init() {
		UserInterfaceTestHelper.resetUserInterface();
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
		final GMHelpAction action = new GMHelpAction();

		assertTrue(action.execute(new String[]{"alter"}, null));
		assertTrue(action.execute(new String[]{"support"}, null));
		assertTrue(action.execute(new String[]{"script"}, null));
		assertTrue(action.execute(new String[]{null}, null));
  
		assertFalse(action.execute(new String[]{"alter", "support"}, null));
		assertFalse(action.execute(new String[]{"alter", "support", "script"}, null));
  
	}

	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final GMHelpAction action = new GMHelpAction();
		assertEquals(1, action.getMaximumParameters());
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final GMHelpAction action = new GMHelpAction();
		assertEquals(0, action.getMinimumParameters());
	}
}
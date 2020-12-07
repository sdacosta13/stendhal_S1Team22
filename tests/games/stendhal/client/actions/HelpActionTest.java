/* $Id$ */
package games.stendhal.client.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.StendhalClient;
import games.stendhal.client.util.UserInterfaceTestHelper;

public class HelpActionTest {
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
		final HelpAction action = new HelpAction();
		// 
		assertTrue(action.execute(new String[]{null}, null));
	}

	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final HelpAction action = new HelpAction();
		assertEquals(0, action.getMaximumParameters());
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final HelpAction action = new HelpAction();
		assertEquals(0, action.getMinimumParameters());
	}
}
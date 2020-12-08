/* $Id$ */
package games.stendhal.client.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.StendhalClient;
import games.stendhal.client.gui.MockUserInterface;
import games.stendhal.client.gui.UserInterface;
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

	 private static MockUserInterface getInterface() {
		 UserInterface ui = ClientSingletonRepository.getUserInterface();
		 if (ui instanceof MockUserInterface) {
			 return (MockUserInterface) ui;
		 }
		 fail();
		 return null;
	 }

	/**
	 * Tests for execute.
	 */
	@Test
	public void testExecute() {
		SlashActionRepository.register();
		final SlashAction action = SlashActionRepository.get("help");

		assertTrue(action.execute(new String[]{null}, null));
		assertEquals("- /" + "removedetail" + " \tRemove the detail layer (e.g. balloon, umbrella, etc.) from character.",getInterface().getLastEventLine());
	}

	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		SlashActionRepository.register();
		final SlashAction action = SlashActionRepository.get("help");
		assertEquals(0, action.getMaximumParameters());
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		SlashActionRepository.register();
		final SlashAction action = SlashActionRepository.get("help");
		assertEquals(0, action.getMinimumParameters());
	}
}
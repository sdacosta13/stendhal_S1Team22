package games.stendhal.client.actions;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.StendhalClient;
import games.stendhal.client.gui.MockUserInterface;
import games.stendhal.client.gui.UserInterface;
import games.stendhal.client.util.UserInterfaceTestHelper;
import games.stendhal.client.MockStendhalClient;

public class ConfigActionTest {
	@BeforeClass
	public static void init() {
		UserInterfaceTestHelper.resetUserInterface();
	}

	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}
	private static MockUserInterface getInterface() {
		// Check the message
		UserInterface ui = ClientSingletonRepository.getUserInterface();
		// sanity check
		if (ui instanceof MockUserInterface) {
			return (MockUserInterface) ui;
		}
		fail();
		// just for the compiler
		return null;
	}

	@Test
	public void testExecuteWithoutIf() { //test if remainder not equal to null or ""
		new MockStendhalClient() {};
		SlashActionRepository.register();
		final SlashAction ca = SlashActionRepository.get("config");
		assertTrue(ca.execute(new String[] {"schnick"}, "test"));
		assertEquals("Changed configuration property "+ "schnick "+"from \"" + "{undefined}" + "\" to \"" + "test" + "\".",getInterface().getLastEventLine());

	}
	@Test
	public void testExecuteWithIf() { //test if remainder equal to null
		new MockStendhalClient() {};
		SlashActionRepository.register();
		final SlashAction ca = SlashActionRepository.get("config");
		assertTrue(ca.execute(new String[] {"schnick"}, null));
		assertEquals("schnick"+"="+"{undefined}",getInterface().getLastEventLine());

	}
	@Test
	public void testExecuteWithIfAgain() { //test if remainder equal to ""
		new MockStendhalClient() {};
		SlashActionRepository.register();
		final SlashAction ca = SlashActionRepository.get("config");
		assertTrue(ca.execute(new String[] {"schnick"}, ""));
		assertEquals("schnick"+"="+"{undefined}",getInterface().getLastEventLine());

	}
	
	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		SlashActionRepository.register();
		final SlashAction ca = SlashActionRepository.get("config");
		assertEquals(1, ca.getMaximumParameters());
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		SlashActionRepository.register();
		final SlashAction ca = SlashActionRepository.get("config");
		assertEquals(1, ca.getMinimumParameters());
	}

	/**
	 * Tests for fromChatline.
	 */

}

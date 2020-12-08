package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.StendhalClient;
import games.stendhal.client.gui.MockUserInterface;
import games.stendhal.client.gui.UserInterface;
import games.stendhal.client.util.UserInterfaceTestHelper;

public class BareBonesBrowserLaunchCommandTest {
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
	public void testExecuteTwo() {
		final BareBonesBrowserLaunchCommand action = new BareBonesBrowserLaunchCommand("https://stendhalgame.org/wiki/StendhalFAQ");
		assertTrue(action.execute(null, null));
		assertEquals("Trying to open #" + "https://stendhalgame.org/wiki/StendhalFAQ" + " in your browser.",getInterface().getLastEventLine());
	}
	@Test
	public void testExecuteOne() {
		final BareBonesBrowserLaunchCommand action = new BareBonesBrowserLaunchCommand("https://stendhalgame.org/wiki/BeginnersGuide");
		assertTrue(action.execute(null, null));
		assertEquals("Trying to open #" + "https://stendhalgame.org/wiki/BeginnersGuide" + " in your browser.",getInterface().getLastEventLine());
	}
	@Test
	public void testExecuteThree() {
		final BareBonesBrowserLaunchCommand action = new BareBonesBrowserLaunchCommand("https://stendhalgame.org/wiki/Stendhal_Manual/Controls_and_Game_Settings");
		assertTrue(action.execute(null, null));
		assertEquals("Trying to open #" + "https://stendhalgame.org/wiki/Stendhal_Manual/Controls_and_Game_Settings" + " in your browser.",getInterface().getLastEventLine());
	}
	@Test
	public void testExecuteFour() {
		final BareBonesBrowserLaunchCommand action = new BareBonesBrowserLaunchCommand("https://stendhalgame.org/wiki/Stendhal_Rules");
		assertTrue(action.execute(null, null));
		assertEquals("Trying to open #" + "https://stendhalgame.org/wiki/Stendhal_Rules" + " in your browser.",getInterface().getLastEventLine());
	}
	@Test
	public void testExecuteFive() {
		final BareBonesBrowserLaunchCommand action = new BareBonesBrowserLaunchCommand("https://stendhalgame.org/account/change-password.html");
		assertTrue(action.execute(null, null));
		assertEquals("Trying to open #" + "https://stendhalgame.org/account/change-password.html" + " in your browser.",getInterface().getLastEventLine());
	}
	@Test
	public void testExecuteSix() {
		final BareBonesBrowserLaunchCommand action = new BareBonesBrowserLaunchCommand("https://stendhalgame.org/account/history.html");
		assertTrue(action.execute(null, null));
		assertEquals("Trying to open #" + "https://stendhalgame.org/account/history.html" + " in your browser.",getInterface().getLastEventLine());
	}
	@Test
	public void testExecuteSeven() {
		final BareBonesBrowserLaunchCommand action = new BareBonesBrowserLaunchCommand("https://stendhalgame.org/account/merge.html");
		assertTrue(action.execute(null, null));
		assertEquals("Trying to open #" + "https://stendhalgame.org/account/merge.html" + " in your browser.",getInterface().getLastEventLine());
	}
	@Test
	public void testExecuteEight() {
		final BareBonesBrowserLaunchCommand action = new BareBonesBrowserLaunchCommand("https://stendhalgame.org/world/hall-of-fame/active_overview.html");
		assertTrue(action.execute(null, null));
		assertEquals("Trying to open #" + "https://stendhalgame.org/world/hall-of-fame/active_overview.html" + " in your browser.",getInterface().getLastEventLine());
	}
	
	
	
	
	@Test
	public void testGetMaximumParameters() {
		final BareBonesBrowserLaunchCommand action = new BareBonesBrowserLaunchCommand("https://stendhalgame.org/wiki/StendhalFAQ");
		assertThat(action.getMaximumParameters(), is(0));
	}

	@Test
	public void testGetMinimumParameters() {
		final BareBonesBrowserLaunchCommand action= new BareBonesBrowserLaunchCommand("https://stendhalgame.org/wiki/StendhalFAQ"); 
		assertThat(action.getMinimumParameters(), is(0));
	}

}

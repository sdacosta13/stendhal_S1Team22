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

public class MuteActionTest {
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
	public void testUnmute() { 
		SlashActionRepository.register();
		final SlashAction ma = SlashActionRepository.get("mute");
		assertTrue(ma.execute(new String[] {null}, null));
		assertEquals("Sounds are now on.",getInterface().getLastEventLine());
		
		
	}
	
	@Test
	public void testMmute() {
		SlashActionRepository.register();
		final SlashAction ma = SlashActionRepository.get("mute");
		assertTrue(ma.execute(new String[] {null}, null));
		assertEquals("Sounds are now off.",getInterface().getLastEventLine());
		
		
	}

	
	@Test
	public void testGetMaximumParameters() {
		SlashActionRepository.register();
		final SlashAction action = SlashActionRepository.get("mute");
		assertThat(action.getMaximumParameters(), is(0));
	}

	@Test
	public void testGetMinimumParameters() {
		SlashActionRepository.register();
		final SlashAction action = SlashActionRepository.get("mute");
		assertThat(action.getMinimumParameters(), is(0));
	}
}
                                                       
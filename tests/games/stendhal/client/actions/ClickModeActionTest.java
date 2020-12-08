package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;


import games.stendhal.client.util.UserInterfaceTestHelper;
import games.stendhal.client.gui.MockUserInterface;
import games.stendhal.client.gui.UserInterface;
import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.StendhalClient;
import org.junit.Test;
import org.junit.BeforeClass;

public class ClickModeActionTest {
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
	public void testDoubleClickMode() {// test when current mode is double click but will be set to single click
		SlashActionRepository.register();
		final SlashAction cl = SlashActionRepository.get("clickmode");
		assertTrue(cl.execute(new String[] {null}, null));
		assertEquals("Click mode is now set to single click.",getInterface().getLastEventLine());
		
	}
	@Test 
	public void testSingleClickMode() {//test when current mode is single click but will be set to double click
		SlashActionRepository.register();
		final SlashAction cl = SlashActionRepository.get("clickmode");
		assertTrue(cl.execute(new String[] {null}, null));
		assertEquals("Click mode is now set to double click.",getInterface().getLastEventLine());
	}


	
	
	
	
	@Test
	public void testGetMaximumParameters() {
		SlashActionRepository.register();
		final SlashAction action = SlashActionRepository.get("clickmode");
		assertThat(action.getMaximumParameters(), is(0));
	}

	@Test
	public void testGetMinimumParameters() {
		SlashActionRepository.register();
		final SlashAction action = SlashActionRepository.get("clickmode");
		assertThat(action.getMinimumParameters(), is(0));
	}

}

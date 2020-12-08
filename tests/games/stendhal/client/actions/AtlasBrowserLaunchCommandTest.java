package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
//import games.stendhal.client.entity.User;
//import utilities.PlayerTestHelper;
import static org.junit.Assert.*;
//import games.stendhal.server.entity.player.Player;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.StendhalClient;
import games.stendhal.client.gui.MockUserInterface;
import games.stendhal.client.gui.UserInterface;
import games.stendhal.client.util.UserInterfaceTestHelper;

public class AtlasBrowserLaunchCommandTest {
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
	public void testExecuteNotIf() {//test skip if condition instantiate
	  SlashActionRepository.register();
	  final SlashAction ablc = SlashActionRepository.get("atlas");
	  assertTrue(ablc.execute(null, null));
	  assertEquals("Trying to open #"+"https://stendhalgame.org/world/atlas.html"+" in your browser.",getInterface().getLastEventLine());
	  
	}
	
		
	@Test
	public void testGetMaximumParameters() {
		  SlashActionRepository.register();
		  final SlashAction action = SlashActionRepository.get("atlas");
		assertThat(action.getMaximumParameters(), is(0));
	}

	@Test
	public void testGetMinimumParameters() {
		  SlashActionRepository.register();
		  final SlashAction action = SlashActionRepository.get("atlas");
		assertThat(action.getMinimumParameters(), is(0));
	}
}

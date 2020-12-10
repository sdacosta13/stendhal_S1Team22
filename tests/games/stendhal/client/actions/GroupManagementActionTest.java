package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import games.stendhal.client.MockStendhalClient;
import marauroa.common.game.RPAction;
import org.junit.After;
import org.junit.Test;

import games.stendhal.client.StendhalClient;

public class GroupManagementActionTest {
	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}
	@Test
	public void testExecuteSkipIf() {//skip if condition
		new MockStendhalClient() {
			@Override
			public void send(RPAction action) {
				assertEquals("group_management",action.get("type"));
				assertEquals("schnick",action.get("action"));
				assertEquals("test",action.get("params"));
			}
		};	
			SlashActionRepository.register();
			final SlashAction gma = SlashActionRepository.get("group"); 
			assertTrue(gma.execute(new String[] {"schnick"},"test"));
	}
	
	@Test
	public void testExecuteIf() {//test if condition
		new MockStendhalClient() {
			@Override
			public void send(RPAction action) {
				assertEquals("group_message",action.get("type"));
				assertEquals("test",action.get("text"));
			}
		};	
			SlashActionRepository.register();
			final SlashAction gma = SlashActionRepository.get("group"); 
			assertTrue(gma.execute(new String[] {"message"},"test"));
	}
	
	
	
	
	
	
	
	@Test
	public void testGetMaximumParameters() {
		  SlashActionRepository.register();
		  final SlashAction action = SlashActionRepository.get("group");
		assertThat(action.getMaximumParameters(), is(1));
	}

	@Test
	public void testGetMinimumParameters() {
		  SlashActionRepository.register();
		  final SlashAction action = SlashActionRepository.get("group");
		assertThat(action.getMinimumParameters(), is(1));
	}

}

package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import games.stendhal.client.StendhalClient;
import games.stendhal.client.MockStendhalClient;
import marauroa.common.game.RPAction;

public class IgnoreActionTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}

	
	@Test 
	public void testParamsOneNull() { // set params one not null but let target and duration and reason not exist
		new MockStendhalClient(){
		@Override
		public void send(final RPAction action) {
			assertEquals("ignore",action.get("type"));
			assertEquals("1",action.get("list"));	
			assertFalse(action.has("target"));
			assertFalse(action.has("duration"));
		}
	};	
		final IgnoreAction ignore = new IgnoreAction();
		assertFalse(ignore.execute(new String[] {"schnick","schnuck"}, null));
		assertFalse(ignore.execute(new String[] {"schnick","schnuck"}, "test"));
		assertTrue(ignore.execute(new String[] {null}, null));
	}
	
	@Test 
	public void testSeconParamNotNull() { // test when params one not equals to null and target,duration and reason exist
		new MockStendhalClient(){
		@Override
		public void send(final RPAction action) {
			assertEquals("ignore",action.get("type"));
			assertEquals("schnick",action.get("target"));
			assertEquals("60",action.get("duration"));
			assertEquals("test",action.get("reason"));
		}
	};	
		final IgnoreAction ignore = new IgnoreAction();
		assertTrue(ignore.execute(new String[] {"schnick","60"}, "test"));
	}
	
	@Test 
	public void testSeconParamException() { // test exception if duration equals *
		new MockStendhalClient(){
		@Override
		public void send(final RPAction action) {
			assertEquals("ignore",action.get("type"));
			assertEquals("schnick",action.get("target"));
			assertEquals("*",action.get("duration"));
			assertEquals("test",action.get("reason"));
		}
	};	
		final IgnoreAction ignore = new IgnoreAction();
		assertFalse(ignore.execute(new String[] {"schnick","*"}, "test"));
	}
	
	@Test 
	public void testSeconParamExceptionAgain() { // test exception when duration equals -
		new MockStendhalClient(){
		@Override
		public void send(final RPAction action) {
			assertEquals("ignore",action.get("type"));
			assertEquals("schnick",action.get("target"));
			assertEquals("-",action.get("duration"));
			assertEquals("test",action.get("reason"));
		}
	};	
		final IgnoreAction ignore = new IgnoreAction();
		assertFalse(ignore.execute(new String[] {"schnick","-"}, "test"));
	}
	
	@Test
	public void testGetMaximumParameters() {
		final IgnoreAction action = new IgnoreAction();
		assertThat(action.getMaximumParameters(), is(2));
	}

	@Test
	public void testGetMinimumParameters() {
		final IgnoreAction action = new IgnoreAction(); 
		assertThat(action.getMinimumParameters(), is(0));
	}

}

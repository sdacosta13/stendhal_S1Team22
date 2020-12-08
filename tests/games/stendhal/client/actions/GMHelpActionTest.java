/* $Id$ */
package games.stendhal.client.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.StendhalClient;
import games.stendhal.client.gui.MockUserInterface;
import games.stendhal.client.gui.UserInterface;
import games.stendhal.client.util.UserInterfaceTestHelper;

public class GMHelpActionTest {
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
   public void testExecuteIf() { //if params[0] == null
	   SlashActionRepository.register();
	   final SlashAction action = SlashActionRepository.get("gmhelp");
	   assertTrue(action.execute(new String[] {null},null));
	   assertEquals("\t\tList the jailed players and their sentences.",getInterface().getLastEventLine());
   }
   
   @Test
   public void testExecuteElseIf() { //if params[0] == "alter"
	   SlashActionRepository.register();
	   final SlashAction action = SlashActionRepository.get("gmhelp");
	   assertTrue(action.execute(new String[] {"alter"},null));
	   assertEquals("\t\t  This will make <testplayer> look like danter" ,getInterface().getLastEventLine());
  }
    
   @Test
   public void testExecuteElesIfAgain() { //if params[0] == "script"
	   SlashActionRepository.register();
	   final SlashAction action = SlashActionRepository.get("gmhelp");
	   assertTrue(action.execute(new String[] {"script"},null));
	   assertEquals("#/script #ResetSlot.class #player #slot : Resets the named slot such as !kills or !quests. Useful for debugging.",getInterface().getLastEventLine());
  }
   

   @Test
   public void testExecuteElseIfThird() { //test if params[0] == ""
	   SlashActionRepository.register();
	   final SlashAction action = SlashActionRepository.get("gmhelp");
	   assertTrue(action.execute(new String[] {"support"},null));
	   //assertEquals(,getInterface().getLastEventLine()); // need to be achieved
  }
   
   @Test
   public void testExecuteElseIfFourth() { //if params[0] != "script" ,"alter" ,"support"
	   SlashActionRepository.register();
	   final SlashAction action = SlashActionRepository.get("gmhelp");
	   assertFalse(action.execute(new String[]{"aler"}, null));
	   assertFalse(action.execute(new String[]{"suport"}, null));		
	   assertFalse(action.execute(new String[]{"scrpt"}, null));
  }
   
   @Test
   public void testExecuteElse() {//test if params length !=1
	   SlashActionRepository.register();
	   final SlashAction action = SlashActionRepository.get("gmhelp");
	   assertFalse(action.execute(new String[]{"alter", "support"}, null));
	   assertFalse(action.execute(new String[]{"alter", "support", "script"}, null));
   
  }
   
   	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		SlashActionRepository.register();
		final SlashAction action = SlashActionRepository.get("gmhelp");
		assertEquals(1, action.getMaximumParameters());
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		SlashActionRepository.register();
		final SlashAction action = SlashActionRepository.get("gmhelp");
		assertEquals(0, action.getMinimumParameters());
	}
}
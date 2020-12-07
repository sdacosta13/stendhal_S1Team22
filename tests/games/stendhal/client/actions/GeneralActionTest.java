package games.stendhal.client.actions;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;


import org.junit.Test;

public class GeneralActionTest {

	@Test
	public void TestAutoWalkCreation() {
		try {
			GeneralAction autowalk = new GeneralAction("AutoWalkAction.xml");
			if(autowalk.getActionComponents() == null) {
				fail();
			}
		} catch(Exception e) {
			fail();
		}
	}
	@Test
	public void TestAutoWalkKeyPairsSame() {
		GeneralAction autowalk = new GeneralAction("AutoWalkAction.xml");
		ActionComponents comp = autowalk.getActionComponents();
		LinkedHashMap<String, String> golden = new LinkedHashMap<String,String>();
		golden.put("TYPE","WALK");
		golden.put("TARGET","remainder");
		if(!golden.equals(comp.RPActionParams)) {
			fail();
		}
	}
	@Test
	public void checkParamCountForAutoWalk() {
		GeneralAction autowalk = new GeneralAction("AutoWalkAction.xml");
		ActionComponents comp = autowalk.getActionComponents();
		try {
			assertTrue(comp.minParams == 0);
			assertTrue(comp.maxParams == 0);
		} catch (NullPointerException e) {
			fail();
		}
		
	}
	@Test
	public void TestAdminNoteCreation() {
		boolean eOccured = false;
		try {
			GeneralAction autowalk = new GeneralAction("AdminNoteAction.xml");
			if(autowalk.getActionComponents() == null) {
				fail();
			}
		} catch(Exception e) {
			eOccured = true;
		}
		if(eOccured) {
			fail();
		}
	}
	@Test
	public void TestAdminNoteKeyPairsSame() {
		GeneralAction autowalk = new GeneralAction("AdminNoteAction.xml");
		ActionComponents comp = autowalk.getActionComponents();
		LinkedHashMap<String, String> golden = new LinkedHashMap<String,String>();
		golden.put("type","adminnote");
		golden.put("target","params0");
		golden.put("note","remainder");
		if(!golden.equals(comp.RPActionParams)) {
			fail();
		}
	}
	@Test
	public void checkParamCountForAdminNote() {
		GeneralAction autowalk = new GeneralAction("AdminNoteAction.xml");
		ActionComponents comp = autowalk.getActionComponents();
		try {
			assertTrue(comp.minParams == 1);
			assertTrue(comp.maxParams == 1);
		} catch (NullPointerException e) {
			fail();
		}
		
	}
	@Test
	public void TestAlterQuestCreation() {
		boolean eOccured = false;
		try {
			GeneralAction autowalk = new GeneralAction("AlterQuestAction.xml");
			if(autowalk.getActionComponents() == null) {
				fail();
			}
		} catch(Exception e) {
			eOccured = true;
		}
		if(eOccured) {
			fail();
		}
	}
	@Test
	public void TestAlterQuestKeyPairsSame() {
		GeneralAction autowalk = new GeneralAction("AlterQuestAction.xml");
		ActionComponents comp = autowalk.getActionComponents();
		LinkedHashMap<String, String> golden = new LinkedHashMap<String,String>();
		golden.put("type","alterquest");
		golden.put("target","params0");
		golden.put("name","params1");
		if(!golden.equals(comp.RPActionParams)) {
			fail();
		}
	}
	@Test
	public void checkParamCountForAlterQuest() {
		GeneralAction autowalk = new GeneralAction("AlterQuestAction.xml");
		ActionComponents comp = autowalk.getActionComponents();
		try {
			assertTrue(comp.minParams == 2);
			assertTrue(comp.maxParams == 3);
		} catch (NullPointerException e) {
			fail();
		}
		
	}
	@Test
	public void TestSentenceCreation() {
		boolean eOccured = false;
		try {
			GeneralAction autowalk = new GeneralAction("SentenceAction.xml");
			if(autowalk.getActionComponents() == null) {
				fail();
			}
		} catch(Exception e) {
			eOccured = true;
		}
		if(eOccured) {
			fail();
		}
	}
	@Test
	public void TestSentanceKeyPairsSame() {
		GeneralAction autowalk = new GeneralAction("SentenceAction.xml");
		
		ActionComponents comp = autowalk.getActionComponents();
		LinkedHashMap<String, String> golden = new LinkedHashMap<String,String>();
		golden.put("type","sentence");
		golden.put("value","remainder");
		
		if(!golden.equals(comp.RPActionParams)) {
			fail();
		}
		
		
		
	}
	@Test
	public void checkParamCountForSentence() {
		GeneralAction autowalk = new GeneralAction("SentenceAction.xml");
		ActionComponents comp = autowalk.getActionComponents();
		try {
			assertTrue(comp.minParams == 0);
			assertTrue(comp.maxParams == 0);
		} catch (NullPointerException e) {
			fail();
		}
		
	}
	@Test
	public void TestAnswerCreation() {
		boolean eOccured = false;
		try {
			GeneralAction autowalk = new GeneralAction("AnswerAction.xml");
			if(autowalk.getActionComponents() == null) {
				fail();
			}
		} catch(Exception e) {
			eOccured = true;
		}
		if(eOccured) {
			fail();
		}
	}
	@Test
	public void TestAnswerKeyPairsSame() {
		GeneralAction autowalk = new GeneralAction("AnswerAction.xml");
		ActionComponents comp = autowalk.getActionComponents();
		LinkedHashMap<String, String> golden = new LinkedHashMap<String,String>();
		golden.put("type","answer");
		golden.put("text","remainder");
		if(!golden.equals(comp.RPActionParams)) {
			fail();
		}
	}
	@Test
	public void checkParamCountForAnswerAction() {
		GeneralAction autowalk = new GeneralAction("AnswerAction.xml");
		ActionComponents comp = autowalk.getActionComponents();
		try {
			assertTrue(comp.minParams == 1);
			assertTrue(comp.maxParams == 0);
		} catch (NullPointerException e) {
			fail();
		}
		
	}

}

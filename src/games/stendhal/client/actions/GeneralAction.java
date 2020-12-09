package games.stendhal.client.actions;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.common.constants.Actions;
import marauroa.common.game.RPAction;
public class GeneralAction implements SlashAction{
	private ActionComponents toUse;
	private String name;
	public GeneralAction(String name) {
		// TODO Auto-generated constructor stub
		// Changes this class such that its functionality will be the same as its migrated class
		// call the xml parser here
		this.name = name.substring(0, name.lastIndexOf('.'));
		try {
			File inp = new File("data/conf/actions/"+name);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			ActionHandler actionHandler = new ActionHandler();
			saxParser.parse(inp, actionHandler);
			toUse = actionHandler.returnComponents();
		} catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

	@Override
	public boolean execute(String[] params, String remainder) {
		RPAction generalRPAction = new RPAction();
		if(toUse.paramNull) {
			if(params == null) {
				return false;
			}
		}
		if(toUse.errorOnRemainder) {
			if(remainder.isEmpty()) {
				return false;
			}
		}
		if(params.length < this.getMinimumParameters()) return false;
		LinkedHashMap<String, String> keypairs = toUse.RPActionParams;
		for(Map.Entry<String, String> entry : keypairs.entrySet()) {
			if(entry.getValue().contains("param")) {
				int index = Integer.parseInt(entry.getValue().substring(6,entry.getValue().length()));
				generalRPAction.put(entry.getKey(), params[index]);
			} else if(entry.getValue().equals("remainder")){
				if(this.checkForConstant(entry.getKey())) {
					String s = this.convertToEnum(entry.getKey());
					generalRPAction.put(s, remainder);
				} else {
					generalRPAction.put(entry.getKey(), remainder);
				}
			} else {
				String a = entry.getKey();
				String b = entry.getValue();
				if(this.checkForConstant(a)) {
					a = this.convertToEnum(entry.getKey());
				} else if(this.checkForConstant(b)) {
					b = this.convertToEnum(entry.getValue());
				}
				generalRPAction.put(a.toLowerCase(), b.toLowerCase());
				
			}
		}
		LinkedHashMap<String, String> optionalParams = toUse.optionalParams;
		if(params.length + 1 > keypairs.size()) {
			for(Map.Entry<String, String> entry : optionalParams.entrySet()) {
				if(entry.getValue().contains("params")) {
					int index = Integer.parseInt(entry.getValue().substring(6,entry.getValue().length()));
					generalRPAction.put(entry.getKey(), params[index]);
				} else if(entry.getValue().equals("remainder")){
					if(this.checkForConstant(entry.getKey())) {
						String s = this.convertToEnum(entry.getKey());
						generalRPAction.put(s, remainder);
					} else {
						generalRPAction.put(entry.getKey(), remainder);
					}
				} else {
					String a = entry.getKey();
					String b = entry.getValue();
					if(this.checkForConstant(a)) {
						a = this.convertToEnum(entry.getKey());
					}
					if(this.checkForConstant(b)) {
						b = this.convertToEnum(entry.getValue());
					}
					generalRPAction.put(a.toLowerCase(), b.toLowerCase());

				}
			}
		}
		
		ClientSingletonRepository.getClientFramework().send(generalRPAction);
		return true;
	}

	@Override
	public int getMaximumParameters() {

		return toUse.maxParams;
	}

	@Override
	public int getMinimumParameters() {

		return toUse.minParams;
	}
	@Override
	public String toString() {
		return "games.stendhal.client.actions."+name+"@"+Integer.toHexString(hashCode());
	}
	private boolean checkForConstant(String s) {
		if(s.toUpperCase().equals(s)) {
			return true;
		} else {
			return false;
		}
	}
	private String convertToEnum(String s) {
		switch(s) {
			case "TYPE":
				return Actions.TYPE;
			case "WALK":
				return Actions.WALK;
			case "TARGET":
				return Actions.TARGET;
			case "MODE":
				return Actions.GHOSTMODE;
			case "INSPECTQUEST":
				return Actions.INSPECTQUEST;
			case "REMOVEDETAIL":
				return Actions.REMOVEDETAIL;
			case "PROGRESS_STATUS":
				return Actions.PROGRESS_STATUS;
			default:
				return null;
			
		}
	}
	public ActionComponents getActionComponents() {
		return this.toUse;
	}
}
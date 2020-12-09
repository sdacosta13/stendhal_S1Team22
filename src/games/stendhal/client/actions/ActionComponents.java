package games.stendhal.client.actions;

import java.util.LinkedHashMap;

public class ActionComponents {
	LinkedHashMap<String, String> RPActionParams = new LinkedHashMap<String, String>();
	LinkedHashMap<String, String> optionalParams = new LinkedHashMap<String, String>();
	int maxParams;
	int minParams;
	boolean paramNull;
	boolean errorOnRemainder;
	
	
	public ActionComponents(LinkedHashMap<String, String> RPActionParams, LinkedHashMap<String, String> optionalParams, int maxParams, int minParams, boolean paramNull, boolean errorOnRemainder) {
		this.RPActionParams = RPActionParams;
		this.maxParams = maxParams;
		this.minParams = minParams;
		this.paramNull = paramNull;
		this.errorOnRemainder = errorOnRemainder;
		this.optionalParams = optionalParams;
	}

}

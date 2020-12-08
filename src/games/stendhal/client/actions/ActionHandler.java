package games.stendhal.client.actions;

import java.util.LinkedHashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ActionHandler extends DefaultHandler {
	private boolean inKeyPair = false;
	private String key;
	private String value;
	private String index;
	private LinkedHashMap<String, String> returnDict = new LinkedHashMap<String, String>();
	private int maxParam;
	private int minParam;
	private boolean mix = false;
	private boolean paramNull = false;
	private boolean errorOnRemainder = false;
	private boolean optional = false;
	private LinkedHashMap<String, String> optionalParams = new LinkedHashMap<String, String>();
	@Override
	public void startDocument() {
		
	}
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName.equals("keypair")) {
			inKeyPair = true;
			if(attributes.getValue("optional") != null) {
				if(attributes.getValue("optional").contentEquals("1")) {
					optional = true;
				}
				
			} 
		} else if(qName.equals("paramNull")) {
			paramNull = true;
		} else if(qName.equals("errorOnRemainder")) {
			errorOnRemainder = true;
		} else if(inKeyPair && qName.equals("key")) {
			key = attributes.getValue("value");
		} else if(inKeyPair && qName.equals("value")) {
			value = attributes.getValue("value");

			if(value.equals("params")) {
				mix = true;
				index = attributes.getValue("index");
			} 
			
			
		} else if(qName.equals("maxParam")) {
			maxParam = Integer.parseInt(attributes.getValue("value"));
		} else if(qName.equals("minParam")) {
			minParam = Integer.parseInt(attributes.getValue("value"));
		}
	}
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("keypair")) {
			inKeyPair = false;
			//some func to set key pair
			if(optional) {
				if(mix) {
					optionalParams.put(key, value+index);
					mix = false;
				} else {
					optionalParams.put(key, value);
				}
				optional = false;
			} else {
				if(mix) {
					returnDict.put(key, value+index);
					mix = false;
				} else {
					returnDict.put(key, value);
				}
			}
			
		} 
	}
	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
	
	}
	public ActionComponents returnComponents() {
		return new ActionComponents(returnDict, optionalParams, maxParam, minParam, paramNull, errorOnRemainder);
	}
}

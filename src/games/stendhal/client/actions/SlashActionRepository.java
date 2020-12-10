/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.client.actions;

import static games.stendhal.common.constants.Actions.ALTERKILL;
import static games.stendhal.common.constants.Actions.INSPECTKILL;
import static games.stendhal.common.constants.Actions.INSPECTQUEST;
import static games.stendhal.common.constants.Actions.REMOVEDETAIL;
import static games.stendhal.common.constants.General.COMBAT_KARMA;

import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

/**
 * Manages Slash Action Objects.
 */
public class SlashActionRepository {

	/** Set of client supported Actions. */
	private static HashMap<String, SlashAction> actions = new HashMap<String, SlashAction>();

	/**
	 * Registers the available Action.
	 */
	public static void register() {
		final SlashAction msg = new GeneralAction("MessageAction.xml");
		final SlashAction supporta = new SupportAnswerAction();
		final SlashAction who = new GeneralAction("WhoAction.xml");
		final SlashAction help = new HelpAction();
		final GroupMessageAction groupMessage = new GroupMessageAction();

		actions.put("/", new RemessageAction());
		actions.put("add", new GeneralAction("AddBuddyAction.xml"));
		actions.put("adminlevel", new AdminLevelAction());
		actions.put("adminnote", new GeneralAction("AdminNoteAction.xml"));
		actions.put("alter", new GeneralAction("AlterAction.xml"));
		actions.put("altercreature", new GeneralAction("AlterCreatureAction.xml"));
		actions.put(ALTERKILL, new AlterKillAction());
		actions.put("alterquest", new GeneralAction("AlterQuestAction.xml"));
		actions.put("answer", new GeneralAction("AnswerAction.xml"));
		actions.put("atlas", new AtlasBrowserLaunchCommand());
		actions.put("away", new AwayAction());

		actions.put("ban", new GeneralAction("BanAction.xml"));

		actions.put("clear", new ClearChatLogAction());
		actions.put("clickmode", new ClickModeAction());
		actions.put("clientinfo", new ClientInfoAction());
		actions.put("commands", help);
		actions.put("config", new ConfigAction());

		actions.put("drop", new DropAction());

		actions.put("cast", new CastSpellAction());

		actions.put("gag", new GeneralAction("GagAction.xml"));
		actions.put("gmhelp", new GMHelpAction());
//		actions.put("group", new GeneralAction("GroupManagementAction.xml")); 
		actions.put("group", new GroupManagementAction(groupMessage)); 
		actions.put("groupmessage", groupMessage);
		actions.put("grumpy", new GrumpyAction());

		actions.put("help", help);

		actions.put("ignore", new IgnoreAction());
		actions.put("inspect", new GeneralAction("InspectAction.xml"));
		actions.put(INSPECTKILL, new InspectKillAction());
		actions.put(INSPECTQUEST, new GeneralAction("InspectQuestAction.xml"));
		actions.put("invisible", new GeneralAction("InvisibleAction.xml"));

		actions.put("jail", new GeneralAction("JailAction.xml"));

		actions.put("listproducers", new GeneralAction("ListProducersAction.xml"));

		actions.put("me", new GeneralAction("EmoteAction.xml"));
		actions.put("msg", msg);
		actions.put("mute", new MuteAction());

		actions.put("names", who);

		actions.put("p", groupMessage);
		actions.put("profile", new ProfileAction());
		actions.put("travellog", new GeneralAction("TravelLogAction.xml"));

		actions.put("quit", new QuitAction());

		actions.put("remove", new GeneralAction("RemoveBuddyAction.xml"));

		actions.put("sentence", new GeneralAction("SentenceAction.xml"));
		actions.put("status", new GeneralAction("SentenceAction.xml")); // Alias for /sentence
		actions.put("settings", new SettingsAction());

		actions.put("sound", new SoundAction());
		actions.put("volume", new VolumeAction());
		actions.put("vol", new VolumeAction());

		actions.put("storemessage", new GeneralAction("StoreMessageAction.xml"));
		actions.put("postmessage", new GeneralAction("StoreMessageAction.xml"));

		actions.put("summonat", new SummonAtAction());
		actions.put("summon", new SummonAction());
		actions.put("supportanswer", supporta);
		actions.put("supporta", supporta);
		actions.put("support", new GeneralAction("SupportAction.xml"));

		actions.put("takescreenshot", new ScreenshotAction());
		actions.put("teleport", new GeneralAction("TeleportAction.xml"));
		actions.put("teleportto", new GeneralAction("TeleportToAction.xml"));
		actions.put("tellall", new GeneralAction("TellAllAction.xml"));
		actions.put("tell", msg);

		actions.put("where", new WhereAction());
		actions.put("who", who);
		actions.putAll(BareBonesBrowserLaunchCommandsFactory.createBrowserCommands());
//		actions.put("wrap", new WrapAction());

		/* Movement */
		actions.put("walk", new GeneralAction("AutoWalkAction.xml"));
		//actions.put("walk", new AutoWalkAction());
		actions.put("stopwalk", new GeneralAction("AutoWalkStopAction.xml"));
		actions.put("movecont", new MoveContinuousAction());

		// PvP challenge actions
		actions.put("challenge", new GeneralAction("CreateChallengeAction.xml"));
		actions.put("accept", new GeneralAction("AcceptChallengeAction.xml"));

		actions.put(COMBAT_KARMA, new SetCombatKarmaAction());

		// allows players to remove the detail layer manually
		actions.put(REMOVEDETAIL, new GeneralAction("RemoveDetailAction.xml"));
	}

	/**
	 * Gets the Action object for the specified Action name.
	 *
	 * @param name
	 *            name of Action
	 * @return Action object
	 */
	public static SlashAction get(String name) {
		String temp = name.toLowerCase(Locale.ENGLISH);
		return actions.get(temp);
	}

	/**
	 * Get all known command names.
	 *
	 * @return set of commands
	 */
	public static Set<String> getCommandNames() {
		return actions.keySet();
	}
}
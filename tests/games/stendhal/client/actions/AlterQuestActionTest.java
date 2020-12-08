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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;

public class AlterQuestActionTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		SlashActionRepository.register();
	}

	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}

	/**
	 * Tests for execute.
	 */
	@Test
	public void testExecute() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("alterquest", action.get("type"));
				assertEquals("schnick", action.get("target"));
				assertEquals("schnack", action.get("name"));
				assertEquals("schnuck", action.get("state"));

			}
		};
//		final AlterQuestAction action = new AlterQuestAction();
		final SlashAction action = SlashActionRepository.get("alterquest");
//		assertFalse(action.execute(null, null));
		assertFalse(action.execute(new String[] { "schnick" }, null));
		assertTrue(action.execute(new String[] { "schnick", "schnack", "schnuck" }, null));

		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("alterquest", action.get("type"));
				assertEquals("schnick", action.get("target"));
				assertEquals("schnick", action.get("name"));
				assertEquals(null, action.get("state"));

			}
		};

		assertTrue(action.execute(new String[] { "schnick", "schnick" }, null));

	}
	
	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final SlashAction action = SlashActionRepository.get("alterquest");
		assertThat(action.getMaximumParameters(), is(3));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final SlashAction action = SlashActionRepository.get("alterquest");
		assertThat(action.getMinimumParameters(), is(2));
	}
}

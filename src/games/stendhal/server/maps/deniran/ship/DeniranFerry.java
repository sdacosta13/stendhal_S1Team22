package games.stendhal.server.maps.deniran.ship;

import java.util.LinkedList;
import java.util.List;

import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.entity.player.Player;
//import games.stendhal.server.util.TimeUtil;



public final class DeniranFerry implements TurnListener {
	
	private static DeniranFerry instance;
	private Status current;
	
	/**
	 * A list of non-player characters that get notice when the ferry arrives or
	 * departs, so that they can react accordingly, e.g. inform nearby players.
	 */
	private final List<IFerryListener> listeners;

	public DeniranFerry() {
		listeners = new LinkedList<IFerryListener>();
		current = Status.ANCHORED_AT_ADOS;

	}

	public static DeniranFerry get() {
		if (instance == null) {
			DeniranFerry instance = new DeniranFerry();
			// initiate the turn notification cycle
			SingletonRepository.getTurnNotifier().notifyInSeconds(1, instance);
			DeniranFerry.instance = instance;
		}
		return instance;

	}

	public Status getState() {
		return current;
	}
	
//	/**
//	 * Gets a textual description of the ferry's status.
//	 * This method may be useful to future releases if we have NPCs.
//	 * @return A String representation of time remaining till next state.
//	 */
//
//	private String getRemainingSeconds() {
//		final int secondsUntilNextState = SingletonRepository.getTurnNotifier()
//				.getRemainingSeconds(this);
//		return TimeUtil.approxTimeUntil(secondsUntilNextState);
//	}

	/**
	 * Is called when the ferry has either arrived at or departed from a harbor.
	 * @param currentTurn the turn when this listener is called
	 */
	@Override
	public void onTurnReached(final int currentTurn) {
		
		current = current.next();
		for (final IFerryListener npc : listeners) {
			npc.onNewFerryState(current);
		}
		
		if (current==Status.ANCHORED_AT_DENIRAN) {
			StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("0_deniran_ship_w2");

			List<Player> players = zone.getPlayers();
			for (int i = 0; i < players.size(); i++) {
			players.get(i).teleport("0_deniran_river_s", 100, 100, Direction.LEFT, null); 
			}
		} else if (current==Status.ANCHORED_AT_ADOS) {
			StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("0_deniran_ship_w2");
			List<Player> players = zone.getPlayers();
			for (int i = 0; i < players.size(); i++) {
				players.get(i).teleport("0_ados_coast_s_w2", 100, 100, Direction.LEFT, null);
			}
		}
		SingletonRepository.getTurnNotifier().notifyInSeconds(current.duration(), this);

	}
	public void addListener(final IFerryListener npc) {
		listeners.add(npc);
	}

	
	/**
	 * Auto registers the listener to Deniranferry.
	 * deregistration must be implemented if it is used for short living objects
	 *
	 */
	public abstract static class FerryListener implements IFerryListener {
		public FerryListener() {
			SingletonRepository.getDeniranFerry().addListener(this);
		}

	}

	public interface IFerryListener {
		void onNewFerryState(Status current);
	}
	
	// toString method is useful for future releases, and is used by getRemainingSeconds method.
	public enum Status {
		ANCHORED_AT_ADOS {
			@Override
			Status next() {
				return DRIVING_TO_DENIRAN;
			}
			
			@Override
			int duration() {
				return 20;
			}
			
//			@Override
//			public String toString() {
//				return "The ferry is currently anchored at the mainland. It will take off in "
//						+ SingletonRepository.getDeniranFerry().getRemainingSeconds() + ".";
//			}
		},
		DRIVING_TO_DENIRAN() {
			@Override
			Status next() {
				return ANCHORED_AT_DENIRAN;
			}
			
			@Override
			int duration() {
				return 20;
			}
			
//			@Override
//			public String toString() {
//				return "The ferry is currently sailing to Deniran. It will arrive in "
//						+ SingletonRepository.getDeniranFerry().getRemainingSeconds() + ".";
//			}
		},
		ANCHORED_AT_DENIRAN {
			@Override
			Status next() {
				return DRIVING_TO_ADOS;
			}

			@Override
			int duration() {

				return 20;//Set duration time to 20 for convenience
			}
			
//			@Override
//			public String toString() {
//				return "The ferry is currently anchored at Deniran. It will take off in "
//						+ SingletonRepository.getDeniranFerry().getRemainingSeconds() + ".";
//			}
		
		},
		DRIVING_TO_ADOS {
			@Override
			Status next() {
				return ANCHORED_AT_ADOS;
			}

			@Override
			int duration() {
				return 20;
			}
			
//			@Override
//			public String toString() {
//				return "The ferry is currently sailing to the mainland. It will arrive in "
//						+ SingletonRepository.getDeniranFerry().getRemainingSeconds() + ".";
//			}
		};

		abstract Status next();

		abstract int duration();
	}

}

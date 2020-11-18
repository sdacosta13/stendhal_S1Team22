package games.stendhal.server.maps.deniran.ship;

import java.util.LinkedList;
import java.util.List;

import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.entity.player.Player;




public final class DeniranFerry implements TurnListener {
	private static DeniranFerry instance;
	private Status current;
	private final List<IFerryListener> listeners;
	/*
	 * private static StendhalRPZone isDeniranZone; private static StendhalRPZone
	 * AdosZone; private static StendhalRPZone ShipZone;
	 */
	// protected Status ferrystate;

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

	public enum Status {
		ANCHORED_AT_DENIRAN {
			@Override
			Status next() {
				return DRIVING_TO_ADOS;
			}

			@Override
			int duration() {

				return 20;//Set duration time to 20 for convenience
			}
		
		},
		ANCHORED_AT_ADOS {
			@Override
			Status next() {
				return DRIVING_TO_DENIRAN;
			}

			@Override
			int duration() {
				return 20;
			}
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
		};

		abstract Status next();

		abstract int duration();
	}

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

	public abstract static class FerryListener implements IFerryListener {
		public FerryListener() {
			SingletonRepository.getDeniranFerry().addListener(this);
		}


	}

	public interface IFerryListener {
		void onNewFerryState(Status current);
	}


}
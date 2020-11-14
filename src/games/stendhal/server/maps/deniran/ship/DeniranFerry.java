package games.stendhal.server.maps.deniran.ship;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.events.TurnListener;

public class DeniranFerry implements TurnListener {
	
	/** The Singleton instance. */
	private static DeniranFerry instance;
	
	private Status current;

	@Override
	public void onTurnReached(int currentTurn) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}
	
	public enum Status {
		ANCHORED_AT_MAINLAND{
			// TODO Auto-generated method stub
		},
		ANCHORED_AT_DENIRAN {
			// TODO Auto-generated method stub
		}
	}
	
}

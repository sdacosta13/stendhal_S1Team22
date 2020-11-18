package games.stendhal.server.maps.ados.townhall;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.entity.item.Corpse;
import marauroa.common.game.RPObject;

public class StorageUnitRemover implements TurnListener {
	private StendhalRPZone zone;
	
	public StorageUnitRemover(StendhalRPZone zone) {
		this.zone = zone;
	}

	@Override
	public void onTurnReached(int currentTurn) {
		// Tell all corpses they are to be removed
		// (stops timers)
		for (RPObject object : zone) {
			if (object instanceof Corpse) {
				((Corpse) object).onRemoved(zone);
			}
		}
		SingletonRepository.getRPWorld().removeZone(zone);
	}


}

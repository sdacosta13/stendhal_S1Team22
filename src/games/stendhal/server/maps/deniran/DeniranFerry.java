package games.stendhal.server.maps.deniran;

import java.util.List;

import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.entity.player.Player;




public final class DeniranFerry implements TurnListener{
	private static DeniranFerry instance;
	private Status current;
	
	private static StendhalRPZone isDeniranZone;
	private static StendhalRPZone AdosZone;
	
	private DeniranFerry() {
			current = Status.ANCHORED_AT_ADOS;
		
			}
	public static DeniranFerry get() {
		if(instance == null) {
			DeniranFerry instance = new DeniranFerry();
			// initiate the turn notification cycle
			SingletonRepository.getTurnNotifier().notifyInSeconds(1, instance);
			DeniranFerry.instance = instance;
		}
		return instance;
		
	}
	public Status getState() {
		return this.current;
	}


	public enum Status{
		ANCHORED_AT_DENIRAN{
			@Override
			Status next(){
				return DRIVING_TO_ADOS;
			}
			@Override
			int duration() {
				
				return 20;
			}
		},
		ANCHORED_AT_ADOS{
			@Override
			Status next(){
				return DRIVING_TO_DENIRAN;
			}
			@Override
			int duration() {
				return 20;
			}
		},
		DRIVING_TO_ADOS{
			@Override
			Status next(){
				return ANCHORED_AT_ADOS;
			}
			@Override
			int duration() {
				return 20;
			}
		},
		DRIVING_TO_DENIRAN(){
			@Override
			Status next(){
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
	
	private StendhalRPZone getIsDeniranZone() {
		if (isDeniranZone == null) {

			isDeniranZone = SingletonRepository.getRPWorld().getZone("0_deniran_river_s"); //modify later
		}

		return isDeniranZone;
	}
	
	
	private static StendhalRPZone getAdosZone() {
		if (AdosZone== null) {
			 AdosZone = SingletonRepository.getRPWorld().getZone("0_ados_coast_s_w2");//modify later
		}
		return AdosZone;
	}
	
	
	public void DisembarkPlayer(final Player player) {
		switch (current) {
		case ANCHORED_AT_DENIRAN:
			player.teleport(getIsDeniranZone(), 100, 100, Direction.LEFT, null);
			
			break;
		case ANCHORED_AT_ADOS:
			player.teleport(getAdosZone(), 16, 89, Direction.LEFT, null);
			
			break;

		default:
			break;

	}

}
	@Override
	public void onTurnReached(int currentTurn) {
			// next state trigger
			current = current.next();
			SingletonRepository.getTurnNotifier().notifyInSeconds(current.duration(), this);
			if(current == Status.ANCHORED_AT_DENIRAN) {
				StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("0_deniran_river_s");
				List<Player> players = zone.getPlayers();
				for(int i =0;i<players.size();i++) {
					players.get(i).teleport("0_deniran_river_s",100,100,Direction.LEFT,null);
				}
				//player.teleport("0_deniran_river_s",100,100,Direction.LEFT,null);
			}
			else if(current == Status.ANCHORED_AT_ADOS) {
				StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("0_ados_coast_s_w2");
				List<Player> players = zone.getPlayers();
				for(int i =0;i<players.size();i++) {
					players.get(i).teleport("0_deniran_river_s",100,100,Direction.LEFT,null);
				}
			}	
			
		}
	
}


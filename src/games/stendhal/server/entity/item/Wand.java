package games.stendhal.server.entity.item;

import java.util.Map;

import games.stendhal.server.entity.RPEntity;

public class Wand extends Item{
	private RPEntity target;
	
	public Wand(final Wand item) {
		super(item);
		// TODO Auto-generated constructor stub
	}

	public Wand(final String name, final String clazz, final String subclass, final Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onUsed(RPEntity user) {
		if (user.getAttackTarget() != null) {
			target = user.getAttackTarget();
			target.setBaseSpeed(target.getBaseSpeed() - 5);
			return true;
		}

		return false;
	}
	
}
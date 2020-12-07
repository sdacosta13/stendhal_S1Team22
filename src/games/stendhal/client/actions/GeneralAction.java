package games.stendhal.client.actions;

public class GeneralAction implements SlashAction {
	String name;
	ActionComponents toUse;
	public GeneralAction(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}

	@Override
	public boolean execute(String[] params, String remainder) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getMaximumParameters() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinimumParameters() {
		// TODO Auto-generated method stub
		return 0;
	}

}

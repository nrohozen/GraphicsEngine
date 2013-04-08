
public class Behaviors {
	
	private String name;
	
	public Behaviors(String name)
	{
		this.name = name;
	}
	public void initialize(Actor actor)
	{
		actor.getState().setExplode(false);
	}
	public String getName()
	{
		return name;
	}
	public void resetState()
	{
		
	}
	public void processInput(Actor actor)
	{
		
	}
	public void changeState()
	{
		
	}
	public void getState()
	{
		
	}
	
}

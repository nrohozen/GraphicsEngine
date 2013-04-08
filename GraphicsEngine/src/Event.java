
public class Event 
{
	private String name;
	private double xLocation;
	private double yLocation;
	private double zLocation;
	
	public Event(String name)
	{
		this.name = name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
}

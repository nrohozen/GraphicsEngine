import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
//

public class Actor 
{
	private boolean exists;
	private double x;
	private double y;
	private double z;
	
	private double rotation;
	private double xRot;
	private double yRot;
	private double zRot;
	
	private ArrayList<Shape> shapes = new ArrayList<Shape> ();
	private ArrayList<Behaviors> behaviors = new ArrayList<Behaviors>();
	private State state = new State();
	

	public void add (Shape shape)
	{
		shapes.add(shape);
	}
	
	public Collection<Shape> getShapes ()
	{
		return Collections.unmodifiableCollection(shapes);
	}
	
	public double getX() 
	{
		return x;
	}
	
	public double getY() 
	{
		return y;
	}
	
	public double getZ() 
	{
		return z;
	}
	
	public void setCoords(double x, double y, double z) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getRotation() 
	{
		return rotation;
	}

	public void setRotation(double rotation) 
	{
		this.rotation = rotation;
	}
	
	public double getxRot() 
	{
		return xRot;
	}

	public double getyRot() 
	{
		return yRot;
	}

	public double getzRot() 
	{
		return zRot;
	}

	public void setRotationCoords(double x, double y, double z) 
	{
		xRot = x;
		yRot = y;
		zRot = z;
	}
	
	public void setX(double x) 
	{
		this.x = x;
	}

	public void setY(double y) 
	{
		this.y = y;
	}

	public void setZ(double z) 
	{
		this.z = z;
	}
	
	public void addBehavior(Behaviors behave)
	{	
	//	behave.initialize(state);
		behaviors.add(behave);
	}
	public State getState()
	{
		return state;
	}
}

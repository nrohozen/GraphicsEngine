import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
//

public class Actor 
{
	private double x;
	private double y;
	private double z;
	private double midpointX;
	private double midpointY;
	private double midpointZ;
	private double rotation;
	private double xRot;
	private double yRot;
	private double zRot;
	private Point point;
	private String name;
	
	private ArrayList<Shape> shapes = new ArrayList<Shape> ();
	private ArrayList<Behaviors> behaviors = new ArrayList<Behaviors>();
	private State state = new State();
	
	public String getName()
	{
		return name;
	}
	
	public void setMidPoint(double x, double y, double z)
	{
		this.midpointX = x;
		this.midpointY = y;
		this.midpointZ = z;
		point = new Point(x, y, z);
	}
	public Point getPoint()
	{
		return point;
	}
	public double getMidPointX()
	{
		return midpointX;
	}
	public double getMidPointY()
	{
		return midpointY;
	}
	
	public double getMidPointZ()
	{
		return midpointZ;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
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

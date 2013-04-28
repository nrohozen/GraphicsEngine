
public class Point 
{
	private double x;
	private double y;
	private double z;
	
	public Point (double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() 
	{
		return x;
	}

	public void setX(double x) 
	{
		this.x = x;
	}

	public double getY() 
	{
		return y;
	}

	public void setY(double y) 
	{
		this.y = y;
	}

	public double getZ() 
	{
		return z;
	}

	public void setZ(double z) 
	{
		this.z = z;
	}
	
	Point subtract (Point p)
	{
		return new Point (x-p.x, y-p.y, z-p.z);
	}
	public double getDistance(Point p)
	{
		System.out.println("p1x: " + x + " p1y: " + y + "p1z: " + z);
		System.out.println("p2x: " + p.x + " p2y: " + p.y + "p2z: " + p.z);
		double distance = Math.sqrt(Math.pow((p.x-x),2)+ Math.pow((p.z-z), 2));
		return distance;
	}
	Point cross (Point p)
	{
		return new Point (y*p.z-z*p.y, z*p.x-x*p.z, x*p.y-y*p.x);
	}
	
	double dot (Point p)
	{
		return x*p.x + y*p.y + z*p.z;
	}

}

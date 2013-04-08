
public class Shape 
{
	private Material material;
	private boolean tiled = false;
	
	private int numPoints;
	private Point [] points;
	
	private double red;
	private double green;
	private double blue;
	
	public Shape (int numPoints)
	{
		points = new Point [numPoints];
		
		this.numPoints = numPoints;
		this.material = Material.None;
	}
	
	public Shape (int numPoints, Material material)
	{
		points = new Point [numPoints];
		
		this.numPoints = numPoints;
		this.material = material;
		
		if (numPoints != 4 && material != Material.None)
			throw new RuntimeException ("Num points: " + numPoints + " with material set");
	}
	
	public Material getMaterial() 
	{
		return material;
	}

	public void setMaterial(Material material) 
	{
		this.material = material;
	}

	public int getNumPoints ()
	{
		return numPoints;
	}
	
	public double getX (int point)
	{
		return points[point].getX();
	}
	
	public double getY (int point)
	{
		return points[point].getY();
	}
	
	public double getZ (int point)
	{
		return points[point].getZ();
	}
	
	public void setPoint (int point, double x, double y, double z)
	{
		points[point] = new Point(x, y, z);
	}
	
	public Point getPoint (int point)
	{
		return points[point];
	}
	
	public double getRed() 
	{
		return red;
	}

	public double getGreen() 
	{
		return green;
	}

	public double getBlue() 
	{
		return blue;
	}

	public void setColor(double red, double green, double blue) 
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public boolean isTiled() 
	{
		return tiled;
	}

	public void setTiled(boolean tiled) 
	{
		this.tiled = tiled;
	}
}

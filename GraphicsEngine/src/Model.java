import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;



public class Model 
{
	private enum Facing {North, South, East, West}

	private ArrayList<Actor> objects = new ArrayList<Actor> ();
	private ArrayList<Event> events = new ArrayList<Event>();
	private ArrayList<Behaviors> behaviors = new ArrayList<Behaviors>();
	EventManager eventManager = new EventManager();
	
	private double [] playerPosition = new double [3];
	private double [] playerLookAt = new double [3];
	private Facing facing = Facing.North;

	private static final int JUMP_TIME = 10;
	private static final double JUMP_INC = 0.01;
	
	private int goingUp = 0;
	private int goingDown = 0;

	
	public Model ()
	{
		
	}
	
	public void init ()
	{
		//loadBehaviors();
		//loadEvents();
		loadWorld();

		playerPosition[0] = 0.0;
		playerPosition[1] = 0.5;
		playerPosition[2] = 0;
		
		playerLookAt[0] = 0.0;
		playerLookAt[1] = 0.5;
		playerLookAt[2] = -1;
		
		
	}

	public void key (char c)
	{
		if (c == ' ' && goingUp == 0 && goingDown == 0)
			goingUp = JUMP_TIME;
		
		if (c == 'w')
		{
			switch (facing)
			{
				case North:
					playerPosition[2]-=0.25;
					playerLookAt[2]=playerPosition[2]-1;
					break;
					
				case South:
					playerPosition[2]+=0.25;
					playerLookAt[2]=playerPosition[2]+1;
					break;
					
				case East:
					playerPosition[0]+=0.25;
					playerLookAt[0]=playerPosition[0]+1;
					break;
					
				case West:
					playerPosition[0]-=0.25;
					playerLookAt[0]=playerPosition[0]-1;
					break;
			}
		}
		
		if (c == 's')
		{
			switch (facing)
			{
				case North:
					playerPosition[2]+=0.25;
					playerLookAt[2]=playerPosition[2]-1;
					break;
					
				case South:
					playerPosition[2]-=0.25;
					playerLookAt[2]=playerPosition[2]+1;
					break;
					
				case East:
					playerPosition[0]-=0.25;
					playerLookAt[0]=playerPosition[0]+1;
					break;
					
				case West:
					playerPosition[0]+=0.25;
					playerLookAt[0]=playerPosition[0]-1;
					break;
			}
		}
		
		if (c == 'a')
		{
			switch (facing)
			{
				case North:
					facing = Facing.West;
					playerLookAt[2]=playerPosition[2];
					playerLookAt[0]=playerPosition[0]-1;
					break;
					
				case South:
					facing = Facing.East;
					playerLookAt[2]=playerPosition[2];
					playerLookAt[0]=playerPosition[0]+1;
					break;
					
				case East:
					facing = Facing.North;
					playerLookAt[0]=playerPosition[0];
					playerLookAt[2]=playerPosition[2]-1;
					break;
					
				case West:
					facing = Facing.South;
					playerLookAt[0]=playerPosition[0];
					playerLookAt[2]=playerPosition[2]+1;
					break;
			}
		}
		
		if (c == 'd')
		{
			switch (facing)
			{
				case North:
					facing = Facing.East;
					playerLookAt[2]=playerPosition[2];
					playerLookAt[0]=playerPosition[0]+1;
					break;
					
				case South:
					facing = Facing.West;
					playerLookAt[2]=playerPosition[2];
					playerLookAt[0]=playerPosition[0]-1;
					break;
					
				case East:
					facing = Facing.South;
					playerLookAt[0]=playerPosition[0];
					playerLookAt[2]=playerPosition[2]+1;
					break;
					
				case West:
					facing = Facing.North;
					playerLookAt[0]=playerPosition[0];
					playerLookAt[2]=playerPosition[2]-1;
					break;
			}
		}
		
		if (c == 'q')
		{
			switch (facing)
			{
				case North:
					playerPosition[0]-=0.25;
					playerLookAt[0]=playerPosition[0];
					break;
					
				case South:
					playerPosition[0]+=0.25;
					playerLookAt[0]=playerPosition[0];
					break;
					
				case East:
					playerPosition[2]-=0.25;
					playerLookAt[2]=playerPosition[2];
					break;
					
				case West:
					playerPosition[2]+=0.25;
					playerLookAt[2]=playerPosition[2];
					break;
			}
		}
		if(c == 'x')
		{
			Event detonate = new Event("Detonate");
			events.add(detonate);
		}

		if (c == 'e')
		{
			switch (facing)
			{
				case North:
					playerPosition[0]+=0.25;
					playerLookAt[0]=playerPosition[0];
					break;
					
				case South:
					playerPosition[0]-=0.25;
					playerLookAt[0]=playerPosition[0];
					break;
					
				case East:
					playerPosition[2]+=0.25;
					playerLookAt[2]=playerPosition[2];
					break;
					
				case West:
					playerPosition[2]-=0.25;
					playerLookAt[2]=playerPosition[2];
					break;
			}
		}
	}
	
	public void update ()
	{
		if (goingDown > 0)
		{
			playerPosition[1] -= JUMP_INC*(JUMP_TIME-goingDown+1);
			playerLookAt[1] -= JUMP_INC*(JUMP_TIME-goingDown+1);
			
			goingDown--;
		}
		
		if (goingUp > 0)
		{
			playerPosition[1] += JUMP_INC*goingUp;
			playerLookAt[1] += JUMP_INC*goingUp;
			
			goingUp--;
			
			if (goingUp == 0)
				goingDown = JUMP_TIME;
		}
	}
	
	public void mouseClicked (double [] p1a, double [] p2a)
	{
		// The calculations to tell if a line is parallel
		// to a plane require us to have a direction 
		// vector for the line
		Point p1 = new Point (p1a[0], p1a[1], p1a[2]);
		Point p2 = new Point (p2a[0], p2a[1], p2a[2]);
		Point dir = p2.subtract(p1);
		
		Actor closest = null;
		double distance = 1.1;
		
		for (Actor object: objects)
		{
			// TODO:
			// Need to take into account the translation and rotation 
			// of the entire object when calculating the coordinates
			// of the internal shapes
			
			for (Shape shape:object.getShapes())
			{
				Point s0 = shape.getPoint(0);
				Point s1 = shape.getPoint(1);
				Point s2 = shape.getPoint(2);
				
				// Need to calculate the plane for the shape
				Point N = (s1.subtract(s0).cross(s1.subtract(s2)));
				
				// Is the plane parallel to the ray?
				if (dir.dot(N) == 0)
					continue;
				
				// No, need to do the more complicated bits
				// Solve for D
				double D = -(N.getX()*s0.getX() + N.getY()*s0.getY() + N.getZ()*s0.getZ());
				
				// And solve for T
				double T = -(N.getX()*p1.getX() - N.getY()*p1.getY() - N.getZ()*p1.getZ() - D);
				T = T / (-N.getX()*p1.getX() + N.getX()*p2.getX() - N.getY()*p1.getY() + N.getY()*p2.getY() - N.getZ()*p1.getZ() + N.getZ()*p2.getZ());
				
				// See if the point of intersection is outside the viewing area
				if (T < 0.0 || T > 1.0)
					continue;
				
				// No, point of intersection is inside the viewing area
				if (T < distance)
				{
					distance = T;
					closest = object;
				}
			}
		}
		
		if (closest != null)
		{
			// Found the closest object they clicked on
			System.out.println ("Hi");
		}
	}
	
	public Collection<Actor> getAll ()
	{
		// 
		// We don't want to return a reference to an internal
		// data structure, because it might be modified and
		// we've made it private for a reason.  But, we don't
		// want to make a copy of it, either, since that's bad
		// for efficiency.  Using an unmodifiable collection
		// is a nice compromise...a copy is not made, but
		// attempts to change the collection will fail.  
		//
		// This does not prevent attempts to change the objects
		// in the collection (e.g. changing their coordinates),
		// but we'll trust the view to not do that.  The ways to
		// prevent this are convoluted, and not worth it when
		// we're writing code where efficiency is of primary
		// importance.
		//
		return Collections.unmodifiableCollection(objects);
	}
	
	public double getPlayerX ()
	{
		return playerPosition[0];
	}

	public double getPlayerY ()
	{
		return playerPosition[1];
	}

	public double getPlayerZ ()
	{
		return playerPosition[2];
	}
	
	public double getPlayerXLookAt ()
	{
		return playerLookAt[0];
	}

	public double getPlayerYLookAt ()
	{
		return playerLookAt[1];
	}

	public double getPlayerZLookAt ()
	{
		return playerLookAt[2];
	}
	
	public Behaviors getBehavior(String behave)
	{
		Behaviors behavior = null;
		for(int i = 0; i < behaviors.size()-1; i++)
		{
			if(behaviors.get(i).getName().equals(behave))
			{
				behavior = behaviors.get(i);
				return behavior;
			}
		}
		return behavior;
	}
	
	public void loadEvents()
	{
		Event explode = new Event("Explode");
		eventManager.addEvent(explode);
	}
	
	public void loadBehaviors()
	{
		Behaviors explode = new Behaviors("Explode");
		Behaviors detonate = new Behaviors("Detonate");
		behaviors.add(explode);
		behaviors.add(detonate);
	}
	public void loadWorld()
	{
		ArrayList<Shape> shape = new ArrayList<Shape>();
		ArrayList<Actor> obj = new ArrayList<Actor>();
		int i = 0;
		int j = 0;
		boolean firstRun = true;
		try 
		{
			File dir = new File("//users//nrohozen//Documents//workspace//GraphicsEngine//");
			for (String fn : dir.list()) 
			{
				if(fn.endsWith(".in"))
				{
				    FileInputStream fstream = new FileInputStream(fn);
				    DataInputStream in = new DataInputStream(fstream);
				    BufferedReader br = new BufferedReader(new InputStreamReader(in));
				    while (br.ready())   
				    {
						String line = br.readLine();
						System.out.println("line: " + line);
						if(line.isEmpty())
						{
							br.close();
							break;
						}
						if(line.charAt(0) == 'p')
						{
							if(!firstRun)
							{
								i++;
							}
							firstRun = false;
							String[] split = line.split(" ");
							int pointNumber = Integer.parseInt(split[1]);
							shape.add(new Shape(pointNumber));
						}
						if(line.charAt(0) == 't')
						{
							String material = line.substring(2);
							if(material.matches("None"))
							{
								shape.get(i).setMaterial(Material.None);
							}
							if(material.matches("Brick"))
							{
								shape.get(i).setMaterial(Material.Brick);
							}
							if(material.matches("Grass"))
							{
								shape.get(i).setMaterial(Material.Grass);
							}
						}
						if(line.charAt(0) == 'f')   
						{
							String[] split = line.split(" ");
							int pointNumberIndex = Integer.parseInt(split[1]);
							double x = Double.parseDouble(split[2]);
							double y = Double.parseDouble(split[3]);
							double z = Double.parseDouble(split[4]);
							shape.get(i).setPoint(pointNumberIndex, x, y, z);
							pointNumberIndex++;
						}
						if(line.charAt(0) == 'l')
						{
							shape.get(i).setTiled(true);
						}
						if(line.charAt(0) == 'c')
						{
							String[] split = line.split(" ");
							double r = Double.parseDouble(split[1]);
							double g = Double.parseDouble(split[2]);
							double b = Double.parseDouble(split[3]);
							shape.get(i).setColor(r, g, b);
						}
						if(line.charAt(0) == 'o')
						{
							obj.add(new Actor ());
						}
						if(line.charAt(0) == 'A')
						{
							Behaviors addBehave;
							String behave = line.substring(2);
							addBehave = getBehavior(behave);
							if(addBehave != null)
							{
								addBehave.initialize(obj.get(j));
								obj.get(j).addBehavior(addBehave);
							}
						}
						if(line.charAt(0) == 'v')
						{
							String[] split = line.split(" ");
							double x = Double.parseDouble(split[1]);
							double y = Double.parseDouble(split[2]);
							double z = Double.parseDouble(split[3]);
							obj.get(j).setCoords(x, y, z);
						}
						if(line.charAt(0) == 'r')
						{
							String[] split = line.split(" ");
							double rot = Double.parseDouble(split[1]);
							obj.get(j).setRotation(rot);
						}
						if(line.charAt(0) == 'd')
						{
							String[] split = line.split(" ");
							double x = Double.parseDouble(split[1]);
							double y = Double.parseDouble(split[2]);
							double z = Double.parseDouble(split[3]);
							obj.get(j).setRotationCoords(x, y, z);
							obj.get(j).add(shape.get(i));
							j++;
						}
						if(line.charAt(0) == '&')
						{
							
							break;
						}
					}
				    }
				}
			
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		for( int z = 0; z < obj.size(); z++)
		{
			objects.add(obj.get(z));
		}		
	}
}

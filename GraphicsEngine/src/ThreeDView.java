import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;


public class ThreeDView implements View
{
	private double[] eyeLocation = new double [3];
	private double[] lookAtLocation = new double [3];

	private Texture cobbles;
	private Texture brick;
	private Texture grass;
	private Texture stainless;
	private Texture blueWall;
	//private TextRenderer renderer;
	//private String text = "Testing Text";
	private Model model;
	
	private double x;
	private double y;

	
	public ThreeDView (double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void init(GLAutoDrawable drawable, Model model) 
	{
		this.model = model;
	//	renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 36));
		eyeLocation[0] = 0.0;
		eyeLocation[1] = 0.0;
		eyeLocation[2] = 0.0;
		lookAtLocation[0] = 0.0;
		lookAtLocation[1] = 0.0;
		lookAtLocation[2] = -1.0;
		
 		try 
		{
			cobbles = loadTexture ("cobbles.jpg");
			brick = loadTexture ("brick.jpg");
			grass = loadTexture ("grass.jpg");
			stainless = loadTexture("stainless.jpg");
			blueWall = loadTexture("BlueWall.jpg");
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
    public void render(GLAutoDrawable drawable) 
    {		
        GL2 gl = drawable.getGL().getGL2();
		GLU glu = new GLU ();
		
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
        //gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        
		//gl.glMatrixMode(GL2.GL_MODELVIEW);
		//gl.glLoadIdentity();
		//gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		/*
        
    	gl.glClearDepth(1.0);
		gl.glViewport(0,0, 100, 100);
		*/
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		glu.gluPerspective(90, ((double)250)/((double)250), 0.1, 100.0);
		
		gl.glViewport(0, 0, 500, 500);
		
		
		// Set camera position
		glu.gluLookAt(model.getPlayerX(), model.getPlayerY(), model.getPlayerZ(), 
				  model.getPlayerXLookAt(), model.getPlayerYLookAt(), model.getPlayerZLookAt(),
				  0.0, 1.0, 0.0);	
		/*
		// Clip to view coords
		gl.glEnable(GL2.GL_SCISSOR_TEST);
		gl.glScissor(250, 250, 250, 250);
		*/
		
		//drawLine(drawable);

		// And handle mouse clicks
/*		if (mouseClicked)
		{
			mouseClicked = false;
			handleMouseClick (drawable, mouseX, mouseY);
		}
*/
		Collection<Actor> objects = model.getAll();
		
		for (Actor object:objects)
		{
			// Remember our current translation matrix
			gl.glPushMatrix();
			
			// For each object, we need to first translate to
			// the object's position, so that shape drawing
			// happens there.
	//		gl.glTranslated(object.getX(), object.getY(), object.getZ());
			
			// And the object's overall rotation
	//		gl.glRotated(object.getRotation(), object.getxRot(), object.getyRot(), object.getzRot());
			/*
			double[] curmat = new double[16];
			gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, curmat, 0);  //
		
				 // Print out the contents of this matrix in OpenGL format
			
				System.out.println(object.getName());
				   System.out.println("as an OpenGL matrix");
				   for (int row = 0; row < 4; row++)
				       for (int col = 0; col < 4; col++)
					   // OpenGL uses column-major order for storage
					   System.out.format("%7.3f%c", curmat[row+col*4], col==3 ? '\n':' ');
			*/
			
			
			// Then draw each shape
			for (Shape shape:object.getShapes())
			{
				// Only supporting triangles and quads.
				// Polygons could easily be supported, too.
				
				if (shape.getMaterial () == Material.None)
				{
					switch (shape.getNumPoints ())
					{
						case 3:
					        gl.glBegin(GL.GL_TRIANGLES);
							break;
							
						case 4:
					        gl.glBegin(GL2.GL_QUADS);
							break;
							
						default:
							// A real engine would have an error logging
							// mechanism here to keep track of the fact that
							// there's a shape out there with an unsupported
							// number of points
							break;
					}
	
					// If we do textures at some point, an if statement here
					// would see if the object's material indicates a texture
					// or not.
					gl.glColor3d(shape.getRed(), shape.getGreen(), shape.getBlue());
					
					for (int p = 0; p < shape.getNumPoints (); p++)
						gl.glVertex3d(shape.getX(p), shape.getY(p), shape.getZ(p));
					
			        gl.glEnd();
				}
				else
				{
					if (shape.isTiled())
						displayTiledTexture(gl, shape);
					else
						displayTexture (gl, shape);
				}
			}
			
			// Undo the rotation and translation to get us back to the origin
			// for the next object.  
			gl.glPopMatrix();
		}
    }

    private Texture loadTexture (String fileName) throws GLException, IOException
    {
    	ByteArrayOutputStream os = new ByteArrayOutputStream ();
    	ImageIO.write(ImageIO.read(new File (fileName)), "jpg", os);
    	InputStream fis = new ByteArrayInputStream(os.toByteArray());
    	
    	return TextureIO.newTexture(fis, true, TextureIO.JPG);
    }
    
    private void displayTexture (GL2 gl, Shape shape)
    {
    	gl.glPushMatrix();
    	
    	gl.glEnable(GL.GL_BLEND);
    	gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
    	gl.glEnable(GL2.GL_ALPHA_TEST);
    	gl.glAlphaFunc(GL.GL_GREATER, 0);
    	
    	gl.glEnable(GL.GL_TEXTURE_2D);
    	
    	Texture texture = getTexture (shape.getMaterial());
    	texture.bind(gl);
    	
    	gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
    	
    	gl.glBegin(GL2.GL_QUADS);
    	
    	gl.glTexCoord2d(0.0, 0.0);
    	gl.glVertex3d (shape.getX(0), shape.getY(0), shape.getZ(0));

    	gl.glTexCoord2d(0.0, 1.0);
    	gl.glVertex3d (shape.getX(1), shape.getY(1), shape.getZ(1));

    	gl.glTexCoord2d(1.0, 1.0);
    	gl.glVertex3d (shape.getX(2), shape.getY(2), shape.getZ(2));
    	
    	gl.glTexCoord2d(1.0, 0.0);
    	gl.glVertex3d (shape.getX(3), shape.getY(3), shape.getZ(3));
    	
    	gl.glEnd ();
    	gl.glPopMatrix();
    }

    private void displayTiledTexture (GL2 gl, Shape shape)
    {
    	gl.glPushMatrix();
    	
    	gl.glEnable(GL.GL_BLEND);
    	gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
    	gl.glEnable(GL2.GL_ALPHA_TEST);
    	gl.glAlphaFunc(GL.GL_GREATER, 0);
    	
    	gl.glEnable(GL.GL_TEXTURE_2D);
    	
    	Texture texture = getTexture (shape.getMaterial());
    	texture.bind(gl);
    	
    	gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);

    	// Assume horizontal tiling (where the y coordinate is the same between
    	// all the points), because that's all I'm using it for.
    	
    	double minX, minZ, maxX, maxZ, y;
    	
    	minX = shape.getX(0);
    	minZ = shape.getX(0);
    	y = shape.getY(0);
    	
    	maxX = minX;
    	maxZ = minZ;
    	
    	for (int i = 1; i < 4; i++)
    	{
    		double temp;
    		
    		temp = shape.getX(i);
    		
    		if (temp < minX)
    			minX = temp;
    		
    		if (temp > maxX)
    			maxX = temp;
    		
    		temp = shape.getZ(i);
    		
    		if (temp < minZ)
    			minZ = temp;
    		
    		if (temp > maxZ)
    			maxZ = temp;
    	}
    	
    	gl.glBegin(GL2.GL_QUADS);
    	
    	for (double x = minX; x < maxX; x++)
    	{
    		for (double z = minZ; z < maxZ; z++)
    		{
    	    	gl.glTexCoord2d(0.0, 0.0);
    	    	gl.glVertex3d (x, y, z);

    	    	gl.glTexCoord2d(0.0, 1.0);
    	    	gl.glVertex3d (x, y, z+1);

    	    	gl.glTexCoord2d(1.0, 1.0);
    	    	gl.glVertex3d (x+1, y, z+1);
    	    	
    	    	gl.glTexCoord2d(1.0, 0.0);
    	    	gl.glVertex3d (x+1, y, z);
    		}
    	}
    	
    	gl.glEnd ();
    	gl.glPopMatrix();
    }
    
	private Texture getTexture(Material material) 
	{
		// Put case statements here to return the appropriate texture
		// based on the material passed in
		switch (material)
		{
			case Brick:
				return brick;
				
			case Cobbles:
				return cobbles;
				
			case Grass:
				return grass;
				
			case BlueWall:
				return blueWall;
				
			case Stainless:
				return stainless;
			
			default:
				break;
		}
		
		throw new RuntimeException ("getTexture called with invalid material");
	}

	@Override
	public void display(GLAutoDrawable drawable)
	{
		// TODO Auto-generated method stub
		render (drawable);
	}
	private void drawLine(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		GLUT glut = new GLUT();
		
		glut.glutSolidSphere(10, 20, 20);
		gl.glBegin (GL.GL_LINES);
		//gl.glVertex2i (0, 0);
		//gl.glVertex2i (1, 0);
		
		gl.glColor3d(1.0, 0.0, 0.0);
		gl.glVertex3d(model.getPlayerX(), 0.7, model.getPlayerZ());
		gl.glVertex3d(0, 0.7, 0);
		gl.glEnd();
	}
}

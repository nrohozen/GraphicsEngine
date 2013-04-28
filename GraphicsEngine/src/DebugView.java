import java.awt.Font;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.awt.TextRenderer;


public class DebugView implements View 
{
	private Model model;
	private double x;
	private double y;
	private double z;
	private TextRenderer renderer;
	private String text = null;
	
	@Override
	public void init(GLAutoDrawable drawable, Model model) 
	{
		this.model = model;
		renderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 36));
	}

	@Override
	public void render(GLAutoDrawable drawable) 
	{
		GL2 gl = drawable.getGL().getGL2();
		GLU glu = new GLU ();
		gl.glEnable(GL2.GL_SCISSOR_TEST);
		gl.glScissor(300, 350, 200, 150);   //x y width height
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		//glu.gluPerspective(90, ((double)250)/((double)250), 0.0, 100.0);
		//gl.glViewport(250/2, 250/2, 250/2, 250/2);
		gl.glViewport(300, 350, 200, 150);
		
		/*
		gl.glEnable(GL2.GL_SCISSOR_TEST);
		gl.glScissor(250, 250, 250, 250);
		gl.glDisable(GL2.GL_SCISSOR_TEST);
		*/
		gl.glDisable(GL2.GL_SCISSOR_TEST);
		
		
		if (model.getPlayerX() != x || model.getPlayerY() != y || model.getPlayerZ () != z)
		{
			//System.out.println ("X: " + model.getPlayerX() + ", Y: " + model.getPlayerY() + ", Z: " + model.getPlayerZ ());
			x = model.getPlayerX();
			y = model.getPlayerY();
			z = model.getPlayerZ();
			text = x + " " + y + " "  + z;
		}
		
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
		render (drawable);
		 renderer.beginRendering(drawable.getWidth(), drawable.getHeight());
		    // optionally set the color
	
		    renderer.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		    renderer.draw(text, 225, 225);
		   
		    // ... more draw commands, color changes, etc.
		    renderer.endRendering();
		
	}
	

}

import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.FPSAnimator;


public class OpenGLView  implements GLEventListener, KeyListener, MouseListener
{
	private Model model;
	
	private int mouseX;
	private int mouseY;
	private boolean mouseClicked = false;
	
	private ArrayList<View> views = new ArrayList<View> ();

	public OpenGLView ()
	{
		
	}
	
	public void add (View view)
	{
		views.add(view);
	}
	
	public void init (Model m)
	{
		model = m;
		
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);
        
        Frame frame = new Frame("JOGL Skeleton");

        frame.setLayout(null);
        frame.setSize(300, 300);
        frame.add(canvas).setBounds(0, 0, 300, 300);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.addGLEventListener(this);
        canvas.requestFocus();

        FPSAnimator animator = new FPSAnimator(canvas, 60);
        animator.add(canvas);
        animator.start();
	}

	@Override
	public void display(GLAutoDrawable drawable) 
	{
		model.update ();

        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        
		for (int i = 0; i < views.size(); i++)
		{
			views.get(i).render(drawable);
			gl.glFlush();
		}
	}

	@Override
	public void init(GLAutoDrawable drawable) 
	{
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepth(1.0);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
		
		for (int i = 0; i < views.size(); i++)
			views.get(i).init(drawable, model);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) 
	{
		
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) 
	{
		GL2 gl = drawable.getGL().getGL2();
		GLU glu = new GLU ();
		
		if (height == 0)
			height = 1;
		
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(90, ((double)width)/((double)height), 0.1, 100.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

    
    private void handleMouseClick(GLAutoDrawable drawable, int x, int y) 
    {
        GL2 gl = drawable.getGL().getGL2();
		GLU glu = new GLU ();

		int [] viewport = new int[4];
		gl.glGetIntegerv(GL2.GL_VIEWPORT, viewport, 0);

		double [] matrix = new double [16];
		gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, matrix, 0);
		
		double [] project = new double [16];
		gl.glGetDoublev(GL2.GL_PROJECTION_MATRIX, project, 0);
		
		y = viewport[3]-y;
		
		double [] nearCoords = new double[3];
		double [] farCoords = new double[3];
		
		glu.gluUnProject(x, y, 0, matrix, 0, project, 0, viewport, 0, nearCoords, 0);
		glu.gluUnProject(x, y, 1, matrix, 0, project, 0, viewport, 0, farCoords, 0);
		
		model.mouseClicked(nearCoords, farCoords);
	}

	// Unneeded for now, but useful later when we put materials in

	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		mouseX = arg0.getX();
		mouseY = arg0.getY();
		mouseClicked  = true;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
	}

	@Override
	public void mouseExited(MouseEvent arg0) 
	{
	}

	@Override
	public void mousePressed(MouseEvent arg0) 
	{
	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
	}
	
	@Override
	public void keyTyped(KeyEvent e) 
	{
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		model.key (e.getKeyChar());
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		
	}
}

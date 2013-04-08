import javax.media.opengl.GLAutoDrawable;


public class DebugView implements View 
{
	private Model model;
	private double x;
	private double y;
	private double z;
	
	@Override
	public void init(GLAutoDrawable drawable, Model model) 
	{
		this.model = model;

	}

	@Override
	public void render(GLAutoDrawable drawable) 
	{
		if (model.getPlayerX() != x || model.getPlayerY() != y || model.getPlayerZ () != z)
		{
			System.out.println ("X: " + model.getPlayerX() + ", Y: " + model.getPlayerY() + ", Z: " + model.getPlayerZ ());
			x = model.getPlayerX();
			y = model.getPlayerY();
			z = model.getPlayerZ();

		}
		
	}
	

}

import javax.media.opengl.GLAutoDrawable;


public interface View 
{
	public void init(GLAutoDrawable drawable, Model model);
	public void display(GLAutoDrawable drawable);
	public void render(GLAutoDrawable drawable);
	
}

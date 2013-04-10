/*
 * Controls:
 * 
 *  SPACE - causes player to jump.  Not useful for anything right now, but shows the technique
 *  N - causes North wall to do something
 *  S - causes South wall to do something
 *  E - causes East wall to do something
 *  W - causes West wall to do something
 *  w, s - moves forward and backward
 *  a, d - rotates
 *  q, e - moves sideways
 *  
 *  Mouse selection not implemented yet, but everything except for the model
 *  transformations is in place.
 *  
 *  With only a handful of key presses and behaviors implemented, you can already see
 *  how messy the key and update methods are in the model.  The actor component 
 *  architecture will clean that up, making it far easier to maintain the code
 *  even with complex worlds.  
 */

public class Run 
{
	private Model model;
	private OpenGLView view;
	
	public static void main(String[] args) 
	{
		Run go = new Run();
		go.go ();
	}
	
	public void go ()
	{
		model = new Model ();
		view = new OpenGLView ();
		
		model.init ();
		
		view.add(new ThreeDView (0, 0));
		view.add(new DebugView ());
		
		view.init (model);
	}
}

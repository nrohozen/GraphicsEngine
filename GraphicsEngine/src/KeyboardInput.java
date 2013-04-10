


public class KeyboardInput {

	private Model model;
	
	public KeyboardInput(Model model)
	{
		this.model = model;
	}
	
	/*
	public void key (char c)
	{	
		if (c == 'w')
		{
			System.out.println("Class is working!");
		}
	}
	*/
	
	/*
	public void key (char c)
	{
		//if (c == ' ' && goingUp == 0 && goingDown == 0)
		//	goingUp = JUMP_TIME;
		
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
	*/
	
}

package application;
import java.util.ArrayList;
import java.util.HashSet;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

//import Person;

public class LiftManager extends Thread
{
	public LiftManager (ArrayList<Person> personsQueue, ArrayList<Person> finished)
	{
		this.personsQueue=personsQueue;
		this.finished=finished;
		lifts= new ArrayList<Lift>();
		lifts.add(new Lift(1));
		lifts.add(new Lift(2));
		personsWait= new HashSet<Person>();
	}
	
	public void run()
	{
		while(true)
		{
		
			
		
			if ((personsQueue.size()-personsWait.size()) > 0)
			{
				Lift liftChoise=null;
				Person personWait=personsQueue.get(personsWait.size());
				int minFloors=Integer.MAX_VALUE;
				int floors;
				for (Lift lift : lifts)
				{
					
					floors=Math.abs(personWait.getFloorCurrent()-lift.getFloorCurrent());
					if (lift.getStateLift()==StateLift.NEITRAL
							&& floors<minFloors)
							{
								liftChoise=lift;
								minFloors=floors;
							}
				}
				
				if (liftChoise!=null)
				{
					liftChoise.call(personWait.getFloorCurrent());
					personsWait.add(personWait);
				}
			}
			AnimationTimer animation=new AnimationTimer(){
					
					private long lastUpdate = 0 ;
					@Override
			           public void handle(long now) {
			                   if (now - lastUpdate >= 28_000_000) {
		                	   for (Lift lift : lifts)
		                	   {
		                		   switch(lift.getStateLift())
			                		   {
			                		   case UP:
			                			   lift.updateImY(-1);
			                			   break;
			                		   case DOWN:
			                			   lift.updateImY(1);
			                			   break;
			                		   case NEITRAL:
			                			   break;
			                		   }
				                	   //System.out.println("lolo "+lift.getImY());
			                	   }
			                       lastUpdate = now ;
			                   }
			           }
			       };
			       
	       animation.start();
	       boolean endAnimation=false;
	       while (!endAnimation)
	       {
	    	   endAnimation=true;
	    	   for (Lift lift : lifts)
	    	   {
	    		   if ((lift.getImage().getImY()<=Globals.windowHeight-(lift.getFloorCurrent()-1)*lift.getImage().getImH()
	    				   		&& lift.getStateLift()==StateLift.DOWN)
	    				   ||(lift.getImage().getImY()>=Globals.windowHeight-(lift.getFloorCurrent()+1)*lift.getImage().getImH()
	    				   		&& lift.getStateLift()==StateLift.UP))
        		   {
	    			   endAnimation=false;
        		   }
	    	   }
	       }
	       animation.stop();
	       for (Lift lift : lifts)
	       {
			
				lift.update();
				if (lift.getFloorCurrent()==lift.getFloorDesired())
				{
					for (Person person : personsWait)
					{
						if (person.getFloorCurrent()==lift.getFloorCurrent())
						{
							//incoming.add(person);
							lift.addPerson(person);
							personsWait.remove(person);
							personsQueue.remove(person);
							break;
						}
					}
					
				}
				ArrayList<Person> arrived=lift.getArrived();
				for (Person person: arrived)
				{
					lift.delPerson(person);
				}
				finished.addAll(arrived);
				if (lift.getFullness()>0)
				{
					int index=0;
					while (lift.getFullness()<lift.getCapacity() 
							&& index<personsQueue.size())
					{
						Person person=personsQueue.get(index);
						if (person.getDirection()==lift.getStateLift()
								&& person.getFloorCurrent()==lift.getFloorCurrent())
						{
							personsQueue.remove(person);
							personsWait.remove(person);
							lift.addPerson(person);
							index--;
						}
						index++;
					}
				}
			}
			
			
			
			try
			{
				Thread.sleep(1000);
				

			}
			catch(InterruptedException ex)
			{}
		}
	}
	
	public void draw(GraphicsContext grp) {
		for (Lift lift :lifts)
        {
        	lift.draw(grp);
        }
	}
	
	
	private HashSet<Person> personsWait;
	private ArrayList<Person> personsQueue;
	private ArrayList<Person> finished;
	private ArrayList<Lift> lifts;
	
}

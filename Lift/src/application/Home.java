package application;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;

public class Home {
	
	public Home()
	{
		finished= new ArrayList<Person>();
		personsQueue= new ArrayList<Person>();
		personGenerator= new PersonGenerator(personsQueue);
		liftManager= new LiftManager(personsQueue,finished);
		personGenerator.start();
		liftManager.start();
	}
	
	public void draw(GraphicsContext grp)
	{
		liftManager.draw(grp);
		for (Person person:personsQueue)
		{
			person.draw(grp);
		}
		for (Person person:finished)
		{
			person.draw(grp);
		}
	}
	
	public void addPerson(int floorCurrent, int floorDesired)
	{
		synchronized(personsQueue)
		{
			personsQueue.add(new Person(floorCurrent,floorDesired));
		}
	}
	
	public void autoAdded(boolean select)
	{
		personGenerator.setActive(select);
	}
	
	public void movePeople()
	{
		ArrayList<Person> removed= new ArrayList<Person>();
		for (Person person:finished)
		{
			person.getImage().updateImX(-1);
			if ((person.getImage().getImX()+person.getImage().getImW())<=0)
			{
				removed.add(person);
			}
		}
		finished.removeAll(removed);
	}
	
	private ArrayList<Person> finished;
	private ArrayList<Person> personsQueue;
	private LiftManager liftManager;
	private PersonGenerator personGenerator;
}

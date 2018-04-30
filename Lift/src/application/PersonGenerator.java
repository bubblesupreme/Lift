package application;
import java.util.ArrayList;
import java.util.Random;

public class PersonGenerator extends Thread
{
	
	public PersonGenerator(ArrayList<Person> personsQueue)
	{
		this.maxFloor=Globals.maxFloor;
		this.personsQueue=personsQueue;
		active=false;
	}
	
	public Person generate()
	{
		if (active)
		{
			Random rand=new Random();
			if (rand.nextBoolean())
			{
				int floorCur=rand.nextInt(maxFloor)+1;
				int floorDesire=rand.nextInt(maxFloor)+1;
				while (floorCur==floorDesire)
				{
					floorDesire=rand.nextInt(maxFloor)+1;
				}
				return new Person(floorCur,floorDesire);
			}
		}
		return null;
	}
	
	public void run()
	{
		while(true)
		{
			synchronized(personsQueue)
			{
				Person genPerson=this.generate();
				if(genPerson!=null)
				{
					personsQueue.add(genPerson);
					System.out.println("\npersonsWait:");
					for(Person person:personsQueue)
					{
						System.out.println(person);
					}
				}
			}
			try
			{
				Thread.sleep(10000);
			}
			catch(InterruptedException ex)
			{
				
			}
		}
	}

	public void setActive(boolean active)
	{
		this.active=active;
	}
	
	private int maxFloor;
	private ArrayList<Person> personsQueue;
	private boolean active;
}

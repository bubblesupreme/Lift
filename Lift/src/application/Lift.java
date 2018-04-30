package application;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Lift 
{
	public Lift(int id)
	{
		this.id=id;
		floorCurrent=1;
		floorDesired=1;
		persons=new ArrayList<Person>();
		state=StateLift.NEITRAL;
		File file = new File("sevilla-1421053317.png");
		try
		{

			image=new ImageDraw(file.toURI().toURL().toString()
					,0
					,0
					,120
					,Globals.windowHeight/Globals.maxFloor);
		}
		catch(MalformedURLException ex)
		{}
		image.setImX(100+(id-1)*(image.getImW()+20));
		image.setImY(910-image.getImH());
	}
	
	public void draw(GraphicsContext grp) {
		image.draw(grp);
       	for (Person person:persons)
       	{
       		person.draw(grp);
       	}
	}
	
	public void call(int floor) {
		if (floorCurrent>floor)
		{
			state=StateLift.DOWN;
		}
		if (floorCurrent<floor)
		{
			state=StateLift.UP;
		}
		floorDesired=floor;
	}
	
	public void addPerson(Person person) {
		person.getImage().setImY(this.image.getImY()+this.image.getImH()-person.getImage().getImH()-5);
		person.getImage().setImX((this.image.getImW()-20)/capacity*persons.size()+10+this.image.getImX());
		persons.add(person);
		if ((person.getFloorDesired()>floorDesired 
						&&state==StateLift.UP)
				|| (person.getFloorDesired()<floorDesired 
						&&state==StateLift.DOWN)
				||(state==StateLift.NEITRAL))
			
		{
			floorDesired=person.getFloorDesired();
			if (floorCurrent>floorDesired)
			{
				state=StateLift.DOWN;
			}
			else
			{
				state=StateLift.UP;
			}
		}
	}
	
	public void delPerson(Person person) {
		persons.remove(person);
	}
	
	public void update()
	{
		switch (state)
		{
		case DOWN:
			floorCurrent--;
			break;
		case UP:
			floorCurrent++;
			break;
		case NEITRAL:
			break;
		}
		if(floorCurrent==floorDesired) {
			state=StateLift.NEITRAL;
		}
		
		System.out.println("\n"+this);
		
		int count=0;
		for (Person person:persons)
		{
			person.getImage().setImY(this.image.getImY()+this.image.getImH()-person.getImage().getImH()-10);
			person.getImage().setImX((this.image.getImW()-20)/capacity*count+10+this.image.getImX());
			count++;
		}
	}

	public ArrayList<Person> getArrived()
	{
		ArrayList<Person> arrived=new ArrayList<Person>();
		for(Person person : persons)
		{
			if(person.getFloorDesired()==floorCurrent)
			{
				arrived.add(person);
			}
		}
		return arrived;
	}
	
	public ArrayList<Person> getPersons()
	{
		return persons;
	}
	
	public void setImY(float y)
	{
		this.setImY(y);
		int count=0;
		for (Person person:persons)
		{
			person.getImage().setImY(this.image.getImY()+this.image.getImH()-person.getImage().getImH()-10);
			person.getImage().setImX((this.image.getImW()-20)/capacity*count+10+this.image.getImX());
			count++;
		}
	}

	public void updateImY(float y)
	{
		this.image.updateImY(y);
		int count=0;
		for (Person person:persons)
		{
			person.getImage().setImY(this.image.getImY()+this.image.getImH()-person.getImage().getImH()-10);
			person.getImage().setImX((this.image.getImW()-20)/capacity*count+10+this.image.getImX());
			count++;
		}
	}
	
	public int getFloorCurrent()
	{
		return floorCurrent;
	}

	public int getFloorDesired()
	{
		return floorDesired;
	}
	
	public int getFullness()
	{
		return persons.size();
	}
	
	public int getCapacity()
	{
		return capacity;
	}
	
	public ImageDraw getImage()
	{
		return image;
	}
	
	public String toString()
	{
		String str=new String();
		str="Lift"+id+"\t"+floorCurrent+"\t"+floorDesired+" :\n";
		for(Person i: persons)
		{
			str+=i+"\n";
		}
		return str;
	}
	
	public StateLift getStateLift()
	{
		return state;
	}
	
	
	private int id;
	private int floorCurrent;
	private int floorDesired;
	private StateLift state;
	private ArrayList<Person> persons;
	private final int capacity=6;
	private ImageDraw image;
}

enum StateLift
{
	UP,
	DOWN,
	NEITRAL	
}

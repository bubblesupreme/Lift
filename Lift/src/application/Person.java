package application;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Person
{
	
	public Person(int floorCurrent, int floorDesired) 
	{
		File file;
		Random rand=new Random();
		if (rand.nextBoolean())
		{
			file = new File("girl.png");
		}
		else
		{
			file = new File("boy.png");
		}
		try
		{
			image=new ImageDraw(file.toURI().toURL().toString()
					,rand.nextInt(Globals.windowWidth-400)+400-40
					,0
					,40
					,(float)(Globals.windowHeight/Globals.maxFloor*0.8));
		}
		catch (MalformedURLException ex)
		{}
		
		image.setImY(Globals.windowHeight-Globals.windowHeight/Globals.maxFloor*(floorCurrent-1)-image.getImH()-5);
		
		this.id=count++;
		this.floorCurrent=floorCurrent;
		this.floorDesired=floorDesired;
	}
	
	public int getFloorCurrent() {
		return floorCurrent;
	}
	
	public int getFloorDesired()
	{
		return floorDesired;
	}
	
	public StateLift getDirection()
	{
		if (floorDesired>floorCurrent)
		{
			return StateLift.UP;
		}
		else
		{
			return StateLift.DOWN;
		}
	}
	
	public String toString()
	{
		return new String("id-"+id+"\t"+floorCurrent+"\t"+floorDesired);
	}
	
	public void draw(GraphicsContext grp) {
		image.draw(grp);
	}
	
	public ImageDraw getImage()
	{
		return image;
	}
	
	private int id;
	private int floorCurrent;
	private int floorDesired;
	private static int count=0;
	private ImageDraw image;
}


package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ImageDraw {
	
//	ImageDraw(String url)
//	{
//		image=new Image(url);
//	}
	
	ImageDraw(String url, float imX, float imY, float imW, float imH )
	{
		image=new Image(url);
		this.imX=imX;
		this.imY=imY;
		this.imW=imW;
		this.imH=imH;
	}
	
	public float getImX()
	{
		return imX;
	}
	
	public float getImY()
	{
		return imY;
	}

	public void setImX(float x)
	{
		imX=x;
	}

	public void setImY(float y)
	{
		imY=y;
	}
	
	public void updateImX(float x)
	{
		imX+=x;
	}

	public void updateImY(float y)
	{
		imY+=y;
	}
	
	public float getImH()
	{
		return imH;
	}
	
	public float getImW()
	{
		return imW;
	}
	
	public void draw(GraphicsContext grp)
	{
		grp.drawImage(image, imX, imY, imW, imH);
	}
	
	private Image image;
	private float imX;
	private float imY;
	private float imH;
	private float imW;
}

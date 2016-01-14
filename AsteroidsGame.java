//Jennelle Lai AsteroidsPt4 Assignment AP Comp Sci Mods 6/7
//http://jenlpcprog1.webs.com/AsteroidsPt4.html
/**
 * Press W to accelerate in your direction
 * <br>
 * Press A to rotate counterclockwise
 * <br>
 * Press D to rotate clockwise
 * <br>
 * Press S to hyperspace
 * <br>
 * Press 0 to shoot bullets
 */
package asteroidspt4;
import processing.core.PApplet;
import java.util.*;
public class AsteroidsPt4 extends PApplet {
	private SpaceShip bob = new SpaceShip();
	private Star[] many = new Star[100];
	private ArrayList <Asteroid> roids;
	private ArrayList <Bullet> bullets;
	public void setup() {
		size(500,500);
		roids = new ArrayList <Asteroid>();
		for (int i = 0; i < 100; i++)
		{
			many[i] = new Star();
		}
		for (int a = 0; a < 10; a++)
		{
			roids.add(new Asteroid());
			System.out.println(roids.size());
		}
		
		bullets = new ArrayList <Bullet>();
		
		
	}
	public void draw() {

		background(0);
		frameRate=30;
		for (int i = 0; i < 100; i++)
		{
			many[i].show();
		}
		bob.show();
		bob.move();
		bob.accelerate(Math.random() / 10);
		bob.rotate(0);
		for(int nI = 0; nI < roids.size(); nI++)
		{
			roids.get(nI).show();
			roids.get(nI).move(0);
		}

		for (int e = 0; e < roids.size(); e++)
		{

			float d = dist((int)bob.getX(), (int)bob.getY(), (int)roids.get(e).getX(), (int)roids.get(e).getY());
			if (d <= 20)
			{
				roids.remove(e);
			}

		}

		
		for (int b = 0; b < bullets.size(); b++)
		{
			bullets.get(b).show();
			bullets.get(b).move();
		}
		
		for (int o = 0; o < roids.size(); o++)
		{
			for (int u = 0; u < bullets.size(); u++)
			{
				float c = dist((int)roids.get(o).getX(), (int)roids.get(o).getY(), (int)bullets.get(u).getX(), (int)bullets.get(u).getY());
				if (c <= 20)
				{
					roids.remove(o);
					bullets.remove(u);
					break;
				}
			}
		}

	}
	
	
	public void keyPressed()
	{
		
		if (key == '0')
		{
			bullets.add(new Bullet(bob));
		}
	}
	
	
	abstract class Floater
	{
		protected int[] xCorners;
		protected int[] yCorners;
		protected int corners; //the number of corners, a triangular floater has 3
		protected int myColorR, myColorG, myColorB;
		protected double myCenterX, myCenterY; //holds center coordinates
		protected double myDirectionX, myDirectionY; //holds x and y coordinates of the vector for direction of travel
		protected double myPointDirection; //holds current direction the ship is pointing in degrees
		abstract public void setX(int x);
		abstract public int getX();
		abstract public void setY(int y);
		abstract public int getY();
		abstract public void setDirectionX(double x);
		abstract public double getDirectionX();
		abstract public void setDirectionY(double y);
		abstract public double getDirectionY();
		abstract public void setPointDirection(int degrees);
		abstract public double getPointDirection();
		public Floater()
		{
			myColorR = (int)((Math.random() * 256));
			myColorG = (int)((Math.random() * 256));
			myColorB = (int)((Math.random() * 256));
			corners = 3;
			xCorners = new int[corners];
			yCorners = new int[corners];
			xCorners[0] = -8;
			yCorners[0] = -8;
			xCorners[1] = 16;
			yCorners[1] = 0;
			xCorners[2] = -8;
			yCorners[2] = 8;
			myCenterX = 250;
			myCenterY = 250;
			myDirectionX = 0;
			myDirectionY = 0;
			myPointDirection = 360;
		}
		public void accelerate (double dAmount)
		{
			//Accelerates the floater in the direction it is pointing
			//(myPointDirection)
			//convert the current direction the floater is pointing to radians
			double dRadians =myPointDirection*(Math.PI/180);
			//change coordinates of direction of travel
			if (keyPressed)
			{
				if (key == 'w' || key == 'W')
				{
					myDirectionX += ((dAmount) * Math.cos(dRadians));
					myDirectionY += ((dAmount) * Math.sin(dRadians));
				}
			}
		}
		void rotate (int nDegreesOfRotation)
		{
			//rotates the floater by a given number of degrees
			myPointDirection+=nDegreesOfRotation;
			if (keyPressed)
			{
				if (key == 'a' || key == 'A')
				{
					myPointDirection = myPointDirection + 10;
				}
				else if (key == 'd' || key == 'D')
				{
					myPointDirection = myPointDirection - 10;
				}
			}
		}
		public void move ()
		{
			//Moves the floater towards the coordinates
			//myDirectionX and myDirectionY
			//move the floater in the current direction of travel
			myCenterX += myDirectionX;
			myCenterY += myDirectionY;
			//wrap around screen
			if(myCenterX >500){
				myCenterX = 0;
			}
			else if (myCenterX<0){
				myCenterX = 500;
			}
			if(myCenterY >500){
				myCenterY = 0;
			}
			else if (myCenterY < 0){
				myCenterY = 500;
			}
		}
		public void show ()
		{
			//Draws the floater at the current position
			fill(myColorR, myColorG, myColorB);
			noStroke();
			//convert degrees to radians for sin and cos
			double dRadians = myPointDirection*(Math.PI/180);
			int xRotatedTranslated, yRotatedTranslated;
			beginShape();
			//rotate and translate the coordinates of the floater using current direction
			for(int nI = 0; nI < corners; nI++)
			{
				xRotatedTranslated = (int)((xCorners[nI]* Math.cos(dRadians)) - (yCorners[nI] * Math.sin(dRadians))+myCenterX);
				yRotatedTranslated = (int)((xCorners[nI]* Math.sin(dRadians)) + (yCorners[nI] * Math.cos(dRadians))+myCenterY);
				vertex(xRotatedTranslated,yRotatedTranslated);
			}
			if (keyPressed)
			{
				if(key == 's' || key == 'S')
				{
					fill(myColorR,myColorG,myColorB,0);
					myCenterX = (int)((Math.random() * 500));
					myCenterY = (int)((Math.random() * 500));
					myDirectionX = 0;
					myDirectionY = 0;
					myPointDirection = (int)((Math.random() * 360));
				}
			}
			endShape(CLOSE);
		}
	}
	class SpaceShip extends Floater
	{
		public SpaceShip()
		{
		}
		public void setX(int x) {
			myCenterX = x;
		}
		public int getX() {
			return (int) myCenterX;
		}
		public void setY(int y) {
			myCenterY = y;
		}
		public int getY() {
			return (int) myCenterY;
		}
		public void setDirectionX(double x) {
			myDirectionX = x;
		}
		public double getDirectionX() {
			return myDirectionX;
		}
		public void setDirectionY(double y) {
			myDirectionY = y;
		}
		public double getDirectionY() {
			return myDirectionY;
		}
		public void setPointDirection(int degrees) {
			myPointDirection = degrees;
		}
		public double getPointDirection() {
			return myPointDirection;
		}
	}
	class Star
	{
		private int size;
		private int x, y;
		public Star()
		{
			size = (int)((Math.random() * 10));
			x = (int)((Math.random() * 500));
			y = (int)((Math.random() * 500));
		}
		public void show()
		{
			noStroke();
			fill((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
			ellipse(x,y,size,size);
		}
	}
	
	
	
	class Asteroid extends Floater
	{
		private double rollRandom;
		private int rColor, gColor, bColor;
		public Asteroid()
		{
			rollRandom = Math.random();
			myCenterX = (int)((Math.random() * 500));
			myCenterY = (int)((Math.random() * 500));
			myDirectionX = ((Math.random() * 10) - 5);
			myDirectionY = ((Math.random() * 10) - 5);
			myPointDirection = 360;
			rColor = (int)(Math.random() * 255);
			gColor = (int)(Math.random() * 255);
			bColor = (int)(Math.random() * 255);
			corners = 6;
			xCorners = new int[corners];
			yCorners = new int[corners];
			xCorners[0] = (int)(Math.random() * 10) + 20;
			yCorners[0] = (int)(Math.random() * 20) + 20;
			xCorners[1] = (int)(Math.random() * 20) + 20;
			yCorners[1] = (int)(Math.random() * 10) + 20;
			xCorners[2] = (int)(Math.random() * 30 + 20);
			yCorners[2] = (int)((Math.random() * 20) * -1) + 5;
			xCorners[3] = (int)((Math.random() * 20) * -1) + 5;
			yCorners[3] = (int)((Math.random() * 20) * -1) + 5;
			xCorners[4] = (int)((Math.random() * 20) * -1);
			yCorners[4] = (int)((Math.random() * 20) * -1) + 5;
			xCorners[5] = (int)((Math.random() * 20) * -1);
			yCorners[5] = (int)(Math.random() * 30) + 20;
		}
		public void setX(int x) {
			myCenterX = x;
		}
		public int getX() {
			return (int) myCenterX;
		}
		public void setY(int y) {
			myCenterY = y;
		}
		public int getY() {
			return (int) myCenterY;
		}
		public void setDirectionX(double x) {
			myDirectionX = x;
		}
		public double getDirectionX() {
			return myDirectionX;
		}
		public void setDirectionY(double y) {
			myDirectionY = y;
		}
		public double getDirectionY() {
			return myDirectionY;
		}
		public void setPointDirection(int degrees) {
			myPointDirection = degrees;
		}
		public double getPointDirection() {
			return myPointDirection;
		}
		public void move(int nDegreesOfRotation)
		{
			//Moves the floater towards the coordinates
			//myDirectionX and myDirectionY
			//move the floater in the current direction of travel
			myCenterX += myDirectionX;
			myCenterY += myDirectionY;

			//wrap around screen
			if(myCenterX >500){
				myCenterX = 0;
			}
			else if (myCenterX<0){
				myCenterX = 500;
			}
			if(myCenterY >500){
				myCenterY = 0;
			}
			else if (myCenterY < 0){
				myCenterY = 500;
			}
			//rotates the floater by a given number of degrees

			myPointDirection+=nDegreesOfRotation;
			if (rollRandom < .5)
			{
				myPointDirection = myPointDirection + (Math.random() * 10);
			}
			else
			{
				myPointDirection = myPointDirection - (Math.random() * 10);
			}

		}
		public void show()
		{
			fill(rColor,gColor,bColor);
			noStroke();
			// Draws the floater at the current position
			// convert degrees to radians for sin and cos
			double dRadians = myPointDirection * (Math.PI / 180);
			int xRotatedTranslated, yRotatedTranslated;
			beginShape();
			// rotate and translate the coordinates of the floater using current
			// direction
			for (int nI = 0; nI < corners; nI++) {
				xRotatedTranslated = (int) ((xCorners[nI] * Math.cos(dRadians))
						- (yCorners[nI] * Math.sin(dRadians)) + myCenterX);
				yRotatedTranslated = (int) ((xCorners[nI] * Math.sin(dRadians))
						+ (yCorners[nI] * Math.cos(dRadians)) + myCenterY);
				vertex(xRotatedTranslated, yRotatedTranslated);
			}
			endShape(CLOSE);
		}
	}

	class Bullet extends Floater
	{
		double dRadians;
		public Bullet(SpaceShip theShip)
		{
			myCenterX = theShip.getX();
			myCenterY = theShip.getY();
			myPointDirection = theShip.getPointDirection();
			dRadians =myPointDirection*(Math.PI/180);
			myDirectionX = 5 * Math.cos(dRadians) + theShip.getDirectionX();
			myDirectionY = 5 * Math.sin(dRadians) + theShip.getDirectionY();
			
		}

		
		public void show()
		{
			fill(255,255,255);
			ellipse((int)myCenterX,(int)myCenterY,10,10);
		}
		
		
		public void setX(int x) {
			myCenterX = x;
			
		}

		
		public int getX() {
			
			return (int)myCenterX;
		}

		
		public void setY(int y) {
			myCenterY = y;
			
		}

		
		public int getY() {
			
			return (int)myCenterY;
		}

		
		public void setDirectionX(double x) {
			myDirectionX = x;
			
		}

		
		public double getDirectionX() {
			
			return myDirectionX;
		}

		
		public void setDirectionY(double y) {
			myDirectionY = y;
			
		}

		
		public double getDirectionY() {
			return myDirectionY;
		}

		
		public void setPointDirection(int degrees) {
			myPointDirection = degrees;
			
		}

		
		public double getPointDirection() {
			
			return myPointDirection;
		}

	}
	
}

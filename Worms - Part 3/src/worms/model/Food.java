package worms.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of food consisting of a World, an X- and Y-coordinate, a radius and an eaten state.
 *
 * @version 2.0
 * 
 * @Author Ruben Schroyen & Ralph Vancampenhoudt
 * 
 * A R² production for the course: Object-oriented Programming at KuLeuven
 *
 */

public class Food 
{
	/**
	 * The World in which the food exists.
	 */
	World world;


	/**
	 * The X-coordinate for the food in meters.
	 */
	double x;


	/**
	 * The Y-coordinate for the food in meters.
	 */
	double y;


	/**
	 * The radius of the food in meters.
	 */
	double radius;


	/**
	 * The state of the food if it is eaten or not.
	 */
	public boolean eaten = false;



	/**
	 * Creates a Food and sets the parameters.
	 * 
	 * @param world
	 * 				creates the food in the given world.
	 * @param x
	 * 				creates the food on the X-Axis of the world
	 * @param y
	 * 				creates the food on the X-Axis of the world
	 * 
	 * @pre 		x must be a valid Position.
	 * 
	 * @pre 		y must be a valid Position. 
	 *
	 * @pre			world must be an existing world
	 * 
	 * @post		X-coordinate of the food is set to given value
	 * 
	 * @post		Y-coordinate of the food is set to given value
	 *
	 * @post		World of the food is set to the given world
	 *
	 */
	public Food(World world, double x, double y) 
	{
		this.setPosX(x);
		this.setPosY(y);
		this.setWorld(world);
	}


	/**
	 * This method sets the world of the food to the given world.
	 * 
	 * @param world
	 *			The World in which the food exists.
	 *
	 * @post 	Sets the world of the food to the given world.
	 */
	@Basic @Model
	public void setWorld(World world) 
	{
		this.world = world;
	}



	/**
	 * This method sets the value for the Y-position of the food.
	 * 
	 * @param y 	The Y-coordinate for the food in meters.
	 * 
	 * @post 		Sets Y-coordinate of the food to the given value.
	 *
	 */
	@Basic @Model
	public void setPosY(double y) 
	{
		if (isValidPosition(this.getPosX(), y))
			this.y = y;
	}


	/**
	 * This method sets the value for the X-position of the food.
	 * 
	 * @param y 	The X-coordinate for the food in meters.
	 * 
	 * @post 		Sets X-coordinate of the food to the given value.
	 *
	 */
	@Basic @Model
	public void setPosX(double x)
	{
		if (isValidPosition(x,this.getPosY()))
			this.x = x;
	}


	/**
	 * This method returns the position of the food on the Y-axis.
	 * @return y
	 */

	@Basic @Raw
	public double getPosY() 
	{
		return y;
	}


	/**
	 * This method returns the position of the food on the X-axis.
	 * @return x
	 */

	@Basic @Raw
	public double getPosX() 
	{
		return x;
	}



	/**
	 * This method returns the world in which the food exists.
	 * @return world
	 */
	@Basic @Raw		
	public World getWorld() 
	{
		return world;
	}



	/**
	 * This method returns the radius of the food.
	 * @return 0.20
	 */
	@Basic @Raw	
	public double getRadius() 
	{
		return 0.20;
	}


	/**
	 * This method checks if the food is still active.
	 * 
	 * @return true if eaten is false, else returns false.
	 * 	| eaten == false
	 */
	public boolean isActive() 
	{
		if (eaten)
			return false;
		return true;
	}

	/**
	 * This method checks is a given position is a valid position.
	 * 
	 * @param posX
	 * 		The position of the worm on the X-axis
	 * 
	 * @param posY
	 * 		The position of the worm on the Y-axis
	 * @return
	 * 		true if the given position is a valid one.
	 * 
	 * @throws IllegalArgumentException
	 * If the position of either X or Y is equal to infinity (both +infinity and -infinity)
	 * 			| posX == Double.NEGATIVE_INFINITY
	 * 			| posY == Double.NEGATIVE_INFINITY
	 * 			| posX == Double.POSITIVE_INFINITY
	 * 			| posY == Double.POSITIVE_INFINITY
	 * 		
	 */
	public boolean isValidPosition(double posX, double posY) throws IllegalArgumentException 
	{
		if ((posX == Double.NEGATIVE_INFINITY) || (posY == Double.NEGATIVE_INFINITY) || (posX == Double.POSITIVE_INFINITY) || (posY == Double.POSITIVE_INFINITY))
			throw new IllegalArgumentException("Not a valid value for position");
		return true;
	}


	/**
	 * This method removes a food from the world.
	 * 
	 * if the food is not active anymore, we destroy it
	 * 		| !isActive()
	 * 
	 * @post
	 * 	removes food from the world
	 * 		| new.getWorld() == null
	 */
	public void destroy()
	{
		if (!isActive())
		{
			world.removeFood(this);
			this.setWorld(null);
		}
	}

}
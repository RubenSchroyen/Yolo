package worms.model;


/**********************************************************************************************************************************
 *	TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO  *
 **********************************************************************************************************************************
 *																																  *
 *		HIGH PRIORITY																											  *
 *		-------------																											  *
 *		1. Shoot doesn't work (projectiles don't seem to spawn or spawn at bottom, causing stackoverflow when shooting again)	  *																						  *
 *																																  *
 *		2. new team gets instantly removed because there are no worms in it yet													  *		
 *																														  		  *
 *																																  *
 *		LOW PRIORITY																											  *
 *		------------																										      *
 *																															      *
 *			 																													  *
 *																																  *
 **********************************************************************************************************************************/




/**
 * A class of worms involving a position consisting of an X- and Y-coordinate, an angle,
 * a radius, a mass, action points (current and maximum)
 *
 * @version 2.0
 * 
 * @Author Ruben Schroyen & Ralph Vancampenhoudt
 * 
 * An R² production for the course: Object-oriented Programming at KuLeuven
 * 
 * @invar isValidRadius(getRadius())
 * 
 * @invar isValidTurn(getAngle())
 * 
 * @invar isValidName(getName())
 * 
 * @invar isValidPosition(getPosX, getPosY)
 * 
 * @invar isValidMass(getMass())
 * 
 * @invar isValidAP(getCurrentAP())
 * 
 * @invar isValidHP(getHP())
 * 
 * @invar isValidPropulsionYield(propulsionYield)
 */

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;

public class Worm 
{                    


	/**
	 *   The X-coordinate for the worm in meters
	 **/
	private double posX;


	/**
	 *   The Y-coordinate for the worm in meters
	 **/
	private double posY;


	/**
	 *  The angle the worm is facing in radial
	 **/
	private double angle;


	/**
	 *   The radius of a worm in meter
	 */
	private double radius;


	/**
	 *   The minimal radius a worm has to be in meter (0.25m)
	 */
	public final double minRadius = 0.25;


	/**
	 *   The mass a worm has in kilogram
	 */
	private double mass;


	/**
	 *   The density a worm has (1062kg/m³)
	 */
	private static final double density = 1062;


	/**
	 *   The current amount of action points a worm has
	 */
	private int currentAP;


	/**
	 *   The maximum amount of action points a worm has
	 */
	private int maxAP;



	/**
	 *   The name a worm has been given
	 */
	private String name;


	/**
	 *   The earth acceleration (9.80665)
	 */
	public final double g = 9.80665;


	/**
	 *   The force with which a worm jumps
	 */
	private double force;


	/**
	 *   The velocity with which a worm jumps
	 */
	private double velocity;


	/**
	 *   The distance a worm jumps in meter
	 */
	private double distance;


	/**
	 *   The time a worm is in the air
	 */
	private double time;


	/**
	 * 	The program this worm might follow if it is a computer-controlled one
	 */
	private Program program;


	/**
	 * Creates the worm with some initialization parameters.
	 * 
	 * @param world
	 * 			creates the worm in a chosen world
	 * 
	 * @param x
	 * 			creates the worm in a position on the X-axis of the world
	 * 
	 * @param y
	 * 			creates the worm in a position on the Y-axis of the world
	 * 
	 * @param angle
	 * 			creates the worm with an initial angle of direction
	 *
	 * @param radius
	 * 			creates the worm with an initial radius
	 * 
	 * @param name 
	 * 			creates the worm with a given name
	 * 
	 * @param program 
	 * 			creates the worm with a given program
	 * 
	 * @pre given radius must be valid
	 * 		| isValidRadius(radius)
	 * 
	 * @pre given name must be valid
	 * 		| isValidName(name)
	 * 
	 * @pre given orientation is valid
	 * 		| isValidAngle(angle)
	 * 
	 * @post X-coordinate is set to given value
	 * 		| new.getPosX() == x
	 * 
	 * @post Y-coordinate is set to given value
	 * 		| new.getPosY() == y
	 * 
	 * @post radius of the worm is set to given value
	 * 		| new.getRadius() == radius
	 * 
	 * @post name of the worm is set to the given string if name is valid
	 * 		| new.getName() == name
	 * 
	 * @post number of action points is set to the maximum number of action points a worm can have 
	 * 		| new.getCurrentAP() == this.getMaxAP()
	 * 
	 * @post number of hitpoints is set to the maximum number of hitpoints a worm can have
	 * 		| new.getHP() == this.getMaxHP()
	 * 
	 * @post world of this worm is set to the chosen world
	 * 		| new.getWorld()  == world
	 * 
	 * @post the program of this worm is set to the chosen program
	 * 		| new.program() == program
	 */ 
	public Worm(World world, double x, double y, double radius, double angle, String name, Program program)
	{
		this.setPosX(x);
		this.setPosY(y);
		this.setRadius(radius);
		this.setAngle(angle);
		this.setName(name);
		this.setCurrentAP(this.getMaxAP());
		this.setHP(this.getMaxHP());
		this.setWorld(world);
		this.setProgram(program);
	}



	/**
	 * Method to calculate the cost of changing the angle of the worm
	 * 
	 * @param angle
	 * 		The new angle the worm will be facing
	 * 
	 * @return
	 * 		The cost of the turn
	 * 			| cost = (int) Math.ceil( |angle|*60 / 2*PI )
	 */
	public int calculateApCostTurn (double angle)
	{
		int cost = (int) Math.ceil(Math.abs(angle)*60/(2*Math.PI));
		return cost;
	}


	/**
	 * Method to inspect whether the turn we try to make is a valid one
	 * 
	 * @param angle
	 * 		The angle we try to achieve
	 * 			If it surpasses the value of PI, we reset it to PI
	 * 			If it surpasses the value of -PI, we reset it to -PI
	 * 
	 * @return
	 * 		true if the turn is valid
	 * 
	 * @throws IllegalArgumentException
	 * 		If the cost of the turn is bigger than the current amount of AP
	 * 			| calculateApCostTurn(angle) < this.getCurrentAP()
	 */
	public boolean isValidTurn(double angle) throws IllegalArgumentException 
	{
		if (angle > Math.PI)
			angle = Math.PI;
		if (angle < -Math.PI)
			angle = -Math.PI;
		if (this.getCurrentAP() < calculateApCostTurn(angle))
			return false;
		return true;
	}            


	/**
	 * Method to inspect whether the radius we try to make is a valid one
	 * 
	 * @param radius
	 * 		The radius of a worm
	 * 
	 * @return
	 * 		true if the radius is valid
	 * 
	 * @throws IllegalArgumentException
	 * 		If the radius is smaller than the minimum radius or equal to infinity (because there is a minimal radius, -infinity is not applicable)
	 */
	public boolean isValidRadius(double radius) throws IllegalArgumentException
	{
		if ((radius < minRadius) && (Double.POSITIVE_INFINITY == radius))
			throw new IllegalArgumentException("Not a valid value for radius");
		return true;
	}


	/**
	 * Method to inspect whether the name we try to give to the worm is a valid one
	 * 
	 * @param name
	 * 		The name of the worm
	 * 
	 * @return
	 * 		true if the name is valid
	 * 
	 * @throws IllegalArgumentException
	 * 		- If the name is shorter than 2 characters
	 * 			| !name.length() > 2
	 * 		- If the name does not start with an uppercase character
	 * 			| !Character.isUpperCase(name.charAt(0))
	 * 		- If the name does not consist of letters, spaces, single or double quotes
	 * 			| !name.matches("[a-zA-Z'\" ]*"))
	 */
	public boolean isValidName(String name) throws IllegalArgumentException 
	{
		if (name.length() < 2)
			throw new IllegalArgumentException("Not enough characters");

		if (!Character.isUpperCase(name.charAt(0)))
			throw new IllegalArgumentException("Not starting with uppercase character");

		if (!name.matches("[a-zA-Z'\" ]*"))
			throw new IllegalArgumentException("Not a valid character");
		return true;

	}


	/**
	 * Method to inspect whether the position of a worm we try to give him is a valid one
	 * 
	 * @param posX
	 * 		The position of the worm on the X-axis
	 * 
	 * @param posY
	 * 		The position of the worm on the Y-axis
	 * 
	 * @return
	 * 		true if the value of position is a valid one
	 * 
	 * @throws IllegalArgumentException
	 * 		If the position of either X or Y is equal to infinity (both +infinity and -infinity)
	 * 			| posX == Double.NEGATIVE_INFINITY
	 * 			| posY == Double.NEGATIVE_INFINITY
	 * 			| posX == Double.POSITIVE_INFINITY
	 * 			| posY == Double.POSITIVE_INFINITY
	 */
	public boolean isValidPosition(double posX, double posY) throws IllegalArgumentException 
	{
		if ((posX == Double.NEGATIVE_INFINITY) || (posY == Double.NEGATIVE_INFINITY) || (posX == Double.POSITIVE_INFINITY) || (posY == Double.POSITIVE_INFINITY))
			throw new IllegalArgumentException("Not a valid value for position");
		return true;
	}



	/**
	 * Method to inspect whether the mass of the worm is a valid one
	 * 
	 * @param mass
	 * 		The mass of a worm
	 * 
	 * @return
	 * 		true if the value of mass is a valid one
	 * 
	 * @throws IllegalArgumentException
	 * 		- If the mass of a worm is smaller than density * (4/3) * PI * minRadius³
	 * 			| (density * (4/3) * Math.PI * Math.pow(minRadius,3) > mass
	 * 		- If the mass is equal to infinity (both +infinity and -infinity)
	 * 			| mass == Double.NEGATIVE_INFINITY
	 * 			| mass == Double.POSITIVE_INFINITY
	 */
	public boolean isValidMass(double mass) throws IllegalArgumentException 
	{
		if ((density * (4/3) * Math.PI * Math.pow(minRadius,3) > mass) || (mass == Double.NEGATIVE_INFINITY) || (mass == Double.POSITIVE_INFINITY) )
			throw new IllegalArgumentException("Not a valid value for mass");
		return true;
	}



	/**
	 * Method to inspect whether the amount of current AP is a valid amount
	 * 
	 * @param currentAP
	 * 		The current amount of AP of the worm
	 * 
	 * @return
	 * 		true if the value of currentAP is a valid one
	 * 
	 * @throws IllegalArgumentException
	 * 		If the amount of currentAP is smaller than zero and bigger than the maximum amount of AP possible
	 * 			| currentAP < 0 
	 * 			| currentAP > this.getMaxAP()
	 */
	public boolean isValidAP(double currentAP) throws IllegalArgumentException 
	{
		if ((0 > currentAP) && (currentAP > this.getMaxAP()))
			throw new IllegalArgumentException("Not a valid value for AP");
		return true;
	}





	/**
	 * The method to turn the worm to a certain new direction if he has enough action points to do so.
	 *
	 * @param newangle
	 *  	The new angle a worm gets after changing it in game. This new angle is used to calculate the cost of this turn
	 * 
	 * @throws IllegalArgumentException
	 * 		If the worm does not have enough AP to do this turn
	 *      | !isValidTurn()
	 *
	 * @post
	 *      If the worm has enough AP to do so, he will turn and change his angle to the new angle, reducing his AP by the calculated amount in calculateApCostTurn.
	 *      To make sure that a worm also loses AP when the new angle is negative, the absolute value of this angle-difference is taken to calculate its cost.
	 *      | new.getAngle() == angle  
	 *      | new.getCurrentAP() == currentAP   
	 */
	public void Turn(double newangle) throws IllegalArgumentException
	{
		if (isValidTurn(newangle) == true )    
		{
			this.angle = this.getAngle() + newangle;
			this.currentAP = this.getCurrentAP() - calculateApCostTurn(Math.abs(newangle - this.angle));    
		}
		else
			throw new IllegalArgumentException("Not enough AP");

	}

	/**
	 * Method to check whether the worm can jump from this location
	 * 
	 * @return
	 * 		true if the worm has more than 0 AP and is standing in a passable location
	 * 			| this.getCurrentAP() > 0 && this.getWorld().isPassable(this.getPosX(), this.getPosY(), this.getRadius())
	 */
	public boolean canJump()
	{
		if (this.getCurrentAP() > 0 && this.getWorld().isPassable(this.getPosX(), this.getPosY(), 0))
			return true;
		return false;
	}

	/**
	 * This method recalls the value of the maximum amount of AP a worm can have, namely the mass a worm has rounded upwards to the next integer
	 * 
	 * @return Max_AP
	 * 		Equal to this.getMass() rounded upwards to the next integer
	 * 		| (int)Math.ceil(this.getMass())
	 */
	@Basic @Raw
	public int getMaxAP() 
	{
		return (int)Math.ceil(this.getMass());
	}


	/**
	 * This method recalls the value of the angle the worm is facing
	 */
	@Basic @Raw
	public double getAngle() 
	{
		return angle;
	}

	/**
	 * This method sets the value for the angle a worm is facing. If the angle surpasses the value of PI, the angle gets reset by extracting PI
	 * 
	 * @param angle
	 * 		The angle of direction a worm is facing
	 * 			If angle surpasses the value of PI, it gets reset to the angle - the amount of times PI goes in the angle
	 * 				| this.angle = angle - k*Math.PI
	 * 			If angle surpasses the value of -PI, it gets reset to the angle + the amount of times PI goes in the angle
	 * 				| this.angle = angle + k*Math.PI
	 * 
	 * @post 
	 * 		Sets the value of the worms angle to a newly calculated or given angle
	 * 		| new.getAngle() == angle
	 */
	@Basic @Model
	public void setAngle(double angle) 
	{
		if (angle > Math.PI)
			angle = Math.PI;
		if (angle < -Math.PI)
			angle = -Math.PI;
		this.angle = angle;
	}



	/**
	 * This method recalls the value of the radius of a worm
	 */
	@Basic @Raw
	public double getRadius() 
	{
		return radius;
	}

	/**
	 * This method sets the value for the radius of the worm
	 * 
	 * @param radius
	 * 		The radius a worm has been given in meters
	 * 
	 * @throws IllegalArgumentException
	 * 		If the value for radius is not a valid one
	 * 		| !isValidRadius(radius)
	 * 
	 * @post 
	 * 		Sets the value of the worms radius to a newly calculated or given radius
	 * 		| new.getRadius == radius
	 */
	@Basic @Model
	public void setRadius(double radius) throws IllegalArgumentException 
	{
		if (!isValidRadius(radius))
			throw new IllegalArgumentException("Radius is not valid");
		this.radius = radius;
	}



	/**
	 * This method recalls the value of the mass a worm has, equal to density*(4/3)*Math.PI*Math.pow(getRadius(), 3)
	 */
	@Basic @Raw
	public double getMass() 
	{
		return density*(4/3)*Math.PI*Math.pow(getRadius(), 3);
	}



	/**
	 * This method recalls the X-position of the worm
	 */
	@Basic @Raw
	public double getPosX() 
	{
		return posX;
	}

	/**
	 * This method sets the value for the X-position of the worm
	 * 
	 * @param xx
	 * 		The X-position of the worm in meters
	 * 
	 * @throws IllegalArgumentException
	 * 		If the value for x is not a valid one
	 * 		| !isValidPosition(x,this.getPosY())
	 * 
	 * @post 
	 * 		Sets the value of the worms X-position to a newly calculated or given X-position
	 * 		| new.getPosX() == x
	 */
	@Basic @Model
	public void setPosX(double x) throws IllegalArgumentException 
	{
		if (!isValidPosition(x,this.getPosY()))
			throw new IllegalArgumentException("Position is not valid");
		this.posX = x;
	}



	/**
	 * This method recalls the Y-position of the worm
	 */
	@Basic @Raw
	public double getPosY() 
	{
		return posY;
	}

	/**
	 * This method sets the value for the Y-position of the worm
	 * 
	 * @param y
	 * 		The Y-position of the worm in meters
	 * 
	 * @throws IllegalArgumentException
	 * 		If the value for y is not a valid one
	 * 		| !isValidPosition(this.getPosX(),y)
	 * 
	 * @post 
	 * 		Sets the value of the worms Y-position to a newly calculated or given Y-position
	 * 		| new.getPosY() == y
	 */
	@Basic @Model
	public void setPosY(double y) throws IllegalArgumentException
	{
		if (!isValidPosition(this.getPosX(),y))
			throw new IllegalArgumentException("Position is not valid");
		this.posY = y;
	}



	/**
	 * This method recalls the current amount of AP a worm has
	 */
	@Basic @Raw
	public int getCurrentAP() {
		return currentAP;
	}

	/**
	 * This method sets the value for the current amount of AP a worm has
	 * 
	 * @param current_AP
	 * 		The current amount of AP a worm has
	 * 
	 * @throws IllegalArgumentException
	 * 		If the value of currentAP is not a valid one
	 * 		| !isValidAP(currentAP)
	 * 
	 * @post
	 * 		Sets the value of the worms current AP to the newly calculated or given current AP
	 * 		| new.getCurrentAP() == currentAP
	 */
	@Basic @Model
	public void setCurrentAP(int currentAP) throws IllegalArgumentException
	{
		if (!isValidAP(currentAP))
			throw new IllegalArgumentException("Current AP is not valid");
		this.currentAP = currentAP;
	}



	/**
	 * This method recalls the given name of the worm
	 */
	@Basic @Raw
	public String getName() 
	{
		return name;
	}

	/**
	 * This method sets the name of the worm
	 * 
	 * @param name
	 * 		The name a worm is given
	 * 
	 * @throws IllegalArgumentException
	 * 		If the name of the worm does not follow his constraints of being longer than 2 characters, starting with an uppercase character and consisting
	 * 		only of letters, single or double quotes and spaces
	 * 		| !isValidName(name)
	 * 
	 * @post
	 * 		Sets the value of the worms name to a newly given name if this name is valid
	 * 		| new.getName() == name
	 */
	@Basic @Model
	public void setName (String name) throws IllegalArgumentException 
	{
		if (isValidName(name) == false)
			throw new IllegalArgumentException("Your name has some invalid characters included");
		this.name = name;
	}                      


	/**
	 * This method recalls the force with which a worm jumps
	 */
	@Basic @Raw
	public double getForce() 
	{
		return force;
	}

	/**
	 * This method sets the value for the force with which a worm jumps
	 * 
	 * @param force
	 * 		The force with which a worm jumps
	 * 
	 * @post 
	 * 		Sets the value of the force with which a worm jumps to the newly calculated or given force
	 * 		| new.getForce() == force
	 */
	@Basic @Model
	public void setForce(double force) 
	{
		this.force = force;
	}



	/**
	 * This method recalls the value of the velocity with which a worm jumps
	 */
	@Basic @Raw
	public double getVelocity() 
	{
		return velocity;
	}

	/**
	 * This method sets the value for the velocity with which a worm jumps
	 * 
	 * @param velocity
	 * 		the velocity with which a worm jumps
	 * 
	 * @post
	 * 		Sets the value of velocity with which a worm jumps to the newly calculated or given velocity
	 * 		| new.getVelocity() == velocity
	 */
	@Basic @Model
	public void setVelocity(double velocity) 
	{
		this.velocity = velocity;
	}



	/**
	 * This method recalls the value of the distance a worm jumps
	 */
	@Basic @Raw
	public double getDistance() 
	{
		return distance;
	}

	/**
	 * This method sets the value for the distance a worm jumps
	 * 
	 * @param distance
	 * 		the distance a worm jumps
	 * 
	 * @post
	 * 		Sets the value of distance a worm jumps to the newly calculated or given distance
	 * 		| new.getDistance() == distance
	 */
	@Basic @Model
	public void setDistance(double distance) 
	{
		this.distance = distance;
	}



	/**
	 * This method recalls the value of the time a worm is in the air while jumping
	 */
	@Basic @Raw
	public double getTime() 
	{
		return time;
	}

	/**
	 * This method sets the value for the time a worm is in the air while jumping
	 * 
	 * @param time
	 * 		The time a worm is in the air while jumping
	 * 
	 * @post 
	 * 		Sets the value of the time a worm is in the air while jumping to the newly calculated or given time
	 * 		| new.getTime() == time
	 */
	@Basic @Model
	public void setTime(double time) 
	{
		this.time = time;
	}

	/*********************************************************************************************
	 * 																						     *
	 * 									   PART 2 ADDITIONS									     *
	 * 																						     *       
	 *********************************************************************************************/
	/**
	 * The current amount of HP a worm has
	 */
	public int currentHP;

	/**
	 * A boolean that indicates whether the object is inside of the world boundaries
	 */
	public boolean outOfBounds = false;

	/**
	 * The list of weapons a worm has (now only a Bazooka and a Rifle)
	 */
	public List<String> weapons = Arrays.asList("Bazooka", "Rifle");

	/**
	 * The selected weapon of this worm (starting at Bazooka and cycling)
	 */
	public String selectedWeapon = "Bazooka";

	/**
	 * The world this worm is in
	 */
	public World world;

	/**
	 * The team this worm is in
	 */
	public Team team;

	/**
	 * The projectile this worm is shooting
	 */
	public Projectile projectile;

	/**
	 * The index to determine which weapon is selected
	 */
	private int index = 0;


	/**
	 * This method sets the value for the selected weapon of a worm
	 * 
	 * @post
	 * 		Sets the value of selected weapon to the weapon on index in weapons
	 * 		| new.getSelectedWeapon() == weapons.get(index)
	 */
	@Model @Basic
	public void setSelectedWeapon()
	{
		this.selectedWeapon = weapons.get(index);
	}


	/**
	 * This method recalls the value of selectedWeapon
	 */
	@Basic @Raw
	public String getSelectedWeapon() 
	{
		return selectedWeapon;
	}

	/**
	 * This method recalls the value of MaxHP
	 *   | MaxHP == (int) Math.ceil(this.getMass())
	 */
	@Basic @Raw
	public int getMaxHP() 
	{
		return (int)Math.ceil(this.getMass());
	}

	/**
	 * This method recalls the value of currentHP
	 */
	@Basic @Raw
	public int getHP() 
	{
		return currentHP;
	}

	/**
	 * Method to inspect whether the worm is still alive
	 * 
	 * @return
	 * 		true if the value of currentHP is a valid one and the worm is in the world boundaries
	 * 			| isValidHP(this.getHP()) && this.getWorld().wormInBounds(this)
	 */
	public boolean isAlive() 
	{
		return (isValidHP(this.getHP()) && this.getWorld().wormInBounds(this));
	}

	/**
	 * This method sets the value for the HP of a worm
	 * 
	 * @param HP
	 * 		the HP of this worm 
	 * 
	 * @post
	 * 		Sets the value of currentHP to the value of HP if isValidHP(HP) returns true
	 * 			| if (isValidHP(HP) == true
	 * 				| new.getHP() == HP
	 * 
	 * @post 
	 * 		Sets the value of currentHP to 0 if HP < 0
	 * 			| if (HP < 0)
	 * 				| new.getHP() == 0
	 * 
	 * @post
	 * 		Sets the value of currentHP to this.getMaxHP() if hp > this.getMaxHP()
	 * 			| if (HP > this.getMaxHP()
	 * 				| new.getCurrentHP() == this.getMaxHP()
	 */
	@Basic @Model
	public void setHP(int HP) 
	{
		if (isValidHP(HP))
			this.currentHP = HP;

		if (HP < 0)
		{
			this.currentHP = 0;
			this.destroy();
		}

		if (HP > this.getMaxHP())
			this.currentHP = this.getMaxHP();
	}


	/**
	 * This method returns the world this worm is in
	 */
	@Basic @Raw 
	public World getWorld() 
	{
		return world;
	}

	/**
	 * This method returns the team this worm is in
	 */
	@Basic @Raw
	public Team getTeam() 
	{
		return team;
	}

	/**
	 * This method sets the world of a worm
	 * 
	 * @param world
	 * 		the world of this worm 
	 * 
	 * @post
	 * 		Sets the world of a worm to this world
	 * 			| new.getWorld() == world
	 */
	@Basic @Model
	public void setWorld(World world)
	{
		this.world = world;
	}

	/**
	 * This method sets the team of a worm
	 * 
	 * @param team
	 * 		the team of this worm 
	 * 
	 * @post
	 * 		Sets the team of a worm to this team
	 * 			| new.getTeam() == team
	 */
	public void setTeam(Team team)
	{
		this.team = team;
	}

	/**
	 * This method selects the next weapon by increasing the index-number of the ArrayList weapons
	 * 
	 * @post
	 * 		if the index surpasses the amount of possible weapons, it gets set to 0
	 * 			| index > weapons.size()
	 * 			| new.index == 0
	 * 
	 * @post
	 * 		if the index falls below zero (should be impossible), it gets set to 0
	 * 			| index < 0
	 * 			| new.index == 0
	 */
	public void selectNextWeapon() 
	{
		if (index == weapons.size() - 1)
			index = 0;
		else if (index < 0)
			index = 0;
		else
			weapons.get(index ++);		
	}

	/**
	 * This method shoots a projectile with a certain propulsion yield
	 * 
	 * @param propulsionYield
	 * 		The propulsion yield a weapon is shot with
	 * 
	 * First we determine what the selected weapon is:
	 * 		- Bazooka:
	 * 			@post
	 * 				Sets the amount of AP to currentAP - 50
	 * 					| new.getCurrentAP() == getCurrentAP() - 50
	 * 
	 * 			@effect
	 * 				shootBazooka(propulsionYield) in class projectile
	 * 
	 * 		- Rifle
	 * 			@post
	 * 				Sets the amount of AP to currentAP - 10
	 * 					| new.getCurrentAP() == getCurrentAP() - 10
	 * 	
	 * 			@effect
	 * 				shootRifle(propulsionYield) in class projectile
	 * 
	 * @throws IllegalArgumentException
	 * 		if the worm can not shoot
	 * 			| !canShoot()
	 * 		if the propulsionYield is invalid
	 * 			| !isValidPropulsionYield(propulsionYield)
	 */
	public void shoot(int propulsionYield) throws IllegalArgumentException
	{
		if (!isValidPropulsionYield(propulsionYield))
			throw new IllegalArgumentException("Not a valid value for propulsionYield");

		if (this.canShoot())
		{
			if (this.getSelectedWeapon() == "Bazooka")
			{
				Projectile projectile = new Projectile(this);
				projectile.shootBazooka(propulsionYield, projectile);
				this.setCurrentAP(this.getCurrentAP() - 50);
			}

			if (this.getSelectedWeapon() == "Rifle")
			{
				Projectile projectile = new Projectile(this);
				projectile.shootRifle(projectile);
				this.setCurrentAP(this.getCurrentAP() - 10);
			}
		}
		else
			throw new IllegalArgumentException("This worm can not shoot");
	}

	/**
	 * This method starts the next turn and the worm regains all his AP and 10 HP
	 * 
	 * @post
	 * 		The worm regains 10 HP
	 * 			| new.getHP() == getHP() + 10
	 * 
	 * @post
	 * 		The worm regains all his AP
	 * 			| new.getCurrentAP() == getMaxAP()
	 */
	public void nextTurn()
	{
		if (this.getWorld().currentWorm() != this)
			this.setHP(getHP() + 10);
		this.setCurrentAP(getMaxAP());
	}


	/**
	 * this method determines whether the worm can shoot or not
	 * 
	 * @return
	 * 		true when the worm is positioned on a passable location 
	 * 			| world.isPassable(this.getPosX(), this.getPosY(), this.getRadius())
	 * 		
	 *		We then make a difference between the selected weapons
	 *			- Bazooka:
	 *				true when the selected weapon is a bazooka and the worm has more than 50 AP left, while in a passable location
	 *					| this.getSelectedWeapon() == "Bazooka"
	 *					| this.getCurrentAP() >= 50
	 *					| this.getWorld().isPassable(this.getPosX(), this.getPosY(), this.getRadius())
	 *
	 *			- Rifle:
	 *				true when the selected weapon is a rifle and the worm has more than 10 AP left, while in a passable location
	 *					| this.getSelectedWeapon() == "Rifle"
	 *					| this.getCurrentAP() >= 10
	 *					| this.getWorld().isPassable(this.getPosX(), this.getPosY(), this.getRadius())
	 */
	public boolean canShoot()
	{
		if (this.getSelectedWeapon() == "Bazooka" && this.getCurrentAP() >= 50 && this.getWorld().isPassable(this.getPosX(), this.getPosY(), this.getRadius()))
			return true;
		else if (this.getSelectedWeapon() == "Rifle" && this.getCurrentAP() >= 10 && this.getWorld().isPassable(this.getPosX(), this.getPosY(), this.getRadius()))
			return true;
		else
			return false;
	}

	/**
	 * This method consumes food when the worm collides with it, making the worm grow 10%
	 * 
	 * @post 
	 * 	  The worm grows 10% if his position collides with the position of the food
	 * 	  We also give eaten in the class food the value of true
	 * 		| new.getRadius() == this.getRadius()*1.1
	 * 		| new.food.eaten == true
	 */
	public void consume(Food food) 
	{
		this.setRadius(this.getRadius()*1.1);
		food.eaten = true;
		food.destroy();
	}

	/**
	 * Method to loop over all the food in this world and check whether the worm is overlapping with it, making it eat and grow
	 * 
	 * If the worm has a world, we check every food in that world by looping through ArrayList fodder in World
	 * 		When a worm is overlapping with a food, the worm eats it
	 * 			| if (this.getWorld() != null)
	 * 				| if (World.isOverlapping(getPosX(), getPosY(), getRadius(), food.getPosX(), food.getPosY(), food.getRadius())
	 * 					| consume(food)
	 * 
	 * @effect
	 * 		The worm grows with 10% and the food gets destroyed
	 */
	private void lookForFood() 
	{
		if (this.getWorld() != null) 
		{
			ArrayList<Food> oldFood = new ArrayList<Food>(getWorld().getFodder());
			for ( Food food : oldFood ) 
			{
				if (World.isOverlapping(getPosX(), getPosY(), getRadius(), food.getPosX(), food.getPosY(), food.getRadius())) 
					consume(food);
			}
		}
	}

	/**
	 * Method to destroy the worm if his HP fall under 0
	 * 
	 * @post
	 * 		Worm gets removed from world
	 * 			| world.amountOfWorms() = world.amountOfWorms() - 1
	 * 
	 * @post 
	 * 		The worm's references get removed
	 * 			| new.getWorld() == null
	 */
	public void destroy()
	{
		if (!isAlive())
		{
			this.getWorld().removeWorm(this);
			this.setWorld(null);
			if (this.getTeam() != null)
				this.getTeam().removeAsWorm(this);
		}
	}

	/**
	 * Adds this worm to a team
	 * 
	 * @param team
	 * 		The team we want to add the worm in
	 * 
	 * If the team exists, we add the worm to this team
	 * 		| if (team != null)
	 * 			| team.addWorm(this)
	 * 
	 * @effect
	 * 	 addWorm(this) in class Team
	 */
	public void addToTeam(Team team)
	{
		if (team != null)
			team.addWorm(this);
	}


	/**
	 * Method to check whether the worm can fall down
	 * 
	 * @return
	 * 		true if the worm is still alive and on a passable spot which is not adjacent
	 * 			| this.isAlive() && this.getWorld().isPassable(this.getPosX(), this.getPosY(), this.getRadius()) && !this.getWorld().isAdjacent(this.getPosX(), this.getPosY(), this.getRadius())
	 */
	public boolean canFall() 
	{
		return this.isAlive() && this.getWorld().isPassable(this.getPosX(), this.getPosY(), this.getRadius()) && !this.getWorld().isAdjacent(this.getPosX(), this.getPosY(), this.getRadius());
	}

	/**
	 * Method to make the worm fall down to the next adjacent location, losing 3HP per fallen meter
	 * 
	 * While this.canFall() is true, the worm will fall down 1% of his radius and lose 3HP per fallen meter
	 * 		| while (this.canFall())
	 * 			| this.setPosY(this.getPosY() - 0.01*this.getRadius())
	 * 			| this.setHP((int) Math.floor(this.getHP() - 3*(distanceFallen)))
	 * 
	 * Afterwards, he will check if he landed on food and try to eat it
	 * 		| lookForFood()
	 * 
	 * @post
	 * 		posY gets set to posY - 0.01 * radius
	 * 			| new.getPosY() == getPosY() - 0.01 * getRadius()
	 * 
	 * @post 
	 * 		HP gets set to HP - 3 * distanceFallen
	 * 			| new.getHP() == getHP() - 3 * distanceFallen
	 * 
	 */
	public void fall() 
	{
		double begin = this.getPosY();
		while (this.canFall())
		{
			this.setPosY(this.getPosY() - 0.01*this.getRadius());
		}
		double distanceFallen = begin - this.getPosY();
		this.setHP((int) Math.floor(this.getHP() - 3*(distanceFallen)));
		lookForFood();
	}


	/**
	 * Method to move the worm
	 * 
	 * @throws IllegalArgumentException
	 * 		if the worm cannot move (not enough AP)
	 * 			| !canMove()
	 * 
	 * @post
	 * 		The position gets set to the old position + the distance travelled
	 * 			| new.getPosX() == getPosX() + this.getMoveDistance()[0]
	 * 			| new.getPosY() == getPosY() + this.getMoveDistance()[1]
	 */
	public void move() throws IllegalArgumentException
	{
		if (!canMove())
			throw new IllegalArgumentException("This move is not available");

		
		if (this.getCurrentAP() > 0)
		{
			double[] distance = this.getMoveDistance();
			this.setCurrentAP(this.getCurrentAP() - this.calculateAPCostMove(distance));
			this.setPosX(getPosX() + distance[0]);
			this.setPosY(getPosY() + distance[1]);
			lookForFood();
			fall();
		}
	}


	/**
	 * Method to retrieve the maximal distance the worm can move at a time 
	 * 
	 * The working of this method is best described inside the method itself to make sure every part is understandable
	 * 
	 * @return
	 * 		Output (a matrix of 2 coordinates that will be the farthest location available that is also adjacent)
	 * 			| output[0] = bestX - this.getPosX()
	 *			| output[1] = bestY - this.getPosY()
	 * 		
	 */
	protected double[] getMoveDistance() 
	{
		//Test different values for X, Y and D
		double X = this.getPosX();
		double Y = this.getPosY();
		double D = Math.abs(getAngle() - Math.atan2(Y - getPosY(), X - getPosX()));

		//initialize the best values for radius, X, Y, D and change them later in the method
		double bestRadius = 0;
		double bestX = X;
		double bestY = Y;
		double bestD = D;

		//initialize the interval of the radius that we want to test (= minimum of the resolution values in world)
		double testRadiusInterval = Math.min(getWorld().getResolutionX(), getWorld().getResolutionY());
		double scaleD = 1;
		double scaleRadius = 1;

		//we search for the farthest adjacent location that we can reach
		boolean adjacent = false;
		for (double testRadius = this.getRadius(); testRadius >= 0.1; testRadius -= testRadiusInterval) 
		{ 
			for (double testAngle =- 0.7875; testAngle <= 0.7875; testAngle += 0.0175) 
			{
				X = this.getPosX() + testRadius*Math.cos(this.getAngle()+testAngle);
				Y = this.getPosY() + testRadius*Math.sin(this.getAngle()+testAngle);
				D = Math.abs(this.getAngle() - Math.atan2(Y - this.getPosY(), X - this.getPosX()));

				//we see if this location is indeed the farthest we can reach and reset our values of bestRadius, bestX, bestY and bestD
				if (scaleD * (D - bestD) + scaleRadius * (bestRadius - testRadius) < 0 ) 
				{ 
					//this can only happen when this location is also adjacent, else we will fall down again
					if ( this.getWorld().isAdjacent(X,Y,getRadius()) ) 
					{
						adjacent = true;
						bestRadius = testRadius;
						bestX = X;
						bestY = Y;
						bestD = D;
					}
				}
			}
		}

		if (!adjacent) 
			//if we have not found any adjacent locations nearby, we use the values for bestX and bestY given in the beginning of this method
		{
			for (double testRadius = getRadius(); testRadius >= 0.1; testRadius -= testRadiusInterval) 
			{ 
				X = this.getPosX() + testRadius * Math.cos(this.getAngle());
				Y = this.getPosY() + testRadius * Math.sin(this.getAngle());
				//this can only when this location is also adjacent, else we will fall down again
				if (this.getWorld().isPassable(X, Y, this.getRadius())) 
				{
					bestX = X;
					bestY = Y;
					break;
				}
			}
		}

		//the output is a matrix of 2 coordinates that will be the farthest location available that is also adjacent
		double[] output = new double[2];
		output[0] = bestX - this.getPosX();
		output[1] = bestY - this.getPosY();
		return output;
	}



	/**
	 * Method to calculate the cost of AP for a move of a certain distance
	 * 
	 * @param distance
	 * 		The distance the worm wants to move
	 * 
	 * @return	
	 * 		The 1 AP per step on the X-axis, 4 AP per step on the Y-axis
	 * 			| (int) Math.ceil( Math.abs(Math.cos(slope)) + Math.abs(4*Math.sin(slope)))
	 */
	protected int calculateAPCostMove(double[] distance) 
	{
		double slope = Math.atan2(distance[1], distance[0]);
		return (int) Math.ceil( Math.abs(Math.cos(slope)) + Math.abs(4*Math.sin(slope)));
	}



	/**
	 * Method to inspect whether the worm can move from this location
	 * 
	 * @return
	 * 		true if the worm has enough AP left to do this move
	 * 			| isValidAP(this.getCurrentAP() - this.calculateAPCostMove(this.getMoveDistance()))
	 */
	public boolean canMove() 
	{
		double[] distance = this.getMoveDistance();
		return (isValidAP(this.getCurrentAP() - this.calculateAPCostMove(distance)) && this.isAlive());
	}



	/**
	 * Method to inspect whether the amount of current AP is a valid amount
	 * 
	 * @param hp
	 * 		The current amount of HP of the worm
	 * 
	 * @return
	 * 		true if the value of hp is a valid one
	 * 
	 * @throws IllegalArgumentException
	 * 		If the amount of hp is smaller than zero and bigger than the maximum amount of HP possible
	 * 			| hp < 0 
	 * 			| hp > this.getMaxHP()
	 */
	public boolean isValidHP(int hp) throws IllegalArgumentException 
	{
		if ((0 > hp) && (hp > this.getMaxHP()))
			throw new IllegalArgumentException("Not a valid value for HP");
		if (hp == 0)
			return false;
		return true;
	}



	/**
	 * Method to inspect whether the value of propulsionYield is a valid one
	 * 
	 * @param propulsionYield
	 * 		The propulsionYield of the worm
	 * 
	 * @return
	 * 		true if the value of propulsionYield is a valid one
	 * 
	 * @throws IllegalArgumentException
	 * 		If the value of propulsionYield is smaller than zero and bigger than 100
	 * 			| propulsionYield < 0 
	 * 			| propulsionYield > 100
	 */
	public boolean isValidPropulsionYield(double propulsionYield) throws IllegalArgumentException 
	{
		if ((0 > propulsionYield) && (propulsionYield > 100))
			throw new IllegalArgumentException("Not a valid value for propulsionYield");
		return true;
	}

	/**
	 * This method makes the object follow a certain direction
	 * 
	 * @param delta
	 * 		The steps in time that are taken to calculate the position
	 * 
	 * @post
	 * 		The positions get set to the calculated ones
	 * 			| new.getPosY() == jumpStep[0]
	 * 			| new.getPosX() == jumpStep[1]
	 */
	public void Jump(double delta)
	{
		double begin = this.getPosY();
		if (this.canJump())
		{
			double[] jumpStep = new double[2];
			jumpStep = this.JumpStep(this.JumpTime(delta));
			this.setPosX(jumpStep[0]);
			this.setPosY(jumpStep[1]);
			this.setCurrentAP(0);
			if (this.getPosY() < begin)
				this.setHP((int) Math.floor(this.getHP() - 3*(begin - this.getPosY())));
		}
		lookForFood();
	}


	/**
	 * This method calculates the in-air time
	 * 
	 * @param delta
	 * 		
	 * @return
	 * 		jumpTime
	 */
	public double JumpTime(double delta)
	{
		double X = this.getPosX();
		double Y = this.getPosY();
		double[] jumpStepResult = new double[2];
		double jumpTime = 0;
		while (getWorld().isPassable(X, Y, this.getRadius()))
		{
			jumpStepResult = this.JumpStep(jumpTime);
			X = jumpStepResult[0];
			Y = jumpStepResult[1];
			jumpTime += delta;
		}
		return jumpTime;
	}

	/**
	 * This method calculates the position in air for the object
	 * 
	 * @param DeltaT
	 *		The steps in time to calculate the position 
	 *
	 * @return
	 * 		jumpStep
	 */
	public double[] JumpStep(double DeltaT)
	{       
		this.setForce(5*this.getCurrentAP() + this.getMass() * g);
		this.setVelocity(this.getForce() * 0.5 / this.getMass());
		double velocityX = this.getVelocity() * Math.cos(this.getAngle());
		double velocityY = this.getVelocity() * Math.sin(this.getAngle());
		double x = this.getPosX() + (velocityX * DeltaT);
		double y = this.getPosY() + (velocityY * DeltaT - 0.5*g*Math.pow(DeltaT, 2));

		double[] jumpstep = new double[] {x,y};

		return jumpstep;

	}

	/*********************************************************************************************
	 * 																						     *
	 * 									   PART 3 ADDITIONS									     *
	 * 																						     *       
	 *********************************************************************************************/

	/**
	 * The method to inspect whether this worm has a program attached to it or not
	 * 
	 * @return
	 * 		true if the worm has a program and is therefore computer-controlled
	 */
	public boolean hasProgram() 
	{	
		if (program != null)
			return true;
		return false;
	}
	


	/**
	 * Sets the program of this worm to the one he is supposed to have
	 * 
	 * @param program
	 * 		The program we want this worm to have
	 * 
	 * @post
	 * 		We set the program of this worm to the chosen one if the worm has a program
	 * 			| new.getProgram() == program
	 * 		We set the program of this worm to null if the worm does not have a program
	 * 			| new.getProgram() == null
	 */
	@Raw @Model
	private void setProgram(Program program) 
	{
		if (this.hasProgram())
			this.program = program;
		else 
			this.program = null;
		
	}
	
	/**
	 * Returns the program of this worm if he has one
	 * 
	 * @return program
	 */
	@Raw @Basic
	private Program getProgram()
	{
		return program;
	}

}

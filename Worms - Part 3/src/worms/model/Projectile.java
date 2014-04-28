package worms.model;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Model;
import be.kuleuven.cs.som.annotate.Raw;
import worms.util.Util;
/**
 * A class of projectiles involving  a world in which they exist, an X- and Y-coordinate, a density, a mass,
 * a force, a worm, a time, a distance and a velocity. All projectiles are under the influence of a gravitational pull.
 * 
 * @version 2.0
 * 
 * @author Ruben Schroyen & Ralph Vancampenhoudt
 *  
 * A R² production for the course: Object-oriented Programming at KuLeuven
 *
 */

public class Projectile 
{	
	/**
	 * The World in which the projectile exists.
	 */
	World world;



	/**
	 * The Y-coordinate for the projectile in meters.
	 */
	private double y;



	/**
	 * The X-coordinate for the projectile in meters.
	 */
	private double x;


	/**
	 * The density of the projectile.
	 */
	final double density = 7800;



	/**
	 * The mass of the projectile.
	 */
	private double mass;



	/**
	 * The force with which a projectile is being launched.
	 */
	private double force;



	/**
	 * The worm who fired the projectile.
	 */
	Worm worm;



	/**
	 * The earths gravitational pull (9.80665)
	 */
	private final double g = 9.80665;



	/**
	 * The time the projectile is in the air.
	 */
	private double time;



	/**
	 * The distance a projectile travels.
	 */
	private double distance;



	/**
	 * The velocity with which a projectile travels.
	 */
	private double velocity;



	/**
	 * Creates a projectile and sets the parameters to the given values.
	 * 
	 * @param worm
	 * 			the worm who fired the projectile.
	 * 
	 * @throws IllegalArgumentException
	 * 		If the position of the projectile is not a valid position.
	 * 			| !(worm.isValidPosition(worm.getPosX() + Math.cos(worm.getAngle() * worm.getRadius()), worm.getPosY() + Math.sin(worm.getAngle() * worm.getRadius())))
	 * 
	 * @pre isValidPosition(getPosX(),getposY())
	 * 
	 * @post projectile has been given an owner.
	 * 			| new.worm = worm
	 * 
	 * @post X-coordinate of the projectile is set to the X-coordinate of the edge of the worm. 
	 * 			| new.getPosX() == worm.getPosX() + Math.cos(worm.getAngle() * worm.getRadius())
	 * 
	 * @post Y-coordinate of the projectile is set to the X-coordinate of the edge of the worm. 
	 * 			| new.getPosY() == worm.getPosY() + Math.sin(worm.getAngle() * worm.getRadius())
	 * 
	 * @post World of the projectile is set to the world where the worm exists.
	 * 			| new.getWorld() == worm.getWorld()
	 */
	public Projectile(Worm worm) throws IllegalArgumentException
	{
		this.worm = worm;
		if (worm.isValidPosition(worm.getPosX() + Math.cos(worm.getAngle() * worm.getRadius()), worm.getPosY() + Math.sin(worm.getAngle() * worm.getRadius())))
		{	
			this.setPosX(worm.getPosX() + Math.cos(worm.getAngle() * worm.getRadius()));
			this.setPosY(worm.getPosY() + Math.sin(worm.getAngle() * worm.getRadius()));
		}
		else 
			throw new IllegalArgumentException("Not a valid position for projectile");
		this.setWorld(worm.getWorld());
	}


	/**
	 * This method sets the world of the projectile to a given world.
	 *
	 * @param world
	 * 
	 * @post Sets Sets the world of the food to the given world
	 * 			|new.world = world
	 */
	@Basic @Model
	public void setWorld(World world) 
	{
		this.world = world;
	}



	/**
	 *  This method returns the world of the projectile.
	 * 
	 * @return world
	 */
	@Basic @Raw
	public World getWorld()
	{
		return world;
	}



	/**
	 * This method sets the Y-coordinate of the projectile to a given value.
	 * 
	 * @param y
	 * 
	 * @post Sets Y-coordinate the world of the projectile to the given value.
	 * 			|new.y = y
	 */
	@Basic @Model
	public void setPosY(double y) 
	{
		if (isValidPosition(this.getPosX(), y))
			this.y = y;
	}



	/**
	 * This method sets the X-coordinate of the projectile to a given value.
	 * 
	 * @param x
	 * 
	 * @post Sets X-coordinate the world of the projectile to the given value.
	 * 			|new.x = x
	 */
	@Basic @Model
	public void setPosX(double x) 
	{
		if (isValidPosition(x,this.getPosY()))
			this.x = x;
	}



	/**
	 * This method returns the position of the projectile on the Y-axis.
	 * 
	 * @return y
	 */
	@Basic @Raw
	public double getPosY() 
	{
		return y;
	}




	/**
	 * This method returns the position of the projectile on the X-axis.
	 * 
	 * @return x
	 */
	@Basic @Raw
	public double getPosX() 
	{
		return x;
	}



	/**
	 * This method returns the radius of the projectile in meters.
	 * 
	 * @return Math.pow(this.getMass()/(density*(4/3)*Math.PI), 1/3)
	 */
	public double getRadius() 
	{
		return Math.pow(this.getMass()/(density*(4/3)*Math.PI), 1/3);
	}


	/**
	 * 
	 */
	private boolean hitWorm = false;

	/**
	 * This method returns if the projectile is still active.
	 * 
	 * @return true if hitworm is true and the projectile is within the boundries of the world and on a passable location.
	 * 			| (hitWorm == true && this.getWorld().projectileInBounds(this) && this.getWorld().isPassable(this.getPosX(), this.getPosY(), this.getRadius()))
	 */
	public boolean isActive() 
	{		
		if (hitWorm == true)
			return false;
		if (!this.getWorld().projectileInBounds(this))
			return false;
		if (!this.getWorld().isPassable(this.getPosX(), this.getPosY(), this.getRadius()))
			return false;
		return true;
	}




	/**
	 * This method loops over all the worms in world to check if the projectile has hit one
	 * 
	 * This can only happen if the projectile is in a world 
	 * 		| this.getWorld() != null
	 * 
	 * @post
	 * 		The boolean hitWorm gets set to true
	 * 			| new.hitWorm = true
	 * 
	 */
	public void lookForWorms()
	{
		if (this.getWorld() != null) 
		{
			ArrayList<Worm> worms = new ArrayList<Worm>(getWorld().getWorms());
			for (Worm worm : worms ) 
			{
				if (worm != this.worm && World.isOverlapping(this.getPosX(), this.getPosY(), this.getRadius(), worm.getPosX(), worm.getPosY(), worm.getRadius())) 
				{
					damage(worm, damageWeapon());
					hitWorm = true;
				}
			}
		}
	}




	/**
	 * This method returns the damage the selected weapon deals.
	 * 
	 * @return 80 if the selectedWeapon == "Bazooka"
	 * 		| this.getWorld().currentWorm().getSelectedWeapon() == "Bazooka"
	 * 
	 * @return 20 if the selectedWeapon == "Rifle"
	 * 		| this.getWorld().currentWorm().getSelectedWeapon() == "Rifle"
	 */
	public int damageWeapon()
	{
		if (this.getWorld().currentWorm().getSelectedWeapon() == "Bazooka")
			return 80;
		if (this.getWorld().currentWorm().getSelectedWeapon() == "Rifle")
			return 20;
		return 0;
	}



	/**
	 * This method applies a given damage points to a given worm.
	 * 
	 * @param worm
	 * 			The given worm.
	 * 
	 * @param damage
	 * 			The given damage points.
	 * 
	 * @post 
	 * 			Lowers the value of the HP of the given worm by the given damage points
	 * 			| new.worm.getHP() == worm.getHP() - damage 				
	 */
	public void damage(Worm worm, int damage)
	{
		worm.setHP(worm.getHP() - damage);
	}




	/**
	 * This method destroys the projectile.
	 * 
	 * @pre the projectile cannot be active.
	 * 
	 * @post removes the projectile from the world.
	 * 			|new.getWorm == null
	 */
	public void destroy()
	{
		if (!isActive())
		{
			this.getWorld().removeProjectile(this);
			this.worm = null;
		}
	}



	/**
	 * This method launches a given rifle projectile.
	 * 
	 * 
	 * @param projectile
	 * 		The given projectile.
	 * 
	 * The active projectile cannot be initialized
	 * 		|getWorld().getActiveProjectile() == null
	 * 
	 * @post
	 * 	The mass and force of the projectile get set to the values of the weapon	
	 * 		| new.getMass() == 10
	 * 		| new.getForce() == 1.5
	 */
	public void shootRifle(Projectile projectile) 
	{
		if (getWorld().getActiveProjectile() == null)
		{
			this.getWorld().addProjectile(projectile);
			projectile.setMass(10);
			projectile.setForce(1.5);
			Jump(0.001);
		}
		else
		{
			getWorld().getActiveProjectile().destroy();
			shootRifle(projectile);
		}

	}



	/**
	 * This method sets the mass of the projectile to a given mass
	 * 
	 * @param mass
	 * 		The given mass.
	 * 
	 * @post Replaces the value for the mass of the projectile with a given value.
	 * 		|new.mass = mass;
	 */
	@Basic @Model
	private void setMass(double mass) 
	{
		this.mass = mass;
	}



	/**
	 * This method returns the mass of the projectile.
	 * 
	 * @return mass
	 */
	@Basic @Raw
	private double getMass()
	{
		return mass;
	}



	/**
	 * This method launches a given bazooka projectile.
	 * 
	 * @param propulsionYield
	 * 		The propulsionYield of the worm
	 * 
	 * @param projectile
	 * 		The given projectile.
	 * 
	 * The active projectile cannot be initialized
	 * 		| getWorld().getActiveProjectile() == null
	 * 
	 * @post
	 * 	The mass and force of the projectile get set to the values of the weapon	
	 * 		| new.getMass() == 300
	 * 		| new.getForce() == 2.5 + 0.07*propulsionYield
	 */
	public void shootBazooka(double propulsionYield, Projectile projectile) 
	{
		if (getWorld().getActiveProjectile() == null)
		{
			this.getWorld().addProjectile(projectile);
			projectile.setMass(300);
			projectile.setForce(2.5 + 0.07*propulsionYield);
			Jump(0.001);
		}
		else
		{
			getWorld().getActiveProjectile().destroy();
			shootBazooka(propulsionYield, projectile);
		}

	}



	/**
	 * This method sets the value for the force of the projectile to a given value.
	 *
	 * @param force
	 * 		The given force.
	 * 
	 * @post
	 * the value for the force of the projectile is been set to the given value.
	 * 		|new.force = force
	 */
	@Basic @Model
	private void setForce(double force) 
	{
		this.force = force;	
	}




	/**
	 * This method returns the force applied to the projectile when launched.
	 * 
	 * @return force
	 */
	@Basic @Raw
	private double getForce()
	{
		return force;
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
		double[] jumpStep = new double[2];
		jumpStep = this.JumpStep(this.JumpTime(delta));
		this.setPosX(jumpStep[0]);
		this.setPosY(jumpStep[1]);		
		lookForWorms();
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
		double X = worm.getPosX();
		double Y = worm.getPosY();
		double[] jumpStepResult = new double[2];
		double jumpTime = 0;
		while (hitWorm == false && getWorld().isPassable(X, Y, worm.getRadius()))
		{
			jumpStepResult = this.JumpStep(jumpTime);
			X = jumpStepResult[0];
			Y = jumpStepResult[1];
			jumpTime += delta;
		}
		return jumpTime;
	}




	/**This method sets the time the projectile is in the air to a given value.
	 * 
	 * @param time
	 * 		The given time.
	 * 
	 * @post The time the projectile is in the air is been set to the given value.
	 * 		| new.time = time
	 * 
	 */
	@Basic @Model
	private void setTime(double time) 
	{
		this.time = time;
	}




	/**This method sets the distance the projectile travels to a given value.
	 * 
	 * @param distance
	 * 		The given distance.
	 * 
	 * @post The distance the projectile travels is been set to the given value.
	 * 		| new.distance = distance
	 * 
	 */
	@Basic @Model
	private void setDistance(double distance) 
	{
		this.distance = distance;
	}




	/**
	 * This method returns the distance the projectile travels.
	 * 
	 * @return distance
	 */
	@Basic @Raw
	private double getDistance() 
	{
		return distance;
	}




	/**
	 * This method returns the time the projectile is in the air.
	 * 
	 * @return distance
	 */
	@Basic @Raw
	private double getTime() 
	{
		return time;
	}




	/**
	 * This method sets the velocity by which the projectile travels to a given value.
	 * 
	 * @param velocity
	 * 		The given velocity.
	 * 
	 * @post The velocity by which the projectile travels is been set to the given value.
	 * 			| new.getVelocity == velocity
	 */
	@Basic @Model
	private void setVelocity(double velocity) 
	{
		this.velocity = velocity;
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
		this.setVelocity(this.getForce() * 0.5 / this.getMass());
		double velocityX = this.getVelocity() * Math.cos(worm.getAngle());
		double velocityY = this.getVelocity() * Math.sin(worm.getAngle());
		double x = this.getPosX() + (velocityX * DeltaT);
		double y = this.getPosY() + (velocityY * DeltaT - 0.5*g*Math.pow(DeltaT, 2));

		double[] jumpstep = new double[] {x,y};

		return jumpstep;

	}




	/**
	 * This method returns the velocity by which the projectile travels.
	 * 
	 * @return velocity
	 */
	private double getVelocity() 
	{
		return velocity;
	}




	/**
	 * This method checks if a given position is a valid position.
	 * 
	 * @param posX
	 * 			The X-coordinate for the position in meters.
	 * 
	 * @param posY
	 * 			The Y-coordinate for the position in meters.
	 * 
	 * @return true if posX and posY are not negative- or positive infinity.
	 * 				| (!(posX == Double.NEGATIVE_INFINITY) || !(posY == Double.NEGATIVE_INFINITY) || !(posX == Double.POSITIVE_INFINITY) || !(posY == Double.POSITIVE_INFINITY))
	 * 
	 * @throws IllegalArgumentException
	 * 			If posX is positive infinity or negative infinity
	 * 				| (posX == Double.POSITIVE_INFINITY)
	 * 				| (posX == Double.NEGATIVE_INFINITY)
	 * 			If posY is positive infinity or negative infinity
	 * 			  	| (posY == Double.POSITIVE_INFINITY)
	 * 				| (posY == Double.NEGATIVE_INFINITY)
	 */
	public boolean isValidPosition(double posX, double posY) throws IllegalArgumentException 
	{
		if ((posX == Double.NEGATIVE_INFINITY) || (posY == Double.NEGATIVE_INFINITY) || (posX == Double.POSITIVE_INFINITY) || (posY == Double.POSITIVE_INFINITY))
			throw new IllegalArgumentException("Not a valid value for position");
		return true;
	}

}
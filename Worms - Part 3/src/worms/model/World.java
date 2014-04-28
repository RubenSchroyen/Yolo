package worms.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import be.kuleuven.cs.som.annotate.*;
import static org.junit.Assert.*;

/**
 * A class of world involving a position consisting of world width and height, a passable map and a random seed to place objects on random locations
 *
 * @version 2.0
 * 
 * @Author Ruben Schroyen & Ralph Vancampenhoudt
 * 
 * A R² production for the course: Object-oriented Programming at KuLeuven
 * 
 * 
 * @invar isValidDimensions(getWorldWidth(), getWorldHeight())
 */
public class World 
{

	/**
	 * The height of the world
	 */
	private double worldHeight;


	/**
	 * The width of the world
	 */
	private double worldWidth;


	/**
	 * An Array of passable locations in the world
	 */
	private boolean[][] passableMap;


	/**
	 * A list of all worms in this world
	 */
	public List<Worm> worms = new ArrayList<Worm>();


	/**
	 * A list of all food in this world
	 */
	public List<Food> fodder = new ArrayList<Food>();


	/**
	 * A list of all projectiles in this world
	 */
	public List<Projectile> projectiles = new ArrayList<Projectile>();


	/**
	 * A list of all teams in this world
	 */
	public List<Team> teams = new ArrayList<Team>();


	/**
	 * An index to do loops through the earlier lists
	 */
	private int index = 0;


	/**
	 * The currently selected team to add worms to
	 */
	private Team currentTeam;


	/**
	 * A random number generator
	 */
	Random RandomGenerator = new Random();


	/**
	 * Creates the world with the initializing parameters
	 * 
	 * @param worldWidth
	 * 		creates the world with a given width
	 * 
	 * @param worldHeight
	 * 		creates the world with a given height
	 * 
	 * @param passableMap
	 * 		creates the world with a given map of passable locations
	 * 
	 * @param random
	 * 		creates the world with a random number
	 * 
	 * @pre given dimensions have to be valid
	 * 		|isValidDimensions(worldWidth, worldHeight)
	 * 
	 * @post 
	 * 		All lists get initialized
	 * 		| new.worms == new ArrayList<Worm>()
	 * 		| new.fodder == new ArrayList<Food>()
	 * 		| new.projectiles == new ArrayList<Projectile>()
	 * 		| new.teams == new ArrayList<Team>()
	 * 
	 * @post
	 * 		The dimensions get set
	 * 		| new.getWorldHeight() == worldHeight
	 * 		| new.getWorldWidth() == worldWidth
	 * 
	 * @post
	 * 		The passable map gets set
	 * 		| new.getPassableMap() == passableMap
	 * 
	 * @post 
	 * 		The randomGenerator gets initialized
	 * 		| new.RandomGenerator == random
	 * 
	 */
	public World(double worldWidth, double worldHeight, boolean[][] passableMap, Random random) 
	{
		this.worms = new ArrayList<Worm>();
		this.fodder = new ArrayList<Food>();
		this.projectiles = new ArrayList<Projectile>();
		this.teams = new ArrayList<Team>();
		this.setWorldHeight(worldHeight);
		this.setWorldWidth(worldWidth);
		this.setPassableMap(passableMap);
		RandomGenerator = random;
	}


	/**
	 * This method gets the currently selected worm
	 * 
	 * @return
	 * 		the currently selected worm
	 * 			| Worm worm = worms.get(this.getIndex()
	 * 
	 * If the current worm has no AP left and there are more worms in this world, the next worm is automatically selected
	 * 		| if (worm.getCurrentAP() == 0 && worms.size() > 1)
	 * 			| nextWorm()
	 */
	public Worm currentWorm()
	{
		Worm worm = worms.get(this.getIndex());
		if (worm.getCurrentAP() == 0 && worms.size() > 1)
		{
			worm.setHP(worm.getHP() + 10);
			worm.setCurrentAP(worm.getMaxHP());
			this.nextWorm();
		}

		return worm;
	}


	/**
	 * This method increases the index by 1 to get to the next worm
	 * 
	 * @post
	 * 		The index increases by 1
	 * 		| new.getIndex() == getIndex() + 1
	 */
	public void nextWorm()
	{
		this.setIndex(this.getIndex() + 1);
	}

	/**
	 * This method returns the index
	 * 
	 * @return index
	 */
	@Basic @Raw
	public int getIndex()
	{
		return index;
	}

	/**
	 * This method sets the index to the corresponding value
	 * 
	 * @param index
	 * 		The index we want to set index to
	 * 
	 * @post
	 * 		If the index surpasses the size of worms, it resets to zero
	 * 			| if (index > worms.size() - 1)
	 * 				| new.index() == 0
	 * 		Else the index gets set to the wanted index 
	 * 			| new.index() == index
	 * 		 	
	 */
	@Basic @Model
	public void setIndex(int index)
	{
		if (index > worms.size() - 1)
			this.index = 0;
		else
			this.index = index;
	}

	/**
	 * This method returns all the worms in this world
	 * 
	 * @return worms
	 */
	@Basic @Raw
	public List<Worm> getWorms()
	{
		return worms;
	}


	/**
	 * This method returns all the food in this world
	 * 
	 * @return fodder
	 */
	@Basic @Raw
	public List<Food> getFodder()
	{
		return fodder;
	}


	/**
	 * This method returns all the projectiles in this world (never more than 1)
	 * 
	 * @return projectiles
	 */
	@Basic @Raw
	public List<Projectile> getProjectiles()
	{
		return projectiles;
	}


	/**
	 * This method returns all the teams in this world (never more than 10)
	 * 
	 * @return teams
	 */
	@Basic @Raw
	public List<Team> getTeams()
	{
		return teams;
	}


	/**
	 * This method returns the amount of worms in this world
	 * 
	 * @return worms.size()
	 */
	@Basic @Raw
	public int amountOfWorms()
	{
		return worms.size();
	}


	/**
	 * This method returns the amount of teams in this world (never more than 10)
	 * 
	 * @return teams.size()
	 */
	@Basic @Raw
	public int amountOfTeams()
	{
		return teams.size();
	}


	/**
	 * This method returns the amount of projectiles in this world (never more than 1)
	 * 
	 * @return projectiles.size()
	 */
	@Basic @Raw
	public int amountOfProjectiles()
	{
		return projectiles.size();
	}


	/**
	 * This method returns the amount of food in this world
	 * 
	 * @return fodder.size()
	 */
	@Basic @Raw
	public int amountOfFood()
	{
		return fodder.size();
	}


	/**
	 * This method returns the winner of the game (either a team or a worm)
	 * 
	 * If the game is finished and there is still a team left, the team wins
	 * 		| if (isFinished() && this.amountOfTeams() == 1)
	 * 
	 * @return (teams.get(0).getName())
	 * 
	 * 
	 * If the game is finished and there is no team left, but still one worm, this worm wins
	 * 		| if (isFinished() && this.amountOfTeams() == 0 && worms.size() == 1)
	 * 			
	 * @return (worms.get(0).getName())
	 * 		
	 * 
	 * If the game is still going on, there is no winner yet
	 * 
	 * @return (null)
	 */
	@Basic @Raw
	public String getWinner()
	{
		if (isFinished() && this.amountOfTeams() == 1)
			return (teams.get(0).getName());
		if (isFinished() && this.amountOfTeams() == 0 && worms.size() == 1)
			return (worms.get(0).getName());
		return null;
	}

	/**
	 * This method checks if the game is finished or not
	 * 
	 * @return
	 * 		True if there is still a team left and the amountOfWorms left is the same as the amount of worms in this team
	 * 			| (teams.size() == 1 && this.amountOfWorms() == teams.get(0).getAllWorms().size())
	 * 
	 * 		True if there are no teams left, but still only 1 worm
	 * 			| (teams.size() == 0 && worms.size() == 1)
	 * 
	 * 		False if none of these are occurring
	 */
	public boolean isFinished()
	{
		if (teams.size() == 1 && this.amountOfWorms() == teams.get(0).getAllWorms().size())
			return true;
		if (teams.size() == 0 && worms.size() == 1)
			return true;
		return false;
	}


	/**
	 * This method checks if this worm exists 
	 * 
	 * @param worm
	 * 		The worm we want to check
	 * 
	 * @return 
	 * 		True if the worm exists
	 * 			| (worm != null)
	 */
	public boolean wormExists(Worm worm)
	{
		return (worm != null);
	}

	/**
	 * This method checks if this worm is in world
	 * 
	 * @param worm
	 * 		The worm we want to check
	 * 
	 * @return
	 * 		True if the worm is already part of worms
	 * 			| worms.contains(worm)
	 */
	public boolean wormInWorld(Worm worm)
	{
		return worms.contains(worm);
	}

	/**
	 * This method adds a worm to this world on a random adjacent location where it is not overlapping with any food or other worms
	 * 
	 * 
	 * @assert 
	 * 		The worm exists and has the same world as this one
	 * 			| (wormExists(worm) && worm.getWorld() == this)
	 * 
	 * @assert 
	 * 		The worm is not yet in this world
	 * 			| (!wormInWorld(worm))
	 * 
	 * @post 
	 * 		the worm is added to the list
	 * 			| new.worms.size() = worms.size() + 1
	 * 
	 * @post
	 * 		The position of the worm is set to a random one
	 * 			| new.getPosX() == findAdjacentX(worm, randomPositionX)
	 * 			| new.getPosY() == findAdjacentY(worm, randomPositionY)
	 * 
	 * @post
	 * 		The worm gets added to the currentTeam
	 * 			| new.getCurrentTeam().size() == getCurrentTeam().size() + 1
	 */
	public void addWorm() 
	{
		double randomPositionX = RandomGenerator.nextDouble() * (this.getWorldWidth() + 1);
		double randomPositionY = RandomGenerator.nextDouble() * (this.getWorldHeight() + 1);
		double randomAngle = RandomGenerator.nextDouble() * 2 * Math.PI;
		double randomRadius = RandomGenerator.nextDouble() * 0.75 + 0.25;


		Worm worm = new Worm(this, randomPositionX, randomPositionY, randomRadius, randomAngle, "Press N to rename", null);
		worm.setPosX(this.findAdjacentX(worm, randomPositionX));
		worm.setPosY(this.findAdjacentY(worm, randomPositionY));

		assert(wormExists(worm) && worm.getWorld() == this);
		assert(!wormInWorld(worm));

		if (wormInBounds(worm) && isAdjacent(worm.getPosX(), worm.getPosY(), worm.getRadius()))
		{
			if (fodder.size() > 1)
			{
				for ( Food food : fodder ) 
				{
					if (World.isOverlapping(worm.getPosX(), worm.getPosY(), worm.getRadius(), food.getPosX(), food.getPosY(), food.getRadius())) 
						return;
				}
			}

			if (worms.size() > 1)
			{
				for ( Worm otherWorm : worms ) 
				{
					if (World.isOverlapping(worm.getPosX(), worm.getPosY(), worm.getRadius(), otherWorm.getPosX(), otherWorm.getPosY(), otherWorm.getRadius())) 
						return;
				}
			}
			worms.add(worm);
			worm.addToTeam(this.getCurrentTeam());
		}
		else 
			addWorm();
	}

	/**
	 * This method recalls the current team to add worms in
	 * 
	 * @return currentTeam
	 */
	@Basic @Raw
	public Team getCurrentTeam() 
	{
		return currentTeam;
	}

	/**
	 * This method sets the current team to add worms in
	 * 
	 * @param currentTeam
	 * 		The currentTeam we want to set it to
	 * 
	 * @post
	 * 		We set the current team to this team
	 * 			| new.currentTeam() == currentTeam
	 */
	public void setCurrentTeam(Team currentTeam) 
	{
		this.currentTeam = currentTeam;
	}

	/**
	 * This method finds a value for X that is adjacent nearby the location of the worm
	 * 
	 * @param worm
	 * 		The worm we are trying to place down
	 * 
	 * @param x
	 * 		The position we place him down at
	 * 
	 * @return
	 * 		CurrentX if the distance between center and currentX > worm.getRadius()
	 * 			| (Math.abs(center - currentX) > worm.getRadius())
	 * 
	 * 		0 if this is not the case
	 */
	public double findAdjacentX(Worm worm, double x)
	{
		double center = this.getWorldWidth() / 2;
		double currentX = worm.getPosX();

		while (Math.abs(center - currentX) > worm.getRadius() && !isAdjacent(currentX, worm.getPosY(), worm.getRadius())) 
		{
			if (currentX < center) 
			{
				currentX += 1 * (this.getWorldWidth() / passableMap[0].length);
			}

			if (currentX > center) 
			{
				currentX -= 1 * (this.getWorldWidth() / passableMap[0].length);
			}
			worm.setPosX(currentX);
		}
		if (Math.abs(center - currentX) > worm.getRadius())
			return currentX;
		else
			return 0;
	}


	/**
	 * This method finds a value for Y that is adjacent nearby the location of the worm
	 * 
	 * @param worm
	 * 		The worm we are trying to place down
	 * 
	 * @param y
	 * 		The position we place him down at
	 * 
	 * @return
	 * 		CurrentX if the distance between center and currentX > worm.getRadius()
	 * 			| (Math.abs(center - currentY) > worm.getRadius())
	 * 
	 * 		0 if this is not the case
	 */
	public double findAdjacentY(Worm worm, double y)
	{
		double center = this.getWorldHeight() / 2;
		double currentY = worm.getPosY();
		while (Math.abs(center - currentY) > worm.getRadius() && !isAdjacent(worm.getPosX(), currentY, worm.getRadius())) 
		{	
			if (currentY < center) 
			{
				currentY += 1 * (this.getWorldHeight() / passableMap.length);
			}

			if (currentY > center) 
			{
				currentY -= 1 * (this.getWorldHeight() / passableMap.length);
			}
			worm.setPosY(currentY);
		}
		if (Math.abs(center - currentY) > worm.getRadius())
			return currentY;
		else
			return 0;
	}

	/**
	 * This method finds a value for X that is adjacent nearby the location of the food
	 * 
	 * @param food
	 * 		The food we are trying to place down
	 * 
	 * @param x
	 * 		The position we place it down at
	 * 
	 * @return
	 * 		CurrentX if the distance between center and currentX > food.getRadius()
	 * 			| (Math.abs(center - currentX) > food.getRadius())
	 * 
	 * 		0 if this is not the case
	 */
	public double findAdjacentX(Food food, double x)
	{
		double center = this.getWorldWidth() / 2;
		double currentX = food.getPosX();

		while (Math.abs(center - currentX) > food.getRadius() && !isAdjacent(currentX, food.getPosY(), food.getRadius())) 
		{
			if (currentX < center) 
			{
				currentX += 1 * (this.getWorldWidth() / passableMap[0].length);

			}

			if (currentX > center) 
			{
				currentX -= 1 * (this.getWorldWidth() / passableMap[0].length);

			}
			food.setPosX(currentX);

		}
		if (Math.abs(center - currentX) > food.getRadius())
			return currentX;
		else
			return 0;
	}


	/**
	 * This method finds a value for Y that is adjacent nearby the location of the food
	 * 
	 * @param food
	 * 		The food we are trying to place down
	 * 
	 * @param y
	 * 		The position we place it down at
	 * 
	 * @return
	 * 		CurrentX if the distance between center and currentX > food.getRadius()
	 * 			| (Math.abs(center - currentY) > food.getRadius())
	 * 
	 * 		0 if this is not the case
	 */
	public double findAdjacentY(Food food, double y)
	{
		double center = this.getWorldHeight() / 2;
		double currentY = food.getPosY();
		while (Math.abs(center - currentY) > food.getRadius() && !isAdjacent(food.getPosX(), currentY, food.getRadius())) 
		{	
			if (currentY < center) 
			{
				currentY += 1 * (this.getWorldHeight() / passableMap.length);
			}

			if (currentY > center) 
			{
				currentY -= 1 * (this.getWorldHeight() / passableMap.length);
			}
			food.setPosY(currentY);
		}
		if (Math.abs(center - currentY) > food.getRadius())
			return currentY;
		else
			return 0;
	}

	/**
	 * This method removes a worm from the world if all conditions are met
	 * 
	 * @param worm
	 * 		The worm we are looking to remove
	 * 
	 * @assert
	 * 		The worm has to exist and be in this world
	 * 		| (wormExists(worm)) && (worm.getWorld() == this)
	 * 
	 * @assert 
	 * 		The worm has to be in this world already
	 * 		| (wormInWorld(worm))
	 * 
	 * @post
	 * 		The worm gets removed
	 * 		| new.worms.size() == worms.size() - 1
	 */
	public void removeWorm(Worm worm) 
	{
		assert (wormExists(worm)) && (worm.getWorld() == this);
		assert (wormInWorld(worm));
		worms.remove(worm);
	}


	/**
	 * This method checks if this food exists 
	 * 
	 * @param food
	 * 		The food we want to check
	 * 
	 * @return 
	 * 		True if the food exists
	 * 			| (food != null)
	 */
	public boolean foodExists(Food food)
	{
		return (food != null);
	}


	/**
	 * This method checks if this food is in world
	 * 
	 * @param food
	 * 		The food we want to check
	 * 
	 * @return
	 * 		True if the food is already part of fodder
	 * 			| fodder.contains(food)
	 */
	public boolean foodInWorld(Food food)
	{
		return fodder.contains(food);
	}


	/**
	 * This method adds a food to this world on a random adjacent location where it is not overlapping with any worm or other food
	 * 
	 * 
	 * @assert 
	 * 		The food exists and has the same world as this one
	 * 			| (foodExists(worm) && food.getWorld() == this)
	 * 
	 * @assert 
	 * 		The food is not yet in this world
	 * 			| (!foodInWorld(worm))
	 * 
	 * @post 
	 * 		the food is added to the list
	 * 			| new.fodder.size() = fodder.size() + 1
	 * 
	 * @post
	 * 		The position of the food is set to a random one
	 * 			| new.getPosX() == findAdjacentX(food, randomPositionX)
	 * 			| new.getPosY() == findAdjacentY(food, randomPositionY)
	 * 
	 */
	public void addFood() 
	{
		double randomPositionX = RandomGenerator.nextDouble() * (this.getWorldWidth() + 1);
		double randomPositionY = RandomGenerator.nextDouble() * (this.getWorldHeight() + 1);

		Food food = new Food(this, randomPositionX, randomPositionY);

		food.setPosX(this.findAdjacentX(food, randomPositionX));
		food.setPosY(this.findAdjacentY(food, randomPositionY));
		assert(foodExists(food) && food.getWorld() == this);
		assert(!foodInWorld(food));
		if (foodInBounds(food) && isAdjacent(food.getPosX(), food.getPosY(), food.getRadius()))
		{
			if (fodder.size() > 1)
			{

				for ( Food otherFood : fodder ) 
				{
					if (World.isOverlapping(food.getPosX(), food.getPosY(), food.getRadius(), otherFood.getPosX(), otherFood.getPosY(), otherFood.getRadius())) 
						return;
				}
			}

			if (worms.size() > 1)
			{
				for ( Worm otherWorm : worms ) 
				{
					if (World.isOverlapping(food.getPosX(), food.getPosY(), food.getRadius(), otherWorm.getPosX(), otherWorm.getPosY(), otherWorm.getRadius())) 
						return;
				}
			}
			fodder.add(food);
		}
		else 
			addFood();
	}


	/**
	 * This method removes a food from the world if all conditions are met
	 * 
	 * @param food
	 * 		The food we are looking to remove
	 * 
	 * @assert
	 * 		The food has to exist and be in this world
	 * 		| (foodExists(food)) && (food.getWorld() == this)
	 * 
	 * @assert 
	 * 		The food has to be in this world already
	 * 		| (foodInWorld(food))
	 * 
	 * @post
	 * 		The food gets removed
	 * 		| new.fodder.size() == fodder.size() - 1
	 */
	public void removeFood(Food food)
	{
		assert (foodExists(food)) && (food.getWorld() == this);
		assert (foodInWorld(food));
		fodder.remove(food);
	}


	/**
	 * This method checks if this projectile exists 
	 * 
	 * @param projectile
	 * 		The projectile we want to check
	 * 
	 * @return 
	 * 		True if the projectile exists
	 * 			| (projectile != null)
	 */
	public boolean projectileExists(Projectile projectile)
	{
		return (projectile != null);
	}


	/**
	 * This method checks if this projectile is in world
	 * 
	 * @param projectile
	 * 		The worm we want to check
	 * 
	 * @return
	 * 		True if the projectile is already part of projectiles
	 * 			| projectiles.contains(projectile)
	 */
	public boolean projectileInWorld(Projectile projectile)
	{
		return projectiles.contains(projectile);
	}


	/**
	 * This method adds a projectile to this world
	 * 
	 * 
	 * @assert 
	 * 		The projectile exists and has the same world as this one
	 * 			| (projectileExists(projectile) && projectile.getWorld() == this)
	 * 
	 * @assert 
	 * 		The projectile is not yet in this world
	 * 			| (!projectileInWorld(projectile))
	 * 
	 * @post 
	 * 		the projectile is added to the list
	 * 			| new.projectiles.size() = projectiles.size() + 1
	 *
	 */
	public void addProjectile(Projectile projectile)
	{
		assertEquals (true, projectileExists(projectile));
		assertEquals(true, projectile.getWorld() == this);
		assertEquals (false, projectileInWorld(projectile));
		projectiles.add(projectile);
	}


	/**
	 * This method removes a projectile from the world if all conditions are met
	 * 
	 * @param projectile
	 * 		The projectile we are looking to remove
	 * 
	 * @assert
	 * 		The projectile has to exist and be in this world
	 * 		| (projectileExists(projectile)) && (projectile.getWorld() == this)
	 * 
	 * @assert 
	 * 		The projectile has to be in this world already
	 * 		| (projectileInWorld(projectile))
	 * 
	 * @post
	 * 		The projectile gets removed
	 * 		| new.projectiles.size() == projectiles.size() - 1
	 */
	public void removeProjectile(Projectile projectile) 
	{
		assertEquals (true, projectileExists(projectile));
		assertEquals (true, projectile.getWorld() == this);
		assertEquals (true, projectileInWorld(projectile));
		projectiles.remove(projectile);
	}

	/**
	 * This method returns the distance between two positions
	 * 
	 * @param x
	 * 		the posX of the first position
	 * 
	 * @param y
	 * 		the posY of the first position
	 * 
	 * @param newX
	 * 		the posX of the second position
	 * 
	 * @param newY
	 * 		the posY of the first position
	 * 
	 * @return Math.sqrt(Math.pow((newX - x), 2) + Math.pow((newY -y), 2))
	 */
	public double getDistance(double x,double y,double newX,double newY){
		return Math.sqrt(Math.pow((newX - x), 2) + Math.pow((newY -y), 2));
	}


	/**
	 * This method checks if this team exists 
	 * 
	 * @param team
	 * 		The team we want to check
	 * 
	 * @return 
	 * 		True if the team exists
	 * 			| (team != null)
	 */
	public boolean teamExists(Team team)
	{
		return (team != null);
	}


	/**
	 * This method checks if this team is in world
	 * 
	 * @param team
	 * 		The team we want to check
	 * 
	 * @return
	 * 		True if the team is already part of teams
	 * 			| teams.contains(team)
	 */
	public boolean teamInWorld(Team team)
	{
		return teams.contains(team);
	}


	/**
	 * This method adds a team to this world
	 * 
	 * 
	 * @assert 
	 * 		The team exists and has the same world as this one
	 * 			| (teamExists(team) && team.getWorld() == this)
	 * 
	 * @assert 
	 * 		The team is not yet in this world
	 * 			| (!teamInWorld(team))
	 * 
	 * @assert
	 * 		There can't be more than 10 teams
	 * 			| teams.size() < 10
	 * 
	 * @post
	 * 		the current team is set to this team
	 * 			|new.getCurrentTeam() == team
	 * 
	 * @post 
	 * 		the team is added to the list
	 * 			| new.teams.size() = teams.size() + 1
	 * 
	 */
	public void addTeam(Team team) throws IllegalArgumentException
	{
		assert (teamExists(team)) && (team.getWorld() == null);
		assert (!teamInWorld(team));
		assert (teams.size() < 10);
		this.setCurrentTeam(team);
		teams.add(team);
	}


	/**
	 * This method removes a team from the world if all conditions are met
	 * 
	 * @param team
	 * 		The team we are looking to remove
	 * 
	 * @assert
	 * 		The team has to exist and be in this world
	 * 		| (teamExists(team)) && (team.getWorld() == this)
	 * 
	 * @assert 
	 * 		The team has to be in this world already
	 * 		| (teamInWorld(team))
	 * 
	 * @post
	 * 		The team gets removed
	 * 		| new.teams.size() == teams.size() - 1
	 */
	public void removeTeam(Team team) throws IllegalArgumentException
	{
		assert (teamExists(team)) && (team.getWorld() == this);
		assert (teamInWorld(team));
		teams.remove(team);
	}


	/**
	 * The method to check whether the worm is in worldbounds
	 * 
	 * @param worm
	 * 		The worm we want to check
	 * 
	 * @return
	 * 		True if the worm is in the world bounds
	 * 		| (!(worm.getPosX() < 0) || !(worm.getPosX() > this.getWorldWidth()) || !(worm.getPosY() < 0) || !(worm.getPosY() > this.getWorldHeight()))
	 */
	public boolean wormInBounds(Worm worm)
	{
		if (worm.getPosX() < 0 || worm.getPosX() > this.getWorldWidth() || worm.getPosY() < 0 || worm.getPosY() > this.getWorldHeight())
			return false;
		else 
			return true;
	}


	/**
	 * The method to check whether the food is in worldbounds
	 * 
	 * @param food
	 * 		The food we want to check
	 * 
	 * @return
	 * 		True if the food is in the world bounds
	 * 		| (!(food.getPosX() < 0) || !(food.getPosX() > this.getWorldWidth()) || !(food.getPosY() < 0) || !(food.getPosY() > this.getWorldHeight()))
	 */
	public boolean foodInBounds(Food food)
	{
		if (food.getPosX() < 0 || food.getPosX() > this.getWorldWidth() || food.getPosY() < 0 || food.getPosY() > this.getWorldHeight())
			return false;
		else 
			return true;
	}


	/**
	 * The method to check whether the projectile is in worldbounds
	 * 
	 * @param projectile
	 * 		The projectile we want to check
	 * 
	 * @return
	 * 		True if the projectile is in the world bounds
	 * 		| (!(projectile.getPosX() < 0) || !(projectile.getPosX() > this.getWorldWidth()) || !(projectile.getPosY() < 0) || !(projectile.getPosY() > this.getWorldHeight()))
	 */
	public boolean projectileInBounds(Projectile projectile)
	{
		if (projectile.getPosX() < 0 || projectile.getPosX() > this.getWorldWidth() || projectile.getPosY() < 0 || projectile.getPosY() > this.getWorldHeight())
			return false;
		else 
			return true;
	}

	/**
	 * The method to recall the worldHeight
	 * 
	 * @return worldHeight
	 */
	@Basic @Raw
	public double getWorldHeight() 
	{
		return worldHeight;
	}

	/**
	 * The method to set the worldHeight
	 * 
	 * @param worldHeight
	 * 		The worldHeight we want to set it to
	 * 
	 * @post 
	 * 		If the worldHeight is responsible for valid dimensions, we set it to the wanted worldHeight
	 * 			| new.getWorldHeight == worldHeight
	 */
	@Basic @Model
	public void setWorldHeight(double worldHeight) 
	{
		if (isValidDimensions(this.getWorldWidth(), worldHeight))
			this.worldHeight = worldHeight;
	}

	/**
	 * The method to recall the worldWidth
	 * 
	 * @return worldWidth
	 */
	@Basic @Raw
	public double getWorldWidth() 
	{
		return worldWidth;
	}


	/**
	 * The method to set the worldWidth
	 * 
	 * @param worldWidth
	 * 		The worldWidth we want to set it to
	 * 
	 * @post 
	 * 		If the worldWidth is responsible for valid dimensions, we set it to the wanted worldWidth
	 * 			| new.getWorldWidth == worldWidth
	 */
	@Basic @Model
	public void setWorldWidth(double worldWidth) 
	{
		if (isValidDimensions(worldWidth, this.getWorldHeight()))
			this.worldWidth = worldWidth;
	}

	/**
	 * The method to get the map of passable locations
	 * 
	 * @return passableMap
	 */
	@Basic @Raw
	public boolean[][] getPassableMap() 
	{
		return passableMap;
	}

	/**
	 * The method to set the map of passable locations
	 * 
	 * @param map
	 * 		The map we want to set it to
	 * 
	 * @throws IllegalArgumentException
	 * 		If the length of the map is zero on the X-axis
	 * 			| map.length == 0
	 * 		If the length of the map is zero on the Y-axis
	 * 			| map[0].length == 0
	 * 
	 * @post
	 * 		We set the map to the one we wanted
	 * 			| new.getPassableMap() == map
	 */
	@Basic @Model
	private void setPassableMap(boolean[][] map) throws IllegalArgumentException
	{
		if (map.length == 0)
			throw new IllegalArgumentException("Empty map!");
		else if (map[0].length == 0)
			throw new IllegalArgumentException("Empty map!");
		this.passableMap = map;
	}

	/**
	 * The method to check whether the dimensions of this world are valid
	 * 
	 * @param width
	 * 		The width we want to check
	 * 
	 * @param height
	 * 		The height we want to check
	 * 
	 * @return true if everything is ok
	 * 
	 * @throws IllegalArgumentException
	 * 		If the width is less than zero or higher than the max value
	 * 			| (width < 0 || width > Double.MAX_VALUE )
	 * 		If the height is less than zero or higher than the max value
	 * 			| (height < 0 || height > Double.MAX_VALUE)
	 */
	public boolean isValidDimensions(double width, double height) throws IllegalArgumentException
	{
		if (width < 0 || width > Double.MAX_VALUE )
			throw new IllegalArgumentException("Not a valid dimension for World");
		if (height < 0 || height > Double.MAX_VALUE)
			throw new IllegalArgumentException("Not a valid dimension for World");
		return true;
	}

	/**
	 * This method returns the amount of pixels in this world on the X-axis
	 * 
	 * @return this.getPassableMap()[0].length
	 */
	@Basic @Raw
	public int getPixelsX() 
	{
		return this.getPassableMap()[0].length;
	}

	/**
	 * This method returns the amount of pixels in this world on the Y-axis
	 * 
	 * @return this.getPassableMap().length
	 */
	@Basic @Raw
	public int getPixelsY() 
	{
		return this.getPassableMap().length;
	}

	/**
	 * This method returns the resolution on the X-axis
	 * 
	 * @return this.getWorldWidth() / ((double) this.getPixelsX())
	 */
	@Basic @Raw
	public double getResolutionX() 
	{
		return (this.getWorldWidth() / ((double) this.getPixelsX()));
	}


	/**
	 * This method returns the resolution on the Y-axis
	 * 
	 * @return this.getWorldHeight() / ((double) getPixelsY())
	 */
	@Basic @Raw
	public double getResolutionY() 
	{
		return (this.getWorldHeight() / ((double) getPixelsY()));
	}


	/**
	 * This method changes a position to a pixel
	 * 
	 * @param x
	 * 		The position on the X-axis
	 * 
	 * @param y
	 * 		The position on the Y-axis
	 * 
	 * @return pixelPosition
	 * 
	 * @throws IllegalArgumentException
	 * 		If the position is not inside the worldboundaries
	 * 			| !isWithinBoundaries(x,y)
	 */
	private int[] positionToPixel(double x, double y) throws IllegalArgumentException 
	{
		if (!isWithinBoundaries(x,y))
			throw new IllegalArgumentException("Not within the boundaries of this world");
		int pixelX = (int) Math.round(x * ((double) this.getPixelsX()-1 ) / this.getWorldWidth());
		int pixelY = (this.getPixelsY()-1) - (int) Math.round(y * ((double) this.getPixelsY()  -1 ) / this.getWorldHeight());

		int[] pixelPosition = new int[2];
		pixelPosition[0] = pixelX;
		pixelPosition[1] = pixelY;

		return pixelPosition;
	}


	/**
	 * This method checks whether the position is inside the worldboundaries
	 * 
	 * @param x
	 * 		The position on the X-axis
	 * 
	 * @param y
	 * 		The position on the Y-axis
	 * 
	 * @return		
	 * 		True if the position is indeed within those boundaries
	 * 		| (!(x < (double) 0) || !(x > this.getWorldWidth()) || !(y < (double) 0) || !(y > this.getWorldHeight()))
	 * 
	 */
	public boolean isWithinBoundaries(double x, double y) 
	{
		if (x < (double) 0 || x > this.getWorldWidth() || y < (double) 0 || y > this.getWorldHeight())
			return false;
		return true;
	}

	/**
	 * This method checks if the pixel is passable
	 * 
	 * @param pixelX
	 * 		the posX of this pixel
	 * 
	 * @param pixelY
	 * 		the posY of this pixel
	 * 
	 * @return this.getPassableMap()[pixelY][pixelX]
	 */
	private boolean isPassablePixel(int pixelX, int pixelY) 
	{
		return this.getPassableMap()[pixelY][pixelX];
	}


	/**
	 * This method checks if the position is passable
	 * 
	 * @param x
	 * 		The posX of this position
	 * 
	 * @param y
	 * 		The posY of this position
	 * 
	 * @return isPassablePixel(pixel[0],pixel[1])
	 *			| isWithinBoundaries(x,y)
	 *
	 */
	private boolean isPassablePosition(double x, double y) 
	{
		if (!isWithinBoundaries(x,y))
			return false;

		int[] pixel = positionToPixel(x,y);
		return isPassablePixel(pixel[0],pixel[1]);
	}


	/**
	 * This method checks if the circle is passable
	 * 
	 * @param x
	 * 		The posX of this circle
	 * 
	 * @param y
	 * 		The posY of this circle
	 * 
	 * @param radius
	 * 		The radius of this circle
	 * 
	 * @return
	 * 		True if the circle is passable
	 * 			| isPassablePosition(x,y)
	 * 			| isPassablePosition(x + testX, y + testY)
	 * 			| isPassablePosition(x + testX, y - testY)
	 * 			| isPassablePosition(x - testX, y + testY)
	 * 			| isPassablePosition(x - testX, y - testY)
	 */
	public boolean isPassable(double x, double y, double radius) 
	{ 
		if (!isPassablePosition(x, y))
			return false;

		int amountOfPixelsX = (int) Math.ceil(0.1*radius / this.getResolutionX());
		int amountOfPixelsY = (int) Math.ceil(0.1*radius / this.getResolutionY());

		double testX = 0;
		double testY = 0;
		for (int pixelX = 0; pixelX < amountOfPixelsX; pixelX++) 
		{
			for (int pixelY = 0; pixelY < amountOfPixelsY; pixelY++) 
			{
				testX = pixelX * this.getResolutionX();
				testY = pixelY * this.getResolutionY();
				if (Math.sqrt(Math.pow(testX,2) + Math.pow(testY,2)) <= 0.1*radius) 
				{
					if (!isPassablePosition(x + testX, y + testY))
						return false;
					if (!isPassablePosition(x - testX, y + testY))
						return false;
					if (!isPassablePosition(x + testX, y - testY))
						return false;
					if (!isPassablePosition(x - testX, y - testY))
						return false;
				}
			}
		}
		return true;
	}

	/**
	 * This method checks if the circle around the passable circle is impassable
	 * 
	 * @param x
	 * 		The posX of this circle
	 * 
	 * @param y
	 * 		The posY of this circle
	 * 
	 * @param radius
	 * 		The radius of this circle
	 * 
	 * @return
	 * 		True if the circle is passable and the outer circle is still within boundaries, but not passable
	 * 			| isWithinBoundaries(x + deltaX, y + deltaY) && !isPassablePosition(x + deltaX, y + deltaY)
	 */
	public boolean isAdjacent(double x, double y, double radius) 
	{
		if (!isPassable(x,y,radius))
			return false;

		for (double testAngle = 0; testAngle < 2*Math.PI; testAngle += 2*Math.PI/40) 
		{
			double deltaX = (0.1*radius + this.getResolutionX()) * Math.cos(testAngle);
			double deltaY = (0.1*radius + this.getResolutionY()) * Math.sin(testAngle);

			if (isWithinBoundaries(x + deltaX, y + deltaY) && !isPassablePosition(x + deltaX, y + deltaY)) 
				return true;
		}
		return false;
	}

	/**
	 * The method to check whether 2 positions are overlapping
	 * 
	 * @param X
	 * 		The posX of the one object
	 * 
	 * @param Y
	 * 		The posY of the one object
	 * 
	 * @param radius
	 * 		The radius of the one object
	 * 
	 * @param x
	 * 		The posX of the other object
	 * 
	 * @param y
	 * 		The posY of the other object
	 * 
	 * @param Radius
	 * 		The radius of the other object
	 * 
	 * @return
	 * 		true if norm < sumRadius
	 * 			| norm < sumRadius
	 */
	public static boolean isOverlapping(double X, double Y, double radius, double x, double y, double Radius) 
	{
		double deltaX = x - X;
		double deltaY = y - Y;

		double norm = Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2));
		double sumRadius = Radius + radius;

		return (norm < sumRadius);
	}


	/**
	 * The method to get the active projectile
	 * 
	 * If the amount of projectiles == 1, return the one projectile
	 * 		|this.amountOfProjectiles() == 1
	 * @return getProjectiles().get(0)
	 * 
	 * Else we return nothing
	 * @return null
	 *	
	 */
	@Basic @Raw
	public Projectile getActiveProjectile() 
	{
		if (this.amountOfProjectiles() == 1)
			return this.getProjectiles().get(0);
		return null;
	}
}

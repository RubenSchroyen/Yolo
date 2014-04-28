package worms.model;


import java.util.Collection;
import java.util.Random;

import worms.gui.game.IActionHandler;
import worms.model.programs.ParseOutcome;


public class Facade implements IFacade 
{

	private Random random = new Random();
	private Team team;




	@Override
	public void turn(Worm worm, double angle) throws ModelException
	{
		try
		{
			worm.Turn(angle);
		}
		catch (IllegalArgumentException exc)
		{
			throw new ModelException(exc.getMessage());
		} 
	}



	@Override
	public double[] getJumpStep(Worm worm, double t) 
	{
		return worm.JumpStep(t);
	}


	@Override
	public double getX(Worm worm) 
	{
		return worm.getPosX();
	}


	@Override
	public double getY(Worm worm) 
	{
		return worm.getPosY();
	}


	@Override
	public double getOrientation(Worm worm) 
	{
		return worm.getAngle();
	}


	@Override
	public double getRadius(Worm worm) 
	{
		return worm.getRadius();
	}


	@Override
	public void setRadius(Worm worm, double newRadius) throws ModelException
	{
		if (worm.getRadius() < this.getMinimalRadius(worm)) 
			throw new ModelException("Your radius is too small");
		worm.setRadius(newRadius);  
	}


	@Override
	public double getMinimalRadius(Worm worm)
	{
		return worm.minRadius; 
	}


	@Override
	public int getActionPoints(Worm worm) 
	{
		return worm.getCurrentAP();
	}


	@Override
	public int getMaxActionPoints(Worm worm) 
	{
		return worm.getMaxAP();
	}


	@Override
	public String getName(Worm worm) 
	{
		return worm.getName();
	}


	@Override
	public void rename(Worm worm, String newName) 
	{

		try 
		{
			worm.setName(newName);
		}
		catch (IllegalArgumentException exc)
		{
			throw new ModelException(exc.getMessage());
		}
	}


	@Override
	public double getMass(Worm worm) 
	{
		return worm.getMass();
	}




	/*********************************************************************************************
	 * 																						     *
	 * 									PART 2 ADDITIONS									     *
	 * 																						     *       
	 *********************************************************************************************/


	@Override
	public Collection<Worm> getWorms(World world) 
	{
		return world.getWorms();
	}


	@Override
	public String getWinner(World world) 
	{
		return world.getWinner();
	}



	@Override
	public Collection<Food> getFood(World world) 
	{
		return world.getFodder();
	}


	@Override
	public double getX(Food food) 
	{
		return food.getPosX();
	}


	@Override
	public double getY(Food food) 
	{
		return food.getPosY();
	}


	@Override
	public double getRadius(Food food) 
	{
		return food.getRadius();
	}



	@Override
	public String getSelectedWeapon(Worm worm) 
	{
		return worm.getSelectedWeapon();
	}


	@Override
	public Worm getCurrentWorm(World world) 
	{
		return world.currentWorm();
	}


	@Override
	public void addEmptyTeam(World world, String newName) throws ModelException
	{
		if (world.amountOfTeams() < 10)
		{
			Team team = new Team(newName, world);
			world.addTeam(team);	
		}
		if (world.amountOfTeams() > 10)
		{
			throw new ModelException("Too many teams in this world");
		}
	}



	@Override
	public void addNewFood(World world) 
	{
		world.addFood();
	}



	@Override
	public void startGame(World world) 
	{
		world = new World(world.getWorldHeight(), world.getWorldWidth(), world.getPassableMap(), random);
	}




	@Override
	public void startNextTurn(World world) 
	{
		world.nextWorm();
		world.currentWorm().setCurrentAP(world.currentWorm().getMaxAP());
		world.currentWorm().setHP(world.currentWorm().getHP() + 10);			
	}


	@Override
	public boolean isImpassable(World world, double randomizedX, double randomizedY, double testRadius) 
	{
		return world.isPassable(randomizedX, randomizedY, testRadius);
	}



	@Override
	public boolean isAdjacent(World world, double randomizedX, double randomizedY, double testRadius) 
	{
		return world.isAdjacent(randomizedX, randomizedY, testRadius);
	}


	@Override
	public double getJumpTime(Worm worm, double jumpTimeStep) 
	{
		return worm.JumpTime(jumpTimeStep);
	}


	@Override
	public String getTeamName(Worm worm) 
	{
		if (worm.getTeam() != null)
			return team.getName();
		return null;
	}


	@Override
	public int getMaxHitPoints(Worm worm) 
	{
		return worm.getMaxHP();
	}


	@Override
	public int getHitPoints(Worm worm) 
	{
		return worm.getHP();
	}


	@Override
	public void jump(Worm worm, double jumpTimeStep) 
	{
		worm.Jump(jumpTimeStep);
	}


	@Override
	public boolean isAlive(Worm worm) 
	{
		return worm.isAlive();
	}


	@Override
	public boolean canMove(Worm worm) 
	{
		return worm.canMove();
	}


	@Override
	public boolean canFall(Worm worm) 
	{
		return worm.canFall();
	}


	@Override
	public void fall(Worm worm) 
	{
		worm.fall();
	}


	@Override
	public void move(Worm worm) throws ModelException
	{
		try
		{
			worm.move();
		}
		catch (IllegalArgumentException exc)
		{
			throw new ModelException(exc.getMessage());
		}
	}


	@Override
	public void selectNextWeapon(Worm worm) 
	{
		worm.selectNextWeapon();
		worm.setSelectedWeapon();
	}


	@Override
	public void shoot(Worm worm, int propulsionYield) throws ModelException
	{
		try{
			worm.shoot(propulsionYield);
		}

		catch (IllegalArgumentException exc)
		{
			throw new ModelException(exc.getMessage());
		}

	}


	@Override
	public Projectile getActiveProjectile(World world) 
	{
		return world.getActiveProjectile();
	}

	@Override
	public double getJumpTime(Projectile projectile, double jumpTimeStep) 
	{
		return projectile.JumpTime(jumpTimeStep);
	}

	@Override
	public double getX(Projectile projectile) 
	{
		return projectile.getPosX();
	}

	@Override
	public double getY(Projectile projectile) 
	{
		return projectile.getPosY();
	}

	@Override
	public double getRadius(Projectile projectile) 
	{
		return projectile.getRadius();
	}

	@Override
	public void jump(Projectile projectile, double jumpTimeStep) 
	{
		projectile.Jump(jumpTimeStep);
	}

	@Override
	public double[] getJumpStep(Projectile projectile, double elapsedTime) 
	{
		return projectile.JumpStep(elapsedTime);
	}

	@Override
	public boolean isActive(Food food) 
	{
		return food.isActive();
	}

	@Override
	public boolean isActive(Projectile projectile) 
	{
		return projectile.isActive();
	}

	@Override
	public Food createFood(World world, double x, double y) 
	{
		Food food = new Food(world, x, y);
		return food;
	}



	@Override
	public World createWorld(double width, double height, boolean[][] passableMap, Random random) 
	{
		return new World(width, height, passableMap, random);
	}



	@Override
	public void addNewWorm(World world, Program program) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean canTurn(Worm worm, double angle) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public Worm createWorm(World world, double x, double y, double direction,
			double radius, String name, Program program) 
	{
		// TODO Auto-generated method stub
		Worm worm = new Worm(world, x, y, direction, radius, name, program);
		return null;
	}



	@Override
	public boolean isGameFinished(World world) {
		// TODO Auto-generated method stub
		return world.isFinished();
	}



	@Override
	public ParseOutcome<?> parseProgram(String programText,
			IActionHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean hasProgram(Worm worm) {
		// TODO Auto-generated method stub
		return worm.hasProgram();
	}



	@Override
	public boolean isWellFormed(Program program) {
		// TODO Auto-generated method stub
		return program.isWellFormed();
	}



}
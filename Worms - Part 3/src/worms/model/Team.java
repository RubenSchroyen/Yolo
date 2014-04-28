package worms.model;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.*;
import worms.model.Worm;
/**
 * A class of Teams involving a name, a world in which the team exists and an list of members.
 * 
 * @version 2.0
 * 
 * @author Ruben Schroyen & Ralph Vancampenhoudt
 * 
 * A R² production for the course: Object-oriented Programming at KuLeuven
 *
 */
public class Team 
{
	/**
	 * The name of the team.
	 */
	private String name;


	/**
	 * The world in which the team exists.
	 */
	private World world;

	/**
	 * The list of the members in the team.
	 */
	private ArrayList<Worm> teamMembers;

	/**
	 * A boolean to determine whether the team has just been created or not
	 */
	private boolean initiatedTeam ;



	/**
	 * Creates the team and sets the parameters to the given values.
	 * 
	 * @param name
	 * 			creates the team with a given name.
	 * 
	 * 
	 * @param world
	 *			creates the team in a given world.
	 *
	 *
	 * @pre		given name must be a valid name.
	 * 			|isValidName(name)
	 *
	 * @throws IllegalArgumentException
	 * 		If the name of the team is invalid.
	 * 			|!isValidName(name)
	 *
	 * @post Name of the team is set to the given valid.
	 * 			|new.name == name
	 * 
	 * 
	 * @post World of the team is set to the given world.
	 *			|new.world == world
	 *
	 *
	 * @post A new arrayList of team members is initialized 
	 * 			|new.teamMembers == new ArrayList<Worm>();
	 * 
	 * @post We say the team is initiated
	 * 			|new.initiated == true
	 */

	public Team(String name, World world) 
	{
		if (!isValidName(name))
			throw new IllegalArgumentException("Name not valid");
		this.name = name;
		this.world = world;
		initiatedTeam = true;
		this.teamMembers = new ArrayList<Worm>();
	}


	/**
	 * This method returns the name of the team.
	 * @return name
	 */
	@Basic @Raw
	public String getName() 
	{
		return this.name;
	}



	/**
	 * This method returns the world in which the team exists.
	 * @return world
	 */
	@Basic @Raw
	public World getWorld() 
	{
		return this.world;
	}



	/**
	 * This method returns the worm with the given index.
	 * 
	 * @param index
	 * 			returns the worm with the given index
	 * 
	 * @return teamMembers.get(index)
	 * 
	 */
	@Basic @Raw
	public Worm getWorm(int index)
	{
		if (index < 0)
			index = 0;
		if (index > getAmountOfWorms() )
			index = 0;
		return teamMembers.get(index);
	}



	/**
	 * This method returns the amount of worms currently in the team.
	 * 
	 * @return teamMembers.size()
	 */
	@Basic @Raw
	public int getAmountOfWorms() {
		return teamMembers.size();
	}



	/**
	 * This method verifies if the given worm has been initialized. 
	 * @param worm
	 * 			The worm that needs checking.
	 * 
	 * 
	 * @return true if the worm is not null
	 * 			|worm != null
	 */
	public boolean wormExists(Worm worm) {
		return (worm != null);
	}



	/**
	 * This method checks is the given worm can be found at a given index.
	 *  
	 * @param worm
	 * 			The worm that needs checking.
	 * 
	 * 
	 * @param index
	 * 			The index that should match with the given worm.
	 * 
	 * 
	 * @return true if the index is smaller then 1 or greater then the total amount of worms.
	 * 			|!(index < 1)) || (!(index > getAmountOfWorms() + 1))
	 * 
	 * @return true if the worm does exist.
	 * 			|(pos == index) && (getWorm(pos) == worm)
	 * @return true if the index of the given worm is the same as the given index.
	 * 			|(pos == index) && (getWorm(pos) == worm)
	 * 
	 */
	public boolean wormExistsAtIndex(Worm worm, int index) 
	{
		if ((!(index < 1)) || (!(index > getAmountOfWorms() + 1)))
			return true;
		if (wormExists(worm))
			return true;
		for (int pos = 1; pos <= getAmountOfWorms(); pos++)
			if ((pos == index) && (getWorm(pos) == worm))
				return true;
		return false;
	}




	/**
	 * This method checks if the worms on the list have been initialized correctly.
	 * 
	 * @return true if every worm on the member list of this team is a member of this team.
	 * 
	 */

	public boolean isValidMembers() 
	{
		for (int index = 1; index <= getAmountOfWorms(); index++) 
		{
			if (!wormExistsAtIndex(getWorm(index), index))
				return false;
			if (getWorm(index).getTeam() != this)
				return false;
		}
		return true;
	}



	/**
	 * This method checks if the given worm is on the member list of this team.
	 * 
	 * @param worm
	 * 			The given worm.
	 * 
	 * 
	 * @return	true if the worm is a member of the team.
	 * 			|teamMembers.contains(worm)
	 */
	public boolean isMember(Worm worm) 
	{
		return teamMembers.contains(worm);
	}




	/**
	 * This method returns the index of a given worm.
	 * 
	 * @param worm
	 * 			The given worm.
	 * 
	 * @return	teamMembers.indexOf(worm)
	 */
	@Basic @Raw
	public int getIndexOfWorm(Worm worm) 
	{
		return teamMembers.indexOf(worm);
	}




	/**
	 * This method returns a list of all worms.
	 * 
	 * @return ArrayList<Worm>(teamMembers) 
	 */

	@Basic @Raw
	public ArrayList<Worm> getAllWorms() 
	{
		return new ArrayList<Worm>(teamMembers);
	}




	/**
	 * This method adds a given worm to the list of this team.
	 * 
	 * @param worm
	 * 			The given worm.
	 * 
	 * 
	 * @pre		The given worm must be initialized and be a member of the team.
	 * 
	 * @pre		The given worm cannot already be a member of another team. 
	 *
	 *
	 * @post	adds the given worm to the member list of this team.
	 * 			|new.teamMembers.size() = new.teamMembers.size() + 1
	 */
	public void addWorm(Worm worm) 
	{
		assert (worm != null) && (worm.getTeam() == this);
		assert !isMember(worm);
		teamMembers.add(worm);
		initiatedTeam = false;
	}




	/**
	 * This method removes a given worm from the member list of this team. 
	 * @param worm
	 * 			The given worm.
	 * 
	 * @pre		The given worm must be initialized and on the member list of this team.
	 * 
	 * @pre 	The given worm must be a member of this team.
	 * 
	 * @post	Removes the given worm from the member list of this team.
	 * 			||new.teamMembers.size() = new.teamMembers.size() - 1
	 */
	public void removeAsWorm(Worm worm) 
	{
		assert (worm != null) && (worm.getTeam() == this);
		assert (isMember(worm));
		teamMembers.remove(worm);
	}




	/**
	 * This method verifies if the name of the team is a valid name.
	 * 
	 * @param name
	 * 		The name of the team
	 * 
	 * 
	 * @return true if the name is valid.
	 * 			|name.matches("[A-Z][a-zA-Z0-9\\s'\"]+")
	 */
	private boolean isValidName(String name) 
	{
		return name.matches("[A-Z][a-zA-Z0-9\\s'\"]+");
	}




	/**
	 * This method verifies if this team is active.
	 * 
	 * @return true if the size of this team is not zero.
	 * 			|teamMembers.size() != 0
	 */
	public boolean isActive()
	{
		if (teamMembers.size() != 0 || initiatedTeam == true)
			return true;
		return false;
	}


	/**
	 * This method removes a team from the world.
	 * 
	 * if the team is not active anymore, we destroy it
	 * 		| !isActive()
	 * 
	 * @post
	 * 	removes team from the world
	 * 		| new.world == null
	 */
	public void destroy()
	{
		if (!isActive())
		{
			world.removeTeam(this);
			this.world = null;
		}
	}
}
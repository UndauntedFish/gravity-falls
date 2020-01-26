package com.ben.gravityfalls;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public ArrayList<Block> cuboid = new ArrayList<>();
	public double radius;
	
	@Override
	public void onEnable()
	{
		loadConfig();
		
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	private void loadConfig()
	{
		this.getConfig().options().copyDefaults();
		saveDefaultConfig();
	}
	
	
	/*
	 * Assuming cuboid arraylist is already filled with buildSquare(), builds a cube out of tha square by building
	 * up on the y axis for each block
	 */
	private void buildCube()
	{
		for (Block b : cuboid)
		{
			for (double i = 1.0; i < radius; i++)
			{
				cuboid.add(b.getLocation().add(0.0, i, 0.0).getBlock());
			}
		}
	}

	/*
	 * Assuming cuboid arraylist is already filled with buildLine(), builds a square out of that line by building
	 * up on the z axis for each block.
	 */
	private void buildSquare()
	{
		for (Block b : cuboid)
		{
			for (double i = 1.0; i < radius; i++)
			{
				cuboid.add(b.getLocation().add(0.0, 0.0, i).getBlock());
			}
		}
	}
	
	/*
	 * Builds the bottom line of the square base of the cube given the central location, then adds that line to the cuboid arraylist
	 */
	private void buildLine(Location l)
	{
		// Gets the bottomCenter from the central location and adds it to cuboid arraylist
		Location bottomCenter = l.add(0.0, 0.0, -radius);
		cuboid.add(bottomCenter.getBlock());
		
		
		// Adds all the blocks to the left and right of bottomCenter to cuboid arraylist
		Location ptrLeft = bottomCenter, ptrRight = bottomCenter;
		for (double i = 1.0; i < radius; i++)
		{
			cuboid.add(ptrLeft.add(-i, 0.0, 0.0).getBlock());
			cuboid.add(ptrRight.add(i, 0.0, 0.0).getBlock());
		}
	}
	
	private void buildCubeAround(Player player)
	{
		Location location = player.getLocation();
		location.setY(location.getY() - 2.0); // sets location to the block below the player
		
		buildLine(location);
		buildSquare();
		buildCube();
	}
	
	
	public void editBlocksInCube(Player player)
	{
		buildCubeAround(player);
		
		for (Block b : cuboid)
		{
			b.setType(Material.GLASS);
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		Player player = e.getPlayer();
		editBlocksInCube(player);
	}
}

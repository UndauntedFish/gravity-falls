package com.ben.gravityfalls;

import java.util.ArrayList;
import java.util.Collection;

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
	
	
	private ArrayList<Block> buildXYStack(Location bottom)
	{
		ArrayList<Block> allXYs;
		Collection<Block> allZs;
		allXYs = new ArrayList<>();
		allZs = new ArrayList<>();
		
		double radius = this.getConfig().getDouble("radius");
		Location ptr = bottom;
		
		// Creates XY Square
		for (double x = 0.0; x < radius * 2.0; x++)
		{
			for (double y = 0.0; y < radius * 2.0; y++)
			{
				ptr.add(x, y, 0.0);
				allXYs.add(ptr.getBlock());
			}
		}
		ptr = bottom;
		
		for (Block b : allXYs)
		{
			for (double z = 1.0; z < radius * 2.0; z++)
			{
				Location tempLocale = b.getLocation().add(0.0, 0.0, z);
				allZs.add(tempLocale.getBlock());
			}
		}
		
		for (Block b : allZs)
		{
			allXYs.add(b);
		}
		
		/* Creates Z-Deep Cube from the XY Squares
		for (double x = 0.0; x < radius * 2; x++)
		{
			for (double z = 0.0; z < radius * 2; z++)
			{
				ptr.add(x, 0.0, z);
				allXYs.add(ptr.getBlock());
			}
		}
		*/
		
		return allXYs;
	}
	
	
	private void editSurroundingBlocks(Player player)
	{
		Location location = player.getLocation();
		double radius = this.getConfig().getDouble("radius");
		
		double x1 = location.getX() - radius;
		double y1 = location.getY() - radius;
		double z1 = location.getZ() - radius;
		
		Location bottom = new Location(player.getWorld(), x1, y1, z1);
		ArrayList<Block> surroundingBlocks = buildXYStack(bottom);
		
		for (Block b : surroundingBlocks)
		{
			b.setType(Material.GLASS);
		}
		
		/*
		Location ptr;
		for (double y = y1; y < y2; y++)
		{
			for (double x = x1; x < x2; x++)
			{
				for (double z = z1; z < z2; z++)
				{
					ptr = new Location(player.getWorld(), x, y, z);
					Block ptrBlock = ptr.getBlock();
					ptr.getWorld().spawnFallingBlock(ptr, ptrBlock.getState().getData());
					ptrBlock.setType(Material.AIR);
				}
			}
		}
		*/
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		Player player = e.getPlayer();
		editSurroundingBlocks(player);
	}
}

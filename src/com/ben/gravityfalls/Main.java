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
import org.bukkit.util.Vector;

public class Main extends JavaPlugin implements Listener
{
	private ArrayList<Block> surroundingBlocks;
	
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
	
	private void editSurroundingBlocks(Player player)
	{
		Location location = player.getLocation();
		double radius = this.getConfig().getDouble("radius");
		
		double x1 = location.getX() - radius;
		double y1 = location.getY() - radius;
		double z1 = location.getZ() - radius;
		double x2 = x1 + (radius * 2);
		double y2 = y1 + (radius * 2);
		double z2 = z1 + (radius * 2);
		
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
			
		
		/*
		for (double i = x - 5; i < x + 5; x++)
		{
			for (double j = z - 5; j < z + 5; j++)
			{
				for (double k = y - 5; k < y + 5; y++)
				{
					Location loc = new Location(player.getWorld(), i, k, j);
					Block ptrBlock = loc.getBlock();
					
					if (ptrBlock.getType() == Material.AIR ||
						ptrBlock.getType() == Material.LAVA ||
						ptrBlock.getType() == Material.WATER ||
						ptrBlock.getType() == Material.OBSIDIAN ||
						ptrBlock.getType() == Material.END_PORTAL ||
						ptrBlock.getType() == Material.END_PORTAL_FRAME ||
						ptrBlock.getType() == Material.END_STONE ||
						ptrBlock.getType() == Material.BEDROCK)
					{
						return;
					}
					
					;;
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

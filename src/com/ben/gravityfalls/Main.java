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
	
	private ArrayList<Block> getSurroundingBlocks(Location location)
	{
		ArrayList<Block> surroundingBlocks = new ArrayList<>();
		
		Location ptr = location.add(new Vector(-5, -5, -5));
		
		for (int i = 0; i < 10 ; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				ptr.add(new Vector(j, i, j));
				
				Block ptrBlock = ptr.getBlock();
				
				if (ptrBlock.getType() != Material.AIR || 
					ptrBlock.getType() != Material.WATER || 
					ptrBlock.getType() != Material.LAVA ||
					ptrBlock.getType() != Material.OBSIDIAN ||
					ptrBlock.getType() != Material.BEDROCK ||
					ptrBlock.getType() != Material.END_STONE)
				{
					surroundingBlocks.add(ptrBlock);
				}
			}
		}
		
		return surroundingBlocks;
			
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		Player player = e.getPlayer();
		surroundingBlocks = getSurroundingBlocks(player.getLocation());
		
		for (Block b : surroundingBlocks)
		{
			b.setType(Material.WATER);
		}
	}
}

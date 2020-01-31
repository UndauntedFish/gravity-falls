package com.ben.gravityfalls;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin implements Listener
{
	public ArrayList<Block> cuboid = new ArrayList<>();
	public double radius;
	
	@Override
	public void onEnable()
	{
		this.getServer().getPluginManager().registerEvents(this, this);

		loadConfig();
		radius = this.getConfig().getDouble("radius");
	}
	
	private void loadConfig()
	{
		this.getConfig().options().copyDefaults();
		saveDefaultConfig();
	}
	
	
	public void editBlocksInCube(Player player)
	{
		if (this.getConfig().getInt("ymax") < 0)
		{
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ERROR! ymax must be greater than 0 in the config!");
			return;
		}
		if (this.getConfig().getInt("ymax") > 127)
		{
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ERROR! ymax must be less than 127 in the config!");
			return;
		}
		int ymax = this.getConfig().getInt("ymax");
		
		// Iterating through each block of the chunk that the player is in
		Chunk c = player.getLocation().getChunk();
		int bcount = 0;
		for (int y = 0; y < ymax; y++)
		{
			for (int x = 0; x < 15; x++)
			{
				for (int z = 0; z < 15; z++)
				{
					c.getBlock(x, y, z).setType(Material.BLACK_STAINED_GLASS);
					bcount++;
				}
			}	
		}
		player.sendMessage(ChatColor.GOLD + "" + bcount);
		
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e)
	{
		Player player = e.getPlayer();
		editBlocksInCube(player);
	}
}

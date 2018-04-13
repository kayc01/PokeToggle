package me.ckay.poketoggle;


import java.nio.file.Path;
import java.util.UUID;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.inject.Inject;

import me.ckay.poketoggle.config.PluginConfiguration;
import me.ckay.poketoggle.config.SettingsManager;
import me.ckay.poketoggle.commands.PokeToggleExectutor;

@Plugin(id = "poketoggle", name = "PokeToggle", version = "1.0")

public class PokeToggle {
	
	private static PokeToggle instance;

	public static PokeToggle getInstance()
	{
		return instance;
	}
	
	@Inject
	private Logger logger;
	
	public Logger getLogger() {
		return logger;
	}
	
	@Inject
	@ConfigDir(sharedRoot = false)
	private Path configDir;

	public Path getConfigDir()
	{
		return configDir;
	}
	
	@Inject
	private PluginContainer container;

	public PluginContainer getContainer()
	{
		return container;
	}
	
	private PluginConfiguration config;
	
	public PluginConfiguration getConfig()
	{
		return config;
	}

	private PluginConfiguration players;

	public PluginConfiguration getPlayersConfig()
	{
		return players;
	}

	public SettingsManager settings;
	
	boolean allowPokesell = true;
	
	@Listener
	public void onInitialize(GameInitializationEvent event) {
		
		instance = this;
		
		this.settings = SettingsManager.getInstance();
		
		this.getLogger().info("Loading...");
		
		CommandSpec cmdSpec = CommandSpec.builder().description(Text.of("Disable people sending you pokemon")).arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("true/false/help")))).executor(new PokeToggleExectutor(this)).build();
		
		Sponge.getCommandManager().register(this, cmdSpec, "poketoggle");
		
		this.settings.setupData(this);
		this.settings.saveData();
		
		this.getLogger().info("Enabled");
	}
	
	@Listener
	public void onSendCmd(SendCommandEvent event, @Root Player p) {
		
		//Player toRemove = Sponge.getServer().getPlayer(p).orElse(null);
		String[] args = event.getArguments().split(" ");

		
		if(event.getCommand().equals("pokesell") || event.getCommand().equals("psell") || event.getCommand().equals("pokegift") || event.getCommand().equals("pgift")) {
			this.getLogger().info("Command = pokesell");
			
			for (Player playersOnline : Sponge.getServer().getOnlinePlayers())
			{
				//UUID uuid = playersOnline.getUniqueId();
				
				if (event.getArguments().startsWith(playersOnline.getName())) {
					//UUID uuid = playersOnline.getUniqueId();
					this.getLogger().info("Starts with a player online");
					if (this.settings.getData().getString("Players." + playersOnline.getUniqueId().toString() + ".pokesell_enabled").equalsIgnoreCase("False")) {
						//cancel the command pokesell
						this.getLogger().info("UUID's Match");
						p.sendMessage(Text.of(TextColors.RED, "Command is disabled for this user!" ));
						event.setCancelled(true);
					}
				}
			}
		}
		event.getCommand();
	}

}

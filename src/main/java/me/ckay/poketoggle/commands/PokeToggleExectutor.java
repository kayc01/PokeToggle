package me.ckay.poketoggle.commands;

import java.util.List;
import java.util.UUID;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.ckay.poketoggle.PokeToggle;


public class PokeToggleExectutor implements CommandExecutor {


	private PokeToggle plugin;
	
	public PokeToggleExectutor(PokeToggle plugin)
	{
		this.plugin = plugin;
	}
	

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		String arg1 = args.getOne("true/false/help").get().toString();
		Player player = (Player) src;
		
		//UUID removeUUID = (UUID) ((List) this.plugin.queues.get(Integer.valueOf(gym))).get(0);
		//Player toRemove = Sponge.getServer().getPlayer(removeUUID).orElse(null);
		
		if (src instanceof Player) {
			
			if (arg1.equalsIgnoreCase("help")) {
				player.sendMessage(Text.of(TextColors.GREEN, "/poketoggle true - Enables people to send you pixelmon via pokesell"));
				player.sendMessage(Text.of(TextColors.RED, "/poketoggle false - Disables people to send you pixelmon via pokesell"));
			}
			
			if (arg1.equalsIgnoreCase("True")) {

				this.plugin.settings.getData().set("Players." + player.getUniqueId() + ".pokesell_enabled", "True");
				//this.plugin.settings.getData().set("config.enable_pokesell", "True");
				this.plugin.settings.saveData();
				player.sendMessage(Text.of(TextColors.GREEN, "Other players can now send you pixelmon via Pokesell"));
				
			}
			else if (arg1.equalsIgnoreCase("False")) {
				this.plugin.settings.getData().set("Players." + player.getUniqueId() + ".pokesell_enabled", "False");
				//this.plugin.settings.getData().set("config.enable_pokesell", "False");
				this.plugin.settings.saveData();
				player.sendMessage(Text.of(TextColors.RED, "Other players can no longer send you pixelmon via Pokesell"));
			}
			
			return CommandResult.success();
		}
		else if (src instanceof ConsoleSource){
			src.sendMessage(Text.of("Can only be ran by player."));
			return CommandResult.success();
		}
		else {
			src.sendMessage(Text.of("I don't know what you are."));
		}
		
		return CommandResult.empty();
	}

}

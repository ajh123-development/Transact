package net.ddns.minersonline.Transact.spigot.commands;

import net.ddns.minersonline.Transact.core.Transact;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandAccounts implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Transact.getInstance().getAccounts().forEach((account -> {
			sender.sendMessage(ChatColor.GREEN+account.toString());
		}));
		return true;
	}
}
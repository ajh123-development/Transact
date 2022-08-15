package net.ddns.minersonline.Transact.spigot.commands;

import net.ddns.minersonline.Transact.core.Transact;
import net.ddns.minersonline.Transact.core.currencies.Currency;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandCurrencies implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Transact.getInstance().getCurrencies().forEach((currency -> {
			sender.sendMessage(ChatColor.GREEN+currency.getName());
		}));
		return true;
	}
}
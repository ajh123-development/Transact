package net.ddns.minersonline.Transact.spigot.commands;

import net.ddns.minersonline.Transact.core.Transact;
import net.ddns.minersonline.Transact.core.currencies.Currency;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandNewCurrency implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length < 2){
			sender.sendMessage(ChatColor.RED+"Usage:");
			sender.sendMessage(ChatColor.GREEN+ Bukkit.getServer().getPluginCommand("newcurr").getUsage());
			return true;
		}
		if (args[0].length() > 1){
			sender.sendMessage(ChatColor.RED+"Symbol must be one character");
			return true;
		}
		if (args[1].length() < 1){
			sender.sendMessage(ChatColor.RED+"Name must be at least one character long");
			return true;
		}
		Currency currency = new Currency(args[0].charAt(0), args[1], 1);
		Transact.getInstance().getCurrencies().add(currency);
		sender.sendMessage(ChatColor.GREEN+"Currency created!");
		return true;
	}
}
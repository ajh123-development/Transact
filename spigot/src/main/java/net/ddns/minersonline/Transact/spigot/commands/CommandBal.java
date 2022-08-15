package net.ddns.minersonline.Transact.spigot.commands;


import net.ddns.minersonline.Transact.spigot.TransactImpl;
import net.ddns.minersonline.Transact.core.Account;
import net.ddns.minersonline.Transact.core.currencies.Currency;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBal implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player player){
			Account account = TransactImpl.getAccount(player.getUniqueId());
			if (account != null) {
				sender.sendMessage(ChatColor.BLUE+"=== Balance ===");
				account.balance.getBalance().forEach((name, value) -> {
					Currency currency = Currency.getCurrency(name);
					if (currency != null) {
						sender.sendMessage(ChatColor.GREEN+currency.format(value)+" "+ChatColor.AQUA+currency.getName());
					}
				});
			} else {
				sender.sendMessage(ChatColor.RED+"You do not have an account");
			}
		} else {
			sender.sendMessage(ChatColor.RED+"Only players can see their balance");
		}
		return true;
	}
}
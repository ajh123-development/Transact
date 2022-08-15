package net.ddns.minersonline.Transact.spigot.commands;

import net.ddns.minersonline.Transact.core.Account;
import net.ddns.minersonline.Transact.core.Transact;
import net.ddns.minersonline.Transact.spigot.TransactImpl;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandNewAccount implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player player){
			if (!TransactImpl.hasAccount(player.getUniqueId())){
				Account account = new Account(player.getUniqueId());
				account.balance.set(Transact.getInstance().getCurrencies().get(0), 0);
				Transact.getInstance().getAccounts().add(account);
				sender.sendMessage(ChatColor.GREEN+"Account created!");
			} else {
				sender.sendMessage(ChatColor.RED+"You already have an account");
			}
		} else {
			sender.sendMessage(ChatColor.RED+"Only players can see their balance");
		}
		return true;
	}
}
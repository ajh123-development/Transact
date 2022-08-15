package net.ddns.minersonline.Transact.spigot;

import net.ddns.minersonline.Transact.core.Account;
import net.ddns.minersonline.Transact.core.Transact;
import net.ddns.minersonline.Transact.core.currencies.Currency;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class VaultHook implements Economy {
	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getName() {
		return "Transact";
	}

	@Override
	public boolean hasBankSupport() {
		return false;
	}

	@Override
	public int fractionalDigits() {
		return 2;
	}

	@Override
	public String format(double amount) {
		return Transact.getInstance().getCurrencies().get(0).format(amount);
	}

	@Override
	public String currencyNamePlural() {
		return "";
	}

	@Override
	public String currencyNameSingular() {
		return "";
	}

	@Override
	public boolean hasAccount(String playerName) {
		Player player = Bukkit.getServer().getPlayer(playerName);
		if (player == null) { return false; }
		UUID id = player.getUniqueId();
		return TransactImpl.hasAccount(id);
	}

	@Override
	public boolean hasAccount(OfflinePlayer player) {
		if (player == null) { return false; }
		UUID id = player.getUniqueId();
		return TransactImpl.hasAccount(id);
	}

	@Override
	public boolean hasAccount(String playerName, String worldName) {
		return hasAccount(playerName);
	}

	@Override
	public boolean hasAccount(OfflinePlayer player, String worldName) {
		return hasAccount(player);
	}

	public Account getAccount(String playerName) {
		Player player = Bukkit.getServer().getPlayer(playerName);
		if (player != null) {
			return TransactImpl.getAccount(player.getUniqueId());
		}
		return null;
	}

	public Account getAccount(OfflinePlayer player) {
		if (player != null) {
			return TransactImpl.getAccount(player.getUniqueId());
		}
		return null;
	}

	@Override
	public double getBalance(String playerName) {
		if (hasAccount(playerName)) {
			return 0;
		}

		Player player = Bukkit.getServer().getPlayer(playerName);
		if (player == null) {
			return 0;
		}

		UUID id = player.getUniqueId();
		return TransactImpl.getBalance(id);
	}

	@Override
	public double getBalance(OfflinePlayer player) {
		return TransactImpl.getBalance(player.getUniqueId());
	}

	@Override
	public double getBalance(String playerName, String world) {
		return getBalance(playerName);
	}

	@Override
	public double getBalance(OfflinePlayer player, String world) {
		return getBalance(player);
	}

	@Override
	public boolean has(String playerName, double amount) {
		Account account = getAccount(playerName);
		if (account == null) {
			return false;
		}
		double balance = getBalance(playerName);
		return balance >= amount;
	}

	@Override
	public boolean has(OfflinePlayer player, double amount) {
		Account account = getAccount(player);
		if (account == null) {
			return false;
		}
		double balance = getBalance(player);
		return balance >= amount;
	}

	@Override
	public boolean has(String playerName, String worldName, double amount) {
		return has(playerName, amount);
	}

	@Override
	public boolean has(OfflinePlayer player, String worldName, double amount) {
		return has(player, amount);
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, double amount) {
		Account account = getAccount(playerName);
		Currency defaultCurrency = Transact.getInstance().getCurrencies().get(0);
		account.balance.withdraw(defaultCurrency, amount);
		return new EconomyResponse(
				amount,
				account.balance.getBalance().get(defaultCurrency.getName()),
				EconomyResponse.ResponseType.SUCCESS,
				null
		);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
		Account account = getAccount(player);
		Currency defaultCurrency = Transact.getInstance().getCurrencies().get(0);
		account.balance.withdraw(defaultCurrency, amount);
		return new EconomyResponse(
				amount,
				account.balance.getBalance().get(defaultCurrency.getName()),
				EconomyResponse.ResponseType.SUCCESS,
				null
		);
	}

	@Override
	public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
		Account account = getAccount(playerName);
		Currency defaultCurrency = Transact.getInstance().getCurrencies().get(0);
		account.balance.withdraw(defaultCurrency, amount);
		return new EconomyResponse(
				amount,
				account.balance.getBalance().get(defaultCurrency.getName()),
				EconomyResponse.ResponseType.SUCCESS,
				null
		);
	}

	@Override
	public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
		Account account = getAccount(player);
		Currency defaultCurrency = Transact.getInstance().getCurrencies().get(0);
		account.balance.withdraw(defaultCurrency, amount);
		return new EconomyResponse(
				amount,
				account.balance.getBalance().get(defaultCurrency.getName()),
				EconomyResponse.ResponseType.SUCCESS,
				null
		);
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, double amount) {
		Account account = getAccount(playerName);
		Currency defaultCurrency = Transact.getInstance().getCurrencies().get(0);
		account.balance.deposit(defaultCurrency, amount);
		return new EconomyResponse(
				amount,
				account.balance.getBalance().get(defaultCurrency.getName()),
				EconomyResponse.ResponseType.SUCCESS,
				null
		);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
		Account account = getAccount(player);
		Currency defaultCurrency = Transact.getInstance().getCurrencies() .get(0);
		account.balance.deposit(defaultCurrency, amount);
		return new EconomyResponse(
				amount,
				account.balance.getBalance().get(defaultCurrency.getName()),
				EconomyResponse.ResponseType.SUCCESS,
				null
		);
	}

	@Override
	public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
		Account account = getAccount(playerName);
		Currency defaultCurrency = Transact.getInstance().getCurrencies().get(0);
		account.balance.deposit(defaultCurrency, amount);
		return new EconomyResponse(
				amount,
				account.balance.getBalance().get(defaultCurrency.getName()),
				EconomyResponse.ResponseType.SUCCESS,
				null
		);
	}

	@Override
	public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
		Account account = getAccount(player);
		Currency defaultCurrency = Transact.getInstance().getCurrencies().get(0);
		account.balance.deposit(defaultCurrency, amount);
		return new EconomyResponse(
				amount,
				account.balance.getBalance().get(defaultCurrency.getName()),
				EconomyResponse.ResponseType.SUCCESS,
				null
		);
	}

	@Override
	public EconomyResponse createBank(String name, String player) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null);
	}

	@Override
	public EconomyResponse createBank(String name, OfflinePlayer player) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null);
	}

	@Override
	public EconomyResponse deleteBank(String name) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null);
	}

	@Override
	public EconomyResponse bankBalance(String name) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null);
	}

	@Override
	public EconomyResponse bankHas(String name, double amount) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null);
	}

	@Override
	public EconomyResponse bankWithdraw(String name, double amount) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null);
	}

	@Override
	public EconomyResponse bankDeposit(String name, double amount) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null);
	}

	@Override
	public EconomyResponse isBankOwner(String name, String playerName) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null);
	}

	@Override
	public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null);
	}

	@Override
	public EconomyResponse isBankMember(String name, String playerName) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null);
	}

	@Override
	public EconomyResponse isBankMember(String name, OfflinePlayer player) {
		return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, null);
	}

	@Override
	public List<String> getBanks() {
		return null;
	}

	public boolean createPlayerAccount(UUID id) {
		if (!TransactImpl.hasAccount(id)){
			Account account = new Account(id);
			Transact.getInstance().getAccounts().add(account);
		}
		return false;
	}

	@Override
	public boolean createPlayerAccount(String playerName) {
		Player player = Bukkit.getServer().getPlayer(playerName);
		if (player != null) {
			UUID id = player.getUniqueId();
			return createPlayerAccount(id);
		}
		return false;
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player) {
		UUID id = player.getUniqueId();
		return createPlayerAccount(id);
	}

	@Override
	public boolean createPlayerAccount(String playerName, String worldName) {
		return createPlayerAccount(playerName);
	}

	@Override
	public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
		return createPlayerAccount(player);
	}
}

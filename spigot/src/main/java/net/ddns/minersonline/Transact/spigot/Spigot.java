package net.ddns.minersonline.Transact.spigot;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.ddns.minersonline.Transact.core.Account;
import net.ddns.minersonline.Transact.core.currencies.Currency;
import net.ddns.minersonline.Transact.spigot.commands.*;
import net.ddns.minersonline.Transact.core.Transact;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.List;
import java.util.logging.Level;

public final class Spigot extends JavaPlugin {
	public static Transact transactCore;
	public static Gson gson = new Gson();

	@Override
	public void onEnable() {
		if(Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
			Bukkit.getServer().getServicesManager().register(Economy.class, new VaultHook(), this, ServicePriority.Highest);
		}
		transactCore = new TransactImpl();

		File root = getDataFolder();

		File accounts = new File(root.getAbsolutePath()+"/accounts.json");
		if (accounts.exists()) {
			try (Reader reader = new FileReader(accounts)) {
				List<Account> accountsList = gson.fromJson(reader, new TypeToken<List<Account>>(){}.getType());
				getLogger().info("[Accounts] Trying to load "+accountsList);
				Transact.getInstance().getAccounts().addAll(accountsList);
			} catch (IOException e) {
				getLogger().log(Level.SEVERE, "An error occurred when enabling Transact!", e);
			}
		}

		File currencies = new File(root.getAbsolutePath()+"/currencies.json");
		if (currencies.exists()) {
			try (Reader reader = new FileReader(currencies)) {
				List<Currency> currencyList = gson.fromJson(reader, new TypeToken<List<Currency>>(){}.getType());
				getLogger().info("[Currencies] Trying to load "+currencyList);
				Transact.getInstance().getCurrencies().addAll(currencyList);
			} catch (IOException e) {
				getLogger().log(Level.SEVERE, "An error occurred when enabling Transact!", e);
			}
		}

		if (getConfig().isSet("taxableCurrencies")) {
			Transact.getInstance().getTaxableCurrencies().addAll((List<String>) getConfig().getList("taxableCurrencies"));
		}

		if (Transact.getInstance().getCurrencies().size() == 0) {
			Currency currency = new Currency('$', "USD", 1);
			Transact.getInstance().getCurrencies().add(currency);
		}

		this.getCommand("balance").setExecutor(new CommandBal());
		this.getCommand("newacc").setExecutor(new CommandNewAccount());
		this.getCommand("newcurr").setExecutor(new CommandNewCurrency());
		this.getCommand("currencies").setExecutor(new CommandCurrencies());
		this.getCommand("accounts").setExecutor(new CommandAccounts());

		getLogger().info("Loaded");
		getLogger().info("[Accounts] "+Transact.getInstance().getAccounts());
		getLogger().info("[Currencies] "+Transact.getInstance().getCurrencies());
	}

	@Override
	public void onDisable() {
		File root = getDataFolder();

		File accounts = new File(root.getAbsolutePath()+"/accounts.json");
		try {
			new File(accounts.getParent()).mkdirs();
			accounts.createNewFile();
			Writer writer = new FileWriter(accounts);
			gson.toJson(Transact.getInstance().getAccounts(), writer);
			writer.close();
		} catch (IOException e){
			getLogger().log(Level.SEVERE, "An error occurred when disabling Transact!", e);
		}

		File currencies = new File(root.getAbsolutePath()+"/currencies.json");
		try  {
			new File(currencies.getParent()).mkdirs();
			currencies.createNewFile();
			Writer writer = new FileWriter(currencies);
			gson.toJson(Transact.getInstance().getCurrencies(), writer);
			writer.close();
		} catch (IOException e){
			getLogger().log(Level.SEVERE, "An error occurred when disabling Transact!", e);
		}
		getConfig().set("taxableCurrencies", Transact.getInstance().getTaxableCurrencies());
	}
}

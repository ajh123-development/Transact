package net.ddns.minersonline.Transact.core;

import net.ddns.minersonline.Transact.core.currencies.Bank;
import net.ddns.minersonline.Transact.core.currencies.Currency;

import java.util.List;

public abstract class Transact {
	private static Transact instance;
	protected static List<Account> accounts;
	protected static List<Bank> banks;
	protected static List<Currency> currencies;
	protected static List<String> taxableCurrencies;

	public static Transact getInstance() {
		return instance;
	}

	public void setInstance(Transact instance) {
		if (Transact.instance != null) {
			Transact.instance = instance;
		}
	}

	public abstract List<Account> getAccounts();

	public abstract List<Bank> getBanks();

	public abstract List<Currency> getCurrencies();

	public abstract List<String> getTaxableCurrencies();
}

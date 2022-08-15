package net.ddns.minersonline.Transact.core;

import net.ddns.minersonline.Transact.core.currencies.Currency;

import java.util.HashMap;
import java.util.Map;

public class Balance {
	private final Map<String, Double> amounts;

	public Balance() {
		this.amounts = new HashMap<>();
	}

	public void set(Currency currency, double amount){
		this.amounts.put(currency.getName(), amount);
	}

	public void deposit(Currency currency, double amount){
		double current = this.amounts.get(currency.getName());
		this.amounts.put(currency.getName(), current + amount);
	}

	public boolean withdraw(Currency currency, double amount){
		double current = this.amounts.get(currency.getName());
		this.amounts.put(currency.getName(), current - amount);
		return true;
	}

	public Map<String, Double> getBalance(){
		return amounts;
	}
}

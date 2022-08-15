package net.ddns.minersonline.Transact.spigot;

import net.ddns.minersonline.Transact.core.Account;
import net.ddns.minersonline.Transact.core.Transact;
import net.ddns.minersonline.Transact.core.currencies.Bank;
import net.ddns.minersonline.Transact.core.currencies.Currency;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TransactImpl extends Transact {

	public TransactImpl() {
		accounts = new ArrayList<>();
		banks = new ArrayList<>();
		currencies = new ArrayList<>();
		taxableCurrencies = new ArrayList<>();
		setInstance(this);
	}

	public static Account getAccount(UUID id) {
		for (Account account : Transact.getInstance().getAccounts()) {
			if (account.id.equals(id)) {
				return account;
			}
		}
		return null;
	}

	public static double getBalance(UUID id) {
		Account account = getAccount(id);
		if (account == null) return 0;

		Map<String, Double> balance = account.balance.getBalance();
		Currency defaultCurrency = Transact.getInstance().getCurrencies().get(0);
		if (defaultCurrency == null) {
			return 0;
		}
		String name = defaultCurrency.getName();
		if (name == null) {
			return 0;
		}
		if (balance == null) {
			return 0;
		}
		return balance.get(name);
	}

	public static boolean hasAccount(UUID id) {
		for (Account account : Transact.getInstance().getAccounts()){
			if (account.id == id){
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Account> getAccounts() {
		return accounts;
	}

	@Override
	public List<Bank> getBanks() {
		return banks;
	}

	@Override
	public List<Currency> getCurrencies() {
		return currencies;
	}

	@Override
	public List<String> getTaxableCurrencies() {
		return taxableCurrencies;
	}
}

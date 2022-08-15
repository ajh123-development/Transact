package net.ddns.minersonline.Transact.core.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.ddns.minersonline.Transact.core.Account;
import net.ddns.minersonline.Transact.core.Transact;
import net.ddns.minersonline.Transact.core.currencies.Bank;
import net.ddns.minersonline.Transact.core.currencies.Currency;

import java.util.ArrayList;
import java.util.List;

public class Serialisation extends Transact {
	public static Gson gson = new Gson();

	public Serialisation() {
		accounts = new ArrayList<>();
		banks = new ArrayList<>();
		currencies = new ArrayList<>();
		taxableCurrencies = new ArrayList<>();

		setInstance(this);

		Currency currency = new Currency('$', "USD", 1);
		Transact.getInstance().getCurrencies().add(currency);

		System.out.println("====Runtime modifications====");
		System.out.println("Serialisation");
		String out = gson.toJson(Transact.getInstance().getCurrencies());
		System.out.println(out);
		Transact.getInstance().getCurrencies().clear();
		System.out.println("Deserialisation");
		List<Currency> currencyList = gson.fromJson(out, new TypeToken<List<Currency>>(){}.getType());
		Transact.getInstance().getCurrencies().addAll(currencyList);
		System.out.println(Transact.getInstance().getCurrencies());
	}

	public static void main(String[] args){
		new Serialisation();
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

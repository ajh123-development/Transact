package net.ddns.minersonline.Transact.core.currencies;

import net.ddns.minersonline.Transact.core.Transact;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Currency {
	private final char symbol;
	private final String name;
	private final double conversionScale;

	private final UUID uid;

	private boolean interestEnabled;

	public Currency(char symbol, String name, double conversionScale) {
		this.symbol = symbol;
		this.name = name;
		this.conversionScale = conversionScale;
		this.uid =  UUID.nameUUIDFromBytes(name.getBytes(StandardCharsets.UTF_8));

		for (Currency currency : Transact.getInstance().getCurrencies()){
			if (currency.name.equals(name)){
				throw new IllegalArgumentException("A currency with that name already exits");
			}
		}
	}

	/**
	 * Whether this Currency supports interest
	 * @return true if allows, else false
	 */
	public boolean hasInterest() {
		return this.interestEnabled;
	}

	/**
	 * Fetch a set of all Currencies supporting interest
	 * @return Set of Interest Currencies
	 */
	public static Set<Currency> getInterestCurrencies() { return Transact.getInstance().getCurrencies().stream().filter(Currency::hasInterest).collect(Collectors.toSet()); }

	/**
	 * Fetches a set of Currencies that have {@link #hasTax()} to true.
	 * @return Set of Taxable Currencies
	 */
	public static Set<Currency> getTaxableCurrencies() { return Transact.getInstance().getCurrencies().stream().filter(Currency::hasTax).collect(Collectors.toSet()); }

	/**
	 * Set the interest acceptation state of this Currency
	 * @param interest Whether to allow interest
	 */
	public void setInterest(boolean interest) {
		this.interestEnabled = interest;
	}

	/**
	 * Whether this Currency is taxable.
	 * <br><br>
	 * This value is not stored within the Currency and is changeable from {@link Transact#getTaxableCurrencies()}.
	 * @return true if taxable, else false
	 */
	public boolean hasTax() {
		return Transact.getInstance().getTaxableCurrencies().contains(this.name);
	}

	/**
	 * Sets whether this Currency is taxable.
	 * <br><br>
	 * This value is not stored within the Currency and is changeable from {@link Transact#getTaxableCurrencies()}.
	 * @param tax Whether to allow tax
	 * @return true if successful, else false
	 */
	public boolean setTax(boolean tax) {
		if (tax){ Transact.getInstance().getTaxableCurrencies().add(this.name); }
		if (!tax){ Transact.getInstance().getTaxableCurrencies().remove(this.name); }
		return true;
	}

	/**
	 * Remove a Currency from the Plugin
	 * @param name Name of the Currency
	 * @see Currency#removeCurrency(Currency)
	 */
	public static void removeCurrency(String name) { removeCurrency(getCurrency(name)); }

	/**
	 * Remove a Currency from the Plugin
	 * @param econ Currency to remove
	 * @throws IllegalArgumentException if Currency is null
	 */
	public static void removeCurrency(Currency econ) throws IllegalArgumentException {
		if (econ == null) throw new IllegalArgumentException("Currency cannot be null");
		Transact.getInstance().getCurrencies().remove(econ);
		Transact.getInstance().getTaxableCurrencies().remove(econ.name);
	}

	/**
	 * Fetch a Currency
	 * @param name Name of the Currency
	 * @return Found Currency, or null if none found
	 * @throws IllegalArgumentException if name is null
	 */
	public static Currency getCurrency(String name) throws IllegalArgumentException {
		if (name == null) throw new IllegalArgumentException("name cannot be null");

		for (Currency currency : Transact.getInstance().getCurrencies()) {
			if (currency.name.equals(name)){
				return currency;
			}
		}
		return null;
	}

	/**
	 * Whether this Currency exists.
	 * @param name Name of the Currency
	 * @return true if Currency exists, else false
	 */
	public static boolean exists(String name) {
		if (name == null) return false;
		Currency currency = getCurrency(name);
		return currency != null;
	}

	/**
	 * Whether a Currency with this UUID exists.
	 * @param uid UUID of the Currency
	 * @return true if Currency exists, else false
	 */
	public static boolean exists(UUID uid) {
		if (uid == null) return false;
		return getCurrency(uid) != null;
	}

	/**
	 * Whether a Currency with this symbol exists.
	 * @param c Symbol of the Currency
	 * @return true if Currency exists, else false
	 */
	public static boolean exists(char c) {
		return getCurrency(c) != null;
	}

	/**
	 * Return the scale of which this Currency will be converted to a different Currency
	 * <p>
	 * A Currency with a conversion scale of {@code 1} and another with a conversion scale of {@code 0.5} would have a 2:1 ratio, meaning that 100 in the first Currency would be 200 in the second Currency.
	 * @return conversion scale of this Currency
	 */
	public double getConversionScale() { return this.conversionScale; }

	/**
	 * Fetch the name of this Currency
	 * @return Name of this Currency
	 */
	public String getName() { return this.name; }

	/**
	 * Convert this Currency to another Currency
	 * @param to The New Currency to convert to
	 * @param fromAmount How much amount is to be converted
	 * @return Converted amount in the other Currency's form
	 * @throws IllegalArgumentException if to or from is null, or Currencies are identical
	 * @see Currency#convertAmount(Currency, Currency, double)
	 */
	public double convertAmount(Currency to, double fromAmount) throws IllegalArgumentException {
		return convertAmount(this, to, fromAmount);
	}
	
	/**
	 * Get the Currency's unique identifier.
	 * @return Unique Identifier
	 */
	public UUID getUniqueId() {
		return this.uid;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Currency Currency = (Currency) o;
		return Currency.getName().equals(this.getName()) || Currency.getUniqueId().equals(this.getUniqueId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(symbol, name, conversionScale, interestEnabled);
	}

	/**
	 * Convert one Currency's balance to another
	 * @param from The Currency to convert from
	 * @param to The Currency to convert to
	 * @param fromAmount How much amount is to be converted
	 * @return Converted amount in the other Currency's form
	 * @throws IllegalArgumentException if to or from is null, or Currencies are identical
	 */
	public static double convertAmount(Currency from, Currency to, double fromAmount) throws IllegalArgumentException {
		if (from == null) throw new IllegalArgumentException("Currency from is null");
		if (to == null) throw new IllegalArgumentException("Currency to is null");
		if (from.getName().equals(to.getName())) throw new IllegalArgumentException("Currencies are identical");
		double scale = from.getConversionScale() / to.getConversionScale();

		return fromAmount * scale;
	}

	/**
	 * Attempts to look up a Currency by its unique id.
	 * @param uid UUID to find
	 * @return Currency found, or null if not found or if UUID is null
	 */
	public static Currency getCurrency(UUID uid) {
		if (uid == null) return null;

		for (Currency econ : Transact.getInstance().getCurrencies()) if (econ.getUniqueId().equals(uid)) return econ;
		return null;
	}

	/**
	 * Fetches an Currency by its unique symbol.
	 * @param symbol Symbol to find
	 * @return Currency found, or null if not found
	 */
	public static Currency getCurrency(char symbol) {
		for (Currency econ : Transact.getInstance().getCurrencies()) if (econ.getSymbol() == symbol) return econ;
		return null;
	}

	@Override
	public String toString() {
		return "Currency{" +
				"symbol=" + symbol +
				", name='" + name + '\'' +
				", conversionScale=" + conversionScale +
				", interestEnabled=" + interestEnabled +
				'}';
	}

	/**
	 * Fetches the Currency's symbol.
	 * @return Symbol of Currency
	 */
	public char getSymbol() {
		return this.symbol;
	}


	public String format(double amount) {
		double roundOff = Math.round(amount * 100.0) / 100.0;
		char symbol = getSymbol();
		return symbol+""+roundOff;
	}
}

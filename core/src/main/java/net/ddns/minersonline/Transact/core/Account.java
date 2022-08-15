package net.ddns.minersonline.Transact.core;

import java.util.UUID;

public class Account {
	public Balance balance = new Balance();
	public UUID id;

	public Account(UUID id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Account{" +
				"id=" + id +
				'}';
	}
}

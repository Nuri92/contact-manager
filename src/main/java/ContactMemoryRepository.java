package main.java;

import java.util.ArrayList;
import java.util.List;

public class ContactMemoryRepository implements ContactRepository {
	private final List<Contact> contacts;
	
	public ContactMemoryRepository() {
		this.contacts = new ArrayList<>();
	}
	
	@Override
	public void save(Contact contact) {
	this.contacts.add(contact);
	}
	
	@Override
	public List<Contact> findAll() {
		return new ArrayList<>(contacts);
	}
	
	@Override
	public void delete(Contact contact) {
	this.contacts.remove(contact);
	}
}


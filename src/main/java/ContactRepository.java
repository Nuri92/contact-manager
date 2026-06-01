package main.java;

import java.util.List;

public interface ContactRepository {
	void save(Contact contact);
	List<Contact> findAll();
	void delete(Contact contact);
}

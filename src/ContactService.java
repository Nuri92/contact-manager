import java.util.ArrayList;
import java.util.List;

public class ContactService {
	private final ContactRepository repository;
	private       int                nextId;
	
	public ContactService(ContactRepository repository) {
		this.repository = repository;
		this.nextId   = 1;
	}
	
	public Contact addContact(String name, String email, String phoneNumber) {
		Contact contact = new Contact(nextId, name, email, phoneNumber);
		repository.save(contact);
		nextId++;
		return contact;
	}
	
	public List<Contact> getContacts() {
		return repository.findAll();
	}
	
	public Contact findById(int id) {
		
		for (Contact currentContact : repository.findAll()) {
			if (currentContact.getId() == id) {
				return currentContact;
			}
		}
		return null;
	}
	
	public Contact markAsFavorite(int id) {
		Contact contact = getExistingContact(id);
		contact.markAsFavorite();
		return contact;
	}
	
	public Contact removeContact(int id) {
		Contact contact = getExistingContact(id);
		repository.delete(contact);
		return contact;
	}
	
	public List<Contact> searchContact(String name) {
		List<Contact> matchedContacts = new ArrayList<>();
		for (Contact currentContact : repository.findAll()) {
			if (currentContact.getName().toLowerCase().contains(name.toLowerCase())) {
				matchedContacts.add(currentContact);
			}
		}
		return matchedContacts;
	}
	
	public Contact updateContact(int id, String name, String email, String phoneNumber) {
		Contact contact = getExistingContact(id);
		contact.update(name, email, phoneNumber);
		return contact;
	}
	
	private Contact getExistingContact(int id) {
		Contact contact = findById(id);
		if (contact == null) {
			throw new IllegalArgumentException("Kontakt mit ID " + id + " nicht gefunden.");
		}
		return contact;
	}
}

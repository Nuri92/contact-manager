import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.*;

public class ContactServiceTest {
	
	@Test
	void schouldAddContact() {
		ContactRepository repository = new ContactMemoryRepository();
		ContactService    service    = new ContactService(repository);
		
		Contact contact = service.addContact("Nuri", "nuri@hotmail.de", "01762387304");
		
		assertEquals("Nuri", contact.getName());
		assertEquals("nuri@hotmail.de", contact.getEmail());
		assertEquals("01762387304", contact.getPhoneNumber());
		assertFalse(contact.isFavorite());
	}
	
	@Test
	void schouldDeleteContact() {
		ContactRepository repository = new ContactMemoryRepository();
		ContactService    service    = new ContactService(repository);
		
		Contact contact = service.addContact("Nuri", "nuri@hotmail.de", "01762387304");
		
		assertEquals(1, service.getContacts().size());
		service.removeContact(contact.getId());
		assertEquals(0, service.getContacts().size());
		
	}
	
	@Test
	void shouldFindContactById() {
		ContactRepository repository = new ContactMemoryRepository();
		ContactService    service    = new ContactService(repository);
		
		Contact contact = service.addContact("Nuri", "nuri@hotmail.de", "01762387304");
		
		assertEquals("Nuri", service.findById(contact.getId()).getName());
		assertNull(
				
				service.findById(999)
		
		);
		
	}
	
	@Test
	void shouldMarkContactAsFavorite() {
		ContactRepository repository = new ContactMemoryRepository();
		ContactService    service    = new ContactService(repository);
		
		Contact contact = service.addContact("Nuri", "nuri@hotmail.de", "01762387304");
		service.toggleFavorite(contact.getId());
		
		Contact updatedContact =
				service.findById(contact.getId());
		
		assertTrue(updatedContact.isFavorite());
	}
	
	@Test
	void shouldToggleFavoriteTwice() {
		ContactRepository repository = new ContactMemoryRepository();
		ContactService    service    = new ContactService(repository);
		
		Contact contact = service.addContact("Nuri", "nuri@hotmail.de", "01762387304");
		service.toggleFavorite(contact.getId());
		
		Contact updatedContactFirst =
				service.findById(contact.getId());
		
		assertTrue(updatedContactFirst.isFavorite());
		
		service.toggleFavorite(contact.getId());
		
		Contact updatedContactSecond =
				service.findById(contact.getId());
		
		assertFalse(updatedContactSecond.isFavorite());
	}
}
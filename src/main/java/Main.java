import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		ContactRepository repository = new ContactDatabaseRepository();
		ContactService    service    = new ContactService(repository);
		
		Scanner scanner = new Scanner(System.in);
		
		boolean isProgramRunning = true;
		
		while (isProgramRunning) {
			
			System.out.println("Auswahl tätigen: ");
			System.out.println("1 - Kontakt erstellen");
			System.out.println("2 - Alle Kontakte anzeigen");
			System.out.println("3 - Kontakt suchen");
			System.out.println("4 - Kontakt bearbeiten");
			System.out.println("5 - Kontakt löschen");
			System.out.println("6 - Als Favorit markieren");
			System.out.println("0 - Beenden");
			
			int userChoice = readInt(scanner);
			scanner.nextLine();
			
			switch (userChoice) {
				case 1 -> {
					System.out.println("Kontakt erstellen");
					
					System.out.println("Namen des Kontaktes eingeben: ");
					String name = scanner.nextLine();
					
					System.out.println("E-Mail des Kontaktes eingeben: ");
					String email = scanner.nextLine();
					
					System.out.println("Nummer des Kontaktes eingeben: ");
					String phoneNumber = scanner.nextLine();
					
					Contact contact = service.addContact(name, email, phoneNumber);
					System.out.println("Kontakt erstellt mit ID: " + contact.getId() + "\n");
				}
				case 2 -> {
					System.out.println("Alle Kontakte anzeigen");
					List<Contact> contacts = service.getContacts();
					if (contacts.isEmpty()) {
						System.out.println("Keine Kontakte vorhanden.");
					} else {
						for (Contact contact : contacts) {
							printContact(contact);
						}
					}
				}
				case 3 -> {
					System.out.println("Kontakt suchen");
					System.out.println("Namen eingeben: ");
					String        nameToSearch = scanner.nextLine();
					List<Contact> contacts     = service.searchContact(nameToSearch);
					if (contacts.isEmpty()) {
						System.out.println("Keine Kontakte gefunden.");
					} else {
						for (Contact contact : contacts) {
							printContact(contact);
						}
					}
				}
				case 4 -> {
					System.out.println("Kontakt bearbeiten");
					try {
						System.out.println("ID des Kontaktes eingeben: ");
						int id = readInt(scanner);
						scanner.nextLine();
						System.out.println("Neuen Namen eintragen: ");
						String name = scanner.nextLine();
						System.out.println("Neue E-Mail eingeben: ");
						String email = scanner.nextLine();
						System.out.println("Neue Nummer eingeben: ");
						String  phoneNumber = scanner.nextLine();
						Contact contact     = service.updateContact(id, name, email, phoneNumber);
						printContact(contact);
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
					}
				}
				case 5 -> {
					System.out.println("Kontakt löschen");
					try {
						System.out.println("ID des Kontaktes eingeben: ");
						int id = readInt(scanner);
						scanner.nextLine();
						Contact contact = service.removeContact(id);
						System.out.println("Kontakt mit ID: " + contact.getId() + " wurde entfernt.\n");
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
					}
				}
				case 6 -> {
					System.out.println("Kontakt favorisieren");
					try {
						System.out.println("ID des Kontaktes eingeben: ");
						int id = readInt(scanner);
						scanner.nextLine();
						Contact contact = service.markAsFavorite(id);
						System.out.println("Kontakt mit ID: " + contact.getId() + " wurde als Favorit markiert.\n");
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
					}
				}
				case 0 -> {
					System.out.println("Programm beenden");
					isProgramRunning = false;
				}
				default -> System.out.println("Eingabe nicht erkannt. Bitte erneut versuchen,");
			}
		}
		
		scanner.close();
	}
	
	private static void printContact(Contact contact) {
		System.out.println("[ID: " + contact.getId()
				+ "; Name: " + contact.getName()
				+ "; E-mail: " + contact.getEmail()
				+ "; Nummer: " + contact.getPhoneNumber()
				+ "; Favorit: " + contact.isFavorite()
				+ "]\n"
		);
	}
	
	private static int readInt(Scanner scanner) {
		while (true) {
			try {
				int number = scanner.nextInt();
				scanner.nextLine();
				return number;
			} catch (InputMismatchException e) {
				System.out.println("Ungültige Eingabe. Bitte Zahl eingeben,");
				scanner.nextLine();
			}
		}
	}
}

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDatabaseRepository implements ContactRepository {
	
	public ContactDatabaseRepository() {
		try {
			DatabaseConnection.createContactsTable();
		} catch (SQLException e) {
			throw new RuntimeException("Datenbank konnte nicht vorbereitet werden,", e);
		}
	}
	
	@Override
	public void save(Contact contact) {
		String sql = """
		             INSERT INTO contacts (
		                 name,
		                 email,
		                 phone_number,
		                 favorite
		             )
		             VALUES (?, ?, ?, ?)
		             """;
		
		try (Connection connection = DatabaseConnection.getConnection();
		     PreparedStatement statement = connection.prepareStatement(sql);
		) {
			statement.setString(1, contact.getName());
			statement.setString(2, contact.getEmail());
			statement.setString(3, contact.getPhoneNumber());
			statement.setInt(4, contact.isFavorite() ? 1 : 0);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Kontakte konnte nicht gespeichert werden.", e);
		}
	}
	
	@Override
	public List<Contact> findAll() {
		List<Contact> contacts = new ArrayList<>();
		String        sql      = "SELECT * FROM contacts";
		
		try (
				Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery();
		) {
			while (resultSet.next()) {
				int     id          = resultSet.getInt("id");
				String  name        = resultSet.getString("name");
				String  email       = resultSet.getString("email");
				String  phoneNumber = resultSet.getString("phone_number");
				boolean favorite    = resultSet.getInt("favorite") == 1;
				
				Contact contact = new Contact(id, name, email, phoneNumber, favorite);
				
				contacts.add(contact);
			}
			return contacts;
		} catch (SQLException e) {
			throw new RuntimeException("Kontakte konnten nicht geladen werden.", e);
		}
	}
	
	@Override
	public void delete(Contact contact) {
		String sql = "DELETE FROM contacts WHERE id = ?";
		
		try (
				Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)
		) {
			statement.setInt(1, contact.getId());
			
			int affectedRows = statement.executeUpdate();
			
			if (affectedRows == 0) {
				System.out.println("Kein Kontakt mit ID " + contact.getId() + " gefunden.");
				return;
			}
			
			System.out.println("Kontakt mit ID " + contact.getId() + " wurde erfolgreich gelöscht.");
			
		} catch (SQLException e) {
			throw new RuntimeException(
					"Kontakt konnte nicht gelöscht werden.",
					e
			);
		}
	}
	
	@Override
	public void update(Contact contact) {
		String sql = """
		             UPDATE contacts
		             SET
		              name = ?,
		              email = ?,
		              phone_number = ?
		             WHERE
		             id = ?
		             """;
		
		try (
				Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
		) {
			statement.setString(1, contact.getName());
			statement.setString(2, contact.getEmail());
			statement.setString(3, contact.getPhoneNumber());
			statement.setInt(4, contact.getId());
			
			int affectedRows = statement.executeUpdate();
			
			if (affectedRows == 0) {
				System.out.println("Kein Kontakt mit ID " + contact.getId() + " gefunden.");
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("Kontakt konnte nicht aktualisiert werden.", e);
		}
	}
	
	@Override
	public void setFavorite(int id, boolean favorite) {
		String sql = """
		             UPDATE contacts
		             SET favorite = ?
		             WHERE id = ?
		             """;
		
		try (
				Connection connection = DatabaseConnection.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql);
		) {
			statement.setInt(1, favorite ? 1 : 0);
			statement.setInt(2, id);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("Keinen Kontakt gefunden mit ID " + id, e);
		}
	}
}

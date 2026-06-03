import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:sqlite:contacts.db");
	}
	
	public static void createContactsTable() throws SQLException {
		Connection connection = getConnection();
		Statement statement = connection.createStatement();
		
		String sql = "CREATE TABLE IF NOT EXISTS contacts" +
				"(id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"name TEXT NOT NULL," +
				"email TEXT NOT NULL," +
				"phone_number TEXT NOT NULL," +
				"favorite INTEGER NOT NULL)";
		
		statement.execute(sql);
		
		statement.close();
		connection.close();
	}
}
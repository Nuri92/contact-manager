public class Contact {
	
	private int     id;
	private String  name;
	private String  email;
	private String  phoneNumber;
	private boolean favorite;
	
	
	public Contact(String name, String email, String phoneNumber) {
		this.name        = name;
		this.email       = email;
		this.phoneNumber = phoneNumber;
		this.favorite    = false;
	}
	
	public Contact(int id, String name, String email, String phoneNumber, boolean favorite) {
		this.id          = id;
		this.name        = name;
		this.email       = email;
		this.phoneNumber = phoneNumber;
		this.favorite    = favorite;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public boolean isFavorite() {
		return favorite;
	}
	
	public void markAsFavorite() {
		this.favorite = true;
	}
	
	public void update(String name, String email, String phoneNumber) {
		this.name        = name;
		this.email       = email;
		this.phoneNumber = phoneNumber;
	}
}

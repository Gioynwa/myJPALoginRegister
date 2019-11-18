package hibernate;

public interface CustomerDAO {
	
	public int insertCustomer(Customer c);
	public Customer getCustomer(String username, String pass);
	public boolean check(String userid, String pass);
}

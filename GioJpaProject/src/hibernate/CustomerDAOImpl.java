package hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import java.util.List;


import hibernate.Customer;


public class CustomerDAOImpl implements CustomerDAO {
	
	public int insertCustomer(Customer c) throws HibernateException {
		
		int status = 0;
		try {
			
			Configuration configuration = new Configuration().configure();
			
			SessionFactory sessionFactory = configuration.buildSessionFactory();
			
			Session session = sessionFactory.openSession();
			
			Transaction transaction = session.beginTransaction();
			
			session.save(c);
			
            transaction.commit();
            
            session.close();
            
    		sessionFactory.close();
			
		}catch(HibernateException e) {
			System.out.println(e);
		}
		
		return status;
	}

	@Override
	public Customer getCustomer(String userid, String pass) throws HibernateException {
		
		
		Customer c= new Customer();
		try {		
			
			Configuration configuration = new Configuration().configure();
			
			SessionFactory sessionFactory = configuration.buildSessionFactory();
			
			Session session = sessionFactory.openSession();
			
			String sql = "SELECT * FROM new_customer WHERE customer = :userid and password = :pass";
			
			NativeQuery query = session.createNativeQuery(sql);
			
	        query.setParameter("userid", userid);
	       
	        query.setParameter("pass", pass);
	        
	        List result = query.list();
	        
	        if(result.isEmpty()) {
	        	session.close();
				sessionFactory.close();
				c = null;
				return c;
	        }
	        
		}catch(HibernateException e) {
			System.out.println(e);
			System.out.println("dao");
		}
		
		c.setUsername(userid);
		c.setPassword(pass);
		return c;
	}
	
	public boolean check(String userid, String pass) {
		
		try {		
			Configuration configuration = new Configuration().configure();
			
			SessionFactory sessionFactory = configuration.buildSessionFactory();
			
			Session session = sessionFactory.openSession();
			
			String sql = "SELECT * FROM new_customer WHERE customer = :userid";
			
			NativeQuery query = session.createNativeQuery(sql);
			
	        query.setParameter("userid", userid);
	        
	        List result = query.list();
	        
	        if(result.isEmpty()) {
	        	session.close();
				sessionFactory.close();
				return false;
	        }
	        
		}catch(HibernateException e) {
			System.out.println(e);
			System.out.println("dao");
		}
		
		return true;
	}

}

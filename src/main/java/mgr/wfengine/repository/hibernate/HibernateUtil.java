package mgr.wfengine.repository.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory_wfengine = buildSessionFactory("wfengine.cfg.xml");
	private static final SessionFactory sessionFactory_wfportallet = buildSessionFactory("wfportallet.cfg.xml");

    private static SessionFactory buildSessionFactory(String conffilePath) {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
        	Configuration configuration = new Configuration().configure(conffilePath);
//            return configuration.buildSessionFactory(
//			    new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build() );
            return configuration.buildSessionFactory(
			    new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(String database) {
    	if ("wfengine".endsWith(database)) {
    		return sessionFactory_wfengine;
    	} else {
    		return sessionFactory_wfportallet;
    	}
        
    }
}

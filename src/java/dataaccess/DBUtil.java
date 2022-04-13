package dataaccess;

import jakarta.persistence.*;
/**
 * utility class for interacting with entity manager factory for the persistence unit
 * "ECSSENProPU"
 * @author 840979
 */
public class DBUtil
{
    private static final
    EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("ECSSENProPU");
    public static EntityManagerFactory getEMFactory()
    {
        return emFactory;
    }
}

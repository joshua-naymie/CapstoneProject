package dataaccess;

import jakarta.persistence.*;

public class DBUtil
{
    private static final
    EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("ECSSENProPU");
    public static EntityManagerFactory getEMFactory()
    {
        return emFactory;
    }
}

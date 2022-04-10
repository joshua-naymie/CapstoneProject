/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import models.User;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import java.util.ArrayList;
import models.ProgramTraining;
import models.Role;

/**
 *
 * @author DWEI
 */
public class UserDB {

    // get and return a user with matching PK ID
    // get for getting a specific user
    public User getByID(int ID) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
            List<User> users = getAll();
            for (User user : users) {
                if (user.getUserId() == ID) {
                    return user;
                }
            }
            return null;
        } finally {
            em.close();
        }
    }

    // get user by full name
    public User getUserByFullName(String firstName, String lastName) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        Query q = em.createQuery("SELECT u FROM User u WHERE u.firstName LIKE :firstName AND u.lastName LIKE :lastName", User.class);
        q.setParameter("firstName", firstName);
        q.setParameter("lastName", lastName);
        try {
            User foundUser = (User) q.getSingleResult();
            return foundUser;
        } finally {
            em.close();
        }
    }

    public List<User> getUsersByFullName(String firstName, String lastName) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        Query q = em.createQuery("SELECT u FROM User u WHERE u.firstName LIKE :firstName OR u.lastName LIKE :lastName", User.class);
        q.setParameter("firstName", firstName);
        q.setParameter("lastName", lastName);
        try {
            List<User> foundUser = q.getResultList();
            return foundUser;
        } finally {
            em.close();
        }
    }

    // getAll active users only
    public List<User> getAllActive() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
            Query q = em.createQuery("SELECT u FROM User u WHERE u.isActive = :isActive", User.class);
            q.setParameter("isActive", true);
            List<User> allUsers = q.getResultList();
            return allUsers;
        } finally {
            em.close();
        }
    }

    // getAll active supervisors or managers base on role name
    public List<User> getAllActiveSupervisorsOrManagers(String roleName) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();

        RoleDB rdb = new RoleDB();
        Role r = rdb.getByRoleName(roleName);
        //Short roleId = r.getRoleId();

        List<ProgramTraining> allUsers = null;
        List<User> allSupervisors = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT p FROM ProgramTraining p WHERE p.roleId = :roleId", ProgramTraining.class);
            //q.setParameter("programId",1);
            q.setParameter("roleId", r);
            allUsers = q.getResultList();
        } catch (NoResultException e) {
            return null;
        }

        for (ProgramTraining u : allUsers) {
            //EntityManager ema = DBUtil.getEMFactory().createEntityManager();
            try {
                Query q;
                q = em.createQuery("SELECT u FROM User u WHERE  u.isActive =:isActive AND u.userId=:userId ORDER BY u.lastName, u.firstName", User.class);
                q.setParameter("isActive", true);
                q.setParameter("userId", u.getUser().getUserId());

                User ur = (User) q.getSingleResult();

                if (ur != null) {
                    allSupervisors.add(ur);
                }
            } catch (NoResultException e) {
                return null;
            }
        }
        em.close();
        return allSupervisors;
    }

    // getAll active supervisors only
    public List<User> getAllActiveSupervisorsByProgram(Short programId) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();

        RoleDB rdb = new RoleDB();
        Role r = rdb.getByRoleName("Supervisor");
        //Short roleId = r.getRoleId();

        List<ProgramTraining> allUsers = null;
        List<User> allSupervisors = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT p FROM ProgramTraining p WHERE p.roleId = :roleId AND p.programTrainingPK.programId = :programId", ProgramTraining.class);
            //q.setParameter("programId",1);
            q.setParameter("roleId", r);
            q.setParameter("programId", programId);
            allUsers = q.getResultList();
        } finally {
            //em.close();
        }

        for (ProgramTraining u : allUsers) {
            //EntityManager ema = DBUtil.getEMFactory().createEntityManager();
            try {
                Query q;
                q = em.createQuery("SELECT u FROM User u WHERE  u.isActive =:isActive AND u.userId=:userId ORDER BY u.lastName, u.firstName", User.class);
                q.setParameter("isActive", true);
                q.setParameter("userId", u.getUser().getUserId());

                User ur = (User) q.getSingleResult();

                if (ur != null) {
                    allSupervisors.add(ur);
                }
            } finally {
                //em.close();
            }
        }
        em.close();
        return allSupervisors;
    }

    public List<User> getAllActiveHotlineCoordinators() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();

        RoleDB rdb = new RoleDB();
        Role r = rdb.getByRoleName("Coordinator");
        //Short roleId = r.getRoleId();

        List<ProgramTraining> allUsers = null;
        List<User> allCoordinators = new ArrayList<>();
        try {
            Query q = em.createQuery("SELECT p FROM ProgramTraining p WHERE p.roleId = :roleId AND p.programTrainingPK.programId = :programId", ProgramTraining.class);
            //q.setParameter("programId",1);
            q.setParameter("roleId", r);
            q.setParameter("programId", 2);
            allUsers = q.getResultList();
        } finally {
            //em.close();
        }

        for (ProgramTraining u : allUsers) {
            //EntityManager ema = DBUtil.getEMFactory().createEntityManager();
            try {
                Query q;
                q = em.createQuery("SELECT u FROM User u WHERE  u.isActive =:isActive AND u.userId=:userId ORDER BY u.lastName, u.firstName", User.class);
                q.setParameter("isActive", true);
                q.setParameter("userId", u.getUser().getUserId());

                User ur = (User) q.getSingleResult();

                if (ur != null) {
                    allCoordinators.add(ur);
                }
            } finally {
                //em.close();
            }
        }
        em.close();
        return allCoordinators;
    }

    // irina
    // getAll for getting all users
    public List<User> getAll() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
//            List<User> allUsers = em.createNamedQuery("User.findAll", User.class).getResultList();
            Query q = em.createQuery("SELECT u FROM User u ORDER BY u.lastName, u.firstName", User.class);
            List<User> allUsers = q.getResultList();
            return allUsers;
        } finally {
            em.close();
        }
    }

    //irina
    // get for getting a specific user
    public User get(String email) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
            List<User> users = getAll();
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    return user;
                }
            }
            return null;
        } finally {
            em.close();
        }
    }

    public List<User> getUserByLastName(String lastName) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        Query q = em.createQuery("SELECT u FROM User u WHERE u.lastName LIKE :keyword ORDER BY u.lastName, u.firstName", User.class);
        q.setParameter("keyword", lastName + "%");
        try {
            List<User> allUsers = q.getResultList();
            return allUsers;
        } finally {
            em.close();
        }
    }

    //agambeer
    // Insert for creating users
    public void insert(User user) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(user);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    //agambeer
    // update for editing users
    public void update(User user) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public User getByUUID(String uuid) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();

        try {
            Query q = em.createNamedQuery("User.findByResetPasswordUuid");
            q.setParameter("resetPasswordUuid", uuid);
            return (User) q.getSingleResult();
        } finally {
            em.close();
        }
    }

    //get all active users for hotline program 
    public List<User> getActiveHotline() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        List<User> allActiveHotlineUsers = new ArrayList<>();
        List<ProgramTraining> allUsers = null;
        try {
            Query q = em.createQuery("SELECT p FROM ProgramTraining p WHERE p.programId = :programId", ProgramTraining.class);
            q.setParameter("programId", 2);
            allUsers = q.getResultList();
        } finally {
        }
        for (ProgramTraining u : allUsers) {
            try {
                Query q;
                q = em.createQuery("SELECT u FROM User u WHERE  u.isActive =:isActive AND u.userId=:userId ORDER BY u.lastName, u.firstName", User.class);
                q.setParameter("isActive", true);
                q.setParameter("userId", u.getUser().getUserId());
                User ur = (User) q.getSingleResult();
                if (ur != null) {
                    allActiveHotlineUsers.add(ur);
                }
            } finally {
            }
        }
        em.close();
        return allActiveHotlineUsers;
    }

}

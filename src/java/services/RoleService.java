/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.RoleDB;
import java.util.List;
import models.Role;

/**
 *class to perform some crud operations on Role table
 * @author 840979
 */
public class RoleService {

    
    /**
     * method to retrieve all existing records in Role table
     * @return list of Role objects
     * @throws Exception 
     */
    public List<Role> getAll() throws Exception {

        RoleDB roleDB = new RoleDB();
        List<Role> roles = roleDB.getAll();
        return roles;

    }
/**
 * method to retrieve specific record from Role table
 * @param roleId
 * @return Role object
 * @throws Exception 
 */
    public Role get(short roleId) throws Exception {
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.get(roleId);
        return role;

    }

    /**
     * method to persist new record into Role table
     * @param roldId
     * @param roleName
     * @param roleDescription
     * @return string msg
     * @throws Exception 
     */
    public String insert(short roldId, String roleName, String roleDescription) throws Exception {
        RoleDB roleDB = new RoleDB();
        Role checkRole = roleDB.getByRoleName(roleName);

        if (checkRole != null) {
            return "This role already exists";
        }
        Role newRole = new Role(roldId, roleDescription);
        roleDB.insert(newRole);

        return "Role " + roleName + " has been created";
    }

    /**
     * method to update existing record in Role table
     * @param roleId
     * @param roleName
     * @param roleDescription
     * @return string msg
     * @throws Exception 
     */
    public String update(short roleId, String roleName, String roleDescription) throws Exception {
        RoleDB roleDB = new RoleDB();
        Role roleToUpdate = roleDB.get(roleId);

        if (roleToUpdate == null) {
            return "This role does not exist";
        }
        roleToUpdate.setRoleDescription(roleDescription);
        roleToUpdate.setRoleName(roleName);
        roleDB.update(roleToUpdate);

        return "Role has been updated";
    }
}

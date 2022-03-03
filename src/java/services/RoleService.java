/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.RoleDB;
import java.util.List;
import models.Role;

/**
 *
 * @author 840979
 */
public class RoleService {

    public List<Role> getAll() throws Exception {

        RoleDB roleDB = new RoleDB();
        List<Role> roles = roleDB.getAll();
        return roles;

    }

    public Role get(short roleId) throws Exception {
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.get(roleId);
        return role;

    }

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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.ProgramDB;
import dataaccess.ProgramTrainingDB;
import dataaccess.UserDB;
import java.util.List;
import models.Program;
import models.ProgramTraining;
import models.ProgramTrainingPK;
import models.Role;
import models.Team;
import models.User;

/**
 *class to perform crud operations on Program and ProgramTraining Tables
 * @author 840979
 */
public class ProgramServices {
    
    /**
     * method to retrieve a record from ProgramTraining table by user id and program id
     * @param userId 
     * @param programId
     * @return ProgramTraining object matching user id and program id
     * @throws Exception 
     */
    public ProgramTraining getUserRoleIdFromProgramTraining(int userId, short programId) throws Exception {
        ProgramTrainingDB ptDB = new ProgramTrainingDB();
        ProgramTraining userRole = ptDB.getProgramTraining(userId, programId);
        return userRole;
    }
    
    /**
     * method to persist new record in ProgramTraining with User's id, role id and team id
     * @param teamId
     * @param roleId
     * @param userId
     * @throws Exception 
     */
    public void insertProgramTrainingUserCreation(int teamId, short roleId, int userId) throws Exception{
        ProgramTraining newUser = new ProgramTraining();
        ProgramTrainingPK newUserPK = new ProgramTrainingPK();
        
        if (teamId != 0 && roleId != 0 && userId != 0){
            
            // program for program training object
            TeamServices ts = new TeamServices();
            Team foundTeam = ts.get(teamId);
            ProgramServices ps = new ProgramServices();
            Program foundProgram = ps.get(foundTeam.getProgramId().getProgramId());
            
            // role object
            RoleService rs = new RoleService();
            Role newRole = rs.get(roleId);
            
            // the newly created user
            AccountServices as = new AccountServices();
            User foundUser = as.getByID(userId);
            
            newUser.setProgram(foundProgram);
            newUser.setRoleId(newRole);
            newUser.setUser(foundUser);
            
            // setting program training pk object
            newUserPK.setProgramId(foundProgram.getProgramId());
            newUserPK.setUserId(userId);
            newUser.setProgramTrainingPK(newUserPK);
        }
        ProgramDB progDB = new ProgramDB();
        progDB.insertProgramTraining(newUser);
    }
/**
 * method to update existing ProgramTraining record 
 * @param teamId
 * @param roleId
 * @param userId
 * @throws Exception 
 */ 
    public void updateProgramTrainingUserCreation(int teamId, short roleId, int userId) throws Exception{
        ProgramTraining oldUser = new ProgramTraining();
        ProgramTrainingPK newUserPK = new ProgramTrainingPK();
        
        if (teamId != 0 && roleId != 0 && userId != 0){
            
            // program to find program Id
            TeamServices ts = new TeamServices();
            Team foundTeam = ts.get(teamId);
            ProgramServices ps = new ProgramServices();
            Program foundProgram = ps.get(foundTeam.getProgramId().getProgramId());
            
            // finding existing programTraining object
            oldUser = getUserRoleIdFromProgramTraining(userId, foundProgram.getProgramId());
            
            // new set role
            RoleService rs = new RoleService();
            Role newRole = rs.get(roleId);
            
            // current user
            AccountServices as = new AccountServices();
            User foundUser = as.getByID(userId);
            
            oldUser.setProgram(foundProgram);
            oldUser.setRoleId(newRole);
            oldUser.setUser(foundUser);
            
            // setting program training pk object
            newUserPK.setProgramId(foundProgram.getProgramId());
            newUserPK.setUserId(userId);
            oldUser.setProgramTrainingPK(newUserPK);
        }
        ProgramDB progDB = new ProgramDB();
        progDB.updateProgramTraining(oldUser);
    }
    /**
     * method to insert new ProgramTraining record 
     * @param programTraining object
     * @throws Exception 
     */
    public void insertProgramTraining(ProgramTraining programTraining) throws Exception{
        ProgramDB progDB = new ProgramDB();
        progDB.insertProgramTraining(programTraining);
    }
    /**
     * method to update existing ProgramTraining record
     * @param programTraining
     * @throws Exception 
     */
    public void updateProgramTraining(ProgramTraining programTraining) throws Exception{
        ProgramDB progDB = new ProgramDB();
        progDB.updateProgramTraining(programTraining);
    }
    
    /**
     * method to retrieve programId field of ProgramTraining record by using program name
     * @param programName
     * @return short program id
     * @throws Exception 
     */
    public short getProgramId(String programName)throws Exception{
        ProgramDB progDB = new ProgramDB();
        short programId = progDB.getProgramId(programName);
        return programId;
    }
/**
 * method to retrieve all existing records from Program table 
 * @return List of Program objects 
 * @throws Exception 
 */
    public List<Program> getAll() throws Exception {
        ProgramDB progDB = new ProgramDB();
        List<Program> programs = progDB.getAll();
        return programs;

    }
/**
 * retrieve specific record from Program table using primary key
 * @param progId program id
 * @return Program object that matches program id
 * @throws Exception 
 */
    public Program get(short progId) throws Exception {
        ProgramDB progDB = new ProgramDB();
        Program program = progDB.get(progId);
        return program;

    }
/**
 * persist new record into program table
 * @param isActive
 * @param programName
 * @param userId
 * @return string msg
 * @throws Exception 
 */
    public String insert(boolean isActive, String programName, int userId) throws Exception {
        ProgramDB progDB = new ProgramDB();
        Program checkProgram = progDB.getByProgramName(programName);
        if (checkProgram != null) {
            return "This program already exists";
        }
        UserDB findUser = new UserDB();
        User existingUser = findUser.getByID(userId);
        Program newProgram = new Program(existingUser, programName, isActive);

        progDB.insert(newProgram);

        return "Program " + programName + " has been created";

    }
/**
 * update current record in Program table 
 * @param programId
 * @param isActive
 * @param programName
 * @param user
 * @return string msg
 * @throws Exception 
 */
    public String update(short programId, boolean isActive, String programName, User user) throws Exception {
        ProgramDB progDB = new ProgramDB();
        Program toUpdate = progDB.get(programId);

        if (toUpdate == null) {
            return "Program does exist";
        }
        toUpdate.setUserId(user);
        toUpdate.setProgramName(programName);
        toUpdate.setIsActive(isActive);
        progDB.update(toUpdate);
        return "Program has been updated";
    }
/**
 * retrieve all ids from Program table
 * @return list of shord ids 
 * @throws Exception 
 */    
    public List<Short> getAllIDs() throws Exception
    {
        ProgramDB programDB = new ProgramDB();
        return programDB.getAllIDs();
    }
    
    public List<Program> getAllActive() throws Exception
    {
        ProgramDB programDB = new ProgramDB();
        return programDB.getAllActive();
    }

}

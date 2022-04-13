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
 *
 * @author 840979
 */
public class ProgramServices {
    
    public ProgramTraining getUserRoleIdFromProgramTraining(int userId, short programId) throws Exception {
        ProgramTrainingDB ptDB = new ProgramTrainingDB();
        ProgramTraining userRole = ptDB.getProgramTraining(userId, programId);
        return userRole;
    }
    
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
    
    public void insertProgramTraining(ProgramTraining programTraining) throws Exception{
        ProgramDB progDB = new ProgramDB();
        progDB.insertProgramTraining(programTraining);
    }
    
    public void updateProgramTraining(ProgramTraining programTraining) throws Exception{
        ProgramDB progDB = new ProgramDB();
        progDB.updateProgramTraining(programTraining);
    }
    
    public short getProgramId(String programName)throws Exception{
        ProgramDB progDB = new ProgramDB();
        short programId = progDB.getProgramId(programName);
        return programId;
    }

    public List<Program> getAll() throws Exception {
        ProgramDB progDB = new ProgramDB();
        List<Program> programs = progDB.getAll();
        return programs;

    }

    public Program get(short progId) throws Exception {
        ProgramDB progDB = new ProgramDB();
        Program program = progDB.get(progId);
        return program;

    }

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

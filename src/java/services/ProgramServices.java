/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.ProgramDB;
import dataaccess.UserDB;
import java.util.List;
import models.Program;
import models.ProgramTraining;
import models.User;

/**
 *
 * @author 840979
 */
public class ProgramServices {
    
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

    public String insert(boolean isActive, String programName, long userId) throws Exception {
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
//        toUpdate.setUser(user);
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
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.ProgramDB;
import java.util.List;
import models.Program;

/**
 *
 * @author 840979
 */
public class ProgramServices {



  public List<Program> getAll() throws Exception {
        ProgramDB progDB = new ProgramDB();
        List<Program> programs = progDB.getAll();
        return programs;
    
}


public Program get (short progId)throws Exception{
        ProgramDB progDB = new ProgramDB();
        Program program = progDB.get(progId);
        return program;


}

public String insert (Short programId, boolean isActive, String programName, String managerName) throws Exception{
         ProgramDB progDB = new ProgramDB();
         Program checkProgram = progDB.get(programId);
                if (checkProgram != null){
                return "This program already exists";
}

        Program newProgram = new Program (programId, isActive,programName, managerName);

        progDB.insert(newProgram);

        return "Program " + programName + " has been created";

}

public String update (short programId, String programName, String managerName) throws Exception{
        ProgramDB progDB = new ProgramDB();
        Program toUpdate = progDB.get(programId);

            if (toUpdate == null){
        return "Program does exist";
}
        toUpdate.setManagerName(managerName);
        toUpdate.setProgramName(programName);
        progDB.update(toUpdate);
        return "Program has been updated";
}
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Main
 */
@Entity
@Table(name = "program")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Program.findAll", query = "SELECT p FROM Program p"),
    @NamedQuery(name = "Program.findByProgramId", query = "SELECT p FROM Program p WHERE p.programId = :programId"),
    @NamedQuery(name = "Program.findByProgramName", query = "SELECT p FROM Program p WHERE p.programName = :programName"),
    @NamedQuery(name = "Program.findByManagerName", query = "SELECT p FROM Program p WHERE p.managerName = :managerName"),
    @NamedQuery(name = "Program.findByIsActive", query = "SELECT p FROM Program p WHERE p.isActive = :isActive")})
public class Program implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "program_id")
    private Short programId;
    @Column(name = "program_name")
    private String programName;
    @Column(name = "manager_name")
    private String managerName;
    @Basic(optional = false)
    @Column(name = "is_active")
    private boolean isActive;
    @OneToMany(mappedBy = "programId", fetch = FetchType.EAGER)
    private List<Team> teamList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "program", fetch = FetchType.EAGER)
    private List<ProgramTraining> programTrainingList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programId", fetch = FetchType.EAGER)
    private List<Task> taskList;

    public Program() {
    }

    public Program(Short programId) {
        this.programId = programId;
    }

    public Program(Short programId, boolean isActive) {
        this.programId = programId;
        this.isActive = isActive;
    }

    public Short getProgramId() {
        return programId;
    }

    public void setProgramId(Short programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @XmlTransient
    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    @XmlTransient
    public List<ProgramTraining> getProgramTrainingList() {
        return programTrainingList;
    }

    public void setProgramTrainingList(List<ProgramTraining> programTrainingList) {
        this.programTrainingList = programTrainingList;
    }

    @XmlTransient
    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (programId != null ? programId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Program)) {
            return false;
        }
        Program other = (Program) object;
        if ((this.programId == null && other.programId != null) || (this.programId != null && !this.programId.equals(other.programId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Program[ programId=" + programId + " ]";
    }
    
}

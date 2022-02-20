/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Main
 */
@Entity
@Table(name = "program_training")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProgramTraining.findAll", query = "SELECT p FROM ProgramTraining p"),
    @NamedQuery(name = "ProgramTraining.findByUserId", query = "SELECT p FROM ProgramTraining p WHERE p.programTrainingPK.userId = :userId"),
    @NamedQuery(name = "ProgramTraining.findByRoleId", query = "SELECT p FROM ProgramTraining p WHERE p.programTrainingPK.roleId = :roleId"),
    @NamedQuery(name = "ProgramTraining.findByProgramId", query = "SELECT p FROM ProgramTraining p WHERE p.programTrainingPK.programId = :programId")})
public class ProgramTraining implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProgramTrainingPK programTrainingPK;
    @JoinColumn(name = "program_id", referencedColumnName = "program_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Program program;
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Role role;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User user;

    public ProgramTraining() {
    }

    public ProgramTraining(ProgramTrainingPK programTrainingPK) {
        this.programTrainingPK = programTrainingPK;
    }

    public ProgramTraining(int userId, short roleId, short programId) {
        this.programTrainingPK = new ProgramTrainingPK(userId, roleId, programId);
    }

    public ProgramTrainingPK getProgramTrainingPK() {
        return programTrainingPK;
    }

    public void setProgramTrainingPK(ProgramTrainingPK programTrainingPK) {
        this.programTrainingPK = programTrainingPK;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (programTrainingPK != null ? programTrainingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProgramTraining)) {
            return false;
        }
        ProgramTraining other = (ProgramTraining) object;
        if ((this.programTrainingPK == null && other.programTrainingPK != null) || (this.programTrainingPK != null && !this.programTrainingPK.equals(other.programTrainingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.ProgramTraining[ programTrainingPK=" + programTrainingPK + " ]";
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 *
 * @author Main
 */
@Embeddable
public class ProgramTrainingPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "user_id")
    private int userId;
    @Basic(optional = false)
    @Column(name = "program_id")
    private short programId;

    public ProgramTrainingPK() {
    }

    public ProgramTrainingPK(int userId, short programId) {
        this.userId = userId;
        this.programId = programId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public short getProgramId() {
        return programId;
    }

    public void setProgramId(short programId) {
        this.programId = programId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (int) programId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProgramTrainingPK)) {
            return false;
        }
        ProgramTrainingPK other = (ProgramTrainingPK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.programId != other.programId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.ProgramTrainingPK[ userId=" + userId + ", programId=" + programId + " ]";
    }
    
}

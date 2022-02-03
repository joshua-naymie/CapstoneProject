/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Main
 */
@Embeddable
public class ProgramTrainingPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "user_id")
    private String userId;
    @Basic(optional = false)
    @Column(name = "role_id")
    private short roleId;
    @Basic(optional = false)
    @Column(name = "program_id")
    private short programId;

    public ProgramTrainingPK() {
    }

    public ProgramTrainingPK(String userId, short roleId, short programId) {
        this.userId = userId;
        this.roleId = roleId;
        this.programId = programId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public short getRoleId() {
        return roleId;
    }

    public void setRoleId(short roleId) {
        this.roleId = roleId;
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
        hash += (userId != null ? userId.hashCode() : 0);
        hash += (int) roleId;
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
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        if (this.roleId != other.roleId) {
            return false;
        }
        if (this.programId != other.programId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dataaccess.ProgramTrainingPK[ userId=" + userId + ", roleId=" + roleId + ", programId=" + programId + " ]";
    }
    
}
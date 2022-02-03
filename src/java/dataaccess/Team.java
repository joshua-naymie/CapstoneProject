/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Main
 */
@Entity
@Table(name = "team")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t"),
    @NamedQuery(name = "Team.findByTeamId", query = "SELECT t FROM Team t WHERE t.teamId = :teamId"),
    @NamedQuery(name = "Team.findByTeamSize", query = "SELECT t FROM Team t WHERE t.teamSize = :teamSize"),
    @NamedQuery(name = "Team.findByTeamSupervisor", query = "SELECT t FROM Team t WHERE t.teamSupervisor = :teamSupervisor")})
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "team_id")
    private Integer teamId;
    @Column(name = "team_size")
    private Short teamSize;
    @Column(name = "team_supervisor")
    private String teamSupervisor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teamId", fetch = FetchType.EAGER)
    private List<Task> taskList;
    @JoinColumn(name = "program_id", referencedColumnName = "program_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Program programId;
    @JoinColumn(name = "store_id", referencedColumnName = "store_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Store storeId;
    @OneToMany(mappedBy = "teamId", fetch = FetchType.EAGER)
    private List<User> userList;

    public Team() {
    }

    public Team(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Short getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(Short teamSize) {
        this.teamSize = teamSize;
    }

    public String getTeamSupervisor() {
        return teamSupervisor;
    }

    public void setTeamSupervisor(String teamSupervisor) {
        this.teamSupervisor = teamSupervisor;
    }

    @XmlTransient
    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public Program getProgramId() {
        return programId;
    }

    public void setProgramId(Program programId) {
        this.programId = programId;
    }

    public Store getStoreId() {
        return storeId;
    }

    public void setStoreId(Store storeId) {
        this.storeId = storeId;
    }

    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teamId != null ? teamId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Team)) {
            return false;
        }
        Team other = (Team) object;
        if ((this.teamId == null && other.teamId != null) || (this.teamId != null && !this.teamId.equals(other.teamId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dataaccess.Team[ teamId=" + teamId + " ]";
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Main
 */
@Entity
@Table(name = "user_task")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserTask.findAll", query = "SELECT u FROM UserTask u"),
    @NamedQuery(name = "UserTask.findByUserId", query = "SELECT u FROM UserTask u WHERE u.userTaskPK.userId = :userId"),
    @NamedQuery(name = "UserTask.findByTaskId", query = "SELECT u FROM UserTask u WHERE u.userTaskPK.taskId = :taskId"),
    @NamedQuery(name = "UserTask.findByIsAssigned", query = "SELECT u FROM UserTask u WHERE u.isAssigned = :isAssigned"),
    @NamedQuery(name = "UserTask.findByIsChosen", query = "SELECT u FROM UserTask u WHERE u.isChosen = :isChosen")})
public class UserTask implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserTaskPK userTaskPK;
    @Column(name = "is_assigned")
    private Boolean isAssigned;
    @Column(name = "is_chosen")
    private Boolean isChosen;
    @JoinColumn(name = "task_id", referencedColumnName = "task_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Task task;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User user;

    public UserTask() {
    }

    public UserTask(UserTaskPK userTaskPK) {
        this.userTaskPK = userTaskPK;
    }

    public UserTask(int userId, long taskId) {
        this.userTaskPK = new UserTaskPK(userId, taskId);
    }

    public UserTaskPK getUserTaskPK() {
        return userTaskPK;
    }

    public void setUserTaskPK(UserTaskPK userTaskPK) {
        this.userTaskPK = userTaskPK;
    }

    public Boolean getIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(Boolean isAssigned) {
        this.isAssigned = isAssigned;
    }

    public Boolean getIsChosen() {
        return isChosen;
    }

    public void setIsChosen(Boolean isChosen) {
        this.isChosen = isChosen;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
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
        hash += (userTaskPK != null ? userTaskPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserTask)) {
            return false;
        }
        UserTask other = (UserTask) object;
        if ((this.userTaskPK == null && other.userTaskPK != null) || (this.userTaskPK != null && !this.userTaskPK.equals(other.userTaskPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.UserTask[ userTaskPK=" + userTaskPK + " ]";
    }
    
}

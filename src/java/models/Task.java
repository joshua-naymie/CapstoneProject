/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Main
 */
@Entity
@Table(name = "task")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t"),
    @NamedQuery(name = "Task.findByTaskId", query = "SELECT t FROM Task t WHERE t.taskId = :taskId"),
    @NamedQuery(name = "Task.findByMaxUsers", query = "SELECT t FROM Task t WHERE t.maxUsers = :maxUsers"),
    @NamedQuery(name = "Task.findByStartTime", query = "SELECT t FROM Task t WHERE t.startTime = :startTime"),
    @NamedQuery(name = "Task.findByEndTime", query = "SELECT t FROM Task t WHERE t.endTime = :endTime"),
    @NamedQuery(name = "Task.findByAvailable", query = "SELECT t FROM Task t WHERE t.available = :available"),
    @NamedQuery(name = "Task.findByNotes", query = "SELECT t FROM Task t WHERE t.notes = :notes"),
    @NamedQuery(name = "Task.findByIsApproved", query = "SELECT t FROM Task t WHERE t.isApproved = :isApproved"),
    @NamedQuery(name = "Task.findByApprovingManager", query = "SELECT t FROM Task t WHERE t.approvingManager = :approvingManager"),
    @NamedQuery(name = "Task.findByTaskDescription", query = "SELECT t FROM Task t WHERE t.taskDescription = :taskDescription"),
    @NamedQuery(name = "Task.findByTaskCity", query = "SELECT t FROM Task t WHERE t.taskCity = :taskCity")})
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "task_id")
    private Long taskId;
    @Column(name = "max_users")
    private Short maxUsers;
    @Basic(optional = false)
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Basic(optional = false)
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @Basic(optional = false)
    @Column(name = "available")
    private boolean available;
    @Column(name = "notes")
    private String notes;
    @Basic(optional = false)
    @Column(name = "is_approved")
    private boolean isApproved;
    @Basic(optional = false)
    @Column(name = "approving_manager")
    private String approvingManager;
    @Column(name = "task_description")
    private String taskDescription;
    @Basic(optional = false)
    @Column(name = "task_city")
    private String taskCity;
    @JoinTable(name = "user_task", joinColumns = {
        @JoinColumn(name = "task_id", referencedColumnName = "task_id")}, inverseJoinColumns = {
        @JoinColumn(name = "user_id", referencedColumnName = "user_id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> userList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "task", fetch = FetchType.EAGER)
    private FoodDeliveryData foodDeliveryData;
    @JoinColumn(name = "program_id", referencedColumnName = "program_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Program programId;
    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Team teamId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "task", fetch = FetchType.EAGER)
    private HotlineData hotlineData;

    public Task() {
    }

    public Task(Long taskId) {
        this.taskId = taskId;
    }

    public Task(Long taskId, Date startTime, Date endTime, boolean available, boolean isApproved, String approvingManager, String taskCity) {
        this.taskId = taskId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.available = available;
        this.isApproved = isApproved;
        this.approvingManager = approvingManager;
        this.taskCity = taskCity;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Short getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(Short maxUsers) {
        this.maxUsers = maxUsers;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getApprovingManager() {
        return approvingManager;
    }

    public void setApprovingManager(String approvingManager) {
        this.approvingManager = approvingManager;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskCity() {
        return taskCity;
    }

    public void setTaskCity(String taskCity) {
        this.taskCity = taskCity;
    }

    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public FoodDeliveryData getFoodDeliveryData() {
        return foodDeliveryData;
    }

    public void setFoodDeliveryData(FoodDeliveryData foodDeliveryData) {
        this.foodDeliveryData = foodDeliveryData;
    }

    public Program getProgramId() {
        return programId;
    }

    public void setProgramId(Program programId) {
        this.programId = programId;
    }

    public Team getTeamId() {
        return teamId;
    }

    public void setTeamId(Team teamId) {
        this.teamId = teamId;
    }

    public HotlineData getHotlineData() {
        return hotlineData;
    }

    public void setHotlineData(HotlineData hotlineData) {
        this.hotlineData = hotlineData;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taskId != null ? taskId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.taskId == null && other.taskId != null) || (this.taskId != null && !this.taskId.equals(other.taskId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dataaccess.Task[ taskId=" + taskId + " ]";
    }
    
}

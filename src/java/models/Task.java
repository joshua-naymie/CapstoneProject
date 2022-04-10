/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

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
    @NamedQuery(name = "Task.findByGroupId", query = "SELECT t FROM Task t WHERE t.groupId = :groupId"),
    @NamedQuery(name = "Task.findByMaxUsers", query = "SELECT t FROM Task t WHERE t.maxUsers = :maxUsers"),
    @NamedQuery(name = "Task.findByStartTime", query = "SELECT t FROM Task t WHERE t.startTime = :startTime"),
    @NamedQuery(name = "Task.findByEndTime", query = "SELECT t FROM Task t WHERE t.endTime = :endTime"),
    @NamedQuery(name = "Task.findByAvailable", query = "SELECT t FROM Task t WHERE t.available = :available"),
    @NamedQuery(name = "Task.findByNotes", query = "SELECT t FROM Task t WHERE t.notes = :notes"),
    @NamedQuery(name = "Task.findByIsApproved", query = "SELECT t FROM Task t WHERE t.isApproved = :isApproved"),
    @NamedQuery(name = "Task.findByApprovingManager", query = "SELECT t FROM Task t WHERE t.approvingManager = :approvingManager"),
    @NamedQuery(name = "Task.findByTaskDescription", query = "SELECT t FROM Task t WHERE t.taskDescription = :taskDescription"),
    @NamedQuery(name = "Task.findByTaskCity", query = "SELECT t FROM Task t WHERE t.taskCity = :taskCity"),
    @NamedQuery(name = "Task.findByIsSubmitted", query = "SELECT t FROM Task t WHERE t.isSubmitted = :isSubmitted"),
    @NamedQuery(name = "Task.findByApprovalNotes", query = "SELECT t FROM Task t WHERE t.approvalNotes = :approvalNotes"),
    @NamedQuery(name = "Task.findByIsDissaproved", query = "SELECT t FROM Task t WHERE t.isDissaproved = :isDissaproved"),
    @NamedQuery(name = "Task.findByAssigned", query = "SELECT t FROM Task t WHERE t.assigned = :assigned"),
    @NamedQuery(name = "Task.findSubmittedToManger", query = "SELECT t FROM Task t WHERE t.isSubmitted = TRUE AND t.approvingManager = :approvingManager"),
    @NamedQuery(name = "Task.findByProgramCityDate", query = "SELECT t FROM Task t WHERE t.programId.programId = :programId AND t.taskCity = :city AND t.isApproved = FALSE AND t.startTime BETWEEN CAST(:startDate AS DATE) AND CAST(:endDate AS DATE)"),
    @NamedQuery(name = "Task.findBySpotsTaken", query = "SELECT t FROM Task t WHERE t.spotsTaken = :spotsTaken")})
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "task_id")
    private Long taskId;
    @Basic(optional = false)
    @Column(name = "group_id")
    private long groupId;
    @Column(name = "max_users")
    private Short maxUsers;
    @Basic(optional = false)
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
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
    private int approvingManager;
    @Column(name = "task_description")
    private String taskDescription;
    @Column(name = "task_city")
    private String taskCity;
    @Column(name = "is_submitted")
    private Boolean isSubmitted;
    @Column(name = "approval_notes")
    private String approvalNotes;
    @Column(name = "is_dissaproved")
    private Boolean isDissaproved;
    @Column(name = "assigned")
    private Boolean assigned;
    @Basic(optional = false)
    @Column(name = "spots_taken")
    private short spotsTaken;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "task", fetch = FetchType.EAGER)
    private FoodDeliveryData foodDeliveryData;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "task", fetch = FetchType.EAGER)
    private HotlineData hotlineData;
    @JoinColumn(name = "program_id", referencedColumnName = "program_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Program programId;
    @JoinColumn(name = "team_id", referencedColumnName = "team_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Team teamId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private User userId;

    public Task() {
    }

    public Task(Long taskId) {
        this.taskId = taskId;
    }

    public Task(Long taskId, long groupId, Date startTime, boolean available, boolean isApproved, int approvingManager, short spotsTaken, String taskCity) {
        this.taskId = taskId;
        this.groupId = groupId;
        this.startTime = startTime;
        this.available = available;
        this.isApproved = isApproved;
        this.approvingManager = approvingManager;
        this.spotsTaken = spotsTaken;
        this.taskCity = taskCity;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
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

    public int getApprovingManager() {
        return approvingManager;
    }

    public void setApprovingManager(int approvingManager) {
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

    public Boolean getIsSubmitted() {
        return isSubmitted;
    }

    public void setIsSubmitted(Boolean isSubmitted) {
        this.isSubmitted = isSubmitted;
    }

    public String getApprovalNotes() {
        return approvalNotes;
    }

    public void setApprovalNotes(String approvalNotes) {
        this.approvalNotes = approvalNotes;
    }

    public Boolean getIsDissaproved() {
        return isDissaproved;
    }

    public void setIsDissaproved(Boolean isDissaproved) {
        this.isDissaproved = isDissaproved;
    }

    public Boolean getAssigned() {
        return assigned;
    }

    public void setAssigned(Boolean assigned) {
        this.assigned = assigned;
    }

    public short getSpotsTaken() {
        return spotsTaken;
    }

    public void setSpotsTaken(short spotsTaken) {
        this.spotsTaken = spotsTaken;
    }

    public FoodDeliveryData getFoodDeliveryData() {
        return foodDeliveryData;
    }

    public void setFoodDeliveryData(FoodDeliveryData foodDeliveryData) {
        this.foodDeliveryData = foodDeliveryData;
    }

    public HotlineData getHotlineData() {
        return hotlineData;
    }

    public void setHotlineData(HotlineData hotlineData) {
        this.hotlineData = hotlineData;
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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        return "models.Task[ taskId=" + taskId + " ]";
    }
    
}

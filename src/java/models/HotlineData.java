/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Main
 */
@Entity
@Table(name = "hotline_data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HotlineData.findAll", query = "SELECT h FROM HotlineData h"),
    @NamedQuery(name = "HotlineData.findByTaskHotlineId", query = "SELECT h FROM HotlineData h WHERE h.taskHotlineId = :taskHotlineId"),
    @NamedQuery(name = "HotlineData.findByHotlineHoursWorked", query = "SELECT h FROM HotlineData h WHERE h.hotlineHoursWorked = :hotlineHoursWorked")})
public class HotlineData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "task_hotline_id")
    private Long taskHotlineId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "hotline_hours_worked")
    private BigDecimal hotlineHoursWorked;
    @JoinColumn(name = "task_hotline_id", referencedColumnName = "task_id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Task task;

    public HotlineData() {
    }

    public HotlineData(Long taskHotlineId) {
        this.taskHotlineId = taskHotlineId;
    }

    public Long getTaskHotlineId() {
        return taskHotlineId;
    }

    public void setTaskHotlineId(Long taskHotlineId) {
        this.taskHotlineId = taskHotlineId;
    }

    public BigDecimal getHotlineHoursWorked() {
        return hotlineHoursWorked;
    }

    public void setHotlineHoursWorked(BigDecimal hotlineHoursWorked) {
        this.hotlineHoursWorked = hotlineHoursWorked;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taskHotlineId != null ? taskHotlineId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HotlineData)) {
            return false;
        }
        HotlineData other = (HotlineData) object;
        if ((this.taskHotlineId == null && other.taskHotlineId != null) || (this.taskHotlineId != null && !this.taskHotlineId.equals(other.taskHotlineId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.HotlineData[ taskHotlineId=" + taskHotlineId + " ]";
    }
    
}

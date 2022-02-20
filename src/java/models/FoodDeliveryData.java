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
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Main
 */
@Entity
@Table(name = "food_delivery_data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FoodDeliveryData.findAll", query = "SELECT f FROM FoodDeliveryData f"),
    @NamedQuery(name = "FoodDeliveryData.findByTaskFdId", query = "SELECT f FROM FoodDeliveryData f WHERE f.taskFdId = :taskFdId"),
    @NamedQuery(name = "FoodDeliveryData.findByMileage", query = "SELECT f FROM FoodDeliveryData f WHERE f.mileage = :mileage"),
    @NamedQuery(name = "FoodDeliveryData.findByFoodHoursWorked", query = "SELECT f FROM FoodDeliveryData f WHERE f.foodHoursWorked = :foodHoursWorked"),
    @NamedQuery(name = "FoodDeliveryData.findByFoodType", query = "SELECT f FROM FoodDeliveryData f WHERE f.foodType = :foodType"),
    @NamedQuery(name = "FoodDeliveryData.findByFoodAmount", query = "SELECT f FROM FoodDeliveryData f WHERE f.foodAmount = :foodAmount"),
    @NamedQuery(name = "FoodDeliveryData.findByFamilyCount", query = "SELECT f FROM FoodDeliveryData f WHERE f.familyCount = :familyCount")})
public class FoodDeliveryData implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "task_fd_id")
    private Long taskFdId;
    @Column(name = "mileage")
    private Short mileage;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "food_hours_worked")
    private BigDecimal foodHoursWorked;
    @Column(name = "food_type")
    private String foodType;
    @Column(name = "food_amount")
    private Short foodAmount;
    @Column(name = "family_count")
    private Short familyCount;
    @JoinColumn(name = "organization_id", referencedColumnName = "organization_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Organization organizationId;
    @JoinColumn(name = "package_id", referencedColumnName = "package_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private PackageType packageId;
    @JoinColumn(name = "store_id", referencedColumnName = "store_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Store storeId;
    @JoinColumn(name = "task_fd_id", referencedColumnName = "task_id", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.EAGER)
    private Task task;

    public FoodDeliveryData() {
    }

    public FoodDeliveryData(Long taskFdId) {
        this.taskFdId = taskFdId;
    }

    public Long getTaskFdId() {
        return taskFdId;
    }

    public void setTaskFdId(Long taskFdId) {
        this.taskFdId = taskFdId;
    }

    public Short getMileage() {
        return mileage;
    }

    public void setMileage(Short mileage) {
        this.mileage = mileage;
    }

    public BigDecimal getFoodHoursWorked() {
        return foodHoursWorked;
    }

    public void setFoodHoursWorked(BigDecimal foodHoursWorked) {
        this.foodHoursWorked = foodHoursWorked;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public Short getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(Short foodAmount) {
        this.foodAmount = foodAmount;
    }

    public Short getFamilyCount() {
        return familyCount;
    }

    public void setFamilyCount(Short familyCount) {
        this.familyCount = familyCount;
    }

    public Organization getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Organization organizationId) {
        this.organizationId = organizationId;
    }

    public PackageType getPackageId() {
        return packageId;
    }

    public void setPackageId(PackageType packageId) {
        this.packageId = packageId;
    }

    public Store getStoreId() {
        return storeId;
    }

    public void setStoreId(Store storeId) {
        this.storeId = storeId;
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
        hash += (taskFdId != null ? taskFdId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FoodDeliveryData)) {
            return false;
        }
        FoodDeliveryData other = (FoodDeliveryData) object;
        if ((this.taskFdId == null && other.taskFdId != null) || (this.taskFdId != null && !this.taskFdId.equals(other.taskFdId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.FoodDeliveryData[ taskFdId=" + taskFdId + " ]";
    }
    
}

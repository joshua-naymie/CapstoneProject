/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Main
 */
@Entity
@Table(name = "package_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PackageType.findAll", query = "SELECT p FROM PackageType p"),
    @NamedQuery(name = "PackageType.findByPackageId", query = "SELECT p FROM PackageType p WHERE p.packageId = :packageId"),
    @NamedQuery(name = "PackageType.findByPackageName", query = "SELECT p FROM PackageType p WHERE p.packageName = :packageName"),
    @NamedQuery(name = "PackageType.findByWeightLb", query = "SELECT p FROM PackageType p WHERE p.weightLb = :weightLb")})
public class PackageType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "package_id")
    private Short packageId;
    @Basic(optional = false)
    @Column(name = "package_name")
    private String packageName;
    @Basic(optional = false)
    @Column(name = "weight_lb")
    private short weightLb;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "packageId", fetch = FetchType.EAGER)
    private List<FoodDeliveryData> foodDeliveryDataList;

    public PackageType() {
    }

    public PackageType(Short packageId) {
        this.packageId = packageId;
    }

    public PackageType(Short packageId, String packageName, short weightLb) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.weightLb = weightLb;
    }

    public Short getPackageId() {
        return packageId;
    }

    public void setPackageId(Short packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public short getWeightLb() {
        return weightLb;
    }

    public void setWeightLb(short weightLb) {
        this.weightLb = weightLb;
    }

    @XmlTransient
    public List<FoodDeliveryData> getFoodDeliveryDataList() {
        return foodDeliveryDataList;
    }

    public void setFoodDeliveryDataList(List<FoodDeliveryData> foodDeliveryDataList) {
        this.foodDeliveryDataList = foodDeliveryDataList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (packageId != null ? packageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PackageType)) {
            return false;
        }
        PackageType other = (PackageType) object;
        if ((this.packageId == null && other.packageId != null) || (this.packageId != null && !this.packageId.equals(other.packageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.PackageType[ packageId=" + packageId + " ]";
    }
    
}

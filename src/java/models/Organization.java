/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Main
 */
@Entity
@Table(name = "organization")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Organization.findAll", query = "SELECT o FROM Organization o"),
    @NamedQuery(name = "Organization.findByOrganizationId", query = "SELECT o FROM Organization o WHERE o.organizationId = :organizationId"),
    @NamedQuery(name = "Organization.findByStreetAddress", query = "SELECT o FROM Organization o WHERE o.streetAddress = :streetAddress"),
    @NamedQuery(name = "Organization.findByPostalCode", query = "SELECT o FROM Organization o WHERE o.postalCode = :postalCode"),
    @NamedQuery(name = "Organization.findByOrgCity", query = "SELECT o FROM Organization o WHERE o.orgCity = :orgCity"),
    @NamedQuery(name = "Organization.findByOrgCode", query = "SELECT o FROM Organization o WHERE o.orgCode = :orgCode"),
    @NamedQuery(name = "Organization.findByOrgName", query = "SELECT o FROM Organization o WHERE o.orgName = :orgName"),
    @NamedQuery(name = "Organization.findByPhoneNum", query = "SELECT o FROM Organization o WHERE o.phoneNum = :phoneNum"),
    @NamedQuery(name = "Organization.findByContact", query = "SELECT o FROM Organization o WHERE o.contact = :contact"),
    @NamedQuery(name = "Organization.findByIsActive", query = "SELECT o FROM Organization o WHERE o.isActive = :isActive")})
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "organization_id")
    private Integer organizationId;
    @Basic(optional = false)
    @Column(name = "street_address")
    private String streetAddress;
    @Column(name = "postal_code")
    private String postalCode;
    @Basic(optional = false)
    @Column(name = "org_city")
    private String orgCity;
    @Basic(optional = false)
    @Column(name = "org_code")
    private short orgCode;
    @Basic(optional = false)
    @Column(name = "org_name")
    private String orgName;
    @Column(name = "phone_num")
    private String phoneNum;
    @Column(name = "contact")
    private String contact;
    @Basic(optional = false)
    @Column(name = "is_active")
    private boolean isActive;
    @OneToMany(mappedBy = "organizationId")
    private List<FoodDeliveryData> foodDeliveryDataList;

    public Organization() {
    }

    public Organization(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Organization(Integer organizationId, String streetAddress, String orgCity, short orgCode, String orgName, boolean isActive) {
        this.organizationId = organizationId;
        this.streetAddress = streetAddress;
        this.orgCity = orgCity;
        this.orgCode = orgCode;
        this.orgName = orgName;
        this.isActive = isActive;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getOrgCity() {
        return orgCity;
    }

    public void setOrgCity(String orgCity) {
        this.orgCity = orgCity;
    }

    public short getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(short orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
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
        hash += (organizationId != null ? organizationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Organization)) {
            return false;
        }
        Organization other = (Organization) object;
        if ((this.organizationId == null && other.organizationId != null) || (this.organizationId != null && !this.organizationId.equals(other.organizationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Organization[ organizationId=" + organizationId + " ]";
    }
    
}

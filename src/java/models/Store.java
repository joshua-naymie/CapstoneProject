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
@Table(name = "store")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Store.findAll", query = "SELECT s FROM Store s"),
    @NamedQuery(name = "Store.findByStoreId", query = "SELECT s FROM Store s WHERE s.storeId = :storeId"),
    @NamedQuery(name = "Store.findByStreetAddress", query = "SELECT s FROM Store s WHERE s.streetAddress = :streetAddress"),
    @NamedQuery(name = "Store.findByPostalCode", query = "SELECT s FROM Store s WHERE s.postalCode = :postalCode"),
    @NamedQuery(name = "Store.findByStoreCity", query = "SELECT s FROM Store s WHERE s.storeCity = :storeCity"),
    @NamedQuery(name = "Store.findByPhoneNum", query = "SELECT s FROM Store s WHERE s.phoneNum = :phoneNum"),
    @NamedQuery(name = "Store.findByContact", query = "SELECT s FROM Store s WHERE s.contact = :contact"),
    @NamedQuery(name = "Store.findByStoreName", query = "SELECT s FROM Store s WHERE s.storeName = :storeName"),
    @NamedQuery(name = "Store.findByIsActive", query = "SELECT s FROM Store s WHERE s.isActive = :isActive")})
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "store_id")
    private Integer storeId;
    @Basic(optional = false)
    @Column(name = "street_address")
    private String streetAddress;
    @Basic(optional = false)
    @Column(name = "postal_code")
    private String postalCode;
    @Basic(optional = false)
    @Column(name = "store_city")
    private String storeCity;
    @Column(name = "phone_num")
    private String phoneNum;
    @Column(name = "contact")
    private String contact;
    @Basic(optional = false)
    @Column(name = "store_name")
    private String storeName;
    @Basic(optional = false)
    @Column(name = "is_active")
    private boolean isActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storeId", fetch = FetchType.EAGER)
    private List<FoodDeliveryData> foodDeliveryDataList;
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private CompanyName companyId;
    @OneToMany(mappedBy = "storeId", fetch = FetchType.EAGER)
    private List<Team> teamList;

    public Store() {
    }

    public Store(Integer storeId) {
        this.storeId = storeId;
    }
//((contact, is_active, phone_num, postal_code, store_city, store_name, street_address, company_id)
    public Store(int storeId, String contact,boolean isActive, String phoneNum,String postalCode, String storeCity, String storeName ,String streetAddress,CompanyName companyId )
 {      this.storeId = storeId;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.storeCity = storeCity;
        this.storeName = storeName;
        this.isActive = isActive;
        this.companyId = companyId;
        this.contact = contact; 
        this.phoneNum = phoneNum;
    }
    
    public Store(Integer storeId, String streetAddress, String postalCode, String storeCity, String storeName, boolean isActive) {
        this.storeId = storeId;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.storeCity = storeCity;
        this.storeName = storeName;
        this.isActive = isActive;
        this.companyId = companyId; 
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
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

    public String getStoreCity() {
        return storeCity;
    }

    public void setStoreCity(String storeCity) {
        this.storeCity = storeCity;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public CompanyName getCompanyId() {
        return companyId;
    }

    public void setCompanyId(CompanyName companyId) {
        this.companyId = companyId;
    }

    @XmlTransient
    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (storeId != null ? storeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Store)) {
            return false;
        }
        Store other = (Store) object;
        if ((this.storeId == null && other.storeId != null) || (this.storeId != null && !this.storeId.equals(other.storeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Store[ storeId=" + storeId + " ]";
    }
    
}

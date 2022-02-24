/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DWEI
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
    @Column(name = "is_active")
    private boolean isActive;

    public Store() {
    }

    public Store(Integer storeId) {
        this.storeId = storeId;
    }

    public Store(String streetAddress, String postalCode, String storeCity, boolean isActive, String phoneNum, String contact) {
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.storeCity = storeCity;
        this.isActive = isActive;
        this.phoneNum = phoneNum;
        this.contact = contact;
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

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
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

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
@Table(name = "company_name")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompanyName.findAll", query = "SELECT c FROM CompanyName c"),
    @NamedQuery(name = "CompanyName.findByCompanyId", query = "SELECT c FROM CompanyName c WHERE c.companyId = :companyId"),
    @NamedQuery(name = "CompanyName.findByCompanyName", query = "SELECT c FROM CompanyName c WHERE LOWER(c.companyName) = LOWER(:companyName)")})
public class CompanyName implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "company_id")
    private Short companyId;
    @Basic(optional = false)
    @Column(name = "company_name")
    private String companyName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "companyId", fetch = FetchType.EAGER)
    private List<Store> storeList;

    public CompanyName() {
    }

    public CompanyName(Short companyId) {
        this.companyId = companyId;
    }

    public CompanyName(Short companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public Short getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Short companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @XmlTransient
    public List<Store> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<Store> storeList) {
        this.storeList = storeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (companyId != null ? companyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompanyName)) {
            return false;
        }
        CompanyName other = (CompanyName) object;
        if ((this.companyId == null && other.companyId != null) || (this.companyId != null && !this.companyId.equals(other.companyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.CompanyName[ companyId=" + companyId + " ]";
    }
    
}

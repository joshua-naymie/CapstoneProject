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
 * @author Main
 */
@Entity
@Table(name = "company_name")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompanyName.findAll", query = "SELECT c FROM CompanyName c"),
    @NamedQuery(name = "CompanyName.findByCompanyId", query = "SELECT c FROM CompanyName c WHERE c.companyId = :companyId"),
    @NamedQuery(name = "CompanyName.findByCompanyName", query = "SELECT c FROM CompanyName c WHERE c.companyName = :companyName")})
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
        return "dataaccess.CompanyName[ companyId=" + companyId + " ]";
    }
    
}

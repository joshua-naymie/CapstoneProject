/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Main
 */
@Entity
@Table(name = "donation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Donation.findAll", query = "SELECT d FROM Donation d"),
    @NamedQuery(name = "Donation.findByDonationId", query = "SELECT d FROM Donation d WHERE d.donationId = :donationId"),
    @NamedQuery(name = "Donation.findByDonationAmount", query = "SELECT d FROM Donation d WHERE d.donationAmount = :donationAmount"),
    @NamedQuery(name = "Donation.findByDonationDate", query = "SELECT d FROM Donation d WHERE d.donationDate = :donationDate"),
    @NamedQuery(name = "Donation.findByDonationType", query = "SELECT d FROM Donation d WHERE d.donationType = :donationType"),
    @NamedQuery(name = "Donation.findByDonor", query = "SELECT d FROM Donation d WHERE d.donor = :donor"),
    @NamedQuery(name = "Donation.findByDonationQuantity", query = "SELECT d FROM Donation d WHERE d.donationQuantity = :donationQuantity")})
public class Donation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "donation_id")
    private Integer donationId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "donation_amount")
    private BigDecimal donationAmount;
    @Basic(optional = false)
    @Column(name = "donation_date")
    @Temporal(TemporalType.DATE)
    private Date donationDate;
    @Basic(optional = false)
    @Column(name = "donation_type")
    private String donationType;
    @Basic(optional = false)
    @Column(name = "donor")
    private String donor;
    @Column(name = "donation_quantity")
    private String donationQuantity;

    public Donation() {
    }

    public Donation(Integer donationId) {
        this.donationId = donationId;
    }

    public Donation(Integer donationId, Date donationDate, String donationType, String donor) {
        this.donationId = donationId;
        this.donationDate = donationDate;
        this.donationType = donationType;
        this.donor = donor;
    }

    public Integer getDonationId() {
        return donationId;
    }

    public void setDonationId(Integer donationId) {
        this.donationId = donationId;
    }

    public BigDecimal getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(BigDecimal donationAmount) {
        this.donationAmount = donationAmount;
    }

    public Date getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }

    public String getDonationType() {
        return donationType;
    }

    public void setDonationType(String donationType) {
        this.donationType = donationType;
    }

    public String getDonor() {
        return donor;
    }

    public void setDonor(String donor) {
        this.donor = donor;
    }

    public String getDonationQuantity() {
        return donationQuantity;
    }

    public void setDonationQuantity(String donationQuantity) {
        this.donationQuantity = donationQuantity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (donationId != null ? donationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Donation)) {
            return false;
        }
        Donation other = (Donation) object;
        if ((this.donationId == null && other.donationId != null) || (this.donationId != null && !this.donationId.equals(other.donationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dataaccess.Donation[ donationId=" + donationId + " ]";
    }
    
}

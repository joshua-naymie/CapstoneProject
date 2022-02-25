/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
    @NamedQuery(name = "Donation.findByDonor", query = "SELECT d FROM Donation d WHERE d.donor = :donor")})
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
    @Column(name = "donor")
    private String donor;

    public Donation() {
    }

    public Donation(Integer donationId) {
        this.donationId = donationId;
    }

    public Donation(Integer donationId, Date donationDate, String donor) {
        this.donationId = donationId;
        this.donationDate = donationDate;
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

    public String getDonor() {
        return donor;
    }

    public void setDonor(String donor) {
        this.donor = donor;
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
        return "models.Donation[ donationId=" + donationId + " ]";
    }
    
}

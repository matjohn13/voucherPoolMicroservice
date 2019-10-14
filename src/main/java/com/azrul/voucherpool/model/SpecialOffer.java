package com.azrul.voucherpool.model;

import javax.persistence.*;

@Entity
@Table(name = "SpecialOffer")
public class SpecialOffer extends AuditModel{
    
    @Id
    @GeneratedValue(generator = "specialOffer_generator")
    @SequenceGenerator(
            name = "specialOffer_generator",
            sequenceName = "specialOffer_sequence",
            initialValue = 1000
    )
    private Long id;
    
    @Column(columnDefinition = "text", unique=true)
    private String offerName;
    
    @Column(columnDefinition = "text")
    private String offerPercentage;

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getOfferName() {
        return offerName;
    }

    public String getOfferPercentage() {
        return offerPercentage;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public void setOfferPercentage(String offerPercentage) {
        this.offerPercentage = offerPercentage;
    }
}

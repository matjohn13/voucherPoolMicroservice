package com.azrul.voucherpool.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "VoucherCode")
public class VoucherCode implements Serializable/*extends AuditModel*/{
    
    @Id
    @GeneratedValue(generator = "VoucherCode_generator")
    @SequenceGenerator(
            name = "VoucherCode_generator",
            sequenceName = "VoucherCode_sequence",
            initialValue = 1000
    )
    private Long id;
    
    @Column(columnDefinition = "text", unique=true)
    private String code;
    
    @Column(columnDefinition = "text")
    private String recipient;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "special_offer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SpecialOffer specialOffer;
    
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date expirationDate;
    
    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date redeemDate;

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public SpecialOffer getSpecialOffer() {
        return specialOffer;
    }

    public void setSpecialOffer(SpecialOffer specialOffer) {
        this.specialOffer = specialOffer;
    }



    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getRedeemDate() {
        return redeemDate;
    }

    public void setRedeemDate(Date redeemDate) {
        this.redeemDate = redeemDate;
    }
}

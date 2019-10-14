package com.azrul.voucherpool.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "recipient")
public class Recipient extends AuditModel{
    
    @Id
    @GeneratedValue(generator = "recipient_generator")
    @SequenceGenerator(
            name = "recipient_generator",
            sequenceName = "recipient_sequence",
            initialValue = 1000
    )
    private Long id;
    
    @NotBlank
    @Size(min = 3, max = 100)
    private String name;
    
    @Column(columnDefinition = "text", unique=true)
    private String email;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    } 
}

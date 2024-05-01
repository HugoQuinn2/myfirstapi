package com.hq.myfirtsapi.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user")

public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String fistName;
    @Column
    private String lastName;
    @Column
    private String email;

    public Long getId() {
        return id;
    }

    public String getFistName() {
        return fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

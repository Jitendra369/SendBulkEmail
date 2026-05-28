package com.sendhrresume.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "user_hr")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String title;

    @Column(unique = true)  // avoid duplicate email entries
    private String email;
    private String company;
    private boolean isDataAddedToDb;
    private boolean isEmailSentToCompany;

}

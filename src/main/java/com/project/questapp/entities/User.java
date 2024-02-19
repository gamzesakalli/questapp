package com.project.questapp.entities;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String username;
    String password;



}

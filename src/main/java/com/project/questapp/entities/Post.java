package com.project.questapp.entities;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@Entity
@Table(name="post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //user objesini hemen çekme, LAZY
    @ManyToOne(fetch = FetchType.EAGER)
    //user objesini user_id kolonu ile bağlan
    @JoinColumn(name="user_id",nullable = false)
    //bir user silindiğinde onun tüm postları silinmedi
    @OnDelete(action = OnDeleteAction.CASCADE)
    User user;

    String title;
    @Lob
    @Column(columnDefinition = "text")
    String text;
}


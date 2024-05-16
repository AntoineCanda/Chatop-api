package com.chatop.api.models;
import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NonNull;

/**
 * Rental entity class
 */
@Data
@Entity
@Table(name = "RENTALS")
public class Rental implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name="name")
    private String name;

    @NonNull
    @Column(name="surface")
    private Integer surface;

    @NonNull
    @Column(name="price")
    private Integer price;

    @NonNull
    @Column(name="picture")
    private String picture;

    @NonNull
    @Column(name="description")
    private String description;

    @NonNull
    @Column (name="owner_id", nullable=false)
    private Integer ownerId;

    @Column(name="created_at")
    private Timestamp createdAt;

    @Column(name="updated_at")
    private Timestamp updatedAt;

}
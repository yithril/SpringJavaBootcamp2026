package com.example.movie_api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movies")
@AllArgsConstructor //Lombok annotation, not JPA
@NoArgsConstructor
@Getter
@Setter
public class Movie {
    //Id means primary key
    //GeneratedValue is auto increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //validation
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank(message = "Synopsis is required")
    @Column(nullable = false, length = 1000)
    private String synopsis;

    @DecimalMin(value = "0.0", message = "Rating cannot be negative")
    @DecimalMax(value = "10.0", message = "Rating cannot exceed 10.0")
    private Double rating;

    @Positive(message = "Runtime must be a positive number")
    private Integer runTimeInMinutes;

    @NotBlank(message = "Language cannot be blank")
    @Column(nullable = false, length = 50)
    private String language = "English";
}

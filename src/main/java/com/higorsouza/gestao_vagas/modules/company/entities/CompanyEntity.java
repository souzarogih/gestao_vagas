package com.higorsouza.gestao_vagas.modules.company.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "company")
public class CompanyEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Length(min = 5, max = 20, message = "The username field must be between 5 and 20 characters.")
    @Pattern(regexp = "\\S+", message = "The username field must contain space")
    private String username;

    @Email(message = "The email field must have a valid value.")
    private String email;

    @Length(min = 10, max = 100, message = "The password field must be between 10 and 100 characters.")
    private String password;
    private String website;

    @NotBlank(message = "The name field must not be blank or have a space")
    private String name;

    @NotNull(message = "The description field is mandatory")
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

}

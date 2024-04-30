package com.higorsouza.gestao_vagas.modules.candidate.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "candidate")
public class CandidateEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "Daniel Lopes", requiredMode = Schema.RequiredMode.REQUIRED, description = "Nome do candidato")
    @NotBlank(message = "The name field must not be blank or have a space")
    private String name;

    @Schema(example = "daniel.lopes", requiredMode = Schema.RequiredMode.REQUIRED, description = "Username do candidato")
    @Length(min = 5, max = 20, message = "The username field must be between 5 and 20 characters.")
    @Pattern(regexp = "\\S+", message = "The username field must contain space")
    private String username;

    @Email(message = "The email field must have a valid value.")
    @Schema(example = "daniel.lopes@mail.com", requiredMode = Schema.RequiredMode.REQUIRED, description = "Senha do candidato")
    private String email;

    @Length(min = 10, max = 100, message = "The password field must be between 10 and 100 characters.")
    @Schema(example = "admin@1234", minLength = 10, maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED, description = "Email do candidato")
    private String password;

    @Schema(example = "Desenvolvedor Java", requiredMode = Schema.RequiredMode.REQUIRED, description = "Breve descrição do candidato")
    @NotNull(message = "The description field is mandatory")
    private String description;


    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;

}

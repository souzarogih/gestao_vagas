package com.higorsouza.gestao_vagas.modules.candidate.controllers;

import com.higorsouza.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import com.higorsouza.gestao_vagas.modules.candidate.models.CandidateEntity;
import com.higorsouza.gestao_vagas.modules.candidate.useCases.ApplyJobCandidateUseCase;
import com.higorsouza.gestao_vagas.modules.candidate.useCases.CreateCandidateUserCase;
import com.higorsouza.gestao_vagas.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import com.higorsouza.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import com.higorsouza.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato.")
public class CandidateController {

    @Autowired
    private CreateCandidateUserCase createCandidateUserCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @PostMapping("/")
    @Operation(summary = "Cadastro de candidato.", description = "Essa função é responsável por cadastrar um candidato.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            schema = @Schema(implementation = CandidateEntity.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "Usuário já existe")
    })
    public ResponseEntity<Object> create(@RequestBody @Valid CandidateEntity candidateEntity) {
        try {
            var result = this.createCandidateUserCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Perfil do candidato.", description = "Essa função é responsável por buscar as informações dos candidatos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                           schema = @Schema(implementation = ProfileCandidateResponseDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = "User not found")
    })
//    @Tag(name = "Candidato", description = "Informações do candidato.")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> get(HttpServletRequest httpServletRequest) {
        var idCandidate = httpServletRequest.getAttribute("candidate_id");
        try {
            var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
//    @Tag(name = "Candidato", description = "Informações do candidato.")
    @Operation(summary = "Listagem de vagas disponiveis para o candidato.", description = "Essa função é responsável por listar todas as vagas disponíveis disponíveis no filtro.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = JobEntity.class))
                    )
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobByFilter(@RequestParam String filter) {
        return listAllJobsByFilterUseCase.execute(filter);
    }

    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name = "jwt_auth")
    @Operation(summary = "Inscrição do candidato para uma vaga.", description = "Essa função é responsável por realizar a inscrição de um candidato em uma vaga.")
    public ResponseEntity<Object> applyJob(HttpServletRequest httpServletRequest, @RequestBody UUID idJob) {

        var idCandidate = httpServletRequest.getAttribute("candidate_id");

        try {
            var result = this.applyJobCandidateUseCase.execute(UUID.fromString(idCandidate.toString()), idJob);
            return ResponseEntity.ok().body(result);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}

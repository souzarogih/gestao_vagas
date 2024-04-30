package com.higorsouza.gestao_vagas.modules.candidate.useCases;

import com.higorsouza.gestao_vagas.exceptions.JobNotFoundException;
import com.higorsouza.gestao_vagas.exceptions.UserNotFoundException;
import com.higorsouza.gestao_vagas.modules.candidate.entity.ApplyJobEntity;
import com.higorsouza.gestao_vagas.modules.candidate.repository.ApplyJobRepository;
import com.higorsouza.gestao_vagas.modules.candidate.repository.CandidateRepository;
import com.higorsouza.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {
            this.candidateRepository.findById(idCandidate)
                    .orElseThrow(() -> {
                        throw new UserNotFoundException();
                    });

            this.jobRepository.findById(idJob)
                    .orElseThrow(() -> {
                        throw new JobNotFoundException();
                    });

            var applyJob = ApplyJobEntity.builder()
                            .candidateId(idCandidate)
                            .jobId(idJob)
                            .build();
            applyJob = applyJobRepository.save(applyJob);
            return applyJob;
    }
}

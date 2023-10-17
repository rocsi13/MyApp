package myApp.myApp.Service;

import myApp.myApp.Entity.Candidate;
import myApp.myApp.Repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {
    @Autowired
    private CandidateRepository candidateRepository;

    public CandidateServiceImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Candidate findByEmail(String email) {
        return candidateRepository.findByEmail(email);
    }

    @Override
    public void saveCandidate(Candidate candidate) {
        candidateRepository.save(candidate);
    }

    @Override
    public List<Candidate> findAll() {
        return candidateRepository.findAll(Sort.by(Sort.Direction.DESC, "noVotes"));
    }

    public Candidate getCandidateById (Long id) {
        return candidateRepository.findById(id).orElse(null);
    }
}

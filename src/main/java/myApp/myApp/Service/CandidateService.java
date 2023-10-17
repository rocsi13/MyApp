package myApp.myApp.Service;

import myApp.myApp.Entity.Candidate;

import java.util.List;

public interface CandidateService {
    void saveCandidate(Candidate candidate);

    Candidate findByEmail(String email);

    List<Candidate> findAll();

    Candidate getCandidateById(Long id);
}

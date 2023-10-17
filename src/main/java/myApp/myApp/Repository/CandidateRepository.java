package myApp.myApp.Repository;

import myApp.myApp.Entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository <Candidate, Long> {
    Candidate findByEmail(String email);
}

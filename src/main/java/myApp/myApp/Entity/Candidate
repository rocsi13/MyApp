package myApp.myApp.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCandidate;

    @Column(nullable=false)
    private String fullNameCandidate;

    @Column(nullable=false, unique = true)
    private String email;

    @Column(nullable=false)
    private String description;

    @Column
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long noVotes=0L;

}

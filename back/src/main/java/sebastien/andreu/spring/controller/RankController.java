package sebastien.andreu.spring.controller;

import org.springframework.web.bind.annotation.*;
import sebastien.andreu.spring.entity.Rank;
import sebastien.andreu.spring.repository.RankRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@RestController
@RequestMapping("/api/rank")
public class RankController {

    @Autowired
    private RankRepository rankRepository;

    @GetMapping("")
    public Optional<Rank> getRankById(@RequestBody Long rankId) {
        return rankRepository.findById(rankId);
    }

    @PostMapping
    public Rank createRank(@RequestBody Rank rank) {
        // Vérifiez les autorisations ici si nécessaire
        return rankRepository.save(rank);
    }
}
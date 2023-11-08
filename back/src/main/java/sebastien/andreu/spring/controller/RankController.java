package sebastien.andreu.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sebastien.andreu.spring.dto.rank.RankDto;
import sebastien.andreu.spring.dto.rank.RankGetDto;
import sebastien.andreu.spring.entity.Rank;
import sebastien.andreu.spring.entity.User;
import sebastien.andreu.spring.repository.ListRepository;
import sebastien.andreu.spring.repository.RankRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/rank")
public class RankController {

    @Autowired
    private RankRepository rankRepository;

    @Autowired
    private ListRepository listRepository;

    @GetMapping("")
    public Optional<Rank> getRankById(@RequestBody Long rankId) {
        return rankRepository.findById(rankId);
    }

    @PostMapping(path = "", produces = "application/json")
    public @ResponseBody ResponseEntity<Map<String, String>> addRank(@RequestBody RankDto rankDto) {
        Map<String, String> response = new HashMap<>();

        try {
            Optional<sebastien.andreu.spring.entity.List> listFound = listRepository.findById(rankDto.getListId());
            if (listFound.isPresent()) {
                Rank rank = new Rank();
                rank.setListId(rankDto.getListId());
                rank.setTitle(rankDto.getTitle());
                rank.setOrdering(rankDto.getOrdering());
                rankRepository.save(rank);

                response.put("message", "rank enregistré avec succès");
                response.put("rank", rank.toString());
                System.out.println(response);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                throw new RuntimeException("List ID not found");
            }
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "", produces = "application/json")
    public @ResponseBody Optional<List<Rank>> getRankByListId(@RequestBody RankGetDto rankGetDto) {
        return rankRepository.findByListId(rankGetDto.getListId());
    }
}
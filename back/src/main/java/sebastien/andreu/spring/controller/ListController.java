package sebastien.andreu.spring.controller;

import sebastien.andreu.spring.entity.List;
import sebastien.andreu.spring.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/list")
public class ListController {

    @Autowired
    private ListRepository listRepository;

    @GetMapping("/{listId}")
    public Optional<List> getListById(@PathVariable Long listId) {
        return listRepository.findById(listId);
    }

    @PostMapping
    public List createList(@RequestBody List list) {
        return listRepository.save(list);
    }

    @GetMapping("")
    public Optional<List> getUserLists(@RequestBody Long userId) {
        return listRepository.findById(userId);
    }
}
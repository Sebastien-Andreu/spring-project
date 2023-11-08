package sebastien.andreu.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sebastien.andreu.spring.dto.list.ListDto;
import sebastien.andreu.spring.dto.list.ListGetDto;
import sebastien.andreu.spring.dto.list.ListPutDto;
import sebastien.andreu.spring.entity.List;
import sebastien.andreu.spring.entity.User;
import sebastien.andreu.spring.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sebastien.andreu.spring.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/list")
public class ListController {

    @Autowired
    private ListRepository listRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{listId}")
    public Optional<List> getListById(@PathVariable Long listId) {
        return listRepository.findById(listId);
    }

    @PostMapping(path = "", produces = "application/json")
    public @ResponseBody ResponseEntity<Map<String, String>> createList(@RequestBody ListDto listDto) {
        Map<String, String> response = new HashMap<>();
        try {
            Optional<User> userFound = userRepository.findById(listDto.getUserId());
            if (userFound.isPresent()) {
                List list = new List();
                list.setTag(listDto.getTag());
                list.setTitle(listDto.getTitle());
                list.setUserId(userFound.get().getUserId());
                listRepository.save(list);
                response.put("message", "List enregistrée avec succès");
                response.put("user", list.toString());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new Exception("User ID not found");
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "", produces = "application/json")
    public ResponseEntity<Map<String, String>> updateList(@RequestBody ListPutDto listPutDto) {
        Map<String, String> response = new HashMap<>();
        try {
            Optional<List> existingListOptional = listRepository.findById(listPutDto.getListId());

            if (existingListOptional.isPresent()) {
                List existingList = existingListOptional.get();
                if (existingList.getListId().equals(listPutDto.getListId())) {
                    existingList.setTag(listPutDto.getTag());
                    existingList.setTitle(listPutDto.getTitle());
                    existingList.setUpdateTime(listPutDto.getUpdateTime());

                    listRepository.save(existingList);

                    response.put("message", "List mise à jour avec succès");
                    response.put("list", existingList.toString());

                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    throw new Exception("this list does not belong to this user");
                }
            } else {
                throw new Exception("List not found with ID: " + listPutDto.getListId());
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "", produces = "application/json")
    public Optional<java.util.List<List>> getUserLists(@RequestBody ListGetDto listGetDto) {
        return listRepository.findByUserId(listGetDto.getUserId());
    }
}
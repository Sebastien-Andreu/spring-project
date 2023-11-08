package sebastien.andreu.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sebastien.andreu.spring.dto.list.ListDto;
import sebastien.andreu.spring.dto.picture.PictureByList;
import sebastien.andreu.spring.dto.picture.PictureByUser;
import sebastien.andreu.spring.dto.picture.PictureDto;
import sebastien.andreu.spring.entity.Picture;
import sebastien.andreu.spring.entity.User;
import sebastien.andreu.spring.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import sebastien.andreu.spring.repository.PictureStockRepository;
import sebastien.andreu.spring.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/picture")
public class PictureController {

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/user", produces = "application/json")
    public Optional<List<Picture>> getUserPictures(@RequestBody PictureByUser pictureByUser) {
        return pictureRepository.findByUserId(pictureByUser.getUserId());
    }

    @PostMapping(path = "", produces = "application/json")
    public @ResponseBody ResponseEntity<Map<String, String>> createList(@RequestBody PictureDto pictureDto) {
        Map<String, String> response = new HashMap<>();
        try {
            Optional<User> userFound = userRepository.findById(pictureDto.getUserId());
            if (userFound.isPresent()) {
                Picture picture = new Picture();
                picture.setTag(pictureDto.getTag());
                picture.setUserId(pictureDto.getUserId());


                /*
                * IL FAUT FAIRE L'implementation de l'image stockage etc
                * */

                pictureRepository.save(picture);
                response.put("message", "List enregistrée avec succès");
                response.put("user", picture.toString());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new Exception("User ID not found");
            }
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
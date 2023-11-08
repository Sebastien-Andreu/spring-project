package sebastien.andreu.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sebastien.andreu.spring.dto.list.ListDto;
import sebastien.andreu.spring.dto.picture.*;
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
    public @ResponseBody ResponseEntity<Map<String, String>> addPictures(@RequestBody AddPictureDto addPictureDto) {
        Map<String, String> response = new HashMap<>();
        addPictureDto.getList().forEach(pictureDto -> {
            Optional<User> userFound = userRepository.findById(pictureDto.getUserId());
            if (userFound.isPresent()) {
                Picture picture = new Picture();
                picture.setTag(pictureDto.getTag());
                picture.setUserId(pictureDto.getUserId());
                pictureRepository.save(picture);

                response.put("message", "Picture enregistrée avec succès");
            } else {
                response.put("error", "User Id not found");
            }
        });

        if (response.containsKey("error")) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @DeleteMapping(path = "", produces = "application/json")
    public @ResponseBody ResponseEntity<Map<String, String>> deletePictures(@RequestBody DeletePictureDto deletePictureDto) {
        Map<String, String> response = new HashMap<>();

        deletePictureDto.getList().forEach(pictureDtoDelete -> {
            Optional<Picture> picture = pictureRepository.findById(pictureDtoDelete.getPictureId());
            if (picture.isPresent()) {
                pictureRepository.delete(picture.get());
                response.put("message", "Picture suprimée avec succès");
            } else {
                response.put("error", "Picture Id not found");
            }
        });

        if (response.containsKey("error")) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
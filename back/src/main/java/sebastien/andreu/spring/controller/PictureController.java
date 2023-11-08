package sebastien.andreu.spring.controller;

import org.springframework.web.bind.annotation.*;
import sebastien.andreu.spring.entity.Picture;
import sebastien.andreu.spring.repository.PictureRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@RestController
@RequestMapping("/api/picture")
public class PictureController {

    @Autowired
    private PictureRepository pictureRepository;

    @GetMapping("/user/{userId}")
    public Optional<Picture> getUserPictures(@PathVariable Long userId) {
        return pictureRepository.findById(userId);
    }

    @GetMapping("/list")
    public Optional<Picture> getPicturesInList(@RequestBody Long listId) {
        return pictureRepository.findById(listId);
    }
}
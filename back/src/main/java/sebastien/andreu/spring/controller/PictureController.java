package sebastien.andreu.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sebastien.andreu.spring.dto.list.ListDto;
import sebastien.andreu.spring.dto.picture.*;
import sebastien.andreu.spring.entity.Picture;
import sebastien.andreu.spring.entity.PictureStock;
import sebastien.andreu.spring.entity.Rank;
import sebastien.andreu.spring.entity.User;
import sebastien.andreu.spring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@RestController
@RequestMapping("/api/picture")
public class PictureController {

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PictureStockRepository pictureStockRepository;

    @Autowired
    private RankRepository rankRepository;

    @GetMapping(path = "/user", produces = "application/json")
    public Optional<List<Picture>> getUserPictures(@RequestBody PictureByUser pictureByUser) {
        return pictureRepository.findByUserId(pictureByUser.getUserId());
    }

    @GetMapping(path = "/list", produces = "application/json")
    public Optional<List<Picture>> getRankPictures(@RequestBody PictureByList pictureByList) {
        List<Picture> returnList = new ArrayList<>();
        Optional<List<Rank>> ranks = rankRepository.findByListId(pictureByList.getListId());
        ranks.ifPresent(rankList -> rankList.forEach(rank -> {
            Optional<List<PictureStock>> lists = pictureStockRepository.findByRankId(rank.getRankId());
            lists.ifPresent(pictureStocks -> pictureStocks.forEach(pictureStock -> {
                Optional<List<Picture>> listPicture = pictureRepository.findAllById(pictureStock.getPictureId());
                listPicture.ifPresent(returnList::addAll);
            }));
        }));
        return Optional.of(returnList);
    }

    @PostMapping(path = "", produces = "application/json")
    public @ResponseBody ResponseEntity<Map<String, String>> addPictures(@RequestBody AddPictureDto addPictureDto) {
        Map<String, String> response = new HashMap<>();
        addPictureDto.getList().forEach(pictureDto -> {
            Optional<User> userFound = userRepository.findById(pictureDto.getUserId());
            if (userFound.isPresent()) {
                if (pictureDto.getPictureId() == null) {
                    Picture picture = new Picture();
                    picture.setTag(pictureDto.getTag());
                    picture.setUserId(pictureDto.getUserId());
                    pictureRepository.save(picture);

                    Picture pictures = pictureRepository.findFirstByOrderByPictureIdDesc();

                    PictureStock stock = new PictureStock();
                    stock.setPictureId(pictures.getPictureId());
                    stock.setRankId(pictureDto.getRankId());

                    pictureStockRepository.save(stock);

                    response.put("message", "Picture enregistrée avec succès");
                } else {
                    Optional<Picture> pictures = pictureRepository.findById(pictureDto.getPictureId());
                    if (pictures.isPresent()) {
                        PictureStock stock = new PictureStock();
                        stock.setPictureId(pictures.get().getPictureId());
                        stock.setRankId(pictureDto.getRankId());

                        pictureStockRepository.save(stock);
                    }
                }
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
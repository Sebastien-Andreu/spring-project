package sebastien.andreu.spring.controller;

import org.hibernate.mapping.Any;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sebastien.andreu.spring.dto.list.ListDto;
import sebastien.andreu.spring.dto.picture.*;
import sebastien.andreu.spring.entity.Picture;
import sebastien.andreu.spring.entity.PictureStock;
import sebastien.andreu.spring.entity.Rank;
import sebastien.andreu.spring.entity.User;
import sebastien.andreu.spring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;
import java.io.File;

@RestController
@RequestMapping("/api/picture")
public class PictureController {

    private static final String IMAGE_UPLOAD_DIR = "./images/";

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

    @GetMapping(path = "/tag/{tag}", produces = "application/json")
    public Optional<List<Picture>> getTagPicture(@PathVariable String tag) {
        return pictureRepository.findByTagContaining(tag);
    }
    @GetMapping(path = "/rank/{rankId}", produces = "application/json")
    public List<Picture> getRankPicture(@PathVariable Long rankId) {
        Optional<List<PictureStock>> pictureStock = pictureStockRepository.findByRankId(rankId);
        List<Picture> test = new ArrayList<>();
        pictureStock.ifPresent(pictureStocks -> pictureStocks.forEach(pictureStock1 -> {
            pictureRepository.findById(pictureStock1.getPictureId()).ifPresent(test::add);
        }));

        return test;
    }

    @GetMapping(path = "/list/{listId}", produces = "application/json")
    public Optional<List<Picture>> getRankPictures(@PathVariable Long listId) {
        List<Picture> returnList = new ArrayList<>();
        Optional<List<Rank>> ranks = rankRepository.findByListId(listId);
        ranks.ifPresent(rankList -> rankList.forEach(rank -> {
            Optional<List<PictureStock>> lists = pictureStockRepository.findByRankId(rank.getRankId());
            lists.ifPresent(pictureStocks -> pictureStocks.forEach(pictureStock -> {
                Optional<List<Picture>> listPicture = pictureRepository.findAllByPictureId(pictureStock.getPictureId());
                listPicture.ifPresent(returnList::addAll);
            }));
        }));
        return Optional.of(returnList);
    }

    @PostMapping(value = "/upload", produces = "application/json")
    public @ResponseBody ResponseEntity<Map<String, String>> uploadImage(@RequestBody PictureUpload pictureUpload) {
        Map<String, String> response = new HashMap<>();
        Optional<Picture> pictures = pictureRepository.findById(pictureUpload.getPictureId());
        if (pictures.isPresent()) {
            PictureStock stock = new PictureStock();
            stock.setPictureId(pictures.get().getPictureId());
            stock.setRankId(pictureUpload.getRankId());

            pictureStockRepository.save(stock);
            response.put("message", "Picture enregistrée avec succès");

        } else {
            response.put("error", "Picture not found");
        }

        if (response.containsKey("error")) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/uploadPicture", consumes = "multipart/form-data", produces = "application/json")
    public @ResponseBody ResponseEntity<Map<String, String>> uploadPicture(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "rankId") Long rankId,
            @RequestParam(value = "tag") String tag
    ) {
        Map<String, String> response = new HashMap<>();

        File uploadDir = new File(IMAGE_UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        Optional<User> userFound = userRepository.findById(userId);
        if (userFound.isPresent()) {
            String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            Picture picture = new Picture();
            picture.setTag(tag);
            picture.setUserId(userId);
            picture.setUrl(uniqueFileName);
            pictureRepository.save(picture);

            Picture pictures = pictureRepository.findFirstByOrderByPictureIdDesc();

            PictureStock stock = new PictureStock();
            stock.setPictureId(pictures.getPictureId());
            stock.setRankId(rankId);


            File uploadedFile = new File(uploadDir.getAbsolutePath() + "/" + uniqueFileName);
            try {
                file.transferTo(uploadedFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            pictureStockRepository.save(stock);

            response.put("message", "Picture enregistrée avec succès");
        } else {
            response.put("error", "User Id not found");
        }

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
                String fileName = picture.get().getUrl();
                File fileToDelete = new File(IMAGE_UPLOAD_DIR + fileName);
                if (fileToDelete.exists()) {
                    fileToDelete.delete();
                }

                pictureStockRepository.deleteByPictureId(picture.get().getPictureId());
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

    @DeleteMapping(path = "/all", produces = "application/json")
    public @ResponseBody ResponseEntity<Map<String, String>> deleteAllPicturesByList(@RequestBody PictureByUser pictureByUser) {
        Map<String, String> response = new HashMap<>();
        Optional<List<Picture>> list = pictureRepository.findByUserId(pictureByUser.getUserId());
        list.ifPresent(pictures -> pictures.forEach(toDelete  -> {
            Optional<Picture> picture = pictureRepository.findById(toDelete.getPictureId());
            if (picture.isPresent()) {
                String fileName = picture.get().getUrl();
                File fileToDelete = new File(IMAGE_UPLOAD_DIR + fileName);
                if (fileToDelete.exists()) {
                    fileToDelete.delete();
                }

                pictureStockRepository.deleteByPictureId(picture.get().getPictureId());
                pictureRepository.delete(picture.get());
                response.put("message", "Picture suprimée avec succès");
            } else {
                response.put("error", "Picture Id not found");
            }
        }));
        if (response.containsKey("error")) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping(path = "/download/{pictureId}")
    public ResponseEntity downloadImage(@PathVariable long pictureId) {
        try {
            Optional<Picture> picture = pictureRepository.findById(pictureId);
            if (picture.isPresent() /*picture.get().isAvailable()*/) {
                System.out.println(IMAGE_UPLOAD_DIR + picture.get().getUrl());
                File imageFile = new File(IMAGE_UPLOAD_DIR + picture.get().getUrl());
                if (!imageFile.exists()) {
                    System.out.println("not found");
                    return ResponseEntity.notFound().build();
                }
                Resource resource = new FileSystemResource(imageFile);
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
            } else {
                return new ResponseEntity<String>("picture not found", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
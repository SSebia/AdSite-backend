package lt.almantas.ad.controllers;

import lt.almantas.ad.model.Entity.Ad;
import lt.almantas.ad.model.Entity.AdComment;
import lt.almantas.ad.model.Entity.User;
import lt.almantas.ad.model.dto.AdCommentCreationDTO;
import lt.almantas.ad.model.dto.AdCreateDTO;
import lt.almantas.ad.model.dto.AdEditDTO;
import lt.almantas.ad.model.fdto.AdCommentsFDTO;
import lt.almantas.ad.services.AdService;
import lt.almantas.ad.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/ads")
@Validated
public class AdsController {
    private final AdService adService;
    private final UserService userService;

    @Autowired
    public AdsController(AdService adService, UserService userService) {
        this.adService = adService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Ad>> getAds(@RequestParam(required = false, name = "catID") Integer catID) {
        List<Ad> ads = adService.getAds(catID);
        return new ResponseEntity<>(ads, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Ad>> searchAds(@RequestParam String title, @RequestParam(required = false, name = "page") Integer page) {
        List<Ad> ads = adService.searchAds(title);
        if (page != null && page > 0) {
            int booksPerPage = 8;
            int maxPages = (int)Math.ceil((double) ads.size() / booksPerPage);
            int startIndex = (page - 1) * booksPerPage;
            int endIndex = Math.min(startIndex + booksPerPage, ads.size());
            ads = ads.subList(startIndex, endIndex);
            return new ResponseEntity<>(ads, (page == maxPages ? HttpStatus.ACCEPTED : HttpStatus.OK));
        }
        return new ResponseEntity<>(ads, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Ad> createBook(@RequestBody AdCreateDTO adCreateDTO) {
        Ad createdAd = adService.createAd(adCreateDTO);
        return new ResponseEntity<>(createdAd, HttpStatus.CREATED);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Ad> editBook(@PathVariable Long id, @RequestBody AdEditDTO adEditDTO) {
        Ad editedAd = adService.editAd(id, adEditDTO);
        return (editedAd != null ? new ResponseEntity<>(editedAd, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<List<AdCommentsFDTO>> getBookComments(@PathVariable Long id) {
        List<AdCommentsFDTO> comments = adService.getAdComments(id);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        adService.deleteAd(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/comments/{id}")
    public ResponseEntity<AdComment> addComment(@PathVariable Long id, @RequestBody AdCommentCreationDTO adCommentCreationDTO) {
        User requester = userService.getCurrentLoggedInUser();
        AdComment adComment = adService.addComment(id, adCommentCreationDTO, requester);
        return new ResponseEntity<>(adComment, HttpStatus.CREATED);
    }
}

package lt.almantas.ad.services;

import lombok.RequiredArgsConstructor;
import lt.almantas.ad.model.Entity.*;
import lt.almantas.ad.model.dto.AdCommentCreationDTO;
import lt.almantas.ad.model.dto.AdCreateDTO;
import lt.almantas.ad.model.dto.AdEditDTO;
import lt.almantas.ad.model.fdto.AdCommentsFDTO;
import lt.almantas.ad.repositories.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdService {
    private final AdRepository adRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public List<Ad> getAds(Integer catID) {
        if (catID != null) {
            return adRepository.findAllByCategory_Id(catID);
        }
        return adRepository.findAll();
    }

    public List<Ad> searchAds(String search) {
        return adRepository.findAllByTitleContainingIgnoreCase(search);
    }

    public Ad createAd(AdCreateDTO adCreateDTO) {
        Ad ad = new Ad(adCreateDTO);
        categoryRepository.findById(adCreateDTO.getCatID()).ifPresent(ad::setCategory);
        return adRepository.save(ad);
    }

    public Ad editAd(Long id, AdEditDTO adEditDTO) {
        Optional<Ad> findAd = adRepository.findById(id);
        if (findAd.isEmpty()) {
            return null;
        }
        Ad currentAd = findAd.get();
        currentAd.setTitle(adEditDTO.getTitle());
        currentAd.setDescription(adEditDTO.getDescription());
        currentAd.setPrice(adEditDTO.getPrice());
        currentAd.setCity(adEditDTO.getCity());
        categoryRepository.findById(adEditDTO.getCatID()).ifPresent(currentAd::setCategory);
        return adRepository.save(currentAd);
    }

    public void deleteAd(Long id) {
        adRepository.deleteById(id);
    }

    public List<AdCommentsFDTO> getAdComments(Long id) {
        Ad ad = adRepository.findById(id).orElse(null);
        if (ad == null) { return null; }
        List<AdCommentsFDTO> adCommentsFDTO = new ArrayList<>();
        for (AdComment adComment : ad.getComments()) {
            AdCommentsFDTO adCommentsFDTO1 = new AdCommentsFDTO();
            adCommentsFDTO1.setUsername(adComment.getUser().getUsername());
            adCommentsFDTO1.setComment(adComment.getComment());
            adCommentsFDTO.add(adCommentsFDTO1);
        }
        return adCommentsFDTO;
    }

    public AdComment addComment(Long adID, AdCommentCreationDTO adCommentCreationDTO, User user) {
        Ad ad = adRepository.findById(adID).orElse(null);
        if (ad == null || user == null) { return null; }
        AdComment adComment = new AdComment();
        adComment.setAd(ad);
        adComment.setUser(user);
        adComment.setComment(adCommentCreationDTO.getComment());
        ad.addComment(adComment);
        adRepository.save(ad);
        return adComment;
    }
}

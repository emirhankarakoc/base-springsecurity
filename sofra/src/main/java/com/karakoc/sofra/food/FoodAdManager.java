package com.karakoc.sofra.food;

import com.karakoc.sofra.cloudinary.service.CloudinaryService;
import com.karakoc.sofra.exceptions.general.BadRequestException;
import com.karakoc.sofra.exceptions.general.NotfoundException;
import com.karakoc.sofra.exceptions.strings.ExceptionMessages;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.karakoc.sofra.food.FoodAd.foodAdToDTO;
import static com.karakoc.sofra.food.FoodAd.foodAdsToShortDTOS;

@Service
@Slf4j
@AllArgsConstructor
public class FoodAdManager implements FoodAdService {
    private final FoodAdRepository repository;
    private final ExceptionMessages messages;
    private final CloudinaryService cloudinaryService;

    @Override
    public FoodAdDTO createFoodAd(CreateFoodAdRequest request,String sellerId) {
        FoodAd ad = new FoodAd();
        ad.setId(UUID.randomUUID().toString());
        ad.setName(request.getName());
        ad.setPrice(request.getPrice());
        ad.setDescription(request.getDescription());
        ad.setLatitude(request.getLatitude());
        ad.setLongitude(request.getLongitude());
        ad.setSellerId(sellerId);
        ad.setAdStatus(AdStatus.LIVE);
        ad.setSharedAt(LocalDateTime.now());
        try {
            if (request.getMultipartFile().isEmpty()) {
                throw new BadRequestException("Empty file.");
            }
            // Cloudinary'ye yükle
            Map uploadResult = cloudinaryService.upload(request.getMultipartFile());
            String photoPath = (String) uploadResult.get("url"); // Cloudinary'den gelen URL'yi al
            ad.setCloudImageId((String) uploadResult.get("public_id"));
            ad.setPhotoPath(photoPath); // Ürünün fotoğraf yolunu ayarla
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new BadRequestException("Something went wrong at uploading image.");
        }

        return foodAdToDTO(repository.save(ad));
    }

    @Override
    public void deleteFoodAd(String id) throws IOException {
        FoodAd ad = repository.findById(id).orElseThrow(()->new NotfoundException(messages.getFOOD_NOT_FOUND_404()));
        ad.setAdStatus(AdStatus.REMOVED);
        cloudinaryService.delete(ad.getCloudImageId());
        repository.save(ad);
    }

    @Override
    public List<FoodAdShortDTO> getAllMyAds(String sellerId) {
        List<FoodAd> ads = repository.findAllBySellerId(sellerId);
        return foodAdsToShortDTOS(ads);
    }
}

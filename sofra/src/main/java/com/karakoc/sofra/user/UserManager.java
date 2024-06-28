package com.karakoc.sofra.user;

import com.karakoc.sofra.cloudinary.service.CloudinaryService;
import com.karakoc.sofra.exceptions.general.BadRequestException;
import com.karakoc.sofra.exceptions.general.NotfoundException;
import com.karakoc.sofra.exceptions.strings.ExceptionMessages;
import com.karakoc.sofra.food.*;
import com.karakoc.sofra.security.UserPrincipal;
import com.karakoc.sofra.security.WebSecurityConfig;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.karakoc.sofra.food.FoodAd.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserManager implements UserService{

    private final UserRepository repository;
    private final FoodAdRepository foodAdRepository;
    private final ExceptionMessages messages;
    private final WebSecurityConfig webSecurityConfig;

    @Override
    public UserDTO createUser(String email, String password) {
        // Kullanıcının zaten mevcut olup olmadığını kontrol edin
        if (repository.findUserByEmail(email).isPresent()) {
            throw new BadRequestException("Email already exists");
        }

        // Kullanıcı oluşturun ve şifreyi şifreleyin
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(email);
        user.setPassword(webSecurityConfig.passwordEncoder().encode(password));
        user.setRole(Roles.ROLE_USER.toString());
        user.setExtraInfo("");
        // Kullanıcıyı veritabanına kaydedin
        log.info("An user signed up system with " +user.getEmail()+" mail adress. ");
        return User.userToDTO(repository.save(user));
    }
    @Override
    public UserDTO getUser(String email){
        User user = repository.findUserByEmail(email).orElseThrow(()-> new NotfoundException(messages.getUSER_NOT_FOUND_404()));
        var dto = User.userToDTO(user);
        return dto;
    }

    @Override
    public UserDTO switchUserType(String userId) {
       User user = repository.findById(userId).orElseThrow(()-> new NotfoundException(messages.getUSER_NOT_FOUND_404()));
       user.setRole(Roles.ROLE_SELLER.toString());
       repository.save(user);
       log.info("Id:" + userId + ", changed account type to Seller.!");
       return User.userToDTO(user);
    }
    @Override
    public String deleteUser(String email){
        User user = repository.findUserByEmail(email).orElseThrow(()-> new NotfoundException(messages.getUSER_NOT_FOUND_404()));
        repository.delete(user);
        return "An user deleted with given email adress:" + user.getEmail();
    }

    @Override
    public UserDTO increaseAccountBalance(String userId,double amount){
        User user = repository.findById(userId).orElseThrow(()-> new NotfoundException(messages.getUSER_NOT_FOUND_404()));
        double userbalance = user.getBalance();
        user.setBalance(userbalance+amount);
        return User.userToDTO(repository.save(user));
    }

    @Override
    public List<FoodAdShortDTO> listAllFoodAdShorts() {
        return foodAdsToShortDTOS(foodAdRepository.findAllByAdStatus(AdStatus.LIVE));
    }

    @Override
    public List<FoodAdDTO> listAllFoodAds() {
        return foodAdsToDTOS(foodAdRepository.findAllByAdStatus(AdStatus.LIVE));
    }

    @Override
    public FoodAdDTO getFoodAdDTO(String id) {
        return foodAdToDTO(foodAdRepository.findById(id).orElseThrow(()-> new NotfoundException(messages.getFOOD_NOT_FOUND_404())));
    }

    @Transactional
    public ResponseEntity buyFoodAd(String adId, String userId) throws IOException {
        User buyer = repository.findById(userId).orElseThrow(()->new NotfoundException(messages.getUSER_NOT_FOUND_404()));
        FoodAd ad = foodAdRepository.findById(adId).orElseThrow(()-> new NotfoundException(messages.getFOOD_NOT_FOUND_404()));
        User seller = repository.findById(ad.getSellerId()).orElseThrow(()->new NotfoundException(messages.getUSER_NOT_FOUND_404()));
        //db fetching done
        if (buyer.getBalance()<ad.getPrice())throw new BadRequestException(messages.getLOW_BALANCE());

        //validations, balance control etc.
        buyer.setBalance(buyer.getBalance() - ad.getPrice());
        repository.save(buyer);
        seller.setBalance(seller.getBalance()+ ad.getPrice());
        repository.save(seller);
        //sell
        ad.setAdStatus(AdStatus.SOLD);
        foodAdRepository.save(ad);
        //put archive.

        return (ResponseEntity) ResponseEntity.accepted();
    }


}

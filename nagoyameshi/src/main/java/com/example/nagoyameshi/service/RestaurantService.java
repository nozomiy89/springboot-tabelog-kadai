package com.example.nagoyameshi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.form.RestaurantEditForm;
import com.example.nagoyameshi.form.RestaurantRegisterForm;
import com.example.nagoyameshi.repository.RestaurantRepository;

@Service
public class RestaurantService {
	private final RestaurantRepository restaurantRepository;
	
	public RestaurantService(RestaurantRepository restaurantRepository) {
		this.restaurantRepository = restaurantRepository;
	}
	
	@Transactional
	public void create(RestaurantRegisterForm restaurantRegisterForm) throws Exception {
		Restaurant restaurant = new Restaurant();
		MultipartFile imageFile = restaurantRegisterForm.getImageFile();
		Time openingTime = getParsedTime(restaurantRegisterForm.getOpeningTime());
		Time closingTime = getParsedTime(restaurantRegisterForm.getClosingTime());
		
		if (!imageFile.isEmpty()) {
			String imageName = imageFile.getOriginalFilename();
			String hashedImageName = generateNewFileName(imageName);
			Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
			copyImageFile(imageFile, filePath);
			restaurant.setImageName(hashedImageName);
		}
		
		restaurant.setName(restaurantRegisterForm.getName());
		restaurant.setCategory(restaurantRegisterForm.getCategory());
		restaurant.setDescription(restaurantRegisterForm.getDescription());
		restaurant.setLowerPrice(restaurantRegisterForm.getLowerPrice());
		restaurant.setUpperPrice(restaurantRegisterForm.getUpperPrice());
		restaurant.setOpeningTime(openingTime);
		restaurant.setClosingTime(closingTime);
		restaurant.setPostalCode(restaurantRegisterForm.getPostalCode());
		restaurant.setAddress(restaurantRegisterForm.getAddress());
		restaurant.setPhoneNumber(restaurantRegisterForm.getPhoneNumber());
		restaurant.setRegularHoliday(restaurantRegisterForm.getRegularHoliday());
		
		restaurantRepository.save(restaurant);
	}
	
	@Transactional
	public void update(RestaurantEditForm restaurantEditForm) throws Exception {
		Restaurant restaurant = restaurantRepository.getReferenceById(restaurantEditForm.getId());
		MultipartFile imageFile = restaurantEditForm.getImageFile();
		Time openingTime = getParsedTime(restaurantEditForm.getOpeningTime());
		Time closingTime = getParsedTime(restaurantEditForm.getClosingTime());
		
		if (!imageFile.isEmpty()){
			String imageName = imageFile.getOriginalFilename();
			String hashedImageName = generateNewFileName(imageName);
			Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
			copyImageFile(imageFile, filePath);
			restaurant.setImageName(hashedImageName);
		}
		
		restaurant.setName(restaurantEditForm.getName());
		restaurant.setCategory(restaurantEditForm.getCategory());
		restaurant.setDescription(restaurantEditForm.getDescription());
		restaurant.setLowerPrice(restaurantEditForm.getLowerPrice());
		restaurant.setUpperPrice(restaurantEditForm.getUpperPrice());
		restaurant.setOpeningTime(openingTime);
		restaurant.setClosingTime(closingTime);
		restaurant.setPostalCode(restaurantEditForm.getPostalCode());
		restaurant.setAddress(restaurantEditForm.getAddress());
		restaurant.setPhoneNumber(restaurantEditForm.getPhoneNumber());
		restaurant.setRegularHoliday(restaurantEditForm.getRegularHoliday());
		
		restaurantRepository.save(restaurant);
	}
	
	// UUIDを使って生成したファイルを返す
	public String generateNewFileName(String fileName) {
		String[] fileNames = fileName.split("\\.");
		for (int i = 0; i < fileNames.length - 1; i++) {
			fileNames[i] = UUID.randomUUID().toString();
		}
		String hashedFileName = String.join(".", fileNames);
		return hashedFileName;
	}
	
	// 画像ファイルを指定したファイルにコピーする
	public void copyImageFile(MultipartFile imageFile, Path filePath) {
		try {
			Files.copy(imageFile.getInputStream(), filePath);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private Time getParsedTime(String from) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return new Time(sdf.parse(from).getTime());
	}
}

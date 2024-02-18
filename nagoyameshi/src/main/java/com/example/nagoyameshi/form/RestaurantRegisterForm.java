package com.example.nagoyameshi.form;

import java.sql.Time;

import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RestaurantRegisterForm {
	@NotBlank(message = "カテゴリを選択してください")
	private Category category;
	
	@NotBlank(message = "店舗名を入力してください")
	private String name;
	
	private MultipartFile imageFile;
	
	@NotBlank(message = "説明を入力してください")
	private String description;
	
	@NotNull(message = "予算（下限）を入力してください")
	@Min(value = 1, message = "1円以上に設定してください。")
	private Integer lowerPrice;
	
	@NotNull(message = "予算（上限）を入力してください")
	@Min(value = 1, message = "1円以上に設定してください。")
	private Integer upperPrice;
	
	@NotNull(message = "開店時間を入力してください")
	private Time openingTime;
	
	@NotNull(message = "閉店時間を入力してください")
	private Time closingTime;
	
	@NotBlank(message = "郵便番号を入力してください")
	private String postalCode;
	
	@NotBlank(message = "住所を入力してください")
	private String address;
	
	@NotBlank(message = "電話番号を入力してください")
	private String phoneNumber;

}

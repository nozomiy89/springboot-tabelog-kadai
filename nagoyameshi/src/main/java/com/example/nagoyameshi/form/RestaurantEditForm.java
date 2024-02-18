package com.example.nagoyameshi.form;

import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestaurantEditForm {
	@NotNull
	private Integer Id;
	
	@NotNull(message = "カテゴリを選択してください")
	private Category category;
	
	@NotBlank(message = "店舗名を入力してください")
	private String name;
	
	private MultipartFile imageFile;
	
	@NotBlank(message = "説明を入力してください")
	private String description;
	
	@NotNull(message = "下限を入力してください")
	@Min(value = 1, message = "1円以上に設定してください。")
	private Integer lowerPrice;
	
	@NotNull(message = "上限を入力してください")
	@Min(value = 1, message = "1円以上に設定してください。")
	private Integer upperPrice;
	
	@NotBlank(message = "開店時間を入力してください")
	private String openingTime;
	
	@NotBlank(message = "閉店時間を入力してください")
	private String closingTime;
	
	@NotBlank(message = "郵便番号を入力してください")
	private String postalCode;
	
	@NotBlank(message = "住所を入力してください")
	private String address;
	
	@NotBlank(message = "電話番号を入力してください")
	private String phoneNumber;
	
	@NotBlank(message = "定休日を入力してください")
	private String regularHoliday;
}

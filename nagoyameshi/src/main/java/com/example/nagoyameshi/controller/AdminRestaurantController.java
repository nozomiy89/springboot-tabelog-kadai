package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Restaurant;
import com.example.nagoyameshi.form.RestaurantEditForm;
import com.example.nagoyameshi.form.RestaurantRegisterForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.RestaurantRepository;
import com.example.nagoyameshi.service.RestaurantService;

@Controller
@RequestMapping("/admin/restaurants")
public class AdminRestaurantController {
	private final RestaurantRepository restaurantRepository;
	private final CategoryRepository categoryRepository;
	private final RestaurantService restaurantService;
	
	public AdminRestaurantController(RestaurantRepository restaurantRepository, CategoryRepository categoryRepository, RestaurantService restaurantService) {
		this.restaurantRepository = restaurantRepository;
		this.categoryRepository = categoryRepository;
		this.restaurantService = restaurantService; 
		}
	
	@GetMapping
	public String index(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, @RequestParam(name = "keyword", required = false)String keyword) {
		Page<Restaurant> restaurantPage;
		
		if (keyword != null && !keyword.isEmpty()) {
			restaurantPage = restaurantRepository.findByNameLike("%" + keyword + "%", pageable);
		} else {
			restaurantPage = restaurantRepository.findAll(pageable);
		}
		
		model.addAttribute("restaurantPage", restaurantPage);
		model.addAttribute("keyword", keyword);
		
		return "admin/restaurants/index";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Integer id, Model model) {
		Restaurant restaurant = restaurantRepository.getReferenceById(id);
		
		model.addAttribute("restaurant", restaurant);
		
		return "admin/restaurants/show";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("restaurantRegisterForm", new RestaurantRegisterForm());
		model.addAttribute("categories", categories);
		return "admin/restaurants/register";
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute @Validated RestaurantRegisterForm restaurantRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {
		if (bindingResult.hasErrors()) {
			return "admin/restaurants/register";
		}
		
		restaurantService.create(restaurantRegisterForm);
		redirectAttributes.addFlashAttribute("successMessage", "店舗を登録しました。");
		
		return "redirect:/admin/restaurants";
	}
	
	@GetMapping("/{id}/edit")
	public String edit(@PathVariable(name = "id") Integer id, Model model) {
		Restaurant restaurant = restaurantRepository.getReferenceById(id);
		String imageName = restaurant.getImageName();
		RestaurantEditForm restaurantEditForm = new RestaurantEditForm(restaurant.getId(), restaurant.getCategory(), restaurant.getName(), null, restaurant.getDescription(), restaurant.getLowerPrice(), restaurant.getUpperPrice(), restaurant.getOpeningTime().toString(), restaurant.getClosingTime().toString(), restaurant.getPostalCode(), restaurant.getAddress(), restaurant.getPhoneNumber(), restaurant.getRegularHoliday());
		List<Category> categories = categoryRepository.findAll();
		
		model.addAttribute("imageName", imageName);
		model.addAttribute("restaurantEditForm", restaurantEditForm);
		model.addAttribute("categories", categories);
		
		return "admin/restaurants/edit";
		}
}
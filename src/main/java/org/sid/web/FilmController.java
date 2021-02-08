package org.sid.web;



import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.sid.dao.CategorieRepository;
import org.sid.dao.FilmRepository;
import org.sid.entities.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;



@Controller
public class FilmController {
	
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@GetMapping("/listFilms")
	public String film(Model model,
			@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "5")int size,
			@RequestParam(name="keyword",defaultValue = "")String mc 
	){ 
		Page<Film> pageFilm=filmRepository.findByTitreContains(mc,PageRequest.of(page, size));
		model.addAttribute("listFilms",pageFilm.getContent());
		model.addAttribute("pages",new int[pageFilm.getTotalPages()]);
		
		model.addAttribute("currentPage",page);
		model.addAttribute("size",size);
		model.addAttribute("keyword",mc);
		
		/*
		 * List<Film> films= filmRepository.findAll(); model.addAttribute("listPatients",films);
		 */
		
		return "film";
	}
	  @GetMapping(path="/home")
	  public String home() {	
		  return "home"; 
	  }
	  
	  @GetMapping(path="/deleteFilm")
	  public String delete(Long id,String keyword,int page,int size) {
		 
		  filmRepository.deleteById(id);

		  return "redirect:/listFilms?page="+page+"&size="+size+"&keyword="+keyword; 
	  }
	 
	  @GetMapping(path="/formFilm")
	  public String formFilm(Model model) {
	
		  model.addAttribute("film",new Film());
		  model.addAttribute("mode","new");
		  model.addAttribute("categorie", categorieRepository.findAll());
		  return "formFilm"; 
	  }
	
		
		  @PostMapping(path="/saveFilm") 
		  public String saveFilm(Model model,@Valid Film
		  film, BindingResult bindingResult, @RequestParam("imageFile") MultipartFile file) throws IOException { 
			 System.out.println(file);
			  if(bindingResult.hasErrors()) {
				  return "formFilm";} 
			  String fileName = StringUtils.cleanPath(file.getOriginalFilename());	
			// System.out.println(fileName);
			//  java.sql.Blob blob =  org.hibernate.Hibernate.createBlob(fileName.getBytes());
			
			  film.setPhoto(Base64.getEncoder().encodeToString(file.getBytes()));
			//  film.setPhoto(Base64.getEncoder().encodeToString(file.getBytes()));  
			// film.setPhoto(fileName);
			 
			  model.addAttribute("film",film); 
			  filmRepository.save(film);
		
			  return "confirmationFilm";
		  }
		 
		
		/*
		 * @PostMapping("/saveFilm") public String saveFilm( Film film) {
		 * filmRepository.save(film);
		 * 
		 * return "confirmationFilm";
		 * 
		 * }
		 */
		  @GetMapping(path="/editFilm")
		  public String editFilm(Model model ,Long id) {
			  Film f= filmRepository.findById(id).get();
			  model.addAttribute("film",f);
			  model.addAttribute("categorie", categorieRepository.findAll());
			  return "formFilm"; 
		  }
		  
		  
			/*
			 * protected void initBinder(HttpServletRequest request,
			 * ServletRequestDataBinder binder) throws ServletException {
			 * 
			 * // Convert multipart object to byte[]
			 * binder.registerCustomEditor(byte[].class, new
			 * ByteArrayMultipartFileEditor());
			 * 
			 * }
			 */
}

	/*
	 * @PostMapping("/uploadImage") public String
	 * uploadImage(@RequestParam("imageFile") MultipartFile multipartFile) { String
	 * value=""; filmRepository.saveImage(imageFile); return value; } }
	 */

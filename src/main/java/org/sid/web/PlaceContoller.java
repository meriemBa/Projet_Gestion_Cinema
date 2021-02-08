package org.sid.web;

import org.sid.dao.PlaceRepository;
import org.sid.dao.SalleRepository;
import org.sid.entities.Film;
import org.sid.entities.Place;
import org.sid.entities.Salle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlaceContoller {
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private SalleRepository salleRepository;
	
	@GetMapping("/listPlace")
	public String listPlace(Model model,Salle salle,
		@RequestParam(name="page",defaultValue = "0") int page,
		@RequestParam(name="size",defaultValue = "5") int size,
		@RequestParam(name="keyword",defaultValue = "")String mc 
		
	){
		Page<Place> pagePlace=placeRepository.findBySalleNameContains(mc,PageRequest.of(page, size));
		model.addAttribute("listPlace",pagePlace.getContent());
		model.addAttribute("pages",new int[pagePlace.getTotalPages()]);
		
		model.addAttribute("currentPage",page);
		model.addAttribute("size",size);
		model.addAttribute("keyword",mc);
		model.addAttribute("salle",salle);
		
		
		return "place.html";
	}
	
	  

	@GetMapping(path="/deletePlace")
	  public String deletePlace(Long id,String keyword,int page,int size) {
		 
		  placeRepository.deleteById(id);

		  return "redirect:/listPlace?page="+page+"&size="+size+"&keyword="+keyword;
	  }
	  @GetMapping(path="/formPlace")
	  public String formPlace(Model model) {
		  model.addAttribute("place",new Place());
		  model.addAttribute("salle",salleRepository.findAll());
			
		  return "formPlace"; 
	  }
	
	  @PostMapping(path="/savePlace")
	  public String savePlace( Place place) {
		
		  placeRepository.save(place);
		  return "confirmationPlace"; 
	  }
	  
	  @GetMapping(path="/editplace")
	  public String editplace(Model model ,Long id) {
		  Place place= placeRepository.findById(id).get();
		  model.addAttribute("salle",salleRepository.findAll());
		  model.addAttribute("place",place);
		  return "formPlace"; 
	  }

}

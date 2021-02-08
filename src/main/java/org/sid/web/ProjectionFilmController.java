package org.sid.web;


import javax.validation.Valid;

import org.sid.dao.FilmRepository;
import org.sid.dao.ProjectionFilmRepository;
import org.sid.dao.SalleRepository;
import org.sid.dao.SeanceRepository;
import org.sid.entities.Categorie;
import org.sid.entities.Film;
import org.sid.entities.ProjectionFilm;
import org.sid.entities.Salle;
import org.sid.entities.Seance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProjectionFilmController {
	
	@Autowired
	private ProjectionFilmRepository projectionRepository;
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private SalleRepository salleRepository;
	@Autowired
	private SeanceRepository seanceRepository;
	
	@GetMapping("/listProjections")
	public String projection(Model model,Film film,Seance seance,Salle salle,
		@RequestParam(name="page",defaultValue = "0")int page,
		@RequestParam(name="size",defaultValue = "5")int size,
		@RequestParam(name="keyword",defaultValue = "")String mc
	) {
		//Page<ProjectionFilm> pageProjection=projectionRepository.findByPrixContains(mc,PageRequest.of(page, size));
		Page<ProjectionFilm> pageProjection=projectionRepository.findByFilmTitreContains(mc,PageRequest.of(page, size));
		model.addAttribute("listProjections", pageProjection.getContent());
		model.addAttribute("pages",new int[pageProjection.getTotalPages()]);
		
		model.addAttribute("currentPage",page);
		model.addAttribute("size",size);
		model.addAttribute("keyword",mc);
		
		model.addAttribute("film",film);
		model.addAttribute("seance",seance);
		model.addAttribute("salle",salle);
		return "projections";
	}
	
	/*
	 * @GetMapping(path="/deleteProjection") public String delete(Long id,String
	 * keyword,int page,int size) {
	 * 
	 * projectionRepository.deleteById(id);
	 * 
	 * return "redirect:/projection?page="+page+"&size="+size+"&keyword="+keyword; }
	 */
	/*
	 * @GetMapping(path="/formProjection") public String formProjection(Model model)
	 * { model.addAttribute("projection",new ProjectionFilm());
	 * model.addAttribute("mode","new"); return "formProjection"; }
	 * 
	 * @PostMapping(path="/saveProjection") public String saveProjection(Model
	 * model,@Valid ProjectionFilm projection, BindingResult bindingResult) {
	 * if(bindingResult.hasErrors()) {return "formCategory";}
	 * model.addAttribute("projection",projection);
	 * projectionRepository.save(projection); return "confirmationProjection"; }
	 * 
	 * @GetMapping(path="/editProjection") public String editProjection(Model model
	 * ,Long id) { ProjectionFilm f= projectionRepository.findById(id).get();
	 * model.addAttribute("categorie",f); model.addAttribute("mode","edit"); return
	 * "formProjection"; }
	 */
	  @GetMapping(path="/deleteProjection")
	  public String delete(Long id,String keyword,int page,int size) {
		 
		  projectionRepository.deleteById(id);

		  return "redirect:/listProjections"; 
	  }
	  @GetMapping(path="/formProjection")
	  public String formProjection(Model model) {
		  model.addAttribute("projectionFilm",new ProjectionFilm());
		  model.addAttribute("film",filmRepository.findAll());
		  model.addAttribute("salle",salleRepository.findAll());
		  model.addAttribute("seance",seanceRepository.findAll());
			
		  return "formProjection"; 
	  }
	
	  @PostMapping(path="/saveProjection")
	  public String saveFilm( ProjectionFilm projectionFilm) {
		
		  projectionRepository.save(projectionFilm);
		  return "confirmationProj"; 
	  }
	  
	  @GetMapping(path="/editProjection")
	  public String editProjection(Model model ,Long id) {
		  ProjectionFilm projectionFilm= projectionRepository.findById(id).get();
		  model.addAttribute("projectionFilm",projectionFilm);
		  model.addAttribute("film", filmRepository.findAll());
		  model.addAttribute("seance", seanceRepository.findAll());
		  model.addAttribute("salle", salleRepository.findAll());
		  return "formProjection"; 
	  }
}

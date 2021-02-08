package org.sid.web;

import java.util.Date;

import javax.validation.Valid;

import org.sid.dao.CinemaRepository;
import org.sid.dao.SeanceRepository;
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
public class SeanceController {
	@Autowired
	SeanceRepository seanceRepository;

	
	@GetMapping("/listeseance")
	public String listseance(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size
			) {
		    Page<Seance> pageseance = seanceRepository.findAll(PageRequest.of(page, size));


		    
		model.addAttribute("listeseance", pageseance.getContent());
		model.addAttribute("pages", new int[pageseance.getTotalPages()]);
		model.addAttribute("currentPage", page);
		

		return "seance";
	}
	//fonction qui permet la suppression d'un cinéma by id
	@GetMapping("/supprimerseance")
	public String supprimerseance(Long id) {
		seanceRepository.deleteById(id);
		return "redirect:/listeseance";
	}
	@GetMapping("/modifierseance")
	public String modifierseance(Model model, Long id) {
		Seance seance =seanceRepository.findById(id).get();
		model.addAttribute("seance",seance);
		

		return "formSeance";
	}
	@PostMapping("/saveseance")
	public String saveseance( Seance seance) {
		
		seanceRepository.save(seance);
		return "confirmationseance";
		
	}
	@GetMapping("/formSeance")
	public String formseance(Model model) {
		model.addAttribute("seance", new Seance());
		
		return "formSeance";
		
	}
}

package org.sid.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.sid.dao.CinemaRepository;
import org.sid.dao.TicketRepository;
import org.sid.dao.VilleRepository;
import org.sid.entities.Cinema;
import org.sid.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class CinemaController {
	@Autowired
	CinemaRepository cinemaRepository;
	@Autowired
    VilleRepository villeRepository;
	@Autowired
    TicketRepository ticketRepository;
	int janv=1; int fev=1; int mars=1;int avr=1; int mai=1; int jui=1;int juil=1; int aou=1; int sept=1;
	int oct=1; int nov=1; int dec=1;
	@GetMapping("/listeCinema")
	public String listCinema(Model model,Ville ville, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "keyword", defaultValue = "") String mc) {
		    Page<Cinema> pageCinema = cinemaRepository.findByNameContains(mc, PageRequest.of(page, size));

//List <Cinema> pageCinema=cinemaRepository.findAll();
//model.addAttribute("listeCinema",pageCinema);
		    
		
		model.addAttribute("listeCinema", pageCinema.getContent());
		model.addAttribute("pages", new int[pageCinema.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword", mc);
		model.addAttribute("ville",ville);

		return "cinemas.html";
	}
	/*
	@GetMapping("/listeVille")
	public List<Ville> allVille() {
		return villeRepository.findAll();
	}*/


	/*
	public String modifierCinema(Model model, Long id, Long id2) {
		Cinema cinema =cinemaRepository.findById(id).get();
		String name = cinema.getName();
		double altitude = cinema.getAltitude();
		double lon = cinema.getLongitude();
		double lat = cinema.getLatitude();
		int nmbr = cinema.getNombreSalles();
		model.addAttribute("cinema", new Cinema(id, altitude, lat, lon, name, nmbr, id2));
		model.addAttribute("cinema",cinema);
		return "formCinema.html";
	}*/
	

	
	@GetMapping("/supprimerCinema")
	public String supprimerCinema(Long id) {
		cinemaRepository.deleteById(id);
		return "redirect:/listeCinema";
	}
	@GetMapping("/modifierCinema")
	public String modifierCinema(Model model, Long id) {
		Cinema cinema =cinemaRepository.findById(id).get();
		model.addAttribute("ville",villeRepository.findAll());
		model.addAttribute("cinema",cinema);
		return "formCinema";
	}
	@GetMapping("/dashboard")
	public String dashboard() {
	
		return "dashboard";
	}
	@GetMapping("/displayBarGraph")
	public String barGraph(Model model) {
		Map<String, Integer> surveyMap = new LinkedHashMap<>();
		
		ticketRepository.findAll().forEach(t->{
			if(t.getProjection().getDateProjection().getMonth()==1) {
				janv=janv+1;
			}
			if(t.getProjection().getDateProjection().getMonth()==2) {
				fev=fev+1;
			}
			if(t.getProjection().getDateProjection().getMonth()==3) {
				mars=mars+1;
			}
			if(t.getProjection().getDateProjection().getMonth()==4) {
				avr=avr+1;
			}
			if(t.getProjection().getDateProjection().getMonth()==5) {
				mai=mai+1;
			}
			if(t.getProjection().getDateProjection().getMonth()==6) {
				jui=jui+1;
			}
			if(t.getProjection().getDateProjection().getMonth()==7) {
				juil=juil+1;
			}
			if(t.getProjection().getDateProjection().getMonth()==8) {
				aou=aou+1;
			}
			if(t.getProjection().getDateProjection().getMonth()==9) {
				sept=sept+1;
			}
			if(t.getProjection().getDateProjection().getMonth()==10) {
				oct=oct+1;
			}
			if(t.getProjection().getDateProjection().getMonth()==11) {
				nov=nov+1;
			}
			if(t.getProjection().getDateProjection().getMonth()==12) {
				dec=dec+1;
			}
			
		});
		surveyMap.put("Janvier", janv);
		surveyMap.put("Fevrier", fev);
		surveyMap.put("Mars", mars);
		surveyMap.put("Avril", avr);
		surveyMap.put("Mai", mai);
		surveyMap.put("Juin", jui);
		surveyMap.put("Juillet", juil);
		surveyMap.put("Aout", aou);
		surveyMap.put("Septembre", sept);
		surveyMap.put("Octobre", oct);
		surveyMap.put("Novembre", nov);
		surveyMap.put("Decembre", dec);
		model.addAttribute("surveyMap", surveyMap);
		return "barGraph";
	}

	@GetMapping("/displayPieChart")
	public String pieChart(Model model) {
		model.addAttribute("pass", 50);
		model.addAttribute("fail", 50);
		return "pieChart";
	}
	@PostMapping("/saveCinema")
	public String saveCinema(Cinema cinema) {
		cinemaRepository.save(cinema);
		
		return "confirmation";
		
	}
	@GetMapping("/formCinema")
	public String formCinema(Model model) {
		model.addAttribute("cinema", new Cinema());
		model.addAttribute("ville",villeRepository.findAll());
		return "formCinema";
		
	}
	
	  @GetMapping(path="/index")
	  public String home() {
		 
		  return "login"; 
	  }
}

package org.sid.web;



import javax.validation.Valid;

import org.sid.dao.TicketRepository;
import org.sid.entities.Ticket;
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
public class TicketController {
	@Autowired
	private TicketRepository ticketRepository;

	
	@GetMapping(path = "/listTicket")
	public String test(Model model,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name="keyword",defaultValue = "")String mc ) {
	
		Page<Ticket> pagetickets = ticketRepository.findByNameClientContains(mc,PageRequest.of(page, size));
		model.addAttribute("listTicket", pagetickets.getContent());
		model.addAttribute("pages", new int[pagetickets.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("size", size);
		return "listTicket";
	}
	
	
	@GetMapping(path = "/deleteTicket")
	public String delete(Long id, String keyword, int page) {

		ticketRepository.deleteById(id);

		return "redirect:/listTicket?page=" + page + "&keyword=" + keyword;
	}

	/*
	 * @GetMapping(path = "/formTickets") public String formTickets(Model model) {
	 * model.addAttribute("ticket", new Ticket()); return "formTickets"; }
	 */
	/*@GetMapping(path = "/index")
	public String Home(Model model) {
		return "index";
	}*/
	
	/*
	 * @PostMapping(path = "/saveTicket") public String savePatient(@Valid Ticket
	 * ticket, BindingResult b, Model model) { if (b.hasErrors()) {
	 * 
	 * model.addAttribute("ticket", ticket); return "formTickets"; } //
	 * patient.setDate(new Date()); ticketRepository.save(ticket); return
	 * "redirect:/listTicket"; }
	 * 
	 * @GetMapping(path = "/edit") public String edit(Model model, int id) { Ticket
	 * ticket = ticketRepository.findById((long) id).get();
	 * model.addAttribute("ticket", ticket); return "formTickets"; }
	 */
}

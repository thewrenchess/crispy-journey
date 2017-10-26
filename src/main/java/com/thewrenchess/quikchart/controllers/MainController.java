package com.thewrenchess.quikchart.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thewrenchess.quikchart.models.Graph;
import com.thewrenchess.quikchart.models.PValue;
import com.thewrenchess.quikchart.models.User;
import com.thewrenchess.quikchart.services.MainService;
import com.thewrenchess.quikchart.validators.UserValidator;

@Controller
public class MainController {
	private final MainService mainService;
	private final UserValidator userValidate;
	
	public MainController(MainService mainService, UserValidator userValidate) {
		this.mainService = mainService;
		this.userValidate = userValidate;
	}
	
	@GetMapping("/")
	public String eventDashboard(Principal principal, Model model) {
		User user = this.mainService.findByUsername(principal.getName());
		if (user == null) {
			return "redirect:/login";
		}
		model.addAttribute("user", user);
		model.addAttribute("graphs", this.mainService.last5Graphs(user.getId()));
		return "dashboardPg";
	}
	
	@GetMapping("/poly")
	public String polyChart(@ModelAttribute("graph") Graph graph) {
		return "polyPg";
	}
	
	@PostMapping("/poly")
	public String addPolyGraph(	Principal principal,
								@Valid @ModelAttribute("graph") Graph graph,
								BindingResult result	) {
		if (result.hasErrors()) {
			return "polyPg";
		}
		User user = this.mainService.findByUsername(principal.getName());
		if (user == null) {
			return "redirect:/login";
		}
		graph = this.mainService.saveGraph(graph, user);
		if (graph == null) {
			return "redirect:/poly";
		}
		return String.format("redirect:/poly/%d", graph.getId());
	}
	
	@GetMapping("/poly/{id}")
	public String polyGraphDetail(	@PathVariable("id") long id,
									@ModelAttribute("graph") Graph graph,
									@ModelAttribute("pairs") PValue pairs,
									Model model) {
		Graph currGraph = this.mainService.findGraphById(id);
		if (currGraph == null) {
			return "redirect:/poly";
		}
		model.addAttribute("currGraph", currGraph);
		model.addAttribute("values", this.mainService.findPValuesByGraphId(id));
		return "polyDetailPg";
	}
	
	@PostMapping("/poly/{id}")
	public String updateGraphDetail(@PathVariable("id") long id,
									@Valid @ModelAttribute("graph") Graph graph,
									BindingResult result,
									Model model) {
		Graph currGraph = this.mainService.findGraphById(id);
		if (currGraph == null) {
			return "redirect:/poly";
		}
		if (result.hasErrors()) {
			model.addAttribute("currGraph", currGraph);
			return "polyDetailPg";
		}
		currGraph.setTitle(graph.getTitle());
		if (graph.getDescription().length() != 0) {
			currGraph.setDescription(graph.getDescription());
		}
		currGraph = this.mainService.updateGraph(currGraph);
		if (currGraph == null) {
			return "redirect:/poly";
		}
		return String.format("redirect:/poly/%d", currGraph.getId());
	}
	
	@PostMapping("/poly/addVal")
	public String addPValueToGraph(@Valid @ModelAttribute("pairs") PValue pairs, BindingResult result) {
		if (result.hasErrors()) {
			return "polyDetailPg";
		}
		pairs = this.mainService.savePValue(pairs);
		if (pairs == null) {
			return "redirect:/";
		}
		Graph graph = this.mainService.getGraphByPValueId(pairs.getId());
		if (graph == null) {
			return "redirect:/";
		}
		return String.format("redirect:/poly/%d", graph.getId());
	}
	
	@GetMapping("/poly/delete/{id}")
	public String deletePValue(@PathVariable("id") long pid) {
		Graph graph = this.mainService.getGraphByPValueId(pid);
		if (graph == null) {
			return "redirect:/";
		}
		this.mainService.deletePValue(pid);
		return String.format("redirect:/poly/%d", graph.getId());
	}
	
	@GetMapping("/login")
	public String loginNReg(@RequestParam(value="logout", required=false) String logout,
							@RequestParam(value="error", required=false) String error,
							@ModelAttribute("user") User user,
							Model model	) {
		if (logout != null) {
			model.addAttribute("logout", "Logout successful!");
		}
		if (error != null) {
			model.addAttribute("error", "Invalid credential. Please try again!");
		}
		return "loginPg";
	}
	
	@PostMapping("/registration")
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result) {
		this.userValidate.validate(user, result);
		if (result.hasErrors()) {
			return "loginPg";
		}
		if (this.mainService.getAllAdmins().size() == 0) {
			this.mainService.saveUserAsAdmin(user);
		} else {
			this.mainService.saveUserAsUser(user);
		}
		return "redirect:/login";
	}
}

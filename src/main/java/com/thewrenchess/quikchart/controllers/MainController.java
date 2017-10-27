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
import com.thewrenchess.quikchart.models.HValue;
import com.thewrenchess.quikchart.models.PValue;
import com.thewrenchess.quikchart.models.PieValue;
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
	
	@GetMapping("/graphs/{id}")
	public String selectGraph(@PathVariable("id") long gid) {
		Graph graph = this.mainService.findGraphById(gid);
		if (graph == null) {
			return "redirect:/";
		}
		if (graph.getType().equals("Histogram")) {
			return String.format("redirect:/histo/%d", graph.getId());
		} else if (graph.getType().equals("Pie")) {
			return String.format("redirect:/pie/%d", graph.getId());
		} else {
			return String.format("redirect:/poly/%d", graph.getId());
		}
	}
	
	@GetMapping("/graphs/delete/{id}")
	public String deleteGraph(@PathVariable("id") long gid) {
		this.mainService.deleteGraph(gid);
		return "redirect:/";
	}
	
	//start of polynomial functions
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
	
	//start of all histogram
	@GetMapping("/histo")
	public String histogram(@ModelAttribute("graph") Graph graph) {
		return "histoPg";
	}
	
	@PostMapping("/histo")
	public String addHistoGraph(Principal principal,
								@Valid @ModelAttribute("graph") Graph graph,
								BindingResult result) {
		if (result.hasErrors()) {
			return "histoPg";
		}
		User user = this.mainService.findByUsername(principal.getName());
		if (user == null) {
			return "redirect:/login";
		}
		graph = this.mainService.saveGraph(graph, user);
		if (graph == null) {
			return "redirect:/histo";
		}
		return String.format("redirect:/histo/%d", graph.getId());
	}
	
	@GetMapping("/histo/{id}")
	public String histoGraphDetail(	@PathVariable("id") long id,
									@ModelAttribute("graph") Graph graph,
									@ModelAttribute("value") HValue value,
									Model model) {
		Graph currGraph = this.mainService.findGraphById(id);
		if (currGraph == null) {
			return "redirect:/poly";
		}
		model.addAttribute("currGraph", currGraph);
		model.addAttribute("values", this.mainService.findHValuesByGraphId(id));
		return "histoDetailPg";
	}
	
	@PostMapping("/histo/addVal")
	public String addHValueToGraph(@Valid @ModelAttribute("value") HValue value, BindingResult result) {
		if (result.hasErrors()) {
			return "histoDetailPg";
		}
		value = this.mainService.saveHValue(value);
		if (value == null) {
			return "redirect:/";
		}
		Graph graph = this.mainService.getGraphByHvalueId(value.getId());
		if (graph == null) {
			return "redirect:/";
		}
		return String.format("redirect:/histo/%d", graph.getId());
	}
	
	@GetMapping("/histo/delete/{id}")
	public String deleteHValue(@PathVariable("id") long hid) {
		Graph graph = this.mainService.getGraphByHvalueId(hid);
		if (graph == null) {
			return "redirect:/";
		}
		this.mainService.deleteHValue(hid);
		return String.format("redirect:/histo/%d", graph.getId());
	}
	
	//start of all piechart
	@GetMapping("/pie")
	public String pieChart(@ModelAttribute("graph") Graph graph) {
		return "piePg";
	}
	
	@PostMapping("/pie")
	public String addPieGraph(	Principal principal,
								@Valid @ModelAttribute("graph") Graph graph,
								BindingResult result	) {
		if (result.hasErrors()) {
			return "piePg";
		}
		User user = this.mainService.findByUsername(principal.getName());
		if (user == null) {
			return "redirect:/login";
		}
		graph = this.mainService.saveGraph(graph, user);
		if (graph == null) {
			return "redirect:/pie";
		}
		return String.format("redirect:/pie/%d", graph.getId());
	}
	
	@GetMapping("/pie/{id}")
	public String pieGraphDetail(	@PathVariable("id") long id,
									@ModelAttribute("graph") Graph graph,
									@ModelAttribute("pairs") PieValue pairs,
									Model model) {
		Graph currGraph = this.mainService.findGraphById(id);
		if (currGraph == null) {
			return "redirect:/poly";
		}
		model.addAttribute("currGraph", currGraph);
		model.addAttribute("values", this.mainService.findPieValuesByGraphId(id));
		return "pieDetailPg";
	}
	
	@PostMapping("/pie/addVal")
	public String addPieValueToGraph(@Valid @ModelAttribute("pairs") PieValue pairs, BindingResult result) {
		if (result.hasErrors()) {
			return "histoDetailPg";
		}
		pairs = this.mainService.savePieValue(pairs);
		if (pairs == null) {
			System.out.println("got here");
			return "redirect:/";
		}
		Graph graph = this.mainService.getGraphByPieValueId(pairs.getId());
		if (graph == null) {
			System.out.println("got here instead");
			return "redirect:/";
		}
		return String.format("redirect:/pie/%d", graph.getId());
	}
	
	@GetMapping("/pie/delete/{id}")
	public String deletePieValue(@PathVariable("id") long pid) {
		Graph graph = this.mainService.getGraphByPieValueId(pid);
		if (graph == null) {
			return "redirect:/";
		}
		this.mainService.deletePieValue(pid);
		return String.format("redirect:/pie/%d", graph.getId());
	}
	
	//login and registration
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

package com.thewrenchess.quikchart.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thewrenchess.quikchart.models.GData;
import com.thewrenchess.quikchart.models.Graph;
import com.thewrenchess.quikchart.models.HValue;
import com.thewrenchess.quikchart.models.PValue;
import com.thewrenchess.quikchart.models.PieValue;
import com.thewrenchess.quikchart.services.MainService;

@RestController
public class AjaxController {
	private final MainService mainService;

	public AjaxController(MainService mainService) {
		this.mainService = mainService;
	}
	
	@GetMapping("/poly/getGraph")
	public String addPolyValue(@RequestParam("gid") long gid) {
		Graph graph = this.mainService.findGraphById(gid);
		List<PValue> pvals = this.mainService.findPValuesByGraphId(gid);
		
		List<Double> xVals = new ArrayList<Double>();
		List<Double> yVals = new ArrayList<Double>();
		for (int i = 0; i < pvals.size(); i++) {
			xVals.add(pvals.get(i).getX());
			yVals.add(pvals.get(i).getY());
		}
		GData data = new GData(graph.getTitle(), graph.getDescription(), xVals, yVals);
		
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();
		return gson.toJson(data);
	}
	
	@GetMapping("/histo/getGraph")
	public String addHistoValue(@RequestParam("gid") long gid) {
		Graph graph = this.mainService.findGraphById(gid);
		List<HValue> hvals = this.mainService.findHValuesByGraphId(gid);
		
		List<Double> vals = new ArrayList<Double>();
		for (int i = 0; i < hvals.size(); i++) {
			vals.add(hvals.get(i).getVal());
		}
		GData data = new GData(graph.getTitle(), graph.getDescription(), vals);

		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();
		return gson.toJson(data);
	}
	
	@GetMapping("/pie/getGraph")
	public String addPieValue(@RequestParam("gid") long gid) {
		Graph graph = this.mainService.findGraphById(gid);
		List<PieValue> pievals = this.mainService.findPieValuesByGraphId(gid);
		
		List<String> names = new ArrayList<String>();
		List<Double> values = new ArrayList<Double>();
		for (int i = 0; i < pievals.size(); i++) {
			names.add(pievals.get(i).getX());
			values.add(pievals.get(i).getY());
		}
		GData data = new GData(names, values, graph.getTitle(), graph.getDescription());
		
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();
		return gson.toJson(data);
	}
}

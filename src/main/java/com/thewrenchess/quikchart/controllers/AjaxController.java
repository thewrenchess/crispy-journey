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
import com.thewrenchess.quikchart.models.PValue;
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
}

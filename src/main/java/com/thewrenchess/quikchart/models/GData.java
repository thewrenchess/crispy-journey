package com.thewrenchess.quikchart.models;

import java.util.List;

public class GData {
	private String title;
	private String description;
	private String data;
	private List<String> xAxis;
	private List<Double> xVals;
	private List<Double> yVals;
	
	public GData() {}
	
	public GData(String title, String description, List<Double> vals) {
		this.title = title;
		this.description = description;
		this.xVals = vals;
	}
	
	public GData(String title, String description, List<Double> xVals, List<Double> yVals) {
		this.title = title;
		this.description = description;
		this.xVals = xVals;
		this.yVals = yVals;
	}
	
	public GData(List<String> xAxis, List<Double> yVals, String title, String description) {
		this.title = title;
		this.description = description;
		this.xAxis = xAxis;
		this.yVals = yVals;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public List<String> getxAxis() {
		return xAxis;
	}

	public void setxAxis(List<String> xAxis) {
		this.xAxis = xAxis;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Double> getxVals() {
		return xVals;
	}

	public void setxVals(List<Double> xVals) {
		this.xVals = xVals;
	}

	public List<Double> getyVals() {
		return yVals;
	}

	public void setyVals(List<Double> yVals) {
		this.yVals = yVals;
	}
}
package com.thewrenchess.quikchart.models;

import java.util.List;

public class GData {
	private String title;
	private String description;
	private String data;
	private List<String> xAxis;
	private List<Integer> yAxis;
	private List<Double> xVals;
	private List<Double> yVals;
	
	public GData() {}
	
	public GData(String title, String description, List<Double> xVals, List<Double> yVals) {
		this.title = title;
		this.description = description;
		this.xVals = xVals;
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

	public List<Integer> getyAxis() {
		return yAxis;
	}

	public void setyAxis(List<Integer> yAxis) {
		this.yAxis = yAxis;
	}
}
package com.thewrenchess.quikchart.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="hvalues")
public class HValue {
	@Id
	@GeneratedValue
	private long id;
	private double val;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="graph_id")
	private Graph graph;
	
	public HValue() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public double getVal() {
		return val;
	}

	public void setVal(double val) {
		this.val = val;
	}
}

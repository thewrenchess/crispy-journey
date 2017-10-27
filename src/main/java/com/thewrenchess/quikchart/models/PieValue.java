package com.thewrenchess.quikchart.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="pievalues")
public class PieValue {
	@Id
	@GeneratedValue
	private long id;
	@Size(min=1)
	private String x;
	private double y;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="graph_id")
	private Graph graph;
	
	public PieValue() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}

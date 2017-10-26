package com.thewrenchess.quikchart.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="graphs")
public class Graph {
	@Id
	@GeneratedValue
	private long id;
	@Size(min=1)
	private String title;
	private String description;
	private String type;
	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToMany(mappedBy="graph", fetch=FetchType.LAZY)
	private List<PValue> pvals;
	
	@OneToMany(mappedBy="graph", fetch=FetchType.LAZY)
	private List<HValue> hvals;
	
	@OneToMany(mappedBy="graph", fetch=FetchType.LAZY)
	private List<PieValue> pievals;
	
	public Graph() {}
	
	@PrePersist
	public void onCreate() {
		this.createdAt = new Date();
	}
	
	@PreUpdate
	public void onUpdate() {
		this.updatedAt = new Date();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<PValue> getPvals() {
		return pvals;
	}

	public void setPvals(List<PValue> pvals) {
		this.pvals = pvals;
	}

	public List<HValue> getHvals() {
		return hvals;
	}

	public void setHvals(List<HValue> hvals) {
		this.hvals = hvals;
	}

	public List<PieValue> getPievals() {
		return pievals;
	}

	public void setPievals(List<PieValue> pievals) {
		this.pievals = pievals;
	}
}

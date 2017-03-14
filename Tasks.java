package ctg.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Tasks implements Serializable {

	/*
	 * id int(11) AI PK Task_Name varchar(100) Task_Description tinytext
	 * Analysis_id int(11) Task_Date_Created datetime Task_Date_Edited datetime
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "analysesId", insertable = false, updatable = false)
	private int analysesId;
	private String taskName;
	private String taskDescription;
	private int step;
	private boolean active;
	
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
	@JoinColumn(name="analysesId", referencedColumnName="id")
	private Analyses analyses;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public int getAnalysesId() {
		return analysesId;
	}
	public void setAnalysesId(int analysesId) {
		this.analysesId = analysesId;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}	
	public Analyses getAnalyses() {
		return analyses;
	}
	public void setAnalyses(Analyses analyses) {
		this.analyses = analyses;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}	
	
	
}

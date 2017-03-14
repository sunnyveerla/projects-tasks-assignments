package ctg.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Analyses implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * Id int(11) PK AnalysisName varchar(100) AnalysisDescription tinytext
	 * AnalysisCategory varchar(45) AnalysisDateCreated datetime
	 * AnalysisDateEdited datetime
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String analysisName;
	private String analysisDescription;
	private String analysisCategory;
	private boolean active;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnalysisName() {
		return analysisName;
	}

	public void setAnalysisName(String analysisName) {
		this.analysisName = analysisName;
	}

	public String getAnalysisDescription() {
		return analysisDescription;
	}

	public void setAnalysisDescription(String analysisDescription) {
		this.analysisDescription = analysisDescription;
	}

	public String getAnalysisCategory() {
		return analysisCategory;
	}

	public void setAnalysisCategory(String analysisCategory) {
		this.analysisCategory = analysisCategory;
	}

	@OneToMany(mappedBy = "analysesId", cascade=CascadeType.ALL)
	private List<Tasks> tasks;

	public List<Tasks> getTasks() {
		return tasks;
	}

	public void setTasks(List<Tasks> tasks) {
		this.tasks = tasks;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}

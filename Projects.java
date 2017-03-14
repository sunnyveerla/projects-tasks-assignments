package ctg.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Projects implements Serializable {
	/*
	 * Id int(11) PK ProjectNumber varchar(45) ProjectName varchar(45)
	 * ProjectDescription tinytext Researcher varchar(45) University varchar(45)
	 * ResearcherAddress varchar(100) InvoiceAddress varchar(100) TotalSamples
	 * int(11) SampleSource varchar(45) Analysis_Id int(11) DateCreated datetime
	 * DateEdited datetime
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	private String projectNumber;
	private String projectName;
	private String projectDescription;
	private String researcher;
	private String university;
	private String researcherAddress;
	private String invoiceAddress;
	private int totalSamples;
	private String sampleSource;
	@Column(name = "Project_Date_Created", insertable = false, updatable = false)
	private Date projectDateCreated;
	@Column(name = "Project_Date_Edited", insertable = false, updatable = false)
	private Date projectDateEdited;
	//private boolean projectFinished;

	private String projectComment;
	//private boolean projectFinished;
	private int projectStatus;
	// private int analysesId;

	public String getProjectComment() {
		return projectComment;
	}

	public void setProjectComment(String projectComment) {
		this.projectComment = projectComment;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public String getResearcher() {
		return researcher;
	}

	public void setResearcher(String researcher) {
		this.researcher = researcher;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getResearcherAddress() {
		return researcherAddress;
	}

	public void setResearcherAddress(String researcherAddress) {
		this.researcherAddress = researcherAddress;
	}

	public String getInvoiceAddress() {
		return invoiceAddress;
	}

	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}

	public int getTotalSamples() {
		return totalSamples;
	}

	public void setTotalSamples(int totalSamples) {
		this.totalSamples = totalSamples;
	}

	public String getSampleSource() {
		return sampleSource;
	}

	public void setSampleSource(String sampleSource) {
		this.sampleSource = sampleSource;
	}

	@ManyToOne
	@JoinColumn(name = "analysesId", updatable = false)
	private Analyses analyses;

	public Analyses getAnalyses() {
		return analyses;
	}

	public void setAnalyses(Analyses analyses) {
		this.analyses = analyses;
	}

	/*public boolean isProjectFinished() {
		return projectFinished;
	}

	public void setProjectFinished(boolean projectFinished) {
		this.projectFinished = projectFinished;
	}*/
	@OneToMany(mappedBy="projectsId")
	private List<TaskAssignments> taskAssignments;

	public List<TaskAssignments> getTaskAssignments() {
		return taskAssignments;
	}

	public void setTaskAssignments(List<TaskAssignments> taskAssignments) {
		this.taskAssignments = taskAssignments;
	}

	public Date getProjectDateCreated() {
		return projectDateCreated;
	}

	public Date getProjectDateEdited() {
		return projectDateEdited;
	}

	/*public boolean isProjectFinished() {
		return projectFinished;
	}

	public void setProjectFinished(boolean projectFinished) {
		this.projectFinished = projectFinished;
	}*/

	public int getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(int projectStatus) {
		this.projectStatus = projectStatus;
	}

	
	
}

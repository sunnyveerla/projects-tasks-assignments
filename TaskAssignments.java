package ctg.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class TaskAssignments implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	/**
	 * d int(11) AI PK Projects_Id int(11) Analyses_Id int(11) Tasks_Id int(11)
	 * Users_Id int(11) Assignments_Date_Created datetime
	 * Assignments_Date_Edited datetime Assignments_Finished bit(1)
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int id;
	private int tasksId;
	private int projectsId;
	private int analysesId;
	private boolean active;
	private int step;
	
	private boolean assignmentsFinished;
	

	private int usersId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProjectsId() {
		return projectsId;
	}

	public void setProjectsId(int projectsId) {
		this.projectsId = projectsId;
	}

	public int getAnalysesId() {
		return analysesId;
	}

	public void setAnalysesId(int analysesId) {
		this.analysesId = analysesId;
	}

	public int getUsersId() {
		return usersId;
	}

	public void setUsersId(int usersId) {
		this.usersId = usersId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "projectsId", insertable = false, updatable = false)
	private Projects projects;

	public Projects getProjects() {
		return projects;
	}

	public void setProjects(Projects projects) {
		this.projects = projects;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "analysesId", insertable = false, updatable = false, nullable = false)
	private Analyses analyses;

	public Analyses getAnalyses() {
		return analyses;
	}

	public void setAnalyses(Analyses analyses) {
		this.analyses = analyses;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tasksId", insertable = false, updatable = false)
	private Tasks tasks;

	public Tasks getTasks() {
		return tasks;
	}

	public void setTasks(Tasks tasks) {
		this.tasks = tasks;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usersId", insertable = false, updatable = false)
	private Users users;

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public int getTasksId() {
		return tasksId;
	}

	public void setTasksId(int tasksId) {
		this.tasksId = tasksId;
	}

	@OneToMany(mappedBy = "assignmentsId")
	// @JoinColumn(name = "id", referencedColumnName = "assignmentsId",
	// insertable = true, updatable = true)
	private List<Entries> entries;

	public List<Entries> getEntries() {
		return entries;
	}

	public void setEntries(List<Entries> entries) {
		this.entries = entries;
	}

	public boolean isAssignmentsFinished() {
		return assignmentsFinished;
	}

	public void setAssignmentsFinished(boolean assignmentsFinished) {
		this.assignmentsFinished = assignmentsFinished;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
	
	
}

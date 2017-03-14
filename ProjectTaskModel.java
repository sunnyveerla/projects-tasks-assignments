package ctg.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "t_projects_tasks")
// @SecondaryTable(name="t_tasks")
public class ProjectTaskModel implements Serializable {

	public ProjectTaskModel() {

	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String project_id;
	private int task_id;
	private String name;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfAssigned;
	private Date dateOfCompletion;
	private int finished;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTask_id() {
		return task_id;
	}

	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public Date getDateOfAssigned() {
		return dateOfAssigned;
	}

	public void setDateOfAssigned(Date dateOfAssigned) {
		this.dateOfAssigned = dateOfAssigned;
	}

	public Date getDateOfCompletion() {
		return dateOfCompletion;
	}

	public void setDateOfCompletion(Date dateOfCompletion) {
		this.dateOfCompletion = dateOfCompletion;
	}

	public int getFinished() {
		return finished;
	}

	public void setFinished(int finished) {
		this.finished = finished;
	}

}

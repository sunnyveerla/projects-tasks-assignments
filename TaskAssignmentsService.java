package ctg.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import ctg.dao.TaskAssignmentsRepository;
import ctg.model.Projects;
import ctg.model.TaskAssignments;

@Service
@Transactional
public class TaskAssignmentsService {
	private final TaskAssignmentsRepository assignmentsRepository;

	@Autowired
	private ProjectsService projectsService;

	public TaskAssignmentsService(TaskAssignmentsRepository assignmentsRepository) {
		this.assignmentsRepository = assignmentsRepository;
	}

	public List<TaskAssignments> findAll() {
		List<TaskAssignments> assignments = new ArrayList<>();
		for (TaskAssignments analysis : assignmentsRepository.findAll()) {
			assignments.add(analysis);
		}
		return assignments;
	}

	public TaskAssignments findById(int id) {
		return assignmentsRepository.findOne(id);
	}

	public void save(TaskAssignments assignment) {
		assignmentsRepository.save(assignment);
	}

	public void saveAll(List<TaskAssignments> assignments) {
		assignmentsRepository.save(assignments);
	}

	public List<TaskAssignments> findByProjectsId(int id) {
		return assignmentsRepository.findByProjectsId(id);
	}

	public List<TaskAssignments> findByProjectsIdAndFinished(int id, boolean finished) {
		return assignmentsRepository.findByProjectsIdAndFinished(id, finished);
	}

	public int getTotalTasksForUserId(int usersId, int finished) {
		return assignmentsRepository.getTotalTasksForUserId(usersId, finished);
	}

	/*
	 * public int setTaskAssignmentFinished(int id, boolean finished) {
	 * 
	 * return assignmentsRepository.setTaskAssignmentFinished(id, finished); }
	 */

	public void insertUpdateTaskAssignments(HttpServletRequest request) {
		List<TaskAssignments> lAssignments = new ArrayList<TaskAssignments>();
		int projectId = Integer.parseInt(request.getParameter("projectsId"));
		int analysesId = Integer.parseInt(request.getParameter("analysesId"));
		String ids[] = request.getParameterValues("ids");
		String tasks[] = request.getParameterValues("taskIds");
		String users[] = request.getParameterValues("users");
		String steps[] = request.getParameterValues("steps");
		boolean active = false;
		for (int i = 0; i < tasks.length; i++) {
			// System.out.println(tasks[i]);

			TaskAssignments tmp = new TaskAssignments();
			// if (!users[i].equals("")) {
			if (ids != null) {
				tmp.setId(Integer.parseInt(ids[i]));
			}
			tmp.setProjectsId(projectId);
			tmp.setAnalysesId(analysesId);
			tmp.setTasksId(Integer.parseInt(tasks[i]));
			tmp.setUsersId(Integer.parseInt(users[i]));
			tmp.setStep(Integer.parseInt(steps[i]));
			if (!users[i].equals("0") && !active) {
				active = true;
				tmp.setActive(active);
			}
			// assignmentsService.save(tmp);
			lAssignments.add(tmp);
			// }

		}
		saveAll(lAssignments);
	}

	public void setTaskAssignmentFinished(@RequestBody TaskAssignments taskAssignments) {
		TaskAssignments tAssignments = new TaskAssignments();
		tAssignments.setId(taskAssignments.getId());
		tAssignments.setAnalysesId(taskAssignments.getAnalysesId());
		tAssignments.setProjectsId(taskAssignments.getProjectsId());
		tAssignments.setTasksId(taskAssignments.getTasksId());
		tAssignments.setUsersId(taskAssignments.getUsersId());
		tAssignments.setAssignmentsFinished(taskAssignments.isAssignmentsFinished());
		tAssignments.setActive(false);
		tAssignments.setStep(taskAssignments.getStep());
		save(tAssignments);
		setNextTaskAssignmentActive(taskAssignments.getProjectsId(), taskAssignments.getStep());
	}

	private void setNextTaskAssignmentActive(int projectsId, int step) {
		// int nextTaskId = assignmentsRepository.getNextTaskId(analysesId,
		// taskAssignmentId);
		TaskAssignments taskAssignment = assignmentsRepository.getNextTaskAssignment(projectsId, step);
		// System.out.println(step);
		Projects project = projectsService.findProject(projectsId);
		if (taskAssignment != null) {
			taskAssignment.setActive(true);
			save(taskAssignment);
			setProjectStatus(project);
		} else {
			project.setProjectStatus(100);
			projectsService.save(project);
		}
	}

	private void setProjectStatus(Projects project) {
		int count = 0;
		int total = project.getTaskAssignments().size();
		if (total > 0) {
			for (TaskAssignments taskAssignment : project.getTaskAssignments()) {
				if (taskAssignment.isAssignmentsFinished()) {
					if (taskAssignment.getUsersId() != 0) {
						count++;
					} else {
						total--;
					}
				}
			}
			project.setProjectStatus((count * 100) / total);
			projectsService.save(project);
		}
	}

	public int getTotalActiveTasksForUserId(int usersId, boolean active) {
		return assignmentsRepository.getTotalActiveTasksForUserId(usersId, active);
	}

	public List<TaskAssignments> findByUserIdAndActive(int usersId, boolean active) {
		return assignmentsRepository.findByUserIdAndActive(usersId, active);
	}

	public List<TaskAssignments> findByUserIdAndFinished(int usersId, boolean finished) {
		return assignmentsRepository.findByUserIdAndFinished(usersId, finished);
	}

	public TaskAssignments findByUserIdAndProjectIdAndActive(int usersId, int projectsId, boolean active) {
		return assignmentsRepository.findByUserIdAndProjectIdAndActive(usersId, projectsId, true);
	}

	public void delete(int id) {
		assignmentsRepository.delete(id);
	}
}

package ctg.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ctg.dao.ProjectsRepository;
import ctg.model.Projects;

@Service
@Transactional
public class ProjectsService {
	private final ProjectsRepository projectsRepository;

	public ProjectsService(ProjectsRepository projectsRepository) {
		this.projectsRepository = projectsRepository;
	}

	public List<Projects> findAll() {
		List<Projects> projects = new ArrayList<>();
		for (Projects project : projectsRepository.findAll()) {
			projects.add(project);
		}
		return projects;
	}

	public Projects findByProjectNumber(String projectNumber) {
		return projectsRepository.findByProjectNumber(projectNumber);
	}

	public List<Projects> findByFinished() {
		return projectsRepository.findByFinished();
	}

	public Projects findProject(int id) {
		return projectsRepository.findOne(id);
	}

	public void save(Projects project) {
		projectsRepository.save(project);
	}

	public int getProjectIdByProjectNumber(String projectNumber) {

		return projectsRepository.findIdByProjectNumber(projectNumber);
	}

	/*public Map<Integer, Integer> getProjectsStatus() {
		Map<Integer, Integer> projectStatus = new HashMap<Integer, Integer>();
		for (Projects project : findAll()) {
			
			
			
			
			if (project.isProjectFinished()) {
				projectStatus.put(project.getId(), 100);
			} else {
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
					projectStatus.put(project.getId(), (count * 100) / total);
				} else {
					projectStatus.put(project.getId(), count);
				}
			}
		}
		return projectStatus;
	}*/

	private List<String> analysisNames;

	public Map<String, List<Projects>> getCurrentProjectsCategories() {
		this.analysisNames = new ArrayList<String>();
		Map<String, List<Projects>> projectCategories = new HashMap<String, List<Projects>>();
		//Map<Integer, Integer> status = getProjectsStatus();
		for (Projects project : findAll()) {
			//if (status.get(project.getId()) < 100) {
			if (project.getProjectStatus() < 100) {
				String analysisName = project.getAnalyses().getAnalysisName();
				if (projectCategories.containsKey(analysisName)) {
					List<Projects> tmp = projectCategories.get(analysisName);
					tmp.add(project);
				} else {
					List<Projects> tmp = new ArrayList<Projects>();
					tmp.add(project);
					projectCategories.put(analysisName, tmp);
					analysisNames.add(analysisName);
				}
			}
		}
		return projectCategories;
	}

	public List<String> getAnalysesNamesOfPendingProjects() {
		getCurrentProjectsCategories();
		return this.analysisNames;
	}

}
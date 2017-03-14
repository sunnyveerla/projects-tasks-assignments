package ctg.service;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ctg.dao.ProjectTaskRepository;
import ctg.model.ProjectTaskModel;


@Service
@Transactional
public class ProjectTaskService {
	private final ProjectTaskRepository projectTaskRepository;
	
	public ProjectTaskService(ProjectTaskRepository projectTaskRepository){
		this.projectTaskRepository = projectTaskRepository;
	}
	public List<ProjectTaskModel> findAll(){
		List<ProjectTaskModel> tasks = new ArrayList<>();
		for(ProjectTaskModel task : projectTaskRepository.findAll()){
			tasks.add(task);
		}
		return tasks;
	}
	
	public ProjectTaskModel findTask(int id){		
		return projectTaskRepository.findOne(id);
	}
	
	/*public List<TaskModel> findByFinished(boolean finished){
		return taskRepository.findByFinished(finished);
	}*/
	
	public void save(ProjectTaskModel task){
		projectTaskRepository.save(task);
	}
	
	/*public void delete(int id){
		projectTaskRepository.delete(id);
	}*/
}

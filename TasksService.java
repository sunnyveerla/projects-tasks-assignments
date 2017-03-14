package ctg.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ctg.dao.TasksRepository;
import ctg.model.Tasks;

@Service
@Transactional
public class TasksService {

	private final TasksRepository tasksRepository;

	public TasksService(TasksRepository tasksRepository) {
		this.tasksRepository = tasksRepository;
	}
	
	public List<Tasks> findAll() {
		List<Tasks> tasks = new ArrayList<>();
		for (Tasks task : tasksRepository.findAll()) {
			tasks.add(task);
		}
		return tasks;
	}
	
	public Tasks findTask(int id){		
		return tasksRepository.findOne(id);
	}
	public void save(Tasks task) {
		tasksRepository.save(task);
	}
	public void saveAll(List<Tasks> tasks) {
		tasksRepository.save(tasks);	
	}
	
}
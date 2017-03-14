package ctg.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ctg.model.ProjectTaskModel;

public interface ProjectTaskRepository extends CrudRepository<ProjectTaskModel, Integer>{
	@Query(value = "SELECT * FROM t_projects_tasks p, t_tasks t WHERE p.task_id = t.task_id", nativeQuery=true)
	List<ProjectTaskModel> findAll();
}

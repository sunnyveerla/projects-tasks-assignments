package ctg.dao;

import org.springframework.data.repository.CrudRepository;

import ctg.model.Tasks;

public interface TasksRepository extends CrudRepository<Tasks,Integer>{

	
}

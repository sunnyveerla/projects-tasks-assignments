package ctg.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ctg.model.TaskAssignments;

public interface TaskAssignmentsRepository extends CrudRepository<TaskAssignments, Integer> {
	@Query(value = "SELECT * FROM task_assignments WHERE PROJECTS_ID=?1 AND USERS_ID!=0", nativeQuery=true)
	List<TaskAssignments> findByProjectsId(int id);
	@Query(value="SELECT COUNT(USERS_ID) FROM task_assignments WHERE USERS_ID=?1 AND ASSIGNMENTS_FINISHED=?2", nativeQuery=true)
	int getTotalTasksForUserId(int user_id,int finished);
	@Query(value="UPDATE TABLE task_assignments SET ASSIGNMENTS_FINISHED=?2  WHERE ID=?1", nativeQuery=true)
	int setTaskAssignmentFinished(int id, boolean finished);
	@Query(value = "SELECT * FROM task_assignments WHERE PROJECTS_ID=?1 AND ASSIGNMENTS_FINISHED=?2", nativeQuery=true)
	List<TaskAssignments> findByProjectsIdAndFinished(int id, boolean finished);
	@Query(value="SELECT * FROM task_assignments WHERE PROJECTS_ID=?1 AND ANALYSES_ID=?2 AND ID=?3 AND USERS_ID!=0", nativeQuery=true)
	TaskAssignments findByProjectsIdAndAnalysesIdAndId(int projectsId,int analysesId, int taskAssignmentId);
	@Query(value="SELECT COUNT(USERS_ID) FROM task_assignments WHERE USERS_ID=?1 AND ACTIVE=?2", nativeQuery=true)
	int getTotalActiveTasksForUserId(int user_id,boolean active);
	@Query(value="SELECT * FROM task_assignments WHERE USERS_ID=?1 AND ACTIVE=?2", nativeQuery=true)
	List<TaskAssignments> findByUserIdAndActive(int usersId, boolean active);
	@Query(value="SELECT * FROM task_assignments WHERE USERS_ID=?1 AND ASSIGNMENTS_FINISHED=?2 ORDER BY ACTIVE DESC", nativeQuery=true)
	List<TaskAssignments> findByUserIdAndFinished(int usersId, boolean finished);
	@Query(value="select * from task_assignments where projects_Id=?1 and Step >?2 and assignments_finished=false and users_id!=0 order by Step asc limit 1", nativeQuery=true)
	TaskAssignments getNextTaskAssignment(int projectsId, int step);
	@Query(value="select * from task_assignments where users_Id=?1 and projects_Id=?2 and active=?3 and assignments_finished=false", nativeQuery=true)
	TaskAssignments findByUserIdAndProjectIdAndActive(int usersId, int projectsId,boolean active);
	
}

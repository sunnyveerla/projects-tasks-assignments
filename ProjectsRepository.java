package ctg.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ctg.model.Projects;

public interface ProjectsRepository extends CrudRepository<Projects, Integer> {
	@Query(value = "SELECT * FROM projects WHERE PROJECT_NUMBER=?1", nativeQuery=true)
	Projects findByProjectNumber(String projectNumber);
	@Query(value="SELECT ID FROM projects WHERE PROJECT_NUMBER=?1", nativeQuery=true)
	int findIdByProjectNumber(String projectNumber);
	@Query(value="SELECT * FROM projects ORDER BY PROJECT_DATE_EDITED DESC", nativeQuery=true)
	List<Projects> findAll();
	@Query(value="SELECT * FROM projects WHERE PROJECT_STATUS=100", nativeQuery=true)
	List<Projects> findByFinished();
	
	//@Query(value="SELECT new Charts(A.ANALYSIS_NAME, COUNT(P.TOTAL_SAMPLES)) FROM PROJECTS P, ANALYSES A WHERE P.ANALYSES_ID = A.ID GROUP BY P.ANALYSES_ID", nativeQuery=true)
	//List<Charts> findByAnalysisCategories();
	//@Query()
}

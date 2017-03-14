package ctg.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ctg.model.Analyses;

public interface AnalysesRepository extends CrudRepository<Analyses, Integer> {
@Query(value="SELECT * FROM analyses WHERE ACTIVE=?1", nativeQuery=true)
List<Analyses> findByActive(boolean active);
}

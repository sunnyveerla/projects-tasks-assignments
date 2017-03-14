package ctg.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import ctg.dao.AnalysesRepository;
import ctg.model.Analyses;
import ctg.model.Tasks;

@Service
@Transactional
public class AnalysesService {
	private final AnalysesRepository analysesRepository;

	public AnalysesService(AnalysesRepository analysesRepository) {
		this.analysesRepository = analysesRepository;
	}

	public List<Analyses> findAll() {
		List<Analyses> analyses = new ArrayList<>();
		for (Analyses analysis : analysesRepository.findAll()) {
			analyses.add(analysis);
		}
		return analyses;
	}
	public List<Analyses>findAllByActive(boolean active){
		return analysesRepository.findByActive(active);
	}
	public Analyses findById(int id) {
		return analysesRepository.findOne(id);
	}

	public void save(Analyses analysis) {
		analysesRepository.save(analysis);
	}
	public void insertUpdateAnalysisTasks(HttpServletRequest request, Analyses analysis){		
		List<Tasks> lTasks = new ArrayList<Tasks>();		
		String ids[] = request.getParameterValues("tasksId");
		String steps[] = request.getParameterValues("tasksStep");
		String tasksName[] = request.getParameterValues("tasksName");
		String tasksDescription[] = request.getParameterValues("tasksDescription");
		for (int i = 0; i < tasksName.length; i++) {
			Tasks tasks = new Tasks();
			if (ids != null) {
				tasks.setId(Integer.parseInt(ids[i]));
			}			
			tasks.setStep(Integer.parseInt(steps[i]));
			tasks.setTaskName(tasksName[i]);
			tasks.setTaskDescription(tasksDescription[i]);
			tasks.setAnalyses(analysis);
			lTasks.add(tasks);
		}
		analysis.setTasks(lTasks);
		save(analysis);
	}
}

package ctg.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ctg.model.Projects;

@Service
public class ChartsService {
	@Autowired
	private ProjectsService projectsService;

	/*public Map<String, Object[]> getProjectSamplesDataPoints() {
		Map<String, Object[]> dataPoints = new HashMap<String, Object[]>(2);
		List<Projects> projects = projectsService.findAll();
		int size = projects.size();
		String[] labels = new String[size];
		Integer[] data = new Integer[size];
		int i = 0;
		for (Projects project : projects) {
			labels[i] = project.getProjectNumber();
			data[i] = project.getTotalSamples();
			i++;
		}
		dataPoints.put("labels", labels);
		dataPoints.put("data", data);
		return (dataPoints);
		// return dataPoints;
	}

	public Map<String, Object[]> getAnalysisDataPoints() {
		List<Projects> projects = projectsService.findAll();
		Map<String, Object[]> dataPoints = new HashMap<String, Object[]>(2);
		Map<String, Integer> analysisTotal = new HashMap<String, Integer>();
		
		for (Projects project : projects) {
			String analysisName = project.getAnalyses().getAnalysisName();
			if (analysisTotal.containsKey(analysisName)) {
				Integer tmp = analysisTotal.get(analysisName) + 1;
				analysisTotal.put(analysisName, tmp);
			} else {
				analysisTotal.put(analysisName, new Integer(1));
			}
		}
		int size = analysisTotal.size();
		String[] labels = new String[size];
		Integer[] data = new Integer[size];
		int i = 0;
		for(Object key:analysisTotal.keySet().toArray()){
			labels[i] = key.toString();
			data[i] = analysisTotal.get(key);
			i++;
		}
		dataPoints.put("labels", labels);
		dataPoints.put("data", data);
		return (dataPoints);

	}*/
	public Map<String, Object[]> getProjectCharts() {
		/*
		 * 			Project Charts include
		 * -----------------------------------------
		 * 		X 				Y				A
		 * -----------------------------------------
		 * 1. Projects		TotalSamples		PT
		 * 2. Year			TotalSamples		YT
		 * 3. Year			Projects			YP
		 * 4. Months		Projects			MP
		 * 5. Months		TotalSamples		MT
		 * 6. Analysis		Projects			AP
		 * 7. Analysis		TotalSamples		AT
		 * 8. Year			Analysis			YA
		 * 9. Months		Analysis			MA
		 */
		
		List<Projects> projects = projectsService.findAll();
		Map<String, Object[]> dataPoints = new HashMap<String, Object[]>(5);		
		Map<String, Integer[]> analysisTotal = new HashMap<String, Integer[]>();
		int size = projects.size();
		String[] pt_Labels = new String[size];
		Integer[] pt_Data = new Integer[size];
		int i = 0;
		for (Projects project : projects) {
			pt_Labels[i] = project.getProjectNumber();
			pt_Data[i] = project.getTotalSamples();
			i++;

			//Analysis<--->Projects, Analysis<---->TotalSamples
			String analysisName = project.getAnalyses().getAnalysisName();
			if (analysisTotal.containsKey(analysisName)) {
				Integer[] tmp = analysisTotal.get(analysisName);
					tmp[0] +=1;
					tmp[1] += project.getTotalSamples();
 				analysisTotal.put(analysisName, tmp);
			} else {
				Integer[] tmp = new Integer[2];
				tmp[0] =1;
				tmp[1] = project.getTotalSamples();
				analysisTotal.put(analysisName, tmp);
			}
		}

		size = analysisTotal.size();
		String[] ap_Labels = new String[size];
		Integer[] ap_Data = new Integer[size];
		Integer[] at_Data = new Integer[size];
		i = 0;
		for(Object key:analysisTotal.keySet().toArray()){
			ap_Labels[i] = key.toString();
			ap_Data[i] = analysisTotal.get(key)[0];
			at_Data[i] = analysisTotal.get(key)[1];
			i++;
		}
		
		dataPoints.put("pt_Labels", pt_Labels);
		dataPoints.put("pt_Data", pt_Data);
		dataPoints.put("ap_Labels", ap_Labels);
		dataPoints.put("ap_Data", ap_Data);
		dataPoints.put("at_Labels", ap_Labels);
		dataPoints.put("at_Data", at_Data);
		return (dataPoints);
		// return dataPoints;
	}

}

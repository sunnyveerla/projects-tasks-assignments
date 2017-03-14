package ctg.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

/*{
    labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
    datasets: [{
        label: '# of Votes',
        data: [12, 19, 3, 5, 2, 3],
        backgroundColor: [
            'rgba(255, 99, 132, 0.2)',
            'rgba(54, 162, 235, 0.2)',
            'rgba(255, 206, 86, 0.2)',
            'rgba(75, 192, 192, 0.2)',
            'rgba(153, 102, 255, 0.2)',
            'rgba(255, 159, 64, 0.2)'
        ],
        borderColor: [
            'rgba(255,99,132,1)',
            'rgba(54, 162, 235, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(153, 102, 255, 1)',
            'rgba(255, 159, 64, 1)'
        ],
        borderWidth: 1
    }]
}*/

@Service
public class ConvertService {
	/*
	 * @Autowired private ProjectsService projectsService;
	 * 
	 * public Map<String,Object[]> getProjectSamplesDataPoints() { Map<String,
	 * Object[]> dataPoints = new HashMap<String, Object[]>(); List<Projects>
	 * projects = projectsService.findAll(); int size = projects.size();
	 * String[] labels = new String[size]; Integer[] data = new Integer[size];
	 * int i = 0; for (Projects project : projects) { labels[i] =
	 * project.getProjectNumber(); data[i] = project.getTotalSamples(); i++; }
	 * dataPoints.put("labels", labels); dataPoints.put("data", data); return
	 * (dataPoints); //return dataPoints; }
	 */

	public Map<String, String> extractFromWord(String file) {
		Map<String, String> infoMap = new HashMap<String, String>();
		try {
			XWPFDocument docx = new XWPFDocument(new FileInputStream(file));
			// using XWPFWordExtractor Class
			XWPFWordExtractor we = new XWPFWordExtractor(docx);
			infoMap = parseText(we.getText());
			// System.out.println("*****" + we.getText());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return infoMap;
	}

	public Map<String, String> extractFromPdf(String file) {
		Map<String, String> infoMap = new HashMap<String, String>();
		try {
			PDDocument doc = PDDocument.load(new File(file));
			if (doc.isEncrypted()) {
				doc.setAllSecurityToBeRemoved(true);
			}
			PDFTextStripper stripper = new PDFTextStripper();
			infoMap = parseText(stripper.getText(doc));
			doc.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return infoMap;
	}

	public Map<String, String> parseText(String text) {
		Map<String, String> infoMap = new HashMap<String, String>();
		String[] sp = text.split("\n");
		for (String s : sp) {
			String[] st = s.split(":");
			if (st.length > 1) {
				String[] ss = st[0].split(" ");
				if (ss.length > 1) {
					String key = ss[0].toLowerCase() + ss[1];
					infoMap.put(key, st[1]);
					System.out.println("Key " + key + " Value " + st[1]);
				}
			}

		}
		return infoMap;
	}

	public String outputJson(List<Map<Object, Object>> dataPoints) {
		Gson gsonObj = new Gson();
		System.out.println(gsonObj.toJson(dataPoints));
		return gsonObj.toJson(dataPoints);
	}

	public ArrayList<String> outputXML(List<Map<Object, Object>> dataPoints) {
		// List<Map<Object, Object>> dataPoints = this.getRandomData(xStart,
		// yStart, length);
		ArrayList<String> rows = new ArrayList<String>();
		rows.add("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		rows.add("<data>");
		for (int i = 0; i < dataPoints.size(); i++) {

			rows.add("<point>");
			rows.add("<x>" + (dataPoints.get(i).get("x")) + "</x>");
			rows.add("<y>" + (dataPoints.get(i).get("y")) + "</y>");
			rows.add("</point>");
		}
		rows.add("</data>");
		return rows;
	}

	public String[] outputCSV(List<Map<Object, Object>> dataPoints) {
		// List<Map<Object, Object>> dataPoints = this.getRandomData(xStart,
		// yStart, length);
		String dpString[] = new String[dataPoints.size()];
		for (int i = 0; i < dataPoints.size(); i++) {
			dpString[i] = dataPoints.get(i).get("x") + "," + dataPoints.get(i).get("y");
		}
		return dpString;
	}
}

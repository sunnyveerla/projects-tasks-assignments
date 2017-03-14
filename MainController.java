package ctg.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import ctg.model.Analyses;
import ctg.model.Entries;
import ctg.model.Projects;
import ctg.model.TaskAssignments;
import ctg.model.Tasks;
import ctg.service.AnalysesService;
import ctg.service.ChartsService;
import ctg.service.ConvertService;
import ctg.service.EntriesService;
import ctg.service.ProjectsService;
import ctg.service.TaskAssignmentsService;
import ctg.service.TasksService;
import ctg.service.UsersService;

@Controller
public class MainController {
	@Autowired
	private ProjectsService projectsService;
	@Autowired
	private TasksService tasksService;
	@Autowired
	private AnalysesService analysesService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private TaskAssignmentsService taskAssignmentsService;
	@Autowired
	private EntriesService entriesService;
	@Autowired
	private ChartsService chartsService;
	@Autowired
	private ConvertService convertService;

	/*
	 * Login
	 */

	/*
	 * @GetMapping("/login") public String login(HttpServletRequest request) {
	 * // request.setAttribute("mode", "MODE_HOME");
	 * 
	 * return "login"; }
	 */

	/*
	 * @GetMapping(value = "/login") public String login(Model model, String
	 * error, String logout) { if (error != null) model.addAttribute("error",
	 * "Your username and password is invalid.");
	 * 
	 * if (logout != null) model.addAttribute("message",
	 * "You have been logged out successfully.");
	 * 
	 * return "login"; }
	 */

	/*
	 * Get the pending assignments count
	 */
	/*
	 * int pcount; int usersId;
	 * 
	 * public void setPendingCount(int usersId) { //
	 * System.out.println(taskAssignmentsService.getTotalTasksForUserId(1, //
	 * 0)); this.pcount = taskAssignmentsService.getTotalTasksForUserId(usersId,
	 * 0); }
	 * 
	 * @PostMapping("/loginCheck") public @ResponseBody String
	 * loginCheck(@RequestBody Users users) { this.usersId =
	 * usersService.validUser(users.getUserName(), users.getUserPassword()); if
	 * (this.usersId != 0) { setPendingCount(this.usersId); return "valid"; }
	 * return "not valid"; }
	 * 
	 * @GetMapping(value = "/getPendingCount") public @ResponseBody String
	 * getPendingCount() { String value = "" + this.pcount;
	 * 
	 * return value; }
	 */

	/*
	 * Home
	 */

	@GetMapping("/home")
	public String home(HttpServletRequest request) {

		// Ongoing projects
		//request.setAttribute("status", projectsService.getProjectsStatus());
		request.setAttribute("analysisNames", projectsService.getAnalysesNamesOfPendingProjects());
		request.setAttribute("categories", projectsService.getCurrentProjectsCategories());
		request.setAttribute("mode", "MODE_HOME");
		return "home";
	}

	/*
	 * Project
	 */

	@GetMapping("/new-project")
	public String newProject(HttpServletRequest request, ModelMap model) {
		request.setAttribute("mode", "MODE_NEW_PROJECT");
		request.setAttribute("project", new Projects());
		model.addAttribute("analysisList", analysesService.findAllByActive(true));
		return "project";
	}

	@PostMapping("/save-project")
	public ModelAndView saveProject(@ModelAttribute("project") Projects project, BindingResult bindingResult,
			HttpServletRequest request) {
		projectsService.save(project);
		return (new ModelAndView("redirect:task-assignments?projectsId=" + project.getId()));
		// return (new ModelAndView("redirect:all-projects"));
	}

	@GetMapping("/update-project")
	public String updateProject(@RequestParam int id, HttpServletRequest request, ModelMap model) {
		// assignmentsService.refresh(id);
		request.setAttribute("project", projectsService.findProject(id));
		model.addAttribute("analysisList", analysesService.findAll());
		request.setAttribute("mode", "MODE_UPDATE_PROJECT");
		return "project";
	}

	@GetMapping("/all-projects")
	public String allProject(HttpServletRequest request) {
		List<Projects> projects = projectsService.findAll();
		request.setAttribute("projects", projects);
		//request.setAttribute("status", projectsService.getProjectsStatus());
		request.setAttribute("mode", "MODE_PROJECTS");
		return "project";
	}
	@GetMapping("/finished-projects")
	public String finishedProjects(HttpServletRequest request) {
		//request.setAttribute("status", projectsService.getProjectsStatus());
		request.setAttribute("projects", projectsService.findByFinished());
		request.setAttribute("mode", "MODE_PROJECTS");
		return "project";
	}
	@GetMapping("/projects-chart")
	public @ResponseBody Map<String, Object[]> projectsChart(RequestBody name) {
		// System.out.println(name.toString());
		/*
		 * switch(name.toString()){ case "Projects": return
		 * chartsService.getProjectSamplesDataPoints(); case "Analysis": return
		 * chartsService.getProjectSamplesDataPoints(); }
		 */
		// System.out.println();
		return chartsService.getProjectCharts();
	}

	/*
	 * Projects Tasks
	 */
	@GetMapping("/task-assignments")
	public String newTaskAssignments(@RequestParam int projectsId, HttpServletRequest request, ModelMap model) {
		request.setAttribute("taskAssignments", new TaskAssignments());
		request.setAttribute("projects", new Projects());
		model.addAttribute("projectInfo", projectsService.findProject(projectsId));
		model.addAttribute("taskAssignmentsList",
				taskAssignmentsService.findByProjectsIdAndFinished(projectsId, false));
		model.addAttribute("userList", usersService.findAll());
		request.setAttribute("mode", "MODE_NEW_TASK_ASSIGNMENT");
		return "taskAssignments";
	}
	
	@PostMapping("/save-task-assignments")
	public ModelAndView saveTaskAssignments(HttpServletRequest request) {

		// Inserting new analyses and tasks of the project
		taskAssignmentsService.insertUpdateTaskAssignments(request);

		// setPendingCount(this.usersId);
		return (new ModelAndView("redirect:all-projects"));
	}

	@GetMapping("/project-tasks-entries")
	public String newTaskEntries(@RequestParam String id, HttpServletRequest request) {
		String sp[] = id.split("#");
		List<TaskAssignments> assignments = taskAssignmentsService.findByProjectsId(Integer.parseInt(sp[0]));
		request.setAttribute("assignments", assignments);
		request.setAttribute("entries", new Entries());
		request.setAttribute("mode", "MODE_PROJECT_TASKS");
		return "projectTasksEntries";
	}

	@PostMapping("/save-ajax-task-entries")
	public @ResponseBody String saveAjaxTaskEntries(@RequestBody Entries entries) {
		entriesService.save(entries);
		return "done";

	}

	/// save-ajax-assignment-finished
	@PostMapping("/save-ajax-assignment-finished")
	public @ResponseBody String saveAjaxAssignmentFinished(@RequestBody TaskAssignments taskAssignments) {
		taskAssignmentsService.setTaskAssignmentFinished(taskAssignments);
		return "done";

	}

	@PostMapping("/save-task-entries")
	public ModelAndView saveTaskEntries(@ModelAttribute("entries") Entries entries, BindingResult bindingResult,
			HttpServletRequest request, @RequestParam int id, ModelMap model) {
		entriesService.save(entries);
		request.setAttribute("mode", "MODE_PROJECT_TASKS");
		request.setAttribute("entries", entries);
		return new ModelAndView(new RedirectView("project-tasks-entries?id=" + id, true));
	}

	/*
	 * @GetMapping("/project-tasks-page") public String
	 * projectTasksPage(@RequestParam int id, HttpServletRequest request) {
	 * return "project-tasks?" + id; }
	 */
	// ******************************//
	/*
	 * Analysis
	 */

	@GetMapping("/new-analysis")
	public String newAnalysis(HttpServletRequest request) {
		request.setAttribute("analysis", new Analyses());
		request.setAttribute("mode", "MODE_NEW_ANALYSIS");
		return "analysisTasks";
	}

	@PostMapping("/save-analysis")
	public String saveAnalysis(@ModelAttribute("analysis") Analyses analysis, BindingResult bindingResult,
			HttpServletRequest request) {
		analysesService.insertUpdateAnalysisTasks(request, analysis);
		request.setAttribute("analyses", analysesService.findAll());
		request.setAttribute("mode", "MODE_ANALYSES");
		return "analysisTasks";
	}

	@GetMapping("/update-analysis")
	public String updateAnalysis(@RequestParam int id, HttpServletRequest request) {
		request.setAttribute("analysis", analysesService.findById(id));
		request.setAttribute("mode", "MODE_UPDATE_ANALYSIS");
		return "analysisTasks";
	}
	@PostMapping("/set-analysis-active-inactive")
	public @ResponseBody String setAnalysisActiveOrInactive(@RequestParam int id, @RequestParam String active){
		Analyses analyses = analysesService.findById(id);
		//System.out.println(Boolean.valueOf(active));
		analyses.setActive(Boolean.valueOf(active));
		analysesService.save(analyses);
		return "done";
	}
	@GetMapping("/all-analysis")
	public String allAnalysis(HttpServletRequest request) {
		request.setAttribute("analyses", analysesService.findAll());
		request.setAttribute("mode", "MODE_ANALYSES");
		return "analysisTasks";
	}

	// ******************************//
	/*
	 * Task
	 */

	@GetMapping("/new-task")
	public String newTask(HttpServletRequest request, ModelMap model) {
		request.setAttribute("task", new Tasks());
		request.setAttribute("analyses", new Analyses());
		model.addAttribute("analysisList", analysesService.findAllByActive(true));
		request.setAttribute("mode", "MODE_NEW_TASK");
		return "task";
	}

	@PostMapping("/save-task")
	public ModelAndView saveTask(@ModelAttribute("task") Tasks task, BindingResult bindingResult,
			HttpServletRequest request) {
		tasksService.save(task);
		// System.out.println(task.getAnalyses().getId());
		return new ModelAndView("redirect:update-analysis?id=" + task.getAnalyses().getId());
	}

	@GetMapping("/update-task")
	public String updateTask(@RequestParam int id, HttpServletRequest request, ModelMap model) {
		request.setAttribute("task", tasksService.findTask(id));
		request.setAttribute("analyses", new Analyses());
		model.addAttribute("analysisList", analysesService.findAll());
		request.setAttribute("mode", "MODE_UPDATE_TASK");
		return "task";
	}

	@GetMapping("/all-tasks")
	public String allTasks(HttpServletRequest request, ModelMap model) {
		request.setAttribute("tasks", tasksService.findAll());
		request.setAttribute("analyses", new Analyses());
		model.addAttribute("analysisList", analysesService.findAll());
		request.setAttribute("mode", "MODE_TASKS");
		return "task";
	}

	// ******************************//

	@GetMapping("/statistic")
	public String statistic(HttpServletRequest request) {
		return "statistic";
	}

	private static String UPLOADED_FOLDER = "F:/Users/Sunnyveerla/Projects/Development/Eclipse_Workspace/Files/";

	@PostMapping("/upload")
	public String singleFileUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			redirectAttributes.addFlashAttribute("mode", "MODE_NEW_PROJECT");
			return "project";
		}

		try {
			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);

			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded '" + file.getOriginalFilename() + "'");

		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, String> infoMap = convertService.extractFromWord(UPLOADED_FOLDER + file.getOriginalFilename());
		request.setAttribute("mode", "MODE_UPDATE_PROJECT");
		infoMap.put("analysisName", "");
		infoMap.put("analyses", "");
		request.setAttribute("project", infoMap);
		request.setAttribute("analysisList", analysesService.findAll());
		return "project";
	}

	/*
	 * @GetMapping("/all-projects-tasks") public String
	 * allProjectsTasks(HttpServletRequest request) { request.setAttri
	 * bute("tasks", projectTaskService.findAll()); request.setAttribute("mode",
	 * "MODE_PROJECTS_TASKS"); return "projects-tasks"; }
	 * 
	 * 
	 * @GetMapping("/ongoing-projects-tasks") public String
	 * ongoingProjectsTasks(HttpServletRequest request) {
	 * request.setAttribute("tasks", taskService.findByFinished(false));
	 * request.setAttribute("mode", "MODE_TASKS"); return "index"; }
	 * 
	 * @GetMapping("/finished-tasks") public String
	 * finishedTasks(HttpServletRequest request) { request.setAttribute("tasks",
	 * taskService.findByFinished(true)); request.setAttribute("mode",
	 * "MODE_TASKS"); return "index"; }
	 * 
	 * 
	 * @GetMapping("/new-task") public String newTask(HttpServletRequest
	 * request) { request.setAttribute("mode", "MODE_NEW"); return "index"; }
	 * 
	 * @PostMapping("/save-task") public String saveTask(@ModelAttribute
	 * TaskModel task, BindingResult bindingResult, HttpServletRequest request)
	 * { task.setDateCreated(new Date()); taskService.save(task);
	 * request.setAttribute("tasks", taskService.findAll());
	 * request.setAttribute("mode", "MODE_TASKS"); return "index"; }
	 * 
	 * @GetMapping("/update-task") public String updateTask(@RequestParam int
	 * id, HttpServletRequest request) { request.setAttribute("task",
	 * taskService.findTask(id)); request.setAttribute("mode", "MODE_UPDATE");
	 * return "index"; }
	 * 
	 * @PostMapping("/delete-task") public String deleteTask(@RequestParam int
	 * id, HttpServletRequest request) { taskService.delete(id);
	 * request.setAttribute("tasks", taskService.findAll());
	 * request.setAttribute("mode", "MODE_TASKS"); return "index"; }
	 */
}

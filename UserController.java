package ctg.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ctg.dao.RoleRepository;
import ctg.model.TaskAssignments;
import ctg.model.Users;
import ctg.service.SecurityService;
import ctg.service.TaskAssignmentsService;
import ctg.service.UserService;
import ctg.validator.UserValidator;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserValidator userValidator;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private TaskAssignmentsService taskAssignmentsService;

	@GetMapping(value = "/registration")
	public String registration(Model model) {
		model.addAttribute("userForm", new Users());
		model.addAttribute("roleList", roleRepository.findAll());

		return "registration";
	}

	@PostMapping(value = "/registration")
	public String registration(@ModelAttribute("userForm") Users userForm, BindingResult bindingResult, Model model) {
		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "registration";
		}

		userService.save(userForm);

		securityService.autologin(userForm.getUserName(), userForm.getUserConfirmPassword());

		return "redirect:/welcome";
	}

	@GetMapping(value = "/login")
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}

	@GetMapping(value = { "/", "/welcome" })
	public String welcome(Model model) {

		return "redirect:/home";
	}

	@GetMapping(value = "/userInfo")
	public @ResponseBody Map<String, Object> userInfo(Principal principal) {
		Map<String, Object> userInfo = new HashMap<String, Object>(2);
		String userName = principal.getName();
		int userId = getUserIdByUserName(userName);
		userInfo.put("userName", userName);
		userInfo.put("userId", userId);
		userInfo.put("pending", getPendingTasksCount(userId));
		return userInfo;
	}

	private int getPendingTasksCount(int usersId) {
		return taskAssignmentsService.getTotalActiveTasksForUserId(usersId, true);
	}

	private int getUserIdByUserName(String userName) {
		return (userService.findByUsername(userName).getId());
	}

	@GetMapping("/active-pending-assignments")
	public String activePendingAssignments(Principal principal, HttpServletRequest request) {
		request.setAttribute("mode", "MODE_PENDING");
		request.setAttribute("assignments",
				taskAssignmentsService.findByUserIdAndActive(getUserIdByUserName(principal.getName()), true));
		return "pendingTasks";
	}

	@GetMapping("/all-pending-assignments")
	public String allPendingAssignments(Principal principal, HttpServletRequest request) {
		request.setAttribute("mode", "MODE_PENDING");
		request.setAttribute("assignments",
				taskAssignmentsService.findByUserIdAndFinished(getUserIdByUserName(principal.getName()), false));
		return "pendingTasks";
	}

	@GetMapping("/getActiveTaskForUser")
	public @ResponseBody Map<String, Object> getActiveTaskForUser(@RequestParam int usersId,
			@RequestParam int projectsId) {
		//System.out.println(usersId);
		Map<String, Object> map = new HashMap<String, Object>();

		TaskAssignments taskAssignments = taskAssignmentsService.findByUserIdAndProjectIdAndActive(usersId,
				projectsId,true);
		if (taskAssignments != null) {
			map.put("tasksId", taskAssignments.getTasks().getId());
			map.put("id", taskAssignments.getId());
		}
		return map;
	}
}

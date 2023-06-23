package com.EmployeeTask.EmployeeTask.services;

import com.EmployeeTask.EmployeeTask.domain.Employee;
import com.EmployeeTask.EmployeeTask.domain.Project;
import com.EmployeeTask.EmployeeTask.domain.Task;
import com.EmployeeTask.EmployeeTask.exception.EmployeeNotFoundException;
import com.EmployeeTask.EmployeeTask.proxy.IEmployeeProxy;
import com.EmployeeTask.EmployeeTask.proxy.INotificationProxy;
import com.EmployeeTask.EmployeeTask.repository.IProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminService implements IAdminService {
    private final IProjectRepository iProjectRepository;
    private final INotificationProxy iNotificationProxy;

    @Autowired
    public AdminService(IProjectRepository iProjectRepository, INotificationProxy iNotificationProxy) {
        this.iProjectRepository = iProjectRepository;
        this.iNotificationProxy = iNotificationProxy;
    }

    @Override
    public Map<String, Integer> countNumberOFProjectAndTask() {
        List<Employee> employeeList = iProjectRepository.findAll();

        int totalProjects = employeeList.stream()
                .mapToInt(employee -> employee.getProjectList().size())
                .sum();

        int totalTasks = employeeList.stream()
                .flatMap(employee -> employee.getProjectList().stream())
                .mapToInt(project -> project.getTaskList() != null ? project.getTaskList().size() : 0)
                .sum();

        if (totalProjects == 0 && totalTasks == 0) {
            // No projects and no tasks found
            Map<String, Integer> counts = new HashMap<>();
            counts.put("projects", 0);
            counts.put("tasks", 0);
            return counts;
        }
        Map<String, Integer> counts = new HashMap<>();
        counts.put("projects", totalProjects);
        counts.put("tasks", totalTasks);

        return counts;
    }

    @Override
    public List<Project> getTop5Projects() {
        List<Employee> employeeList = iProjectRepository.findAll();
        Map<Project, Integer> projectTaskCountMap = new HashMap<>();
        for (Employee employee : employeeList) {
            List<Project> projects = employee.getProjectList();
            for (Project project : projects) {
                List<Task> taskList = project.getTaskList();
                if (taskList != null) {
                    int taskCount = taskList.size();
                    if (projectTaskCountMap.containsKey(project)) {
                        int existingTaskCount = projectTaskCountMap.get(project);
                        projectTaskCountMap.put(project, existingTaskCount + taskCount);
                    } else {
                        projectTaskCountMap.put(project, taskCount);
                    }
                }
            }
        }
        List<Project> sortedProjects = projectTaskCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .toList();
        // Retrieve the top 5 projects with the highest task count
        List<Project> top5Projects = sortedProjects.stream()
                .limit(5)
                .collect(Collectors.toList());
        return top5Projects;
    }

    @Override
    public String removeEmployee(String employeeEmail) throws EmployeeNotFoundException {
        Optional<Employee> employee = iProjectRepository.findById(employeeEmail);
        if (employee.isPresent()) {


            iProjectRepository.deleteById(employeeEmail);

            List<Employee> employeeList = iProjectRepository.findAll();
            if (employeeList != null) {
                for (Employee employee1 : employeeList) {
                    List<Project> projectsList = employee1.getProjectList();
                    if (projectsList != null) {
                        for (Project project : projectsList) {
                            Set<String> assignEmployeeList = project.getEmployeeList();
                            if (assignEmployeeList.contains(employeeEmail)) {
                                assignEmployeeList.remove(employeeEmail);

                                List<Task> taskList = project.getTaskList();
                                if (taskList != null) {
                                    List<Task> tasksToRemove = new ArrayList<>();
                                    for (Task task : taskList) {
                                        Set<String> assignTaskEmployeeList = task.getAssignTaskEmployees();
                                        if (assignTaskEmployeeList != null) {
                                            if (assignTaskEmployeeList.contains(employeeEmail)) {
                                                assignTaskEmployeeList.remove(employeeEmail);
                                            }
                                        }
                                        if (task.getTaskCreatedBY().equalsIgnoreCase(employeeEmail)) {
                                            tasksToRemove.add(task);
                                        }
                                    }
                                    taskList.removeAll(tasksToRemove);
                                }
                            }
                        }
                    }
                }
            }

            iNotificationProxy.deleteEmployee(employeeEmail);
            iProjectRepository.saveAll(employeeList);

            return "Employee removed successfully.";
        }

        throw new EmployeeNotFoundException();
    }





}

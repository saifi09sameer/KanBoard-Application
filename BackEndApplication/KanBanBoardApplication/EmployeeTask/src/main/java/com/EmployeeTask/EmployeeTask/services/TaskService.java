package com.EmployeeTask.EmployeeTask.services;

import com.EmployeeTask.EmployeeTask.configuration.NotificationDTO;
import com.EmployeeTask.EmployeeTask.domain.Employee;
import com.EmployeeTask.EmployeeTask.domain.Project;
import com.EmployeeTask.EmployeeTask.domain.Task;
import com.EmployeeTask.EmployeeTask.exception.EmployeeNotFoundException;
import com.EmployeeTask.EmployeeTask.exception.ProjectNotFoundException;
import com.EmployeeTask.EmployeeTask.exception.TaskNotFoundException;
import com.EmployeeTask.EmployeeTask.repository.IProjectRepository;
import org.json.simple.JSONObject;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService implements ITaskService {
    private final IProjectRepository iProjectRepository;
    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange directExchange;

    @Autowired
    public TaskService(IProjectRepository iProjectRepository, RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.iProjectRepository = iProjectRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    public int getRandomNumber() {
        return (int) (Math.random() * 900) + 100;
    }


    @Override
    public boolean createTask(Task task, String employeeEmail, String projectCreatedEmployee, int projectID)
            throws EmployeeNotFoundException, ProjectNotFoundException {
        Optional<Employee> employeeOptional = iProjectRepository.findById(projectCreatedEmployee);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            List<Project> projectList = employee.getProjectList();
            if (projectList != null) {
                for (Project project : projectList) {
                    if (project.getProjectID() == projectID) {
                        List<Task> allTasks = project.getTaskList();
                        if (allTasks == null) {
                            allTasks = new ArrayList<>();
                        }
                        task.setTaskID(getRandomNumber());
                        task.setTaskCreatedBY(employeeEmail);
                        allTasks.add(task);
                        project.setTaskList(allTasks);
                        employee.setProjectList(projectList);
                        iProjectRepository.save(employee);
                        return true;
                    }
                }
            }
            throw new ProjectNotFoundException();
        } else {
            throw new EmployeeNotFoundException();
        }
    }


    @Override
    public List<Task> getAllTasks(String employeeEmail, int projectID) throws TaskNotFoundException {
        Optional<Employee> employeeOptional = iProjectRepository.findById(employeeEmail);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            List<Project> projects = employee.getProjectList();
            for (Project project : projects) {
                if (project.getProjectID() == projectID) {
                    return project.getTaskList();
                }
            }
        }
        throw new TaskNotFoundException();
    }

    @Override
    public boolean updateTask(String updatedTaskDescription, String projectCreatedBY, int projectID, int taskID) throws EmployeeNotFoundException, ProjectNotFoundException, TaskNotFoundException {
        Optional<Employee> optionalEmployee = iProjectRepository.findById(projectCreatedBY);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            List<Project> projectList = employee.getProjectList();
            if (projectList != null) {
                for (Project project : projectList) {
                    if (project.getProjectID() == projectID) {
                        List<Task> taskList = project.getTaskList();
                        if (taskList != null) {
                            for (Task task : taskList) {
                                if (task.getTaskID() == taskID) {
                                    task.setTaskDescription(updatedTaskDescription);
                                    project.setTaskList(taskList);
                                    employee.setProjectList(projectList);
                                    iProjectRepository.save(employee);
                                    return true;
                                }
                            }
                            throw new TaskNotFoundException();
                        }
                    }
                }
                throw new ProjectNotFoundException();
            }
        }
        throw new EmployeeNotFoundException();
    }


    @Override
    public boolean deleteTask(String projectCreatedEmployee, int projectID, int taskID,String employeeEmail) throws TaskNotFoundException, ProjectNotFoundException, EmployeeNotFoundException {
        Optional<Employee> employeeOptional = iProjectRepository.findById(projectCreatedEmployee);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            List<Project> projects = employee.getProjectList();
            for (Project project : projects) {
                if (project.getProjectID() == projectID) {
                    List<Task> tasks = project.getTaskList();
                    for (Task task : tasks) {
                        if (task.getTaskID() == taskID) {
                            tasks.remove(task);
                            project.setTaskList(tasks);
                            employee.setProjectList(projects);
                            iProjectRepository.save(employee);
                            // Sending notification to all employees associated with the task
                            Set<String> employeeEmails = task.getAssignTaskEmployees();
                            if (employeeEmails != null) {
                                for (String email : employeeEmails) {
                                    JSONObject messageData = new JSONObject();
                                    String finalMessage = "The task with ID " + task.getTaskID() + " has been deleted by " + employeeEmail + ".";
                                    messageData.put("message", finalMessage);

                                    NotificationDTO message = new NotificationDTO();
                                    message.setEmployeeEmail(email);
                                    message.setJsonObject(messageData);
                                    System.out.println("This is Message Sent to RabbitMQ: " + message);
                                    rabbitTemplate.convertAndSend(directExchange.getName(), "product_routing_key", message);
                                }
                            }

                            return true;
                        }
                    }
                    throw new TaskNotFoundException();
                }
            }
            throw new ProjectNotFoundException();
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public boolean updateTaskStage(String projectCreatedEmployee, int projectID, int taskID, String taskUpdatedStage) throws EmployeeNotFoundException, ProjectNotFoundException, TaskNotFoundException {
        Optional<Employee> employee = iProjectRepository.findById(projectCreatedEmployee);
        if (employee.isPresent()) {
            Employee retriveEmployee = employee.get();
            List<Project> projectList = retriveEmployee.getProjectList();
            if (projectList != null) {
                for (Project project : projectList) {
                    if (project.getProjectID() == projectID) {
                        List<Task> taskList = project.getTaskList();
                        if (taskList != null) {
                            for (Task task : taskList) {
                                if (task.getTaskID() == taskID) {
                                    if(taskUpdatedStage.equalsIgnoreCase("Deleted")){
                                        task.getAssignTaskEmployees().clear();
                                    }
                                    task.setTaskStage(taskUpdatedStage);
                                    project.setTaskList(taskList);
                                    retriveEmployee.setProjectList(projectList);
                                    iProjectRepository.save(retriveEmployee);
                                    return true;
                                }
                            }
                        }
                        throw new TaskNotFoundException();
                    }
                }
                throw new ProjectNotFoundException();
            }
            throw new ProjectNotFoundException();
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public boolean assignTask(String projectCreatedBy, int projectID, int taskID, String assignEmployee) throws EmployeeNotFoundException, ProjectNotFoundException, TaskNotFoundException {
        Optional<Employee> employeeOptional = iProjectRepository.findById(projectCreatedBy);
        Project finalProject = null;

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            List<Project> projectList = employee.getProjectList();

            if (projectList != null) {
                for (Project project : projectList) {
                    if (project.getProjectID() == projectID) {
                        finalProject = project;
                        break;
                    }
                }
            }

            if (finalProject != null) {
                Task finalTask = null;
                List<Task> taskList = finalProject.getTaskList();

                if (taskList != null) {
                    for (Task task : taskList) {
                        if (task.getTaskID() == taskID) {
                            finalTask = task;
                            break;
                        }
                    }
                }

                if (finalTask != null) {
                    Set<String> assignEmployees = finalTask.getAssignTaskEmployees();

                    if (assignEmployees == null) {
                        assignEmployees = new HashSet<>();
                    }

                    assignEmployees.add(assignEmployee);
                    finalTask.setAssignTaskEmployees(assignEmployees);
                    employee.setProjectList(projectList);
                    iProjectRepository.save(employee);

                    JSONObject messageData = new JSONObject();
                    String finalMessage = "You have been assigned a new task: " + finalTask.getTaskName() + " in project: " + finalProject.getProjectName() + " by " + finalTask.getTaskCreatedBY() + ".";
                    messageData.put("message", finalMessage);

                    NotificationDTO message = new NotificationDTO();
                    message.setEmployeeEmail(assignEmployee);
                    message.setJsonObject(messageData);
                    System.out.println("This is Message Sent to RabbitMQ: " + message);
                    rabbitTemplate.convertAndSend(directExchange.getName(), "product_routing_key", message);
                    return true;
                } else {
                    throw new TaskNotFoundException();
                }
            } else {
                throw new ProjectNotFoundException();
            }
        } else {
            throw new EmployeeNotFoundException();
        }
    }

    @Override
    public Set<String> filteredEmployeeListForAssign(String projectCreatedBy, int projectID, int taskID, Set<String> employeeList) throws EmployeeNotFoundException, ProjectNotFoundException, TaskNotFoundException {
        Optional<Employee> optionalEmployee = iProjectRepository.findById(projectCreatedBy);

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            List<Project> projectList = employee.getProjectList();

            if (projectList != null) {
                for (Project project : projectList) {
                    if (project.getProjectID() == projectID) {
                        List<Task> taskList = project.getTaskList();

                        if (taskList != null) {
                            for (Task task : taskList) {
                                if (task.getTaskID() == taskID) {
                                    Set<String> retrievedEmployees = task.getAssignTaskEmployees();

                                    if (retrievedEmployees == null) {
                                        return employeeList;
                                    } else {
                                        Set<String> assignEmployeeList = new HashSet<>(retrievedEmployees);
                                        for (String employeeEmail : assignEmployeeList) {
                                            if (employeeList.contains(employeeEmail)) {
                                                employeeList.remove(employeeEmail);
                                            }
                                        }
                                        return employeeList;
                                    }
                                }
                            }
                        } else {
                            throw new TaskNotFoundException();
                        }
                    }
                }
            } else {
                throw new ProjectNotFoundException();
            }
        }
        throw new EmployeeNotFoundException();

    }


    @Override
    public Set<String> getAssignEmployeeList(String projectCreatedBy, int projectID, int taskID) throws EmployeeNotFoundException, ProjectNotFoundException, TaskNotFoundException {
        Optional<Employee> optionalEmployee = iProjectRepository.findById(projectCreatedBy);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            List<Project> projectList = employee.getProjectList();
            List<Task> taskLists = new ArrayList<>();
            if (projectList != null) {
                for (Project project : projectList) {
                    if (project.getProjectID() == projectID) {
                        List<Task> taskList = project.getTaskList();
                        if (taskList != null) {
                            taskLists.addAll(taskList);
                        }
                    }
                }
                if (taskLists.isEmpty()) {
                    throw new TaskNotFoundException();
                }
            } else {
                throw new ProjectNotFoundException();
            }

            for (Task task : taskLists) {
                if (task.getTaskID() == taskID) {
                    Set<String> assignEmployees = task.getAssignTaskEmployees();
                    if (assignEmployees == null) {
                        assignEmployees = new HashSet<>();
                    }
                    return assignEmployees;
                }
            }

            throw new TaskNotFoundException();
        }

        throw new EmployeeNotFoundException();
    }

    @Override
    public boolean removeAssignEmployee(String projectCreatedBy, int projectID, int taskID, String removeEmployee) throws EmployeeNotFoundException, ProjectNotFoundException, TaskNotFoundException {
        Optional<Employee> optionalEmployee = iProjectRepository.findById(projectCreatedBy);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            List<Project> projectList = employee.getProjectList();
            if (projectList != null) {
                Optional<Project> optionalProject = projectList.stream()
                        .filter(project -> project.getProjectID() == projectID)
                        .findFirst();
                if (optionalProject.isPresent()) {
                    Project project = optionalProject.get();
                    List<Task> taskList = project.getTaskList();
                    if (taskList != null) {
                        Optional<Task> optionalTask = taskList.stream()
                                .filter(task -> task.getTaskID() == taskID)
                                .findFirst();
                        if (optionalTask.isPresent()) {
                            Task task = optionalTask.get();
                            Set<String> assignEmployees = task.getAssignTaskEmployees();
                            if (assignEmployees != null) {
                                boolean removed = assignEmployees.remove(removeEmployee);
                                if (removed) {
                                    iProjectRepository.save(employee);
                                    // Sending notification to RabbitMQ
                                    JSONObject messageData = new JSONObject();
                                    String finalMessage = "You have been removed from task: " + task.getTaskName() + " in project: " + project.getProjectName() + " by " + task.getTaskCreatedBY() + ".";
                                    messageData.put("message", finalMessage);

                                    NotificationDTO message = new NotificationDTO();
                                    message.setEmployeeEmail(removeEmployee);
                                    message.setJsonObject(messageData);
                                    System.out.println("This is Message Sent to RabbitMQ: " + message);
                                    rabbitTemplate.convertAndSend(directExchange.getName(), "product_routing_key", message);
                                    return true;
                                } else {
                                    throw new EmployeeNotFoundException();
                                }
                            } else {
                                throw new EmployeeNotFoundException();
                            }
                        } else {
                            throw new TaskNotFoundException();
                        }
                    } else {
                        throw new TaskNotFoundException();
                    }
                } else {
                    throw new ProjectNotFoundException();
                }
            } else {
                throw new ProjectNotFoundException();
            }
        }
        return false;
    }

    @Override
    public List<String> getEmployeeEmailList(List<String> employeeEmailList, String projectCreatedBY, int projectID) throws ProjectNotFoundException, TaskNotFoundException, EmployeeNotFoundException {
        Optional<Employee> optionalEmployee = iProjectRepository.findById(projectCreatedBY);
        List<String> restrictedEmployeeEmailList = new ArrayList<>();
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            List<Project> projectList = employee.getProjectList();
            if (projectList != null) {
                for (Project project : projectList) {
                    if (project.getProjectID() == projectID) {
                        for (String employeeEmail : employeeEmailList) {
                            int taskCount = 0;
                            List<Task> taskList = project.getTaskList();
                            if (taskList != null) {
                                for (Task task : taskList) {
                                    Set<String> assignedEmployees = task.getAssignTaskEmployees();
                                    if (assignedEmployees != null && assignedEmployees.contains(employeeEmail)) {
                                        taskCount++;
                                        if (taskCount >= 5) {
                                            break;
                                        }
                                    }
                                }
                            } else {
                                throw new TaskNotFoundException();
                            }

                            if (taskCount < 5) {
                                restrictedEmployeeEmailList.add(employeeEmail);
                            }
                        }
                    }
                }
            } else {
                throw new ProjectNotFoundException();
            }
        }

        return restrictedEmployeeEmailList;
    }

    @Override
    public List<Task> getAllDeletedTasks(String projectCreatedBy, String employeeEmail, int projectID) throws EmployeeNotFoundException, ProjectNotFoundException {
        Optional<Employee> optionalEmployee = iProjectRepository.findById(projectCreatedBy);
        List<Task> finalTasksList = new ArrayList<>();

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            List<Project> projectList = employee.getProjectList();

            if (projectList != null) {
                boolean projectFound = false;

                for (Project project : projectList) {
                    if (project.getProjectID() == projectID) {
                        projectFound = true;
                        List<Task> taskList = project.getTaskList();

                        if (taskList != null) {
                            if (projectCreatedBy.equals(employeeEmail)) {
                                // Condition: projectCreatedBy and employeeEmail are the same
                                for (Task task : taskList) {
                                    if (task.getTaskStage().equalsIgnoreCase("Deleted")) {
                                        finalTasksList.add(task);
                                    }
                                }
                            } else {
                                // Condition: projectCreatedBy and employeeEmail are different
                                for (Task task : taskList) {
                                    if (task.getTaskCreatedBY().equalsIgnoreCase(employeeEmail) && task.getTaskStage().equalsIgnoreCase("Deleted")) {
                                        finalTasksList.add(task);
                                    }
                                }
                            }
                        }

                        break;
                    }
                }

                if (!projectFound) {
                    throw new ProjectNotFoundException();
                }
            } else {
                throw new ProjectNotFoundException();
            }
        } else {
            throw new EmployeeNotFoundException();
        }

        return finalTasksList;
    }

    @Override
    public boolean deleteAllDeletedTasks(String projectCreatedBY, String employeeEmail, int projectID) throws EmployeeNotFoundException, ProjectNotFoundException {
        Optional<Employee> optionalEmployee = iProjectRepository.findById(projectCreatedBY);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            List<Project> projectList = employee.getProjectList();
            if (projectList != null) {
                for (Project project : projectList) {
                    if (project.getProjectID() == projectID) {
                        List<Task> taskList = project.getTaskList();
                        if (taskList != null) {
                            Iterator<Task> taskIterator = taskList.iterator();
                            while (taskIterator.hasNext()) {
                                Task task = taskIterator.next();
                                if (task.getTaskStage().equalsIgnoreCase("Deleted")) {
                                    taskIterator.remove();
                                }
                            }
                            project.setTaskList(taskList);
                            employee.setProjectList(projectList);
                            iProjectRepository.save(employee);
                            return true;
                        }
                    }
                }
                throw new ProjectNotFoundException();
            }
        }
        throw new EmployeeNotFoundException();
    }


}
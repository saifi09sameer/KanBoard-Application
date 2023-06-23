package com.EmployeeTask.EmployeeTask.services;
import com.EmployeeTask.EmployeeTask.configuration.NotificationDTO;
import com.EmployeeTask.EmployeeTask.domain.Employee;
import com.EmployeeTask.EmployeeTask.domain.EmployeeDTO;
import com.EmployeeTask.EmployeeTask.domain.Project;
import com.EmployeeTask.EmployeeTask.domain.Task;
import com.EmployeeTask.EmployeeTask.exception.AssignEmployeeNotFoundException;
import com.EmployeeTask.EmployeeTask.exception.EmployeeAlreadyExistsException;
import com.EmployeeTask.EmployeeTask.exception.EmployeeNotFoundException;
import com.EmployeeTask.EmployeeTask.exception.ProjectNotFoundException;
import com.EmployeeTask.EmployeeTask.proxy.IEmployeeProxy;
import com.EmployeeTask.EmployeeTask.repository.IProjectRepository;
import org.json.simple.JSONObject;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProjectService implements IProjectService {

    IProjectRepository iProjectRepository;
    IEmployeeProxy iEmployeeProxy;
    private RabbitTemplate rabbitTemplate;
    private DirectExchange directExchange;

    @Autowired
    public ProjectService(IProjectRepository iProjectRepository, IEmployeeProxy iEmployeeProxy, RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.iProjectRepository = iProjectRepository;
        this.iEmployeeProxy = iEmployeeProxy;
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    @Override
    public Employee insertNewEmployee(Employee employee) throws EmployeeAlreadyExistsException {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeName(employee.getEmployeeName());
        employeeDTO.setEmployeeEmail(employee.getEmployeeEmail());
        employeeDTO.setEmployeePassword(employee.getEmployeePassword());
        employeeDTO.setEmployeePhoneNumber(employee.getEmployeePhoneNumber());
        employeeDTO.setNationality(employee.getNationality());
        iEmployeeProxy.registerEmployee(employeeDTO);
        if (iProjectRepository.findById(employee.getEmployeeEmail()).isEmpty()) {
            employee.setProjectList(new ArrayList<>());

            JSONObject messageData = new JSONObject();
            String finalMessage = "Welcome "+employee.getEmployeeName();
            messageData.put("message", finalMessage);
            NotificationDTO message = new NotificationDTO();
            message.setEmployeeEmail(employee.getEmployeeEmail());
            message.setJsonObject(messageData);
            System.out.println("This is Message Sent to RabbitMQ: " + message);
            rabbitTemplate.convertAndSend(directExchange.getName(), "product_routing_key", message);

            return iProjectRepository.insert(employee);
        }
        throw new EmployeeAlreadyExistsException();
    }

    @Override
    public Employee updatePassword(EmployeeDTO employeeDTO) throws EmployeeNotFoundException {
        Optional<Employee> optionalEmployee = iProjectRepository.findById(employeeDTO.getEmployeeEmail());
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setEmployeeEmail(employeeDTO.getEmployeeEmail());
            employee.setEmployeePassword(employeeDTO.getEmployeePassword());
            return iProjectRepository.save(employee);
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public Employee updateEmployee(EmployeeDTO employeeDTO) throws EmployeeNotFoundException {
        Optional<Employee> optionalEmployee = iProjectRepository.findById(employeeDTO.getEmployeeEmail());
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setEmployeeName(employeeDTO.getEmployeeName());
            employee.setEmployeeEmail(employeeDTO.getEmployeeEmail());
            employee.setEmployeePhoneNumber(employeeDTO.getEmployeePhoneNumber());

            employee.setNationality(employeeDTO.getNationality());
            return iProjectRepository.save(employee);
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public List<Project> getProjects(String employeeEmail) throws EmployeeNotFoundException, ProjectNotFoundException {
        List<Project> finalProjectList = new ArrayList<>();
        if (iProjectRepository.findById(employeeEmail).isPresent()) {
            Employee employee = iProjectRepository.findById(employeeEmail).get();
            List<Project> projects = employee.getProjectList();
            if (projects != null) {
                finalProjectList.addAll(projects);
            }
            List<Employee> employeeList = iProjectRepository.findAll();
            finalProjectList.addAll(ProjectManager.getAllProjectsBasedOnAssign(employeeList, employeeEmail));
            if (finalProjectList == null) {
                throw new ProjectNotFoundException();
            } else {
                return finalProjectList;
            }
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public boolean deleteProject(String employeeEmail, int projectID) throws ProjectNotFoundException, EmployeeNotFoundException {
        Optional<Employee> optionalEmployee = iProjectRepository.findById(employeeEmail);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeNotFoundException();
        }
        Employee employee = optionalEmployee.get();
        Project projectToRemove = null;
        for (Project project : employee.getProjectList()) {
            if (project.getProjectID() == projectID) {
                projectToRemove = project;
                break;
            }
        }
        if (projectToRemove == null) {
            throw new ProjectNotFoundException();
        }
        employee.getProjectList().remove(projectToRemove);
        iProjectRepository.save(employee);

        // Sending notification to all employees associated with the project
        Set<String> employeeEmails = projectToRemove.getEmployeeList();
        if (employeeEmails != null) {
            for (String email : employeeEmails) {
                JSONObject messageData = new JSONObject();
                String finalMessage = "The project: " + projectToRemove.getProjectName() + " has been deleted by " + employeeEmail + ".";
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

    @Override
    public boolean createProject(String employeeEmail, Project project) throws EmployeeNotFoundException {
        Optional<Employee> optionalEmployee = iProjectRepository.findById(employeeEmail);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeNotFoundException();
        }
        Employee employee = optionalEmployee.get();
        List<Project> projectList = employee.getProjectList();
        if (projectList == null) {
            projectList = new ArrayList<>(); // Create a new ArrayList if projectList is null
            employee.setProjectList(projectList); // Set the updated projectList to the employee
        }
        project.setCreatedBY(employeeEmail);
        project.setProjectID(generateRandomNumber());
        projectList.add(project);
        iProjectRepository.save(employee);
        return true;
    }


    @Override
    public List<String> getEmployeeEmailList(List<String> employeeEmailList) throws ProjectNotFoundException, EmployeeNotFoundException {
        List<Employee> employeeList = iProjectRepository.findAll();
        return ProjectManager.getEmployeeEmailList(employeeList, employeeEmailList);
    }

    @Override
    public Set<String> filteredEmployeeListForAssign(String projectCreatedBY, int projectID, Set<String> employeeList) throws EmployeeNotFoundException, ProjectNotFoundException {
        Optional<Employee> employeeOptional = iProjectRepository.findById(projectCreatedBY);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            List<Project> projectList = employee.getProjectList();
            if (projectList != null) {
                for (Project project : projectList) {
                    if (project.getProjectID() == projectID) {
                        Set<String> retrieveEmployeeList = project.getEmployeeList();
                        if (retrieveEmployeeList == null || retrieveEmployeeList.isEmpty()) {
                            return employeeList;
                        }
                        employeeList.removeAll(retrieveEmployeeList);
                        return employeeList;
                    }
                }
            } else {
                throw new ProjectNotFoundException();
            }
        }
        throw new EmployeeNotFoundException();

    }


    @Override
    public boolean assignEmployeeToProjects(String assignEmployeeEmail, int projectID, String employeeEmail) throws ProjectNotFoundException, EmployeeNotFoundException {
        Optional<Employee> employee = iProjectRepository.findById(employeeEmail);
        if (employee.isPresent()) {
            Employee employee1 = employee.get();
            List<Project> projects = employee1.getProjectList();
            boolean projectFound = false;
            for (Project project : projects) {
                if (project.getProjectID() == projectID) {
                    projectFound = true;
                    Set<String> finalEmployeeList = project.getEmployeeList();
                    if (finalEmployeeList==null){
                        finalEmployeeList = new HashSet<>();
                    }
                    finalEmployeeList.add(assignEmployeeEmail);
                    project.setEmployeeList(finalEmployeeList);

                    JSONObject messageData = new JSONObject();
                    String finalMessage = "You have been assigned to project: " + project.getProjectName() + " by " + employeeEmail + ".";
                    messageData.put("message", finalMessage);

                    NotificationDTO message = new NotificationDTO();
                    message.setEmployeeEmail(assignEmployeeEmail);
                    message.setJsonObject(messageData);
                    System.out.println("This is Message Sent to RabbitMQ: " + message);
                    rabbitTemplate.convertAndSend(directExchange.getName(), "product_routing_key", message);
                    break;

                }
            }
            if (projectFound) {
                iProjectRepository.save(employee1);
                // Sending notification to RabbitMQ

                return true;
            }
            throw new ProjectNotFoundException();
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public Set<String> getAssignEmployeeList(String employeeEmail, int projectID) throws EmployeeNotFoundException, ProjectNotFoundException, AssignEmployeeNotFoundException {
        Optional<Employee> employee = iProjectRepository.findById(employeeEmail);
        if (employee.isPresent()) {
            Employee employee1 = employee.get();
            List<Project> projects = employee1.getProjectList();
            if (projects != null) {
                for (Project project : projects) {
                    if (project.getProjectID() == projectID) {
                        Set<String> employeeList = project.getEmployeeList();
                        if (employeeList != null) {
                            return employeeList;
                        }
                        throw new AssignEmployeeNotFoundException();
                    }
                }
            }
            throw new ProjectNotFoundException();
        }
        throw new EmployeeNotFoundException();

    }

    @Override
    public boolean deleteAssignEmployee(String employeeEmail, int projectID, String assignEmployeeEmail) throws ProjectNotFoundException, AssignEmployeeNotFoundException {
        Optional<Employee> employee = iProjectRepository.findById(employeeEmail);
        if (employee.isPresent()) {
            Employee employee1 = employee.get();
            List<Project> projects = employee1.getProjectList();
            if (projects != null) {
                for (Project project : projects) {
                    if (project.getProjectID() == projectID) {
                        Set<String> employeeList = project.getEmployeeList();
                        if (employeeList.contains(assignEmployeeEmail)) {
                            employeeList.remove(assignEmployeeEmail);
                            project.setEmployeeList(employeeList);
                            employee1.setProjectList(projects);
                            iProjectRepository.save(employee1);

                            // Sending notification to RabbitMQ
                            JSONObject messageData = new JSONObject();
                            String finalMessage = "You have been removed from project: " + project.getProjectName() + " by " + employeeEmail + ".";
                            messageData.put("message", finalMessage);

                            NotificationDTO message = new NotificationDTO();
                            message.setEmployeeEmail(assignEmployeeEmail);
                            message.setJsonObject(messageData);
                            System.out.println("This is Message Sent to RabbitMQ: " + message);
                            rabbitTemplate.convertAndSend(directExchange.getName(), "product_routing_key", message);
                            return true;
                        }
                        throw new AssignEmployeeNotFoundException();
                    }
                }
                throw new ProjectNotFoundException();
            }
        }
        return false;
    }

    @Override
    public Project findProjectBasedOnProjectID(int projectID, String employeeEmail) throws ProjectNotFoundException {
        List<Employee> employeeList = iProjectRepository.findAll();
        return ProjectManager.findProjectBasedOnProjectID(employeeList, projectID, employeeEmail);
    }






    @Override
    public boolean argentTaskNotification(String employeeEmail) throws EmployeeNotFoundException, ProjectNotFoundException {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("I am inside the argentTaskNotification");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        Map<Integer, List<Integer>> projectTaskMap = new HashMap<>();
        Date currentDate = new Date();
        boolean isNotificationSent = false;

        List<Project> projectList = getProjects(employeeEmail);
        if (projectList != null) {
            for (Project project : projectList) {
                int projectId = project.getProjectID(); // Get the project ID
                List<Task> taskList = project.getTaskList();
                if (taskList != null) {
                    for (Task task : taskList) {
                        Set<String> assignEmployeeList = task.getAssignTaskEmployees();
                        if (assignEmployeeList != null && assignEmployeeList.contains(employeeEmail)) {
                            String taskEndDateStr = task.getTaskEndDate();

                            // Convert task end date string to Date object
                            Date taskEndDate = parseDate(taskEndDateStr);

                            // Calculate remaining days
                            long remainingDays = daysBetween(currentDate, taskEndDate);
                            if (remainingDays < 4) {
                                // Check if project ID already exists in the map
                                if (projectTaskMap.containsKey(projectId)) {
                                    projectTaskMap.get(projectId).add(task.getTaskID());
                                } else {
                                    List<Integer> taskIdList = new ArrayList<>();
                                    taskIdList.add(task.getTaskID());
                                    projectTaskMap.put(projectId, taskIdList);
                                }
                            }
                        }
                    }
                }
            }
        }

        // Generate notification strings for each project
        List<String> notificationList = new ArrayList<>();
        for (Map.Entry<Integer, List<Integer>> entry : projectTaskMap.entrySet()) {
            int projectId = entry.getKey();
            List<Integer> taskIds = entry.getValue();

            // Create the notification string
            StringBuilder notification = new StringBuilder();
            notification.append("Project ID: ").append(projectId).append("\n");
            notification.append("Task IDs: ");
            for (int taskId : taskIds) {
                notification.append(taskId).append(" ");
            }
            notification.append("\n");
            notification.append("You need to complete these tasks soon");

            notificationList.add(notification.toString());
        }
        if (notificationList!=null){
            System.out.println("This notificationList i am going to send to the rebitMQ " + notificationList);
        }
        if (notificationList != null) {
            for (String notification : notificationList) {
                try {
                    // Sending notification to RabbitMQ
                    JSONObject messageData = new JSONObject();
                    messageData.put("message", notification);

                    NotificationDTO message = new NotificationDTO();
                    message.setEmployeeEmail(employeeEmail);
                    message.setJsonObject(messageData);
                    System.out.println("This is Message Sent to RabbitMQ: " + message);
                    rabbitTemplate.convertAndSend(directExchange.getName(), "product_routing_key", message);
                    isNotificationSent = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle the exception as per your requirement
                    isNotificationSent = false;
                }
            }
        }

        // Perform further operations with the notificationList, such as sending it to the employee

        return isNotificationSent;
    }

    // Helper method to parse string date to Date object
    private Date parseDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper method to calculate the number of days between two dates
    private long daysBetween(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        return diff / (24 * 60 * 60 * 1000);
    }









    public static int generateRandomNumber() {
        return (int) (Math.random() * (99999 - 10000 + 1)) + 10000;
    }

}
//package com.EmployeeTask.EmployeeTask.service;
//        import com.EmployeeTask.EmployeeTask.domain.Employee;
//        import com.EmployeeTask.EmployeeTask.domain.Project;
//        import com.EmployeeTask.EmployeeTask.domain.Task;
//        import com.EmployeeTask.EmployeeTask.exception.EmployeeNotFoundException;
//        import com.EmployeeTask.EmployeeTask.exception.ProjectNotFoundException;
//        import com.EmployeeTask.EmployeeTask.exception.TaskNotFoundException;
//        import com.EmployeeTask.EmployeeTask.repository.IProjectRepository;
//        import com.EmployeeTask.EmployeeTask.services.TaskService;
//        import org.junit.jupiter.api.Assertions;
//        import org.junit.jupiter.api.Test;
//        import org.mockito.InjectMocks;
//        import org.mockito.Mock;
//        import org.mockito.MockitoAnnotations;
//        import org.springframework.boot.test.context.SpringBootTest;
//
//        import java.util.*;
//
//        import static org.junit.jupiter.api.Assertions.*;
//        import static org.mockito.Mockito.*;
//
//@SpringBootTest
//class TaskServiceTest {
//
//    @Mock
//    private IProjectRepository iProjectRepository;
//
//    @InjectMocks
//    private TaskService taskService;
//
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void createTask_Success() throws EmployeeNotFoundException, ProjectNotFoundException {
//        // Arrange
//        String employeeEmail = "employee@example.com";
//        String projectCreatedEmployee = "projectCreator@example.com";
//        int projectID = 123;
//
//        Employee employee = new Employee();
//        employee.setEmployeeEmail(projectCreatedEmployee);
//
//        Project project = new Project();
//        project.setProjectID(projectID);
//
//        List<Project> projectList = new ArrayList<>();
//        projectList.add(project);
//        employee.setProjectList(projectList);
//
//        Task task = new Task();
//
//        when(iProjectRepository.findById(projectCreatedEmployee)).thenReturn(Optional.of(employee));
//        when(iProjectRepository.save(employee)).thenReturn(employee);
//
//        // Act
//        boolean result = taskService.createTask(task, employeeEmail, projectCreatedEmployee, projectID);
//
//        // Assert
//        assertTrue(result);
//        verify(iProjectRepository, times(1)).findById(projectCreatedEmployee);
//        verify(iProjectRepository, times(1)).save(employee);
//
//        assertEquals(1, project.getTaskList().size());
//        assertEquals(task, project.getTaskList().get(0));
//        assertEquals(employeeEmail, task.getTaskCreatedBY());
//    }
//
//    @Test
//    public void createTask_ProjectNotFound() {
//        // Arrange
//        String employeeEmail = "employee@example.com";
//        String projectCreatedEmployee = "projectCreator@example.com";
//        int projectID = 123;
//
//        // Employee found, but project not found
//        Employee employee = new Employee();
//        employee.setEmployeeEmail(projectCreatedEmployee);
//
//        when(iProjectRepository.findById(projectCreatedEmployee)).thenReturn(Optional.of(employee));
//
//        // Act and Assert
//        assertThrows(ProjectNotFoundException.class, () -> {
//            taskService.createTask(new Task(), employeeEmail, projectCreatedEmployee, projectID);
//        });
//
//        verify(iProjectRepository, times(1)).findById(projectCreatedEmployee);
//        verify(iProjectRepository, never()).save(any(Employee.class));
//    }
//
//
//    @Test
//    public void getAllTasks_TasksFound_Success() throws TaskNotFoundException {
//        // Arrange
//        String employeeEmail = "employee@example.com";
//        int projectID = 123;
//
//        Employee employee = new Employee();
//        employee.setEmployeeEmail(employeeEmail);
//
//        Project project = new Project();
//        project.setProjectID(projectID);
//
//        Task task1 = new Task();
//        task1.setTaskID(1);
//        Task task2 = new Task();
//        task2.setTaskID(2);
//
//        List<Task> taskList = new ArrayList<>();
//        taskList.add(task1);
//        taskList.add(task2);
//
//        project.setTaskList(taskList);
//
//        List<Project> projectList = new ArrayList<>();
//        projectList.add(project);
//        employee.setProjectList(projectList);
//
//        when(iProjectRepository.findById(employeeEmail)).thenReturn(Optional.of(employee));
//
//        // Act
//        List<Task> result = taskService.getAllTasks(employeeEmail, projectID);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertTrue(result.contains(task1));
//        assertTrue(result.contains(task2));
//
//        verify(iProjectRepository, times(1)).findById(employeeEmail);
//    }
//
//    @Test
//    public void getAllTasks_EmployeeOrProjectNotFound_ThrowsTaskNotFoundException() {
//        // Arrange
//        String employeeEmail = "employee@example.com";
//        int projectID = 123;
//
//        // Simulate the case where the employee is not found
//        when(iProjectRepository.findById(employeeEmail)).thenReturn(Optional.empty());
//
//        // Act and Assert
//        Assertions.assertThrows(TaskNotFoundException.class, () -> {
//            taskService.getAllTasks(employeeEmail, projectID);
//        });
//
//        verify(iProjectRepository, times(1)).findById(employeeEmail);
//    }
//
//
//    @Test
//    public void deleteTask_TaskFound_Success() throws TaskNotFoundException, ProjectNotFoundException, EmployeeNotFoundException {
//        // Arrange
//        String projectCreatedEmployee = "projectCreator@example.com";
//        int projectID = 123;
//        int taskID = 1;
//        String employeeEmail="abcd@gmailcom";
//        Employee employee = new Employee();
//        employee.setEmployeeEmail(projectCreatedEmployee);
//
//        Project project = new Project();
//        project.setProjectID(projectID);
//
//        Task task1 = new Task();
//        task1.setTaskID(taskID);
//
//        List<Task> taskList = new ArrayList<>();
//        taskList.add(task1);
//
//        project.setTaskList(taskList);
//
//        List<Project> projectList = new ArrayList<>();
//        projectList.add(project);
//        employee.setProjectList(projectList);
//
//        when(iProjectRepository.findById(projectCreatedEmployee)).thenReturn(Optional.of(employee));
//        when(iProjectRepository.save(employee)).thenReturn(employee);
//
//        // Act
//        boolean result = taskService.deleteTask(projectCreatedEmployee, projectID, taskID, employeeEmail);
//
//        // Assert
//        assertTrue(result);
//        verify(iProjectRepository, times(1)).findById(projectCreatedEmployee);
//        verify(iProjectRepository, times(1)).save(employee);
//
//        assertTrue(project.getTaskList().isEmpty());
//    }
//
//
//
//    @Test
//    public void deleteTask_TaskExists_ReturnsTrue() throws TaskNotFoundException, ProjectNotFoundException, EmployeeNotFoundException {
//        // Arrange
//        String projectCreatedEmployee = "employee@example.com";
//        int projectID = 123;
//        int taskID = 456;
//        String employeeEmail="abcd@gmail.com";
//        Employee employee = new Employee();
//        employee.setEmployeeEmail(projectCreatedEmployee);
//
//        Project project = new Project();
//        project.setProjectID(projectID);
//
//        Task task = new Task();
//        task.setTaskID(taskID);
//
//        List<Task> tasks = new ArrayList<>();
//        tasks.add(task);
//        project.setTaskList(tasks);
//
//        List<Project> projects = new ArrayList<>();
//        projects.add(project);
//        employee.setProjectList(projects);
//
//        when(iProjectRepository.findById(projectCreatedEmployee)).thenReturn(Optional.of(employee));
//
//        // Act
//        boolean result = taskService.deleteTask(projectCreatedEmployee, projectID, taskID, employeeEmail);
//
//        // Assert
//        assertTrue(result);
//        assertFalse(project.getTaskList().contains(task));
//        verify(iProjectRepository, times(1)).save(employee);
//    }
//
//    @Test
//    public void getAssignEmployeeList_TaskAndProjectExist_ReturnsAssignEmployees() throws EmployeeNotFoundException, ProjectNotFoundException, TaskNotFoundException {
//        // Arrange
//        String projectCreatedBy = "createdBy@example.com";
//        int projectID = 123;
//        int taskID = 456;
//
//        Employee employee = new Employee();
//        employee.setEmployeeEmail(projectCreatedBy);
//
//        Project project = new Project();
//        project.setProjectID(projectID);
//
//        Task task = new Task();
//        task.setTaskID(taskID);
//
//        Set<String> assignEmployees = new HashSet<>();
//        assignEmployees.add("employee1@example.com");
//        assignEmployees.add("employee2@example.com");
//        task.setAssignTaskEmployees(assignEmployees);
//
//        List<Task> tasks = new ArrayList<>();
//        tasks.add(task);
//        project.setTaskList(tasks);
//
//        List<Project> projects = new ArrayList<>();
//        projects.add(project);
//        employee.setProjectList(projects);
//
//        when(iProjectRepository.findById(projectCreatedBy)).thenReturn(Optional.of(employee));
//
//        // Act
//        Set<String> result = taskService.getAssignEmployeeList(projectCreatedBy, projectID, taskID);
//
//        // Assert
//        assertEquals(assignEmployees, result);
//    }
//    @Test
//    public void getEmployeeEmailList_ProjectAndTasksExist_ReturnsRestrictedEmailList() throws EmployeeNotFoundException, ProjectNotFoundException, TaskNotFoundException {
//        // Arrange
//        String projectCreatedBy = "createdBy@example.com";
//        int projectID = 123;
//        List<String> employeeEmailList = new ArrayList<>();
//        employeeEmailList.add("employee1@example.com");
//        employeeEmailList.add("employee2@example.com");
//        employeeEmailList.add("employee3@example.com");
//        employeeEmailList.add("employee4@example.com");
//        employeeEmailList.add("employee5@example.com");
//        employeeEmailList.add("employee6@example.com");
//
//        Employee employee = new Employee();
//        employee.setEmployeeEmail(projectCreatedBy);
//
//        Project project = new Project();
//        project.setProjectID(projectID);
//
//        Task task1 = new Task();
//        Set<String> assignedEmployees1 = new HashSet<>();
//        assignedEmployees1.add("employee1@example.com");
//        assignedEmployees1.add("employee2@example.com");
//        assignedEmployees1.add("employee3@example.com");
//        task1.setAssignTaskEmployees(assignedEmployees1);
//
//        Task task2 = new Task();
//        Set<String> assignedEmployees2 = new HashSet<>();
//        assignedEmployees2.add("employee1@example.com");
//        assignedEmployees2.add("employee2@example.com");
//        assignedEmployees2.add("employee4@example.com");
//        task2.setAssignTaskEmployees(assignedEmployees2);
//
//        Task task3 = new Task();
//        Set<String> assignedEmployees3 = new HashSet<>();
//        assignedEmployees3.add("employee2@example.com");
//        assignedEmployees3.add("employee3@example.com");
//        task3.setAssignTaskEmployees(assignedEmployees3);
//
//        List<Task> tasks = new ArrayList<>();
//        tasks.add(task1);
//        tasks.add(task2);
//        tasks.add(task3);
//        project.setTaskList(tasks);
//
//        List<Project> projects = new ArrayList<>();
//        projects.add(project);
//        employee.setProjectList(projects);
//
//        when(iProjectRepository.findById(projectCreatedBy)).thenReturn(Optional.of(employee));
//
//        // Act
//        List<String> result = taskService.getEmployeeEmailList(employeeEmailList, projectCreatedBy, projectID);
//
//        // Assert
//        List<String> expected = new ArrayList<>();
//        expected.add("employee1@example.com");
//        expected.add("employee2@example.com");
//        expected.add("employee3@example.com");
//        expected.add("employee4@example.com");
//        expected.add("employee5@example.com");
//        expected.add("employee6@example.com");
//        assertEquals(expected, result);
//    }
//    @Test
//    public void updateTaskStage_TaskFound_Success() throws EmployeeNotFoundException, ProjectNotFoundException, TaskNotFoundException {
//        // Arrange
//        String projectCreatedEmployee = "projectCreator@example.com";
//        int projectID = 123;
//        int taskID = 1;
//        String updatedStage = "In Progress";
//
//        Employee employee = new Employee();
//        employee.setEmployeeEmail(projectCreatedEmployee);
//
//        Project project = new Project();
//        project.setProjectID(projectID);
//
//        Task task1 = new Task();
//        task1.setTaskID(taskID);
//        task1.setTaskStage("To Do");
//
//        List<Task> taskList = new ArrayList<>();
//        taskList.add(task1);
//
//        project.setTaskList(taskList);
//
//        List<Project> projectList = new ArrayList<>();
//        projectList.add(project);
//        employee.setProjectList(projectList);
//
//        when(iProjectRepository.findById(projectCreatedEmployee)).thenReturn(Optional.of(employee));
//        when(iProjectRepository.save(employee)).thenReturn(employee);
//
//        // Act
//        boolean result = taskService.updateTaskStage(projectCreatedEmployee, projectID, taskID, updatedStage);
//
//        // Assert
//        assertTrue(result);
//        verify(iProjectRepository, times(1)).findById(projectCreatedEmployee);
//        verify(iProjectRepository, times(1)).save(employee);
//
//        assertEquals(updatedStage, task1.getTaskStage());
//    }
//
//
//    @Test
//    public void updateTaskStage_EmployeeNotFound() throws EmployeeNotFoundException, ProjectNotFoundException, TaskNotFoundException {
//
//        String projectCreatedEmployee = "sam@gmail.com";
//        int projectID = 123;
//        int taskID = 1;
//        String updatedStage = "In Progress";
//
//        //we assume that the specified employee does not exist in the repository
//        // the findById method will return an empty Optional, test case asserts that the EmployeeNotFoundException is thrown, it is showing that the employee was not found
//        when(iProjectRepository.findById(projectCreatedEmployee)).thenReturn(Optional.empty());
//
//        assertThrows(EmployeeNotFoundException.class, () -> taskService.updateTaskStage(projectCreatedEmployee, projectID, taskID, updatedStage));
//
//        verify(iProjectRepository, times(1)).findById(projectCreatedEmployee);
//
//        //  repository's Save method never be called since the employee is not found
//        verify(iProjectRepository, never()).save(any());
//    }
//
//
//    @Test
//    void testGetAllDeletedTasks_SameCreatedByAndEmployee_Success() throws EmployeeNotFoundException, ProjectNotFoundException {
//        // Arrange
//        String projectCreatedBy = "user1@example.com";
//        String employeeEmail = "user1@example.com";
//        int projectID = 1;
//
//        Employee employee = new Employee();
//        employee.setProjectList(new ArrayList<>());
//        Project project = new Project();
//        project.setProjectID(projectID);
//        project.setTaskList(new ArrayList<>());
//        Task task1 = new Task();
//        task1.setTaskID(1);
//        task1.setTaskStage("Deleted");
//        project.getTaskList().add(task1);
//        employee.getProjectList().add(project);
//
//        when(iProjectRepository.findById(projectCreatedBy)).thenReturn(Optional.of(employee));
//
//        // Act
//        List<Task> result = taskService.getAllDeletedTasks(projectCreatedBy, employeeEmail, projectID);
//
//        // Assert
//        assertEquals(1, result.size());
//        assertEquals(task1, result.get(0));
//    }
//
//    @Test
//    void testGetAllDeletedTasks_DifferentCreatedByAndEmployee_Success() throws EmployeeNotFoundException, ProjectNotFoundException {
//        // Arrange
//        String projectCreatedBy = "user1@example.com";
//        String employeeEmail = "user2@example.com";
//        int projectID = 1;
//
//        Employee employee = new Employee();
//        employee.setProjectList(new ArrayList<>());
//        Project project = new Project();
//        project.setProjectID(projectID);
//        project.setTaskList(new ArrayList<>());
//        Task task1 = new Task();
//        task1.setTaskID(1);
//        task1.setTaskCreatedBY(employeeEmail);
//        task1.setTaskStage("Deleted");
//        project.getTaskList().add(task1);
//        employee.getProjectList().add(project);
//
//        when(iProjectRepository.findById(projectCreatedBy)).thenReturn(Optional.of(employee));
//
//        // Act
//        List<Task> result = taskService.getAllDeletedTasks(projectCreatedBy, employeeEmail, projectID);
//
//        // Assert
//        assertEquals(1, result.size());
//        assertEquals(task1, result.get(0));
//    }
//
//    @Test
//    void testGetAllDeletedTasks_EmployeeNotFound_ThrowsException() {
//        // Arrange
//        String projectCreatedBy = "user1@example.com";
//        String employeeEmail = "user1@example.com";
//        int projectID = 1;
//
//        when(iProjectRepository.findById(projectCreatedBy)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(EmployeeNotFoundException.class, () -> taskService.getAllDeletedTasks(projectCreatedBy, employeeEmail, projectID));
//    }
//
//    @Test
//    void testGetAllDeletedTasks_ProjectNotFound_ThrowsException() {
//        // Arrange
//        String projectCreatedBy = "user1@example.com";
//        String employeeEmail = "user1@example.com";
//        int projectID = 1;
//
//        Employee employee = new Employee();
//        employee.setProjectList(new ArrayList<>());
//
//        when(iProjectRepository.findById(projectCreatedBy)).thenReturn(Optional.of(employee));
//
//        assertThrows(ProjectNotFoundException.class, () -> taskService.getAllDeletedTasks(projectCreatedBy, employeeEmail, projectID));
//    }
//
//
//    @Test
//    void testDeleteAllDeletedTasks_Success() throws EmployeeNotFoundException, ProjectNotFoundException {
//        // Arrange
//        String projectCreatedBy = "user1@example.com";
//        String employeeEmail = "user1@example.com";
//        int projectID = 1;
//
//        Employee employee = new Employee();
//        employee.setProjectList(new ArrayList<>());
//        Project project = new Project();
//        project.setProjectID(projectID);
//        project.setTaskList(new ArrayList<>());
//        Task task1 = new Task();
//        task1.setTaskID(1);
//        task1.setTaskStage("Deleted");
//        project.getTaskList().add(task1);
//        Task task2 = new Task();
//        task2.setTaskID(2);
//        task2.setTaskStage("Pending");
//        project.getTaskList().add(task2);
//        employee.getProjectList().add(project);
//
//        when(iProjectRepository.findById(projectCreatedBy)).thenReturn(Optional.of(employee));
//        boolean result = taskService.deleteAllDeletedTasks(projectCreatedBy, employeeEmail, projectID);
//        assertTrue(result);
//        assertEquals(1, project.getTaskList().size());
//        assertEquals(task2, project.getTaskList().get(0));
//    }
//
//    @Test
//    void testDeleteAllDeletedTasks_EmployeeNotFound_ThrowsException() {
//        // Arrange
//        String projectCreatedBy = "user1@example.com";
//        String employeeEmail = "user1@example.com";
//        int projectID = 1;
//
//        when(iProjectRepository.findById(projectCreatedBy)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(EmployeeNotFoundException.class, () -> taskService.deleteAllDeletedTasks(projectCreatedBy, employeeEmail, projectID));
//    }
//
//    @Test
//    void testDeleteAllDeletedTasks_ProjectNotFound_ThrowsException() {
//        // Arrange
//        String projectCreatedBy = "user1@example.com";
//        String employeeEmail = "user1@example.com";
//        int projectID = 1;
//
//        Employee employee = new Employee();
//        employee.setProjectList(new ArrayList<>());
//
//        when(iProjectRepository.findById(projectCreatedBy)).thenReturn(Optional.of(employee));
//
//        assertThrows(ProjectNotFoundException.class, () -> taskService.deleteAllDeletedTasks(projectCreatedBy, employeeEmail, projectID));
//    }
//}
//

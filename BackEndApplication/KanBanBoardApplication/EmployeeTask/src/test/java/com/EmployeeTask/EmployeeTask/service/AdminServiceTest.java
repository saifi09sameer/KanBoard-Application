//package com.EmployeeTask.EmployeeTask.service;
//
//
//import com.EmployeeTask.EmployeeTask.domain.Employee;
//import com.EmployeeTask.EmployeeTask.domain.Project;
//import com.EmployeeTask.EmployeeTask.domain.Task;
//import com.EmployeeTask.EmployeeTask.exception.EmployeeNotFoundException;
//import com.EmployeeTask.EmployeeTask.repository.IProjectRepository;
//import com.EmployeeTask.EmployeeTask.services.AdminService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.*;
//
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class AdminServiceTest {
//
//    @Mock
//    private IProjectRepository iProjectRepository;
//
//    private AdminService adminService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        adminService = new AdminService(iProjectRepository);
//    }
//
//    @Test
//    void countNumberOfProjectAndTask_NoProjectsOrTasks_ReturnsZeroCounts() {
//        // Arrange
//        List<Employee> employeeList = new ArrayList<>();
//        when(iProjectRepository.findAll()).thenReturn(employeeList);
//
//        // Act
//        Map<String, Integer> counts = adminService.countNumberOFProjectAndTask();
//
//        // Assert
//        Assertions.assertEquals(0, counts.get("projects"));
//        Assertions.assertEquals(0, counts.get("tasks"));
//        verify(iProjectRepository).findAll();
//    }
//
//    @Test
//    void countNumberOfProjectAndTask_HasProjectsAndTasks_ReturnsCorrectCounts() {
//        // Arrange
//        Employee employee1 = new Employee();
//        Project project1 = new Project();
//        project1.setTaskList(Arrays.asList(new Task(), new Task(), new Task()));
//        employee1.setProjectList(Arrays.asList(project1));
//
//        Employee employee2 = new Employee();
//        Project project2 = new Project();
//        project2.setTaskList(Arrays.asList(new Task(), new Task()));
//        employee2.setProjectList(Arrays.asList(project2));
//
//        List<Employee> employeeList = Arrays.asList(employee1, employee2);
//        when(iProjectRepository.findAll()).thenReturn(employeeList);
//
//        // Act
//        Map<String, Integer> counts = adminService.countNumberOFProjectAndTask();
//
//        // Assert
//        Assertions.assertEquals(2, counts.get("projects"));
//        Assertions.assertEquals(5, counts.get("tasks"));
//        verify(iProjectRepository).findAll();
//    }
//
//    @Test
//    void getTop5Projects_HasProjects_ReturnsTop5ProjectsBasedOnTaskCount() {
//        // Arrange
//        Employee employee1 = new Employee();
//        Project project1 = new Project();
//        project1.setTaskList(Arrays.asList(new Task(), new Task()));
//        employee1.setProjectList(Arrays.asList(project1));
//
//        Employee employee2 = new Employee();
//        Project project2 = new Project();
//        project2.setTaskList(Arrays.asList(new Task(), new Task(), new Task()));
//        employee2.setProjectList(Arrays.asList(project2));
//
//        Employee employee3 = new Employee();
//        Project project3 = new Project();
//        project3.setTaskList(Arrays.asList(new Task()));
//        employee3.setProjectList(Arrays.asList(project3));
//
//        List<Employee> employeeList = Arrays.asList(employee1, employee2, employee3);
//        when(iProjectRepository.findAll()).thenReturn(employeeList);
//
//        // Act
//        List<Project> topProjects = adminService.getTop5Projects();
//
//        // Assert
//        Assertions.assertEquals(3, topProjects.size());
//        Assertions.assertEquals(project2, topProjects.get(0));
//        Assertions.assertEquals(project1, topProjects.get(1));
//        Assertions.assertEquals(project3, topProjects.get(2));
//        verify(iProjectRepository).findAll();
//    }
//
//    @Test
//    void getTop5Projects_LessThan5Projects_ReturnsAllProjectsSortedByTaskCount() {
//        // Arrange
//        Employee employee1 = new Employee();
//        Project project1 = new Project();
//        project1.setTaskList(Arrays.asList(new Task(), new Task()));
//        employee1.setProjectList(Arrays.asList(project1));
//
//        Employee employee2 = new Employee();
//        Project project2 = new Project();
//        project2.setTaskList(Arrays.asList(new Task(), new Task(), new Task()));
//        employee2.setProjectList(Arrays.asList(project2));
//
//        List<Employee> employeeList = Arrays.asList(employee1, employee2);
//        when(iProjectRepository.findAll()).thenReturn(employeeList);
//
//        // Act
//        List<Project> topProjects = adminService.getTop5Projects();
//
//        // Assert
//        Assertions.assertEquals(2, topProjects.size());
//        Assertions.assertEquals(project2, topProjects.get(0));
//        Assertions.assertEquals(project1, topProjects.get(1));
//        verify(iProjectRepository).findAll();
//    }
//
//    @Test
//    void removeEmployee_ValidEmployeeEmail_RemovesEmployeeAndUpdatesProjects() throws EmployeeNotFoundException {
//        // Arrange
//        String employeeEmail = "example@example.com";
//        Employee employee = new Employee();
//        Project project1 = new Project();
//        project1.setEmployeeList(new HashSet<>(Arrays.asList(employeeEmail)));
//        Project project2 = new Project();
//        project2.setEmployeeList(new HashSet<>(Arrays.asList(employeeEmail)));
//        employee.setProjectList(Arrays.asList(project1, project2));
//
//        List<Employee> employeeList = Arrays.asList(employee);
//        when(iProjectRepository.findById(employeeEmail)).thenReturn(Optional.of(employee));
//        when(iProjectRepository.findAll()).thenReturn(employeeList);
//
//        // Act
//        String result = adminService.removeEmployee(employeeEmail);
//
//        // Assert
//        Assertions.assertNull(result);
//        verify(iProjectRepository).findById(employeeEmail);
//        verify(iProjectRepository).deleteById(employeeEmail);
//        verify(iProjectRepository, times(2)).save(any(Employee.class));
//    }
//
//    @Test
//    void removeEmployee_InvalidEmployeeEmail_ThrowsEmployeeNotFoundException() {
//        // Arrange
//        String employeeEmail = "example@example.com";
//        when(iProjectRepository.findById(employeeEmail)).thenReturn(Optional.empty());
//
//        // Act and Assert
//        Assertions.assertThrows(EmployeeNotFoundException.class, () -> adminService.removeEmployee(employeeEmail));
//        verify(iProjectRepository).findById(employeeEmail);
//        verify(iProjectRepository, never()).deleteById(anyString());
//        verify(iProjectRepository, never()).save(any(Employee.class));
//    }
//
//}
//
//

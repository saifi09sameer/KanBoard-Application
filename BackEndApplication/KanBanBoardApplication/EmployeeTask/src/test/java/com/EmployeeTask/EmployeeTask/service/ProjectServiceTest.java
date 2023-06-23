//package com.EmployeeTask.EmployeeTask.service;
//
//import com.EmployeeTask.EmployeeTask.domain.Employee;
//import com.EmployeeTask.EmployeeTask.domain.EmployeeDTO;
//import com.EmployeeTask.EmployeeTask.domain.Project;
//import com.EmployeeTask.EmployeeTask.exception.AssignEmployeeNotFoundException;
//import com.EmployeeTask.EmployeeTask.exception.EmployeeAlreadyExistsException;
//import com.EmployeeTask.EmployeeTask.exception.EmployeeNotFoundException;
//import com.EmployeeTask.EmployeeTask.exception.ProjectNotFoundException;
//import com.EmployeeTask.EmployeeTask.proxy.IEmployeeProxy;
//import com.EmployeeTask.EmployeeTask.repository.IProjectRepository;
//import com.EmployeeTask.EmployeeTask.services.ProjectService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class ProjectServiceTest {
//
//    @Mock
//    private IEmployeeProxy iemployeeProxy;
//
//    @Mock
//    private IProjectRepository projectRepository;
//
//
//    @InjectMocks
//    private ProjectService projectService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//    }
//
//    @AfterEach
//    void tearDown() {
//        // Perform any necessary cleanup
//    }
//
//    @Test
//    void insertNewEmployee_ShouldInsertEmployee() throws EmployeeAlreadyExistsException {
//        // Arrange
//        Employee employee = new Employee(); // Create an instance of Employee
//        when(projectRepository.findById(employee.getEmployeeEmail())).thenReturn(Optional.empty());
//        when(projectRepository.insert(employee)).thenReturn(employee);
//
//        // Act
//        Employee result = projectService.insertNewEmployee(employee);
//
//        // Assert
//        assertNotNull(result);
//        verify(iemployeeProxy, times(1)).registerEmployee(any());
//        verify(projectRepository, times(1)).findById(employee.getEmployeeEmail());
//        verify(projectRepository, times(1)).insert(employee);
//    }
//
//    @Test
//    void insertNewEmployee_ShouldThrowEmployeeAlreadyExistsException() {
//        // Arrange
//        Employee existingEmployee = new Employee(); // Create an existing employee instance
//        existingEmployee.setEmployeeEmail("existing@example.com");
//
//        Employee newEmployee = new Employee(); // Create a new employee instance
//        newEmployee.setEmployeeEmail("existing@example.com"); // Set the same email as the existing employee
//
//        when(projectRepository.findById(newEmployee.getEmployeeEmail())).thenReturn(Optional.of(existingEmployee));
//
//        // Act and Assert
//        assertThrows(EmployeeAlreadyExistsException.class, () -> {
//            projectService.insertNewEmployee(newEmployee);
//        });
//
//        // Verify
//        verify(projectRepository, times(1)).findById(newEmployee.getEmployeeEmail());
//        verify(projectRepository, never()).insert(newEmployee);
//    }
//
//    @Test
//    public void testUpdatePassword_Success() throws EmployeeNotFoundException {
//        // Mock data
//        String email = "example@example.com";
//        String newPassword = "newPassword";
//
//        // Create a sample employee with existing data
//        Employee existingEmployee = new Employee();
//        existingEmployee.setEmployeeEmail(email);
//        existingEmployee.setEmployeeName("John");
//        existingEmployee.setEmployeePassword("oldPassword");
//        existingEmployee.setEmployeePhoneNumber("1234567890");
//        existingEmployee.setNationality("US");
//
//        // Create a mock Optional containing the existing employee
//        Optional<Employee> optionalEmployee = Optional.of(existingEmployee);
//
//        // Create a mock EmployeeDTO with the new password
//        EmployeeDTO employeeDTO = new EmployeeDTO();
//        employeeDTO.setEmployeeEmail(email);
//        employeeDTO.setEmployeePassword(newPassword);
//
//        // Mock the repository behavior
//        when(projectRepository.findById(email)).thenReturn(optionalEmployee);
//        when(projectRepository.save(existingEmployee)).thenReturn(existingEmployee);
//
//        // Perform the update password operation
//        Employee result = projectService.updatePassword(employeeDTO);
//
//        // Verify the repository methods were called with the correct arguments
//        verify(projectRepository, times(1)).findById(email);
//        verify(projectRepository, times(1)).save(existingEmployee);
//
//        // Assert the result and new password
//        assertEquals(existingEmployee, result);
//        assertEquals(newPassword, existingEmployee.getEmployeePassword());
//    }
//
//
//    @Test
//    public void testUpdateEmployee_EmployeeNotFound() {
//        // Mock data
//        String email = "example@example.com";
//        String newName = "John Doe";
//        String newPhoneNumber = "1234567890";
//        String newNationality = "US";
//
//        // Create a mock EmployeeDTO with the new data
//        EmployeeDTO employeeDTO = new EmployeeDTO();
//        employeeDTO.setEmployeeEmail(email);
//        employeeDTO.setEmployeeName(newName);
//        employeeDTO.setEmployeePhoneNumber(newPhoneNumber);
//        employeeDTO.setNationality(newNationality);
//
//        // Mock the repository behavior
//        when(projectRepository.findById(email)).thenReturn(Optional.empty());
//
//        // Perform the update employee operation and expect an EmployeeNotFoundException
//        assertThrows(EmployeeNotFoundException.class, () -> {
//            projectService.updateEmployee(employeeDTO);
//        });
//
//        // Verify the repository method was called with the correct argument
//        verify(projectRepository, times(1)).findById(email);
//
//        // Ensure that the repository save method was not called
//        verify(projectRepository, never()).save(any());
//    }
//
//    @Test
//    public void testUpdateEmployee_Success() throws EmployeeNotFoundException {
//        // Mock data
//        String email = "example@example.com";
//        String newName = "John Doe";
//        String newPhoneNumber = "1234567890";
//        String newNationality = "US";
//
//        // Create a sample employee with existing data
//        Employee existingEmployee = new Employee();
//        existingEmployee.setEmployeeEmail(email);
//        existingEmployee.setEmployeeName("John");
//        existingEmployee.setEmployeePhoneNumber("9876543210");
//        existingEmployee.setNationality("UK");
//
//        // Create a mock Optional containing the existing employee
//        Optional<Employee> optionalEmployee = Optional.of(existingEmployee);
//
//        // Create a mock EmployeeDTO with the new data
//        EmployeeDTO employeeDTO = new EmployeeDTO();
//        employeeDTO.setEmployeeEmail(email);
//        employeeDTO.setEmployeeName(newName);
//        employeeDTO.setEmployeePhoneNumber(newPhoneNumber);
//        employeeDTO.setNationality(newNationality);
//
//        // Mock the repository behavior
//        when(projectRepository.findById(email)).thenReturn(optionalEmployee);
//        when(projectRepository.save(existingEmployee)).thenReturn(existingEmployee);
//
//        // Perform the update employee operation
//        Employee result = projectService.updateEmployee(employeeDTO);
//
//        // Verify the repository methods were called with the correct arguments
//        verify(projectRepository, times(1)).findById(email);
//        verify(projectRepository, times(1)).save(existingEmployee);
//
//        // Assert the result and updated data
//        assertEquals(existingEmployee, result);
//        assertEquals(newName, existingEmployee.getEmployeeName());
//        assertEquals(newPhoneNumber, existingEmployee.getEmployeePhoneNumber());
//        assertEquals(newNationality, existingEmployee.getNationality());
//    }
//
//    @Test
//    public void testGetProjects_Success() throws EmployeeNotFoundException, ProjectNotFoundException {
//        // Mock data
//        String employeeEmail = "example@example.com";
//
//        // Create a sample employee with projects
//        Employee employee = new Employee();
//        employee.setEmployeeEmail(employeeEmail);
//        List<Project> projects = new ArrayList<>();
//        employee.setProjectList(projects);
//
//        // Create a sample list of employees
//        List<Employee> employeeList = new ArrayList<>();
//        employeeList.add(employee);
//
//        // Mock the repository behavior
//        when(projectRepository.findById(employeeEmail)).thenReturn(Optional.of(employee));
//        when(projectRepository.findAll()).thenReturn(employeeList);
//
//        // Perform the getProjects operation
//        List<Project> result = projectService.getProjects(employeeEmail);
//
//        // Verify the repository methods were called with the correct arguments
//        verify(projectRepository, times(2)).findById(employeeEmail);
//        verify(projectRepository, times(1)).findAll();
//
//        // Assert the result and project list
//        assertNotNull(result);
//        assertEquals(projects.size(), result.size());
//        assertEquals(projects, result);
//    }
//
//
//    @Test
//    public void testGetEmployeeEmailList_Success() throws ProjectNotFoundException, EmployeeNotFoundException {
//        // Mock data
//        List<String> employeeEmailList = Arrays.asList("mohan@gmail.com", "rohan@gmail.com");
//
//        // Create a sample employee list
//        List<Employee> employeeList = new ArrayList<>();
//        Employee employee1 = new Employee();
//        employee1.setEmployeeEmail("mohan@gmail.com");
//        employee1.setEmployeeName("mohan");
//        employee1.setProjectList(new ArrayList<>()); // Set an empty project list
//        Employee employee2 = new Employee();
//        employee2.setEmployeeEmail("rohan@gmail.com");
//        employee2.setEmployeeName("rohan");
//        employee2.setProjectList(new ArrayList<>()); // Set an empty project list
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//
//        // Mock the repository behavior
//        when(projectRepository.findAll()).thenReturn(employeeList);
//
//        // Perform the get employee email list operation
//        List<String> result = projectService.getEmployeeEmailList(employeeEmailList);
//
//        // Verify the repository method was called
//        verify(projectRepository, times(1)).findAll();
//
//        // Assert the result
//        assertEquals(employeeEmailList, result);
//    }
//
//    @Test
//    public void testGetEmployeeEmailList_Failure() throws ProjectNotFoundException, EmployeeNotFoundException {
//        // Mock data
//        List<String> employeeEmailList = Arrays.asList("example1@example.com", "example2@example.com");
//
//        // Mock the repository behavior to throw an exception
//        when(projectRepository.findAll()).thenThrow(new RuntimeException("Database connection error"));
//
//        // Perform the get employee email list operation and expect an exception
//        assertThrows(RuntimeException.class, () -> projectService.getEmployeeEmailList(employeeEmailList));
//
//        // Verify the repository method was called
//        verify(projectRepository, times(1)).findAll();
//    }
//
//    @Test
//    public void testFilteredEmployeeListForAssign() throws EmployeeNotFoundException, ProjectNotFoundException {
//        // Set up dependencies and mocks
//        String projectCreatedBy = "John Doe";
//        int projectID = 1;
//        Set<String> employeeList = new HashSet<>(Arrays.asList("employee1@example.com", "employee2@example.com"));
//
//        // Create a sample employee object
//        Employee employee = new Employee();
//        employee.setEmployeeEmail("john.doe@example.com");
//        employee.setEmployeeName("John Doe");
//        Project project = new Project();
//        project.setProjectID(1);
//        Set<String> assignedEmployees = new HashSet<>(Arrays.asList("employee1@example.com"));
//        project.setEmployeeList(assignedEmployees);
//        employee.setProjectList(Collections.singletonList(project));
//
//        // Mock the repository behavior
//        when(projectRepository.findById(projectCreatedBy)).thenReturn(Optional.of(employee));
//
//        // Invoke the method
//        Set<String> result = projectService.filteredEmployeeListForAssign(projectCreatedBy, projectID, employeeList);
//
//        // Verify repository interactions
//        verify(projectRepository, times(1)).findById(projectCreatedBy);
//
//        // Assert the expected result
//        Set<String> expected = new HashSet<>(Arrays.asList("employee2@example.com"));
//        assertEquals(expected, result);
//    }
//
//    @Test
//    public void testFilteredEmployeeListForAssign_Failure() throws EmployeeNotFoundException, ProjectNotFoundException {
//        // Set up dependencies and mocks
//        String projectCreatedBy = "John Doe";
//        int projectID = 1;
//        Set<String> employeeList = new HashSet<>(Arrays.asList("employee1@example.com", "employee2@example.com"));
//
//        // Mock the repository behavior to return an empty Optional
//        when(projectRepository.findById(projectCreatedBy)).thenReturn(Optional.empty());
//
//        // Invoke the method and expect an exception
//        assertThrows(EmployeeNotFoundException.class, () -> projectService.filteredEmployeeListForAssign(projectCreatedBy, projectID, employeeList));
//
//        // Verify repository interactions
//        verify(projectRepository, times(1)).findById(projectCreatedBy);
//    }
//
//    @Test
//    public void testDeleteAssignEmployee_Success() throws ProjectNotFoundException, AssignEmployeeNotFoundException {
//        // Create a sample employee
//        Employee employee = new Employee();
//        employee.setEmployeeEmail("sameer@gmail.com");
//        employee.setProjectList(new ArrayList<>());
//
//        // Create a sample project with an assigned employee
//        Project project = new Project();
//        project.setProjectID(1);
//        Set<String> employeeList = new HashSet<>();
//        employeeList.add("trisha@gmail.com");
//        project.setEmployeeList(employeeList);
//
//        // Add the project to the employee's project list
//        employee.getProjectList().add(project);
//
//        // Mock the repository to return the sample employee
//        when(projectRepository.findById("sameer@gmail.com")).thenReturn(Optional.of(employee));
//
//        // Call the deleteAssignEmployee method
//        boolean result = projectService.deleteAssignEmployee("sameer@gmail.com", 1, "trisha@gmail.com");
//
//        // Assert that the assigned employee is successfully removed
//        assertTrue(result);
//        assertFalse(project.getEmployeeList().contains("trisha@gmail.com"));
//    }
//
//    @Test
//    public void testDeleteAssignEmployee_ProjectNotFound() throws ProjectNotFoundException, AssignEmployeeNotFoundException {
//        // Create a sample employee
//        Employee employee = new Employee();
//        employee.setEmployeeEmail("nik@gmail.com");
//        employee.setProjectList(new ArrayList<>());
//
//        // Create a sample project with an assigned employee
//        Project project = new Project();
//        project.setProjectID(1);
//        Set<String> employeeList = new HashSet<>();
//        employeeList.add("priyanshu@gmail.com");
//        project.setEmployeeList(employeeList);
//
//        // Add the project to the employee's project list
//        employee.getProjectList().add(project);
//
//        // Mock the repository to return the sample employee
//        when(projectRepository.findById("nik@gmail.com")).thenReturn(Optional.of(employee));
//
//        // Call the deleteAssignEmployee method with a non-existing projectID
//        assertThrows(ProjectNotFoundException.class, () -> {
//            projectService.deleteAssignEmployee("nik@gmail.com", 2, "priyanshu@gmail.com");
//        });
//    }
//
//}
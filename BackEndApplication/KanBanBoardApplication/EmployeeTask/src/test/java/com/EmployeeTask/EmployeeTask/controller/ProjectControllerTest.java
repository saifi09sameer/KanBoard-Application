//package com.EmployeeTask.EmployeeTask.controller;
//
//import com.EmployeeTask.EmployeeTask.domain.Employee;
//import com.EmployeeTask.EmployeeTask.domain.EmployeeDTO;
//import com.EmployeeTask.EmployeeTask.domain.Project;
//import com.EmployeeTask.EmployeeTask.exception.EmailNotFoundException;
//import com.EmployeeTask.EmployeeTask.exception.EmployeeAlreadyExistsException;
//import com.EmployeeTask.EmployeeTask.exception.EmployeeNotFoundException;
//import com.EmployeeTask.EmployeeTask.exception.ProjectNotFoundException;
//import com.EmployeeTask.EmployeeTask.services.IProjectService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import javax.servlet.http.HttpServletRequest;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static junit.framework.Assert.assertNotNull;
//import static junit.framework.Assert.fail;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class ProjectControllerTest {
//    @Mock
//    private IProjectService projectService;
//
//    @Mock
//    private HttpServletRequest request;
//
//    @InjectMocks
//    private ProjectController projectController;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testRegisterEmployee() throws EmployeeAlreadyExistsException {
//
//        Employee employee = new Employee();
//
//        when(projectService.insertNewEmployee(employee)).thenReturn(employee);
//
//        ResponseEntity<?> response = projectController.registerEmployee(employee);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(employee, response.getBody());
//
//        verify(projectService).insertNewEmployee(employee);
//    }
//
//    @Test
//    public void testUpdateEmployee() throws EmployeeNotFoundException {
//        EmployeeDTO employeeDTO = new EmployeeDTO();
//
//        ResponseEntity<?> response = projectController.updateEmployee(employeeDTO);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//    }
//
//    @Test
//    public void testUpdatePassword() throws EmployeeNotFoundException {
//        EmployeeDTO employeeDTO = new EmployeeDTO();
//
//        ResponseEntity<?> response = projectController.updatePassword(employeeDTO);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//    }
//
//    @Test
//    public void testGetProjects() throws ProjectNotFoundException, EmailNotFoundException, EmployeeNotFoundException {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        when(request.getAttribute("employeeEmail")).thenReturn("example@example.com");
//
//        ResponseEntity<?> response = projectController.getProjects(request);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//    }
//    @Test
//    public void testDeleteProject() throws EmailNotFoundException, ProjectNotFoundException, EmployeeNotFoundException {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        when(request.getAttribute("employeeEmail")).thenReturn("example@example.com");
//
//        ResponseEntity<?> response = projectController.deleteProject(request, 1);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//    }
//    @Test
//    public void testCreateProject() throws EmployeeNotFoundException {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        when(request.getAttribute("employeeEmail")).thenReturn("example@example.com");
//
//        Project project = new Project();
//
//
//        ResponseEntity<?> response = projectController.createProject(request, project);
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//
//    }
//    @Test
//    public void testAssignEmployeeToProjects() throws EmailNotFoundException, ProjectNotFoundException, EmployeeNotFoundException {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        when(request.getAttribute("employeeEmail")).thenReturn("example@example.com");
//
//        ResponseEntity<?> response = projectController.assignEmployeeToProjects(request, "assign@example.com", 1);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//    }
//    @Test
//    public void testGetEmployeesList() throws ProjectNotFoundException, EmailNotFoundException, EmployeeNotFoundException {
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        when(request.getAttribute("employeeEmail")).thenReturn("example@example.com");
//
//        List<String> employeeEmailList = new ArrayList<>();
//
//
//        ResponseEntity<?> response = projectController.getEmployessEmailListResponseEntity(request, employeeEmailList);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//    }
//
//
//
//
//
//    @Test
//    public void testUpdateEmployee_employeeNotFound() throws EmployeeNotFoundException {
//
//        EmployeeDTO employeeDTO = new EmployeeDTO();
//
//
//
//        when(projectService.updateEmployee(employeeDTO)).thenThrow(EmployeeNotFoundException.class);
//
//
//        ResponseEntity<?> response;
//        try {
//            response = projectController.updateEmployee(employeeDTO);
//        } catch (EmployeeNotFoundException e) {
//
//            assertEquals(EmployeeNotFoundException.class, e.getClass());
//
//
//            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//
//        assertNotNull(response);
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//
//
//
//        verify(projectService).updateEmployee(employeeDTO);
//    }
//
//
//    @Test
//    public void testUpdatePassword_employeeNotFound() throws EmployeeNotFoundException {
//
//        EmployeeDTO employeeDTO = new EmployeeDTO();
//
//        when(projectService.updatePassword(employeeDTO)).thenThrow(EmployeeNotFoundException.class);
//
//
//        ResponseEntity<?> response;
//        try {
//            response = projectController.updatePassword(employeeDTO);
//        } catch (EmployeeNotFoundException e) {
//
//            assertEquals(EmployeeNotFoundException.class, e.getClass());
//
//
//            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//
//        assertNotNull(response);
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//
//        verify(projectService).updatePassword(employeeDTO);
//    }
//}

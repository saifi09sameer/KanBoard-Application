//package com.example.AuthenticationApplication.Service;
//
//
//import com.example.AuthenticationApplication.domain.Employee;
//import com.example.AuthenticationApplication.exception.EmployeeAlreadyExistsException;
//import com.example.AuthenticationApplication.exception.EmployeeNotFoundException;
//import com.example.AuthenticationApplication.repository.IEmployeeRepository;
//import com.example.AuthenticationApplication.service.EmployeeService;
//import com.example.AuthenticationApplication.service.IMailService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mindrot.jbcrypt.BCrypt;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//public class EmployeeServiceTest {
//
//    @Mock
//    private IEmployeeRepository iEmployeeRepository;
//
//    @InjectMocks
//    private EmployeeService employeeService;
//
//    @Mock
//    private IMailService iMailService;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void registerEmployee_SuccessTest() throws EmployeeAlreadyExistsException {
//        // Create a sample employee
//        Employee employee = new Employee();
//        employee.setEmployeeEmail("nik@gmail.com");
//        employee.setEmployeePassword("nik@123");
//
//        // Mock the repository to return an empty result, indicating the employee does not exist
//        when(iEmployeeRepository.findById("nik@gmail.com")).thenReturn(Optional.empty());
//
//        // Mock the repository to save the employee and return it
//        when(iEmployeeRepository.save(employee)).thenReturn(employee);
//
//        // Call the registerEmployee method
//        Employee registeredEmployee = employeeService.registerEmployee(employee);
//
//        // Assert that the registered employee matches the original employee
//        assertEquals(employee, registeredEmployee);
//
//        // Assert the employee status is set to "Offline"
//        assertEquals("Offline", registeredEmployee.getStatus());
//
//        // Verify that the repository methods were called as expected
//        verify(iEmployeeRepository, times(1)).findById("nik@gmail.com");
//        verify(iEmployeeRepository, times(1)).save(employee);
//
//        // Verify that the registration confirmation email was sent
//        verify(iMailService, times(1)).sendRegistrationConfirmation(registeredEmployee);
//    }
//
//
//    @Test
//    public void registerEmployeeAlreadyExistsTest() {
//        Employee employee = createEmployee();
//        when(iEmployeeRepository.findById(employee.getEmployeeEmail())).thenReturn(Optional.of(employee));
//        assertThrows(EmployeeAlreadyExistsException.class, () -> employeeService.registerEmployee(employee));
//        verify(iEmployeeRepository, times(1)).findById(employee.getEmployeeEmail());
//        verify(iEmployeeRepository, never()).save(any(Employee.class));
//    }
//
////
////    @Test
////    public void loginEmployeeSuccessTest() throws EmployeeNotFoundException {
////
////        Employee testEmployee = new Employee();
////        testEmployee.setEmployeeEmail("testing@gmail.com");
////        String plainTextPassword = "testing@123";
////        testEmployee.setEmployeePassword(hashPassword(plainTextPassword));
////
////
////        when(iEmployeeRepository.findById("testing@gmail.com"))
////                .thenReturn(Optional.of(testEmployee));
////
////
////        Employee result = employeeService.loginEmployee("testing@gmail.com", plainTextPassword);
////
////
////        assertEquals("Online", result.getStatus());
////
////
////        verify(iEmployeeRepository, times(1)).findById("testing@gmail.com");
////
////        verify(iEmployeeRepository, times(1)).save(testEmployee);
////    }
//
//    private String hashPassword(String password) {
//        return BCrypt.hashpw(password, BCrypt.gensalt());
//    }
//
//
//    @Test
//    public void loginEmployeeNotFoundTest() {
//        String employeeEmail = "bantu@gmail.com";
//        String employeePassword = "password123";
//
//        when(iEmployeeRepository.findById(employeeEmail)).thenReturn(Optional.empty());
//
//        assertThrows(EmployeeNotFoundException.class, () -> employeeService.loginEmployee(employeeEmail, employeePassword));
//
//        verify(iEmployeeRepository, times(1)).findById(employeeEmail);
//        verify(iEmployeeRepository, never()).save(any(Employee.class));
//    }
//
//    private Employee createEmployee() {
//        Employee employee = new Employee();
//        employee.setEmployeeEmail("testing@gmail.com");
//        employee.setEmployeeID(123456);
//        employee.setEmployeeName("testing");
//        employee.setGender("Male");
//        employee.setMaritalStatus("Single");
//        employee.setEmployeeDOB("1990-01-01");
//        employee.setEmployeeAadhaarNumber(1234567890L);
//        employee.setBloodGroup("A+");
//        employee.setEmployeePhoneNumber("1234567890");
//        employee.setEmployeePassword("testing@123");
//        employee.setJoiningDate(LocalDate.now().toString());
//        employee.setEmployeeRole("Employee");
//        employee.setNationality("Indian");
//        employee.setEmployeeCity("Noida");
//        employee.setStatus("Offline");
//
//        return employee;
//    }
//
//    @Test
//    public void getEmployeeDetailsSuccessTest() throws EmployeeNotFoundException {
//        // Create a test employee
//        Employee testEmployee = new Employee();
//        testEmployee.setEmployeeEmail("testing@gmail.com");
//        testEmployee.setEmployeeName("testing");
//
//        // Set up the behavior of iEmployeeRepository.findById
//        when(iEmployeeRepository.findById("testing@gmail.com"))
//                .thenReturn(Optional.of(testEmployee));
//
//        // Call the getEmployeeDetails method
//        Employee result = employeeService.getEmployeeDetails("testing@gmail.com");
//
//        System.out.println("name of the employee is " + result.getEmployeeName());
//        // Perform assertions on the result
//        assertEquals(testEmployee.getEmployeeName(), result.getEmployeeName());
//        // Verify that findById was called once with the correct email
//        // The reason for this is that the findById() method is called twice in the getEmployeeDetails() method in the service
//        verify(iEmployeeRepository, times(2)).findById("testing@gmail.com");
//    }
//
//    @Test
//    public void getEmployeeDetailsEmployeeNotFoundTest() {
//        // Set up the behavior of iEmployeeRepository.findById
//        when(iEmployeeRepository.findById("chantu@gmail.com"))
//                .thenReturn(Optional.empty());
//
//        // Call the getEmployeeDetails method and expect EmployeeNotFoundException
//        assertThrows(EmployeeNotFoundException.class,
//                () -> employeeService.getEmployeeDetails("chantu@gmail.com"));
//
//        // Verify that findById was called once with the correct email
//        verify(iEmployeeRepository, times(1)).findById("chantu@gmail.com");
//    }
//}
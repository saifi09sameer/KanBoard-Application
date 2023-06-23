//package com.example.AuthenticationApplication.Repository;
//
//import com.example.AuthenticationApplication.domain.Employee;
//import com.example.AuthenticationApplication.repository.IEmployeeRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class EmployeeRepositoryTest {
//
//    @Autowired
//    private IEmployeeRepository employeeRepository;
//
//    private Employee employee;
//
//    @BeforeEach
//    public void setup() {
//        employee = new Employee();
//        employee.setEmployeeEmail("test@example.com");
//        employee.setEmployeeID(1);
//        employee.setEmployeeName("John Doe");
//        employee.setGender("Male");
//        employee.setMaritalStatus("Single");
//        employee.setEmployeeDOB("1990-01-01");
//        employee.setEmployeeAadhaarNumber(1234567890L);
//        employee.setBloodGroup("O+");
//        employee.setEmployeePhoneNumber("1234567890");
//        employee.setEmployeePassword("password123");
//        employee.setJoiningDate("2023-01-01");
//        employee.setEmployeeRole("Employee");
//        employee.setNationality("USA");
//        employee.setEmployeeCity("New York");
//        employee.setStatus("Active");
//        employee.setImageURL("https://example.com/image.jpg");
//    }
//
//    @AfterEach
//    public void tearDown() {
//        employeeRepository.deleteAll();
//    }
//
//    @Test
//    public void insertEmployeeTestSuccess() {
//        Employee savedEmployee = employeeRepository.save(employee);
//        assertNotNull(savedEmployee.getEmployeeEmail());
//        assertEquals(employee, savedEmployee);
//    }
//
//
//    @Test
//    public void findEmployeeByEmailTestSuccess() {
//        employeeRepository.save(employee);
//        Optional<Employee> foundEmployee = employeeRepository.findById(employee.getEmployeeEmail());
//        assertTrue(foundEmployee.isPresent());
//        assertEquals(employee, foundEmployee.get());
//    }
//
//    @Test
//    public void findEmployeeByEmailTestFail() {
//        Optional<Employee> foundEmployee = employeeRepository.findById("nonexistent@example.com");
//        assertFalse(foundEmployee.isPresent());
//    }
//
//    @Test
//    public void deleteEmployeeTestSuccess() {
//        employeeRepository.save(employee);
//        assertTrue(employeeRepository.existsById(employee.getEmployeeEmail()));
//        employeeRepository.deleteById(employee.getEmployeeEmail());
//        assertFalse(employeeRepository.existsById(employee.getEmployeeEmail()));
//    }
//
//    @Test
//    public void deleteEmployeeTestFail() {
//        assertFalse(employeeRepository.existsById("nonexistent@example.com"));
//
//        try {
//            employeeRepository.deleteById("nonexistent@example.com");
//            fail("Expected EmptyResultDataAccessException was not thrown.");
//        } catch (EmptyResultDataAccessException ex) {
//
//        }
//
//        assertFalse(employeeRepository.existsById("nonexistent@example.com"));
//    }
//
//}

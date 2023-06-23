//package com.EmployeeTask.EmployeeTask.repository;
//
//import com.EmployeeTask.EmployeeTask.domain.Employee;
//import com.EmployeeTask.EmployeeTask.repository.IProjectRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(SpringExtension.class)
//@DataMongoTest
//public class AdminRepositoryTest {
//
//    @Autowired
//    private IProjectRepository projectRepository;
//
//    private Employee employee;
//
//    @BeforeEach
//    public void setup() {
//        employee = new Employee();
//        employee.setEmployeeEmail("test@example.com");
//        employee.setEmployeeName("John Doe");
//        employee.setEmployeePassword("password123");
//        employee.setEmployeePhoneNumber("1234567890");
//        employee.setNationality("USA");
//        employee.setProjectList(Collections.emptyList());
//    }
//
//    @AfterEach
//    public void tearDown() {
//        projectRepository.deleteAll();
//    }
//
//    @Test
//    public void insertEmployeeTestSuccess() {
//        Employee savedEmployee = projectRepository.insert(employee);
//        assertNotNull(savedEmployee.getEmployeeEmail());
//        assertEquals(employee, savedEmployee);
//    }
//
//    @Test
//    public void insertEmployeeTestFail() {
//        projectRepository.insert(employee);
//        assertThrows(DuplicateKeyException.class, () -> projectRepository.insert(employee));
//    }
//
//    @Test
//    public void findEmployeeByEmailTestSuccess() {
//        projectRepository.insert(employee);
//        Employee foundEmployee = projectRepository.findById(employee.getEmployeeEmail()).orElse(null);
//        assertNotNull(foundEmployee);
//        assertEquals(employee, foundEmployee);
//    }
//
//    @Test
//    public void findEmployeeByEmailTestFail() {
//        Employee foundEmployee = projectRepository.findById("nonexistent@example.com").orElse(null);
//        assertNull(foundEmployee);
//    }
//
//    @Test
//    public void deleteEmployeeTestSuccess() {
//        projectRepository.insert(employee);
//        assertTrue(projectRepository.existsById(employee.getEmployeeEmail()));
//        projectRepository.deleteById(employee.getEmployeeEmail());
//        assertFalse(projectRepository.existsById(employee.getEmployeeEmail()));
//    }
//
//    @Test
//    public void deleteEmployeeTestFail() {
//        assertFalse(projectRepository.existsById("nonexistent@example.com"));
//        projectRepository.deleteById("nonexistent@example.com");
//        assertFalse(projectRepository.existsById("nonexistent@example.com"));
//    }
//}

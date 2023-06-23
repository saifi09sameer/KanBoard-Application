package com.example.AuthenticationApplication.service;

import com.example.AuthenticationApplication.domain.Employee;
import com.example.AuthenticationApplication.domain.EmployeeDTO;
import com.example.AuthenticationApplication.exception.EmailNotFoundException;
import com.example.AuthenticationApplication.exception.EmployeeAlreadyExistsException;
import com.example.AuthenticationApplication.exception.EmployeeListNotFoundException;
import com.example.AuthenticationApplication.exception.EmployeeNotFoundException;
import com.example.AuthenticationApplication.proxy.IEmployeeProxy;
import com.example.AuthenticationApplication.repository.IEmployeeRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService implements IEmployeeService {
    private final IEmployeeRepository iEmployeeRepository;
    private final IEmployeeProxy iEmployeeProxy;
    private final IMailService iMailService;

    @Autowired
    public EmployeeService(IEmployeeRepository iEmployeeRepository, IEmployeeProxy iEmployeeProxy, IMailService iMailService) {
        this.iEmployeeRepository = iEmployeeRepository;
        this.iEmployeeProxy = iEmployeeProxy;
        this.iMailService = iMailService;
    }

    @Override
    public Employee registerEmployee(Employee employee) throws EmployeeAlreadyExistsException {
        if (iEmployeeRepository.findById(employee.getEmployeeEmail()).isEmpty()) {
            // employee does not exist, proceed with registration
            employee.setEmployeeID(generateRandomNumber());
            employee.setEmployeeRole("employee");
            employee.setStatus("Offline");
            employee.setJoiningDate(LocalDate.now().toString());
            employee.setEmployeePassword(hashPassword(employee.getEmployeePassword()));
            Employee retriveEmployee = iEmployeeRepository.save(employee);
            iMailService.sendRegistrationConfirmation(retriveEmployee);
            return retriveEmployee;
        } else {
            throw new EmployeeAlreadyExistsException();
        }
    }


    public static int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(9999 - 1000 + 1) + 1000;
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    // login method changed at the time of testing
    @Override
    public Employee loginEmployee(String employeeEmail, String employeePassword) throws EmployeeNotFoundException {
        Optional<Employee> employeeOptional = iEmployeeRepository.findById(employeeEmail);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            if (checkPassword(employeePassword, employee.getEmployeePassword())) {
                employee.setStatus("Online");
                iEmployeeRepository.save(employee);
                iEmployeeProxy.argentTaskNotification(employee.getEmployeeEmail());
                return employee;
            }
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public String forgotPassword(String employeeEmail, String employeePassword) throws EmployeeNotFoundException {
        Optional<Employee> employeeOptional = iEmployeeRepository.findById(employeeEmail);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setEmployeePassword(hashPassword(employeePassword));
            iEmployeeRepository.save(employee);
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setEmployeeEmail(employeeEmail);
            employeeDTO.setEmployeePassword(employeePassword);
            iEmployeeProxy.updatePassword(employeeDTO);
            return employeePassword;
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public Employee getEmployeeDetails(String employeeEmail) throws EmployeeNotFoundException {
        if (iEmployeeRepository.findById(employeeEmail).isPresent()) {
            return iEmployeeRepository.findById(employeeEmail).get();
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public Employee updateEmployee(Employee employee) throws EmployeeNotFoundException {
        if (iEmployeeRepository.findById(employee.getEmployeeEmail()).isPresent()) {
            Employee existingEmployee = iEmployeeRepository.findById(employee.getEmployeeEmail()).get();
            existingEmployee.setEmployeeName(employee.getEmployeeName());
            existingEmployee.setGender(employee.getGender());
            existingEmployee.setMaritalStatus(employee.getMaritalStatus());
            existingEmployee.setEmployeeDOB(employee.getEmployeeDOB());
            existingEmployee.setEmployeeAadhaarNumber(employee.getEmployeeAadhaarNumber());
            existingEmployee.setBloodGroup(employee.getBloodGroup());
            existingEmployee.setEmployeePhoneNumber(employee.getEmployeePhoneNumber());
            existingEmployee.setJoiningDate(employee.getJoiningDate());
            existingEmployee.setEmployeeRole(employee.getEmployeeRole());
            existingEmployee.setNationality(employee.getNationality());
            existingEmployee.setEmployeeCity(employee.getEmployeeCity());

            existingEmployee.setImageURL(employee.getImageURL());
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setEmployeeName(employee.getEmployeeName());
            employeeDTO.setEmployeeEmail(employee.getEmployeeEmail());
            employeeDTO.setNationality(employee.getNationality());
            employeeDTO.setEmployeePhoneNumber(employee.getEmployeePhoneNumber());
            iEmployeeProxy.updateEmployee(employeeDTO);
            return iEmployeeRepository.save(existingEmployee);
        }
        throw new EmployeeNotFoundException();
    }


    @Override
    public List<Employee> getAllEmployees() throws EmployeeNotFoundException {
        List<Employee> allEmployees = iEmployeeRepository.findAll();

        List<Employee> employeesWithoutAdminRole = allEmployees.stream()
                .filter(employee -> !employee.getEmployeeRole().equals("Admin"))
                .collect(Collectors.toList());

        if (employeesWithoutAdminRole.isEmpty()) {
            throw new EmployeeNotFoundException();
        }

        Comparator<Employee> employeeComparator = new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                if (o1.getEmployeeID()>o2.getEmployeeID()){
                    return 1;
                } else if (o1.getEmployeeID()<o2.getEmployeeID()) {
                    return -1;
                }
                return 0;
            }
        };

        Collections.sort(employeesWithoutAdminRole,employeeComparator);

        return employeesWithoutAdminRole;
    }




    @Override
    public boolean deleteEmployee(String employeeEmail) throws EmployeeNotFoundException {
        if (iEmployeeRepository.findById(employeeEmail).isPresent()) {
            iEmployeeRepository.deleteById(employeeEmail);
            iEmployeeProxy.removeEmployee(employeeEmail);
            return true;
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public List<String> getAllEmployeesStatus() throws EmployeeNotFoundException {
        List<String> employeeList = new ArrayList<>();
        List<Employee> allEmployees = iEmployeeRepository.findAll();
        if (allEmployees.isEmpty()) {
            throw new EmployeeNotFoundException();
        }
        for (Employee employee : allEmployees) {
            if (employee.getStatus().equalsIgnoreCase("Online")) {
                employeeList.add(employee.getEmployeeEmail());
            }
        }
        if (employeeList.isEmpty()) {
            throw new EmployeeNotFoundException();
        }
        return employeeList;
    }

    @Override
    public boolean offLineStatus(String employeeEmail) throws EmployeeNotFoundException {
        Optional<Employee> optionalEmployee = iEmployeeRepository.findById(employeeEmail);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setStatus("Offline");
            iEmployeeRepository.save(employee);
            return true;
        } else {
            throw new EmployeeNotFoundException();
        }
    }

    @Override
    public List<String> employeeEmailList(String employeeEmail) throws EmployeeNotFoundException, EmployeeListNotFoundException {
        List<Employee> employees = iEmployeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new EmployeeListNotFoundException();
        }
        List<String> emailList = new ArrayList<>();
        for (Employee employee : employees) {
            if (!"Admin".equalsIgnoreCase(employee.getEmployeeRole()) && !employee.getEmployeeEmail().equalsIgnoreCase(employeeEmail)) {
                emailList.add(employee.getEmployeeEmail());
            }
        }
        if (emailList.isEmpty()) {
            throw new EmployeeNotFoundException();
        }
        return emailList;
    }


    private boolean checkPassword(String inputPassword, String hashedPassword) {
        return BCrypt.checkpw(inputPassword, hashedPassword);
    }


}

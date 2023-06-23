package com.example.AuthenticationApplication.service;


import com.example.AuthenticationApplication.domain.Employee;
import com.example.AuthenticationApplication.exception.EmployeeNotFoundException;
import com.example.AuthenticationApplication.repository.IEmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Random;

@Service
public class MailService implements IMailService {


    private JavaMailSender mailSender;

    private IEmployeeRepository iEmployeeRepository;

    @Autowired
    public MailService(JavaMailSender mailSender, IEmployeeRepository iEmployeeRepository) {
        this.mailSender = mailSender;
        this.iEmployeeRepository = iEmployeeRepository;
    }


    @Override
    public int sendOTP(String employeeEmail) throws EmployeeNotFoundException {
        Optional<Employee> optionalEmployee = iEmployeeRepository.findById(employeeEmail);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            int OTP = generateRandomNumber();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("kanbanboardapplication@gmail.com");
            message.setTo(employeeEmail);
            message.setSubject("Password Reset OTP");

            String emailContent = "Dear " + employee.getEmployeeName() + ",\n\n"
                    + "You have requested to reset your password. Please use the following OTP to proceed:\n\n"
                    + "OTP: " + OTP + "\n\n"
                    + "If you didn't initiate this request, please ignore this email.\n\n"
                    + "Thank you,\n"
                    + "Sameer Saifi..\n"
                    + "Kan Ban Board Application";

            message.setText(emailContent);
            mailSender.send(message);

            return OTP;
        }
        throw new EmployeeNotFoundException();
    }

    @Override
    public void sendRegistrationConfirmation(Employee employee) {
        String loginURL = "http://localhost:4200/login";

        String emailContent = "Dear " + employee.getEmployeeName() + ",\n\n"
                + "Thank you for creating an account on our Kanban Board application. Your registration is successful.\n\n"
                + "Employee ID: " + employee.getEmployeeID() + "\n"
                + "Employee Name: " + employee.getEmployeeName() + "\n\n"
                + "The Kanban Board is a project management tool that helps teams visualize their work and track progress using the Kanban method.\n\n"
                + "If you have any questions or need assistance, feel free to contact our support team. They will be glad to help you make the most of our application.\n\n"
                + "To log in to your account, please visit the following link and enter your credentials:\n\n"
                + loginURL + "\n\n"
                + "Thank you for choosing our Kanban Board application to manage your projects and tasks.\n\n"
                + "If you have any feedback or suggestions, we would love to hear from you.\n\n"
                + "Thank you,\n"
                + "Sameer Saifi\n"
                + "Kanban Board Application";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kanbanboardapplication@gmail.com");
        message.setTo(employee.getEmployeeEmail());
        message.setSubject("Account Registration Confirmation");
        message.setText(emailContent);
        mailSender.send(message);
    }




    public static int generateRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;
        return randomNumber;
    }
}

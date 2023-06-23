package com.kanbanboard.ContactService.controller;

import com.kanbanboard.ContactService.domain.Contact;
import com.kanbanboard.ContactService.exception.ContactNotFoundException;
import com.kanbanboard.ContactService.exception.RoleNotValidException;
import com.kanbanboard.ContactService.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/Contact")
public class ContactController {
    IContactService iContactService;

    @Autowired
    public ContactController(IContactService iContactService) {
        this.iContactService = iContactService;
    }

    //http://localhost:9944/api/Contact/sendMessage    [TOKEN]
    @PostMapping("/sendMessage")
    public ResponseEntity<?> sendMessage(@RequestBody Contact contact){
        return new ResponseEntity<>(iContactService.sendMessage(contact), HttpStatus.OK);
    }
    //http://localhost:9944/api/Contact/getAllMessages    [TOKEN]
    @GetMapping("/getAllMessages")
    public ResponseEntity<?> getAllMessages(HttpServletRequest request) throws RoleNotValidException {
       String employeeRole = (String) request.getAttribute("employeeRole");
       if (employeeRole.equalsIgnoreCase("Admin")){
           return new ResponseEntity<>(iContactService.getAllMessages(),HttpStatus.OK);
       }
       throw new RoleNotValidException();
    }

    //http://localhost:9944/api/Contact/deleteMessages/    [TOKEN]
    @DeleteMapping("/deleteMessages/{id}")
    public ResponseEntity<?> deleteMessages(HttpServletRequest request , @PathVariable int id) throws ContactNotFoundException, RoleNotValidException {
        System.out.println(id);
        System.out.println("I am here with this id "+id);
        String employeeRole = (String) request.getAttribute("employeeRole");
        if (employeeRole.equalsIgnoreCase("Admin")){
            return new ResponseEntity<>(iContactService.deleteMessages(id),HttpStatus.OK);
        }
        throw new RoleNotValidException();
    }

}

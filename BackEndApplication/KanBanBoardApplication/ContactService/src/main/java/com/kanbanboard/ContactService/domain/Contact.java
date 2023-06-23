package com.kanbanboard.ContactService.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Contact {
    @Id
    int id;
    String name;
    String email;
    String subject;
    String message;
    String date;
}

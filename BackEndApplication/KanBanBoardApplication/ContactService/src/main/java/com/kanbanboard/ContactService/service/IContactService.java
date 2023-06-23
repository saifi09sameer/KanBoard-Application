package com.kanbanboard.ContactService.service;

import com.kanbanboard.ContactService.domain.Contact;
import com.kanbanboard.ContactService.exception.ContactNotFoundException;

import java.util.List;

public interface IContactService {
    Contact sendMessage(Contact contact);
    List<Contact> getAllMessages();
    boolean deleteMessages(int id) throws ContactNotFoundException;
}

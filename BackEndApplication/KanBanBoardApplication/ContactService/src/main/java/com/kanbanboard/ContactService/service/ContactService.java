package com.kanbanboard.ContactService.service;

import com.kanbanboard.ContactService.domain.Contact;
import com.kanbanboard.ContactService.exception.ContactNotFoundException;
import com.kanbanboard.ContactService.repository.IContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ContactService implements IContactService{
    IContactRepository iContactRepository;
    @Autowired
    public ContactService(IContactRepository iContactRepository) {
        this.iContactRepository = iContactRepository;
    }

    @Override
    public Contact sendMessage(Contact contact) {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(currentDate);
        contact.setDate(formattedDate);
        contact.setId(generateRandomNumber());
        return iContactRepository.insert(contact);
    }


    public static int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(999 - 100 + 1) + 100;
    }
    @Override
    public List<Contact> getAllMessages() {
        return iContactRepository.findAll();
    }

    @Override
    public boolean deleteMessages(int id) throws ContactNotFoundException {
        Optional<Contact> optionalContact = iContactRepository.findById(id);
        if (optionalContact.isPresent()){
            iContactRepository.deleteById(id);
            return true;
        }
        throw new ContactNotFoundException();
    }
}

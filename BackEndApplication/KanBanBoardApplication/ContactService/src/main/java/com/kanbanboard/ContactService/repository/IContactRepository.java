package com.kanbanboard.ContactService.repository;

import com.kanbanboard.ContactService.domain.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IContactRepository extends MongoRepository<Contact,Integer> {
}

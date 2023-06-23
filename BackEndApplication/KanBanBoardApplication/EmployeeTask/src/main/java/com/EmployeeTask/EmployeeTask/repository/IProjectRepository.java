package com.EmployeeTask.EmployeeTask.repository;

import com.EmployeeTask.EmployeeTask.domain.Employee;
import com.EmployeeTask.EmployeeTask.domain.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProjectRepository extends MongoRepository<Employee,String> {

}

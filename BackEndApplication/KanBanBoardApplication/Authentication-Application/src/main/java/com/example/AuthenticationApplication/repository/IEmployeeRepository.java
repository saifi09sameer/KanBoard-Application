package com.example.AuthenticationApplication.repository;

import com.example.AuthenticationApplication.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee,String> {

}

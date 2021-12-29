package com.Niche.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Niche.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}

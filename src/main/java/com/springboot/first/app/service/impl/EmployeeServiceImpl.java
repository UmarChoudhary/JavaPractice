package com.springboot.first.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.first.app.exception.ResourceNotFoundException;
import com.springboot.first.app.model.Employee;
import com.springboot.first.app.repository.EmployeeRepository;
import com.springboot.first.app.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(long Id) {
//	Optional<Employee>employee = employeeRepository.findById(Id);
//	if (employee.isPresent()) {
//		return employee.get();
//	}
//		else
//		{
//			throw new ResourceNotFoundException("Employee", "id", Id); 
//	}
	return employeeRepository.findById(Id).orElseThrow(()-> 
					new ResourceNotFoundException("Employee","Id", Id));
	}

	@Override
	public Employee updateEmployee(Employee employee, long id) {
		
		//check if id is exist or not
		Employee existingEmployee = employeeRepository.findById(id).orElseThrow(
			()-> new ResourceNotFoundException("Employee","id",id));
		
		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setEmail(employee.getEmail());
		//save existing employee to db
		employeeRepository.save(existingEmployee);
		return existingEmployee;
	}

	@Override
	public void deleteEmployee(long id) {

		//check whether employee exist in db or not
		employeeRepository.findById(id).orElseThrow(()->
				new ResourceNotFoundException("Employee","id",id));
		
		employeeRepository.deleteById(id);
	}
	}
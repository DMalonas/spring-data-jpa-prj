package com.udacity.jdnd.course3.critter.user;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.pet.PetRepository;

import static com.udacity.jdnd.course3.critter.util.Util.convertDTOToEntity;
import static com.udacity.jdnd.course3.critter.util.Util.convertEntityToDTO;

@Service
public class UserService {
	private final CustomerRepository customerRepository;
	private final EmployeeRepository employeeRepository;
	private final PetRepository petRepository;
	public UserService(CustomerRepository customerRepository, EmployeeRepository employeeRepository, PetRepository petRepository) {
		this.customerRepository = customerRepository;
		this.employeeRepository = employeeRepository;
		this.petRepository = petRepository;
	}

	@Transactional
	public Customer saveCustomer(CustomerDTO customerDTO) {
		return customerRepository.save(convertDTOToEntity(customerDTO, Customer.class));
	}

	@Transactional
	public Customer getCustomerByPet(long id) {
		return customerRepository.findByPets(petRepository.getOne(id));
	}

	@Transactional
	public Customer getCustomer(long ownerId) {
		return customerRepository.getOne(ownerId);
	}

	@Transactional
	public List<Customer> getCustomers() {
		return customerRepository.findAll();
	}

	@Transactional
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}


	@Transactional
	public Employee getEmployee(long id) {
		return employeeRepository.getOne(id);
	}

	@Transactional
	public Employee setEmployeeAvailability(Set<DayOfWeek> days, long id) {
		Employee employeeToUpdate = employeeRepository.getOne(id);
		employeeToUpdate.setWorkDays(days);
		return employeeRepository.save(employeeToUpdate);
	}

	@Transactional
	public List<Employee> findEmployeesForService(EmployeeRequestDTO employeeRequest) {
		Set<EmployeeSkill> skills = employeeRequest.getSkills();
		LocalDate date = employeeRequest.getDate();
		List<Employee> matchedEmployees = employeeRepository.findAllByWorkDaysAndSkillsIn(date.getDayOfWeek(), skills)
				.stream()
				.filter(employee -> employee.getSkills().containsAll(skills))
				.collect(Collectors.toList());
		return  matchedEmployees;
	}

	@Transactional
	public List<Employee> getEmployeesByIds(List<Long> employeeIds) {
		return employeeRepository.findAllById(employeeIds);
	}

}

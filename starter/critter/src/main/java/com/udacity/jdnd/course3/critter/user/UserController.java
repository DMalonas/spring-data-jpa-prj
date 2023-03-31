package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.udacity.jdnd.course3.critter.util.Util.convertEntityToDTO;

/**
 * Handles web requests related to User.
 *
 * Includes requests for both Customer and Employee Entities.
 * 
 */
@RestController
@RequestMapping("/user")
public class UserController {

	private UserService userService;

	private final PetService petService;

	public UserController(UserService userService, PetService petService) {
		this.userService = userService;
		this.petService = petService;
	}

	@PostMapping("/customer")
	public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
		return convertEntityToDTO(userService.saveCustomer(customerDTO), new CustomerDTO());
	}
	@GetMapping("/customer")
	public List<CustomerDTO> getAllCustomers() {
		return userService.getCustomers()
				.stream()
				.map(customer -> convertEntityToDTO(customer, new CustomerDTO()))
				.collect(Collectors.toList());
	}

	@GetMapping("/customer/pet/{petId}")
	public CustomerDTO getOwnerByPet(@PathVariable long petId) {
		return convertEntityToDTO(userService.getCustomerByPet(petId), new CustomerDTO());
	}

	@PostMapping("/employee")
	public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
		Employee employee = convertDTOToEmployee(employeeDTO);
		return convertEmployeeToDTO(userService.saveEmployee(employee));
	}

	@PostMapping("/employee/{employeeId}")
	public EmployeeDTO getEmployee(@PathVariable long employeeId) {
		return convertEmployeeToDTO(userService.getEmployee(employeeId));
	}

	
	@PutMapping("/employee/{employeeId}")
	public EmployeeDTO setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
		return convertEmployeeToDTO(userService.setEmployeeAvailability(daysAvailable, employeeId));
	}

	@GetMapping("/employee/availability")
	public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
		return userService.findEmployeesForService(employeeDTO).stream().map(employee -> convertEmployeeToDTO(employee))
				.collect(Collectors.toList());
	}



	private EmployeeDTO convertEmployeeToDTO(Employee employee) {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		BeanUtils.copyProperties(employee, employeeDTO);
		employeeDTO.setDaysAvailable(employee.getWorkDays());
		return employeeDTO;
	}

	public Employee convertDTOToEmployee(EmployeeDTO employeeDTO) {
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeDTO, employee);
		employee.setWorkDays(employeeDTO.getDaysAvailable());
		return employee;
	}
}

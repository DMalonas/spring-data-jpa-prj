package com.udacity.jdnd.course3.critter.user;

import org.springframework.web.bind.annotation.*;
import com.udacity.jdnd.course3.critter.pet.PetService;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static com.udacity.jdnd.course3.critter.util.Util.convertDTOToEntity;
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

	private final UserService userService;
	private final PetService petService;

	public UserController(UserService userService, PetService petService) {
		this.userService = userService;
		this.petService = petService;
	}

	@PostMapping("/customer")
	public CustomerDTO saveCustomer(@RequestBody CustomerDTO dto) {
		return convertEntityToDTO(userService.saveCustomer(dto), new CustomerDTO());
	}

	@GetMapping("/customer")
	public List<CustomerDTO> getAllCustomers() {
		return userService.getCustomers().stream()
				.map(c -> convertEntityToDTO(c, new CustomerDTO()))
				.collect(Collectors.toList());
	}

	@GetMapping("/customer/pet/{petId}")
	public CustomerDTO getOwnerByPet(@PathVariable long petId) {
		return convertEntityToDTO(userService.getCustomerByPet(petId), new CustomerDTO());
	}

	@PostMapping("/employee")
	public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO dto) {
		Employee e = convertDTOToEntity(dto, Employee.class);
		return convertEntityToDTO(userService.saveEmployee(e), new EmployeeDTO());
	}

	@GetMapping("/employee/{employeeId}")
	public EmployeeDTO getEmployee(@PathVariable long employeeId) {
		return convertEntityToDTO(userService.getEmployee(employeeId), new EmployeeDTO());
	}

	@PutMapping("/employee/{employeeId}")
	public EmployeeDTO setAvailability(@RequestBody Set<DayOfWeek> days, @PathVariable long employeeId) {
		return convertEntityToDTO(userService.setEmployeeWorkDays(days, employeeId), new EmployeeDTO());
	}

	@GetMapping("/employee/availability")
	public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO dto) {
		List<Employee> employees = userService.findEmployeesByService(dto);
		return employees.stream()
				.map(e -> convertEntityToDTO(e, new EmployeeDTO()))
				.collect(Collectors.toList());
	}

}

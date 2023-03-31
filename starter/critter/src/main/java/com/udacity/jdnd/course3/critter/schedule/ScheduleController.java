package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.web.bind.annotation.*;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.UserService;
import java.util.List;
import java.util.stream.Collectors;

import static com.udacity.jdnd.course3.critter.util.Util.convertDTOToEntity;
import static com.udacity.jdnd.course3.critter.util.Util.convertEntityToDTO;

/**
 * Handles web requests related to Schedules.
 * 
 * Includes requests for Schedule Entities.
 * 
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
	private UserService userService;
	private PetService petService;
	private ScheduleService scheduleService;

	public ScheduleController(UserService userService, PetService petService, ScheduleService scheduleService) {
		this.userService = userService;
		this.petService = petService;
		this.scheduleService = scheduleService;
	}

	@PostMapping
	public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
		return convertEntityToDTO(scheduleService.saveSchedule(convertDTOToEntity(scheduleDTO, Schedule.class)), new ScheduleDTO());
	}

	@GetMapping
	public List<ScheduleDTO> getAllSchedules() {
		return scheduleService.getSchedules().stream().map(schedule -> convertEntityToDTO(schedule, new ScheduleDTO()))
				.collect(Collectors.toList());
	}

	@GetMapping("/pet/{petId}")
	public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
		List<Schedule> schedules = scheduleService.getPetSchedules(petId);
		return schedules.stream().map(schedule -> convertEntityToDTO(schedule, new ScheduleDTO()))
				.collect(Collectors.toList());
	}

	@GetMapping("/employee/{employeeId}")
	public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
		List<Schedule> schedules = scheduleService.getEmployeeSchedules(employeeId);
		return schedules.stream().map(schedule -> convertEntityToDTO(schedule, new ScheduleDTO()))
				.collect(Collectors.toList());
	}

	@GetMapping("/customer/{customerId}")
	public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
		List<Schedule> schedules = scheduleService.getCustomerSchedules(customerId);
		return schedules.stream().map(schedule -> convertEntityToDTO(schedule, new ScheduleDTO()))
				.collect(Collectors.toList());
	}




}

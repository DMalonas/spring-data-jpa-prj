package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Employee;
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
		Schedule schedule = convertDTOToEntity(scheduleDTO, Schedule.class);
		return convertEntityToDTO(scheduleService.saveSchedule(schedule), new ScheduleDTO());
	}

	@GetMapping
	public List<ScheduleDTO> getAllSchedules() {
		List<Schedule> schedules = scheduleService.getSchedules();
		List<ScheduleDTO> scheduleDTOs = schedules.stream().map(schedule -> convertEntityToDTO(schedule, new ScheduleDTO()))
				.collect(Collectors.toList());
        for(ScheduleDTO sc: scheduleDTOs) {
        	System.out.println(sc.getPetIds());
        }
		return scheduleDTOs;
	}

	@GetMapping("/pet/{petId}")
	public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
		List<Schedule> schedules = scheduleService.getSchedulesForPet(petId);
		List<ScheduleDTO> scheduleDTOs = schedules.stream().map(schedule -> convertEntityToDTO(schedule, new ScheduleDTO()))
				.collect(Collectors.toList());
		return scheduleDTOs;
	}

	@GetMapping("/employee/{employeeId}")
	public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
		List<Schedule> schedules = scheduleService.getSchedulesForEmployee(employeeId);
		List<ScheduleDTO> scheduleDTOs = schedules.stream().map(schedule -> convertEntityToDTO(schedule, new ScheduleDTO()))
				.collect(Collectors.toList());
		return scheduleDTOs;
	}

	@GetMapping("/customer/{customerId}")
	public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
		List<Schedule> schedules = scheduleService.getSchedulesForCustomer(customerId);
		List<ScheduleDTO> scheduleDTOs = schedules.stream().map(schedule -> convertEntityToDTO(schedule, new ScheduleDTO()))
				.collect(Collectors.toList());
		return scheduleDTOs;
	}




}

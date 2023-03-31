package com.udacity.jdnd.course3.critter.schedule;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;

@Service
@Transactional
public class ScheduleService {

	private final ScheduleRepository scheduleRepository;

	private final CustomerRepository customerRepository;

	private final EmployeeRepository employeeRepository;


	private final PetRepository petRepository;

	public ScheduleService(ScheduleRepository scheduleRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository, PetRepository petRepository) {
		this.scheduleRepository = scheduleRepository;
		this.customerRepository = customerRepository;
		this.employeeRepository = employeeRepository;
		this.petRepository = petRepository;
	}

	public Schedule saveSchedule(Schedule schedule) {
		return scheduleRepository.save(schedule);
	}

	public List<Schedule> getSchedules() {
		return scheduleRepository.findAll();
	}


	public List<Schedule> getSchedulesForCustomer(long id) {
		List<Schedule> schedulesCustomer = new ArrayList<Schedule>();
		List<Pet> customerPets = customerRepository.getOne(id).getPets();
		for (Pet pet : customerPets) {
			schedulesCustomer.addAll(scheduleRepository.findByPets(pet));
		}
		return schedulesCustomer;
	}

	public List<Schedule> getSchedulesForEmployee(long id) {
		return scheduleRepository.findByEmployees(employeeRepository.getOne(id));
	}

	public List<Schedule> getSchedulesForPet(long id) {
		return scheduleRepository.findByPets(petRepository.getOne(id));
	}

}

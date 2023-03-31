package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

import static com.udacity.jdnd.course3.critter.util.Util.convertDTOToEntity;
import static com.udacity.jdnd.course3.critter.util.Util.convertEntityToDTO;

/**
 * Handles web requests related to Pets.
 * 
 * Includes requests for both Customer and Pet Entities.
 * 
 */
@RestController
@RequestMapping("/pet")
public class PetController {

	private final PetService petService;
	private final UserService userService;

	@Autowired
	public PetController(PetService petService, UserService userService) {
		this.petService = petService;
		this.userService = userService;
	}

	@PostMapping
	public PetDTO savePet(@RequestBody PetDTO petDTO) {
		Customer customer = userService.getCustomer(petDTO.getOwnerId());
		Pet pet = convertDTOToEntity(petDTO, Pet.class);
		pet.setCustomer(customer);
		return convertEntityToDTO(petService.savePet(pet), new PetDTO());
	}

	@GetMapping("/{petId}")
	public PetDTO getPet(@PathVariable long petId) {
		return convertEntityToDTO(petService.getPet(petId), new PetDTO());
	}

	@GetMapping
	public List<PetDTO> getPets() {
		return petService.getPets()
				.stream()
				.map(pet -> convertEntityToDTO(pet, new PetDTO()))
				.collect(Collectors.toList());
	}
	@GetMapping("/owner/{ownerId}")
	public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
		return petService.getPetsByOwner(ownerId)
				.stream()
				.map(pet -> convertEntityToDTO(pet, new PetDTO())).collect(Collectors.toList());
	}
}

package com.udacity.jdnd.course3.critter.pet;

import java.util.List;
import javax.transaction.Transactional;
import com.udacity.jdnd.course3.critter.user.UserService;
import org.springframework.stereotype.Service;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import static com.udacity.jdnd.course3.critter.util.Util.convertDTOToEntity;

@Service
@Transactional
public class PetService {

	private final PetRepository petRepository;
	private final CustomerRepository customerRepository;
	private final UserService userService;


	public PetService(PetRepository petRepository, CustomerRepository customerRepository, UserService userService) {
		this.petRepository = petRepository;
		this.customerRepository = customerRepository;
		this.userService = userService;
	}

	public Pet getPet(long id) {
		return petRepository.getOne(id);
	}

	public List<Pet> getPets() {
		return petRepository.findAll();
	}

	public List<Pet> getPetsByIds(List<Long> petIds) {
		return petRepository.findAllById(petIds);
	}

	public List<Pet> getPetsByOwner(long id) {
		return petRepository.findAllByCustomerId(id);
	}

	public Pet savePet(PetDTO petDTO) {
		Customer customer = userService.getCustomer(petDTO.getOwnerId());
		Pet pet = convertDTOToEntity(petDTO, Pet.class);
		pet.setCustomer(customer);
		Pet savedPet = petRepository.save(pet);
		customer = savedPet.getCustomer();
		customer.addPet(savedPet);
		customerRepository.save(customer);
		return savedPet;
	}

}

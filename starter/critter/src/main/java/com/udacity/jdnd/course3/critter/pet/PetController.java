package com.udacity.jdnd.course3.critter.pet;

import org.springframework.web.bind.annotation.*;
import com.udacity.jdnd.course3.critter.user.UserService;
import java.util.List;
import java.util.stream.Collectors;
import static com.udacity.jdnd.course3.critter.util.Util.convertEntityToDTO;


@RestController
@RequestMapping("/pet")
public class PetController {

	private final PetService petService;

	public PetController(PetService petService) {
		this.petService = petService;
	}

	@PostMapping
	public PetDTO savePet(@RequestBody PetDTO petDTO) {
		return convertEntityToDTO(petService.savePet(petDTO), new PetDTO());
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

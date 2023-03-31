package com.udacity.jdnd.course3.critter.user;

import java.util.List;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;
import com.udacity.jdnd.course3.critter.pet.Pet;
import java.util.ArrayList;

@Entity
@Data
public class Customer extends User {
	@Nationalized
	private String notes;

	@OneToMany(mappedBy = "customer")
	private List<Pet> pets = new ArrayList<Pet>();

	public void addPet(Pet pet) {
		pets.add(pet);
	}

}

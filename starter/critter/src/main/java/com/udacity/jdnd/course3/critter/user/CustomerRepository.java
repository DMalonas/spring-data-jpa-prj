package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import com.udacity.jdnd.course3.critter.pet.Pet;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findByPets(Pet pet);

}

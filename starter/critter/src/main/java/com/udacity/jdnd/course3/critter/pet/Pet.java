package com.udacity.jdnd.course3.critter.pet;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import org.hibernate.annotations.Nationalized;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.user.Customer;

@Entity
@Data
public class Pet {

	@Id
	@GeneratedValue
	private long id;

	@Column(name = "type_code")
	@Enumerated(EnumType.STRING)
	private PetType type;

	@Nationalized
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ManyToMany(mappedBy = "pets")
	private List<Schedule> schedules;
	@Column
	private LocalDate birthDate;

	@Nationalized
	private String notes;

}

package com.udacity.jdnd.course3.critter.schedule;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.Data;

@Entity
@Data
public class Schedule {

	@Id
	@GeneratedValue
	private long id;

	@ManyToMany
	@JoinTable(name = "schedule_employee")																																																										// later
	private List<Employee> employees;

	@ManyToMany
	private List<Pet> pets;

	@Column
	private LocalDate date;

	@ElementCollection(targetClass = EmployeeSkill.class)
	@Enumerated(EnumType.STRING)
	@Column
	private Set<EmployeeSkill> activities = new HashSet<>();
}

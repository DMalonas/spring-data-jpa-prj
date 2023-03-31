package com.udacity.jdnd.course3.critter.user;

import javax.persistence.*;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Employee extends User {
	@Column
	@ElementCollection(targetClass = EmployeeSkill.class)
	private Set<EmployeeSkill> skills;

	@Column
	@ElementCollection(targetClass = DayOfWeek.class)
	private Set<DayOfWeek> workDays;
	@ManyToMany(mappedBy = "employees")
	private List<Schedule> schedules;

}

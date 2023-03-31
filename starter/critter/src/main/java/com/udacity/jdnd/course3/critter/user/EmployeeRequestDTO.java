package com.udacity.jdnd.course3.critter.user;

import lombok.Data;
import java.time.LocalDate;
import java.util.Set;


@Data
public class EmployeeRequestDTO {
	private Set<EmployeeSkill> skills;
	private LocalDate date;
}

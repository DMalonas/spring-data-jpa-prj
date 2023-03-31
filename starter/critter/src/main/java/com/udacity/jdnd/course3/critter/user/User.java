package com.udacity.jdnd.course3.critter.user;

import javax.persistence.*;

import lombok.Data;
import org.hibernate.annotations.Nationalized;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class User {
	@Id
	@GeneratedValue
	private Long id;
	@Nationalized
	private String name;
	@Column
	private String phoneNumber;
}

package com.udacity.jdnd.course3.critter.user;

import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
public class UserDTO {

    private Long id;
    private String name;
    private String phoneNumber;
}

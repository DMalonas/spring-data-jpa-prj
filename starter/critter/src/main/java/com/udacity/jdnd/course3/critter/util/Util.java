package com.udacity.jdnd.course3.critter.util;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Util {

    private static PetService petService ;
    private static UserService userService;

    public Util(PetService petService, UserService userService) {
        this.petService = petService;
        this.userService = userService;
    }

    public static <S, T> T convertDTOToEntity(S dto, Class<T> entityClass) {
        T entity = null;
        try {
            entity = entityClass.newInstance();
            BeanUtils.copyProperties(dto, entity);
            if (dto instanceof EmployeeDTO) {
                if (((EmployeeDTO) dto).getDaysAvailable() != null) {
                    ((Employee) entity).setWorkDays(((EmployeeDTO) dto).getDaysAvailable().stream().collect(Collectors.toSet()));
                }
            }
            if (dto instanceof ScheduleDTO) {
                List<Long> petIds = ((ScheduleDTO)dto).getPetIds();
                if (petIds != null) {
                    List<Pet> pets = petService.getPetsByIds(petIds);
                    ((Schedule)entity).setPets(pets);
                }
                List<Long> employeeIds = ((ScheduleDTO)dto).getEmployeeIds();
                if (petIds != null) {
                    List<Employee> employees = userService.getEmployeesByIds(employeeIds);
                    ((Schedule)entity).setEmployees(employees);
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public static <E, D> D convertEntityToDTO(E entity, D dto) {
        BeanUtils.copyProperties(entity, dto);
        if (dto instanceof PetDTO) {
            Pet pet = (Pet) entity;
            PetDTO petDTO = (PetDTO) dto;
            petDTO.setOwnerId(pet.getCustomer().getId());
        } else if (dto instanceof ScheduleDTO) {
            Schedule schedule = (Schedule) entity;
            ScheduleDTO scheduleDTO = (ScheduleDTO) dto;
            List<Pet> pets = schedule.getPets();
            if (pets != null) {
                List<Long> petIds = pets.stream().map(pet -> pet.getId()).collect(Collectors.toList());
                scheduleDTO.setPetIds(petIds);
            }
            List<Employee> employees = schedule.getEmployees();
            if (employees != null) {
                List<Long> employeeIds = employees.stream().map(employee -> employee.getId()).collect(Collectors.toList());
                scheduleDTO.setEmployeeIds(employeeIds);
            }
        } else if (dto instanceof EmployeeDTO) {
            Employee employee = (Employee) entity;
            EmployeeDTO employeeDTO = (EmployeeDTO) dto;
            employeeDTO.setDaysAvailable(employee.getWorkDays());
        } else if (dto instanceof CustomerDTO) {
            Customer customer = (Customer)entity;
            CustomerDTO customerDTO = (CustomerDTO) dto;
            List<Long> petIds = customer.getPets().stream().map(pet -> pet.getId()).collect(Collectors.toList());
            customerDTO.setPetIds(petIds);
        }
        return dto;
    }

}

package co.uk.jdreamer.critter.service;

import co.uk.jdreamer.critter.entity.Employee;
import co.uk.jdreamer.critter.entity.Pet;
import co.uk.jdreamer.critter.entity.Schedule;
import co.uk.jdreamer.critter.exception.ResourceNotFoundException;
import co.uk.jdreamer.critter.repository.CustomerRepository;
import co.uk.jdreamer.critter.repository.EmployeeRepository;
import co.uk.jdreamer.critter.repository.PetRepository;
import co.uk.jdreamer.critter.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    private ScheduleRepository scheduleRepository;
    private CustomerRepository customerRepository;
    private EmployeeRepository employeeRepository;
    private PetRepository petRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository, PetRepository petRepository) {
        this.scheduleRepository = scheduleRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
    }

    public Schedule saveSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        List<Employee> employees = new ArrayList<>();
        List<Pet> pets = new ArrayList<>();

        if (employeeIds != null) {
            for (Long employeeId: employeeIds) {
                employees.add(employeeRepository.findEmployeeById(employeeId));
            }
        }
        schedule.setEmployees(employees);

        if (petIds != null) {
            for (Long petId: petIds) {
                pets.add(petRepository.findPetById(petId));
            }
        }
        schedule.setPets(pets);

        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getAllSchedulesForPetId(long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new ResourceNotFoundException("Pet not found, ID: " + petId));
        return scheduleRepository.getAllByPetsContains(pet);
    }

    public List<Schedule> getAllSchedulesForEmployeeId(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee not found, ID: " + employeeId));
        return scheduleRepository.getAllByEmployeesContains(employee);
    }

    public List<Schedule> getAllSchedulesForCustomerId(long customerId) {
        List<Pet> pets = customerRepository.getOne(customerId).getPets();
        return scheduleRepository.getAllByPetsIn(pets);
    }
}

package co.uk.jdreamer.critter.service;

import co.uk.jdreamer.critter.dto.EmployeeRequestDTO;
import co.uk.jdreamer.critter.entity.Employee;
import co.uk.jdreamer.critter.exception.ResourceNotFoundException;
import co.uk.jdreamer.critter.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findEmployeeById(long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() ->  new ResourceNotFoundException("Employee not found, ID: " + employeeId));
    }

    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee not found, ID: " + employeeId));
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> findEmployeesForService(EmployeeRequestDTO employeeDTO) {
        return employeeRepository.getAllByDaysAvailableContains(employeeDTO.getDate().getDayOfWeek()).stream()
                .filter(employee -> employee.getSkills().containsAll(employeeDTO.getSkills()))
                .collect(Collectors.toList());
    }
}

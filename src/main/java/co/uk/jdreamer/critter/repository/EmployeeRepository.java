package co.uk.jdreamer.critter.repository;

import co.uk.jdreamer.critter.entity.Employee;
import co.uk.jdreamer.critter.entity.EmployeeSkill;
import co.uk.jdreamer.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    List<Employee> getAllByDaysAvailableContains(DayOfWeek dayOfWeek);

    Employee findEmployeeById(Long employeeId);

}

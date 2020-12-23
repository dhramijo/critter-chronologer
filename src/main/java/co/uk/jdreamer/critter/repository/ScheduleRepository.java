package co.uk.jdreamer.critter.repository;

import co.uk.jdreamer.critter.entity.Employee;
import co.uk.jdreamer.critter.entity.Pet;
import co.uk.jdreamer.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    List<Schedule> getAllByPetsContains(Pet pet);

    List<Schedule> getAllByEmployeesContains(Employee employee);

    List<Schedule> getAllByPetsIn(List<Pet> pets);
}

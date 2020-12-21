package co.uk.jdreamer.critter.repository;

import co.uk.jdreamer.critter.entity.Customer;
import co.uk.jdreamer.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
}

package co.uk.jdreamer.critter.repository;

import co.uk.jdreamer.critter.entity.Customer;
import co.uk.jdreamer.critter.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet,Long> {
    List<Pet> findByCustomer(Optional<Customer> byId);
}

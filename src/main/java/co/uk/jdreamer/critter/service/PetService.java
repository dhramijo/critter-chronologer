package co.uk.jdreamer.critter.service;

import co.uk.jdreamer.critter.dto.PetDTO;
import co.uk.jdreamer.critter.entity.Customer;
import co.uk.jdreamer.critter.entity.Pet;
import co.uk.jdreamer.critter.exception.ResourceNotFoundException;
import co.uk.jdreamer.critter.repository.CustomerRepository;
import co.uk.jdreamer.critter.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetService {

    private PetRepository petRepository;
    private CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public Customer findOwnerByPetId(long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new ResourceNotFoundException("Pet not found, ID: " + petId));
        return pet.getCustomer();
    }

    public Pet savePet(Pet pet, long ownerId) {
        Customer customer = customerRepository.getOne(ownerId);
        pet.setCustomer(customer);
        pet = petRepository.save(pet);
        List<Pet> pets = customer.getPets();
        if (pets == null) {
             pets = new ArrayList<Pet>();
        }
        pets.add(pet);
        customer.setPets(pets);
        customerRepository.save(customer);
        return pet;
    }

    public Pet getPetById(long petId) {
        return petRepository.findById(petId).orElseThrow(() -> new ResourceNotFoundException("Pet not found, ID: " + petId));
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> findPetsByOwnerId(long ownerId) {
        return petRepository.findByCustomer(customerRepository.findById(ownerId));
    }
}

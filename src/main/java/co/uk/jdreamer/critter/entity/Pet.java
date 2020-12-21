package co.uk.jdreamer.critter.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = Customer.class)
    private Customer customer;

    private String name;
    private LocalDate birthDate;
    private PetType type;
    private String notes;

}

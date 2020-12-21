package co.uk.jdreamer.critter.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "customer", targetEntity = Pet.class, cascade = CascadeType.ALL)
    private List<Pet> pets;

    private String name;
    private String phoneNumber;
    private String notes;

}

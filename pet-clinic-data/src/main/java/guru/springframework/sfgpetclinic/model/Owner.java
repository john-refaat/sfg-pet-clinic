package guru.springframework.sfgpetclinic.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author john
 * @since 12/12/2023
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"pets"}, callSuper = true)
@Entity
@Table(name="owners")
public class Owner extends Person {

    @Builder
    public Owner(Long id, String firstName, String lastName, String address, String city, String telephone) {
        super(id, firstName, lastName);
        this.address = address;
        this.city = city;
        this.telephone = telephone;
    }

    @Column(name="address")
    private String address;

    @Column(name="city")
    private String city;

    @Column(name="telephone")
    private String telephone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Pet> pets = new HashSet<>();

    public Owner addPet(Pet pet) {
        pet.setOwner(this);
        pets.add(pet);
        return this;
    }


    /**
     * @param name the name of the pet
     * @return Optional Pet with the given name
     */
    public Optional<Pet> findPetByNameIgnoreNew(String name) {
        return pets.stream().filter(pet -> !pet.isNew() && pet.getName().equalsIgnoreCase(name)).findFirst();
    }

    @Override
    public String toString() {
        return "Owner{" +
                "firstName='" + super.getFirstName() + '\'' +
                "lastName='" + super.getLastName() + '\'' +
                "address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", telephone='" + telephone + '\'' +
                "} " + super.toString();
    }
}

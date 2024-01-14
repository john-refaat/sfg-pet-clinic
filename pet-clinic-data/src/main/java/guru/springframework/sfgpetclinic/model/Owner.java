package guru.springframework.sfgpetclinic.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
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
    public Owner(String firstName, String lastName, String address, String city, String telephone) {
        super(firstName, lastName);
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>();

    public Owner addPet(Pet pet) {
        pet.setOwner(this);
        pets.add(pet);
        return this;
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

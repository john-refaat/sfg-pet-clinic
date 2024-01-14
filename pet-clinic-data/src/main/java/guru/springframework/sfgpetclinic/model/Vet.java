package guru.springframework.sfgpetclinic.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author john
 * @since 12/12/2023
 */
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="vets")
public class Vet extends Person {
    
    @Builder
    public Vet(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="vet_specialities",
            joinColumns = @JoinColumn(name="vet_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private Set<Speciality> specialities = new HashSet<>();

}

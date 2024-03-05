package guru.springframework.sfgpetclinic.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * @author john
 * @since 12/12/2023
 */
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"visits"}, callSuper = true)
@Entity
@Table(name="pets")
public class Pet extends BaseEntity {

    @Builder
    public Pet(Long id, String name, PetType type, Owner owner, LocalDate birthDate) {
        super(id);
        this.name = name;
        this.type = type;
        this.owner = owner;
        this.birthDate = birthDate;
    }

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private PetType type;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
    private Set<Visit> visits = new HashSet<>();

    @Override
    public String toString() {
        return "Pet{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", owner=" + owner +
                ", birthDate=" + birthDate +
                '}';
    }

    public void addVisit(Visit visit) {
        visits.add(visit);
        visit.setPet(this);
    }

}

package guru.springframework.sfgpetclinic.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author john
 * @since 30/12/2023
 */
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"pet"}, callSuper = true)
@Table(name = "visits")
public class Visit extends BaseEntity {

    @Setter
    @Column(name = "date")
    @DateTimeFormat(pattern = "dd-MM-YYYY hh:mm:ss")
    private LocalDate date;

    @Setter
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @Override
    public String toString() {
        return "Visit{" +
                "date=" + date +
                ", description='" + description + '\'' +
                ", pet=" + pet +
                '}';
    }

    public void setPet(Pet pet) {
        pet.addVisit(this);
        this.pet = pet;
    }
}

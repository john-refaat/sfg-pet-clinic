package guru.springframework.sfgpetclinic.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author john
 * @since 30/12/2023
 */
@Getter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"pet"}, callSuper = true)
@Table(name = "visits")
public class Visit extends BaseEntity {

    @Builder
    public Visit(Long id, LocalDateTime date, String description) {
        super(id);
        this.date = date;
        this.description = description;
    }

    @Setter
    @Column(name = "date", columnDefinition = "TIMESTAMP")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    private LocalDateTime date;

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
        this.pet = pet;
    }
}

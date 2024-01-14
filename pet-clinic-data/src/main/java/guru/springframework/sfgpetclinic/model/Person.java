package guru.springframework.sfgpetclinic.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author john
 * @since 12/12/2023
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public class Person extends BaseEntity{

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

}

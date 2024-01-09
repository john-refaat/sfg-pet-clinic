package guru.springframework.sfgpetclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author john
 * @since 30/12/2023
 */
@Entity
@Table(name = "specialities")
public class Speciality extends BaseEntity  {

    @Column(name = "names")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

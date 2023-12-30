package guru.springframework.sfgpetclinic.model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author john
 * @since 12/12/2023
 */
public class Vet extends Person {

    private Set<Speciality> specialities = new HashSet<>();

    public Set<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<Speciality> specialities) {
        this.specialities = specialities;
    }
}

package guru.springframework.sfgpetclinic.repositories;

import guru.springframework.sfgpetclinic.model.Speciality;
import org.springframework.data.repository.CrudRepository;

/**
 * @author john
 * @since 09/01/2024
 */
public interface SpecialityRepository extends CrudRepository<Speciality, Long> {

}

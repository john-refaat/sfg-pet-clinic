package guru.springframework.sfgpetclinic.repositories;

import guru.springframework.sfgpetclinic.model.PetType;
import org.springframework.data.repository.CrudRepository;

/**
 * @author john
 * @since 09/01/2024
 */
public interface PetTypeRepository extends CrudRepository<PetType, Long> {

}

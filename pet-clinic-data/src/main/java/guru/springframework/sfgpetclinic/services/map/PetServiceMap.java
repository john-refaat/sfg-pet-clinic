package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.PetService;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author john
 * @since 18/12/2023
 */
@Service
public class PetServiceMap extends AbstractMapService<Pet, Long> implements PetService {
    private final AtomicLong generator = new AtomicLong(1L);

    @Override
    public Pet save(Pet pet) {
        if(pet.getId() == null) {
            pet.setId(generator.getAndIncrement());
        }
        return super.save(pet.getId(), pet);
    }

}

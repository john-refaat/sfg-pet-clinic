package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * @author john
 * @since 18/12/2023
 */
@Service
@Profile({"default", "map"})
public class PetMapService extends AbstractMapService<Pet, Long> implements PetService {

    private final PetTypeService petTypeService;

    public PetMapService(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public Pet save(Pet pet) {
        if (pet == null)
            throw new RuntimeException("Invalid Pet");

        if(pet.getType() == null)
            throw new RuntimeException("Pet Type is required");


        if (pet.getOwner().getId() == null)
            throw new RuntimeException("Owner is Not Saved. Please save owner first");

        if(pet.getType().getId() == null || petTypeService.findById(pet.getType().getId())==null) {
            PetType savedPetType = petTypeService.save(pet.getType());
            pet.getType().setId(savedPetType.getId());
        }

        return super.save(pet);
    }

}

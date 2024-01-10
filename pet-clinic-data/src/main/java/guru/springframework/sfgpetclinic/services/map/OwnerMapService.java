package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author john
 * @since 18/12/2023
 */
@Service
@Profile({"default", "map"})
public class OwnerMapService extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetService petService;

    public OwnerMapService(PetService petService) {
        this.petService = petService;
    }

    public Owner save(Owner owner) {
        if (owner == null)
            throw new RuntimeException("Invalid Owner");
        Owner savedOwner = super.save(owner);
        if(owner.getPets() != null) {
            persistPets(savedOwner);
        }
        return savedOwner;
    }

    private void persistPets(final Owner savedOwner) {
        savedOwner.getPets().forEach(pet -> {
            if (pet.getId() ==null || petService.findById(pet.getId()) == null) {
                pet.setOwner(savedOwner);
                Pet savedPet = petService.save(pet);
                pet.setId(savedPet.getId());
            }
        });
    }


    @Override
    public Set<Owner> findByLastName(String name) {
        return map.values().stream().filter(value -> value.getLastName().equals(name)).collect(Collectors.toSet());
    }
}

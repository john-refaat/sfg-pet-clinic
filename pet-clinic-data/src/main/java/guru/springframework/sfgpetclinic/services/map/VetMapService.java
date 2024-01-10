package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.SpecialityService;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * @author john
 * @since 18/12/2023
 */
@Service
@Profile({"default", "map"})
public class VetMapService extends AbstractMapService<Vet, Long> implements VetService {

    private final SpecialityService specialityService;

    public VetMapService(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    @Override
    public Vet save(Vet vet) {
        if(!vet.getSpecialities().isEmpty()) {
            saveSpecialities(vet);
        }
        return super.save(vet);
    }

    private void saveSpecialities(Vet vet) {
        vet.getSpecialities().forEach(speciality -> {
            if (speciality.getId() == null) {
                Speciality savedSpeiality = specialityService.save(speciality);
                speciality.setId(savedSpeiality.getId());
            }

        });
    }
}

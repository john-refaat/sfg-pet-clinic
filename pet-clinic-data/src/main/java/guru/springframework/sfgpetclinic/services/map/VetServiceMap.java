package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.VetService;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author john
 * @since 18/12/2023
 */
public class VetServiceMap extends AbstractMapService<Vet, Long> implements VetService {
    private final AtomicLong generator = new AtomicLong(1L);

    @Override
    public Vet save(Vet vet) {
        if (vet.getId() == null) {
            vet.setId(generator.getAndIncrement());
        }
        return super.save(vet.getId(), vet);
    }
}

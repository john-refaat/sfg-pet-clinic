package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @author john
 * @since 18/12/2023
 */
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {

    private final AtomicLong generator = new AtomicLong(1L);

    public Owner save(Owner owner) {
        if (owner.getId() == null){
            owner.setId(generator.getAndIncrement());
        }
        return super.save(owner.getId(), owner);
    }

    @Override
    public Set<Owner> findByLastName(String name) {
        return map.values().stream().filter(value -> value.getLastName().equals(name)).collect(Collectors.toSet());
    }
}

package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author john
 * @since 18/12/2023
 */
@Service
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {


    public Owner save(Owner owner) {
        return super.save(owner);
    }

    @Override
    public Set<Owner> findByLastName(String name) {
        return map.values().stream().filter(value -> value.getLastName().equals(name)).collect(Collectors.toSet());
    }
}

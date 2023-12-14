package guru.springframework.sfgpetclinic.services;

import guru.springframework.sfgpetclinic.model.Owner;

import java.util.Set;

/**
 * @author john
 * @since 15/12/2023
 */
public interface OwnerService {
    Set<Owner> findByLastName(String name);
    Owner findById(Long id);
    Owner save(Owner owner);
    Set<Owner> findAll();
}

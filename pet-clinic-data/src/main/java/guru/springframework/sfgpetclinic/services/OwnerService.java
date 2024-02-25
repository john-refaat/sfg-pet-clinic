package guru.springframework.sfgpetclinic.services;

import guru.springframework.sfgpetclinic.model.Owner;

import java.util.Set;

/**
 * @author john
 * @since 15/12/2023
 */
public interface OwnerService extends CrudService<Owner, Long> {
    Set<Owner> findByLastName(String name);
    Set<Owner> findByLastNameContainsIgnoreCase(String name);
}

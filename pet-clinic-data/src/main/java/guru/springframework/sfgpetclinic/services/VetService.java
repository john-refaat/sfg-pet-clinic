package guru.springframework.sfgpetclinic.services;

import guru.springframework.sfgpetclinic.model.Vet;

import java.util.Set;

/**
 * @author john
 * @since 15/12/2023
 */
public interface VetService {

    Vet findById();
    Vet save(Vet vet);
    Set<Vet> findAll();
}

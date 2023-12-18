package guru.springframework.sfgpetclinic.services;

import java.util.Set;

/**
 * @author john
 * @since 18/12/2023
 */
public interface CrudService<T, ID> {
    Set<T> findAll();
    T findById(ID id);
    T save(T object);
    void delete(T object);
    void deleteById(ID id);
}

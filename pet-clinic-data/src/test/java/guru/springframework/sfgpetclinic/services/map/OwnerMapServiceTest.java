package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author john
 * @since 26/01/2024
 */
class OwnerMapServiceTest {

    public static final String SMITH = "Smith";
    private OwnerMapService ownerMapService;
    private final Long ownerId = 1L;

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetMapService(new PetTypeMapService()));
        Owner owner = Owner.builder().lastName(SMITH).build();
        owner.setId(ownerId);
        ownerMapService.save(owner);
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerMapService.findAll();
        assertEquals(1, owners.size());
    }

    @Test
    void saveExistingId() {
        Long id= 2L;
        Owner owner = Owner.builder().build();
        owner.setId(id);
        Owner savedOwner = ownerMapService.save(owner);
        assertEquals(id, savedOwner.getId());
    }

    @Test
    void saveNoId() {
        Owner owner = Owner.builder().build();
        Owner savedOwner = ownerMapService.save(owner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void delete() {
        assertEquals(1, ownerMapService.findAll().size());
        ownerMapService.delete(ownerMapService.findById(ownerId));
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void deleteById() {
        assertEquals(1, ownerMapService.findAll().size());
        ownerMapService.deleteById(ownerId);
        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void findByLastName() {
        Set<Owner> owners = ownerMapService.findByLastName(SMITH);
        assertEquals(1, owners.size());
        assertTrue(owners.stream().findFirst().isPresent());
        assertEquals(SMITH, owners.stream().findFirst().get().getLastName());
        assertEquals(1L, owners.stream().findFirst().get().getId());
    }

    @Test
    void findByLastNameNotFound() {
        Set<Owner> owners = ownerMapService.findByLastName("Foo");
        assertEquals(0, owners.size());
    }
}
package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author john
 * @since 26/01/2024
 */
@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    private static final String LAST_NAME = "Smith";
    private static final Long ID = 10L;
    public static final String FIRST_NAME = "John";
    public static final String TELEPHONE = "222444666888";
    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    OwnerSDJpaService ownerSDJpaService;

    private Set<Owner> expectedOwners;
    private Owner expectedOwner;
    @BeforeEach
    void setUp() {
        expectedOwner = Owner.builder().firstName(FIRST_NAME).lastName(LAST_NAME).telephone(TELEPHONE).build();
        expectedOwner.setId(ID);
        expectedOwners = new HashSet<>();
        expectedOwners.add(expectedOwner);
    }

    @Test
    void findAll() {

        Mockito.when(ownerRepository.findByLastName(ArgumentMatchers.anyString())).thenReturn(expectedOwners);

        Set<Owner> actualSet = ownerSDJpaService.findByLastName(LAST_NAME);
        assertEquals(1, actualSet.size());
        assertTrue(actualSet.stream().findFirst().isPresent());
        assertEquals(LAST_NAME, actualSet.stream().findFirst().get().getLastName());

        Mockito.verify(ownerRepository, Mockito.times(1)).findByLastName(ArgumentMatchers.any());
    }

    @Test
    void findById() {
        Mockito.when(ownerRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(expectedOwner));
        Owner actual = ownerSDJpaService.findById(ID);
        assertNotNull(actual);
        assertEquals(expectedOwner, actual);
        assertEquals(ID, actual.getId());
        Mockito.verify(ownerRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().firstName(FIRST_NAME).lastName(LAST_NAME).telephone(TELEPHONE).build();
        ownerToSave.setId(1L);
        Mockito.when(ownerRepository.save(ArgumentMatchers.any(Owner.class))).thenReturn(expectedOwner);
        Owner savedOwner = ownerSDJpaService.save(ownerToSave);
        assertNotNull(savedOwner);
        assertEquals(ID, savedOwner.getId());
        assertEquals(FIRST_NAME, savedOwner.getFirstName());
        assertEquals(LAST_NAME, savedOwner.getLastName());
        assertEquals(TELEPHONE, savedOwner.getTelephone());
        Mockito.verify(ownerRepository, Mockito.times(1)).save(ArgumentMatchers.any(Owner.class));

    }

    @Test
    void delete() {
        ownerSDJpaService.delete(expectedOwner);
        Mockito.verify(ownerRepository, Mockito.times(1)).delete(ArgumentMatchers.any(Owner.class));
    }

    @Test
    void deleteById() {
        ownerSDJpaService.deleteById(1L);
        Mockito.verify(ownerRepository, Mockito.times(1)).deleteById(ArgumentMatchers.anyLong());
    }

    @Test
    void findByLastName() {
        Mockito.when(ownerRepository.findByLastName(ArgumentMatchers.anyString())).thenReturn(expectedOwners);
        Set<Owner> foundOwners = ownerSDJpaService.findByLastName(LAST_NAME);
        assertNotNull(foundOwners);
        assertEquals(1, foundOwners.size());
        Mockito.verify(ownerRepository, Mockito.times(1)).findByLastName(ArgumentMatchers.anyString());

    }
}
package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author john
 * @since 26/01/2024
 */
@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @InjectMocks
    private OwnerController ownerController;

    @Mock
    private OwnerService ownerService;

    private Set<Owner> owners;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        owners = new HashSet<>();
        Owner owner1 = Owner.builder().id(1L).build();
        Owner owner2 = Owner.builder().id(2L).build();

        owners.add(owner1);
        owners.add(owner2);

        mockMvc = MockMvcBuilders
                .standaloneSetup(ownerController)
                .build();
    }

    @Test
    void listOwners() throws Exception {
        Mockito.when(ownerService.findAll()).thenReturn(owners);
        mockMvc.perform(MockMvcRequestBuilders.get("/owners"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/index"))
                .andExpect(MockMvcResultMatchers.model().attribute("owners", Matchers.hasSize(2)));
    }

    @Test
    void listOwnersByIndex() throws Exception {
        Mockito.when(ownerService.findAll()).thenReturn(owners);
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/index"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/index"))
                .andExpect(MockMvcResultMatchers.model().attribute("owners", Matchers.hasSize(2)));
    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/find"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/findOwners"));
        Mockito.verifyNoInteractions(ownerService);
    }

    @Test
    void processFindFormReturnMany() throws Exception {
        Mockito.when(ownerService.findByLastNameContainsIgnoreCase(ArgumentMatchers.anyString())).thenReturn(owners);
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/searchResults"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("selection"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/ownersList"));
        Mockito.verify(ownerService, Mockito.times(1)).findByLastNameContainsIgnoreCase(ArgumentMatchers.anyString());
    }

    @Test
    void processFindFormReturnOne() throws Exception {
        Mockito.when(ownerService.findByLastNameContainsIgnoreCase(ArgumentMatchers.anyString())).thenReturn(Collections.singleton(Owner.builder().id(1L).build()));
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/searchResults"))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("selection"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/1"));
        Mockito.verify(ownerService, Mockito.times(1)).findByLastNameContainsIgnoreCase(ArgumentMatchers.anyString());
    }

    @Test
    void processFindFormReturnEmpty() throws Exception {
        Mockito.when(ownerService.findByLastNameContainsIgnoreCase(ArgumentMatchers.anyString())).thenReturn(Collections.emptySet());
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/searchResults"))
                .andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("selection"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/findOwners"));
        Mockito.verify(ownerService, Mockito.times(1)).findByLastNameContainsIgnoreCase(ArgumentMatchers.anyString());
    }

    @Test
    void processEmptyFindFormReturnMany() throws Exception {
        Mockito.when(ownerService.findByLastNameContainsIgnoreCase(ArgumentMatchers.anyString())).thenReturn(owners);
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/searchResults").param("lastName", " "))
              .andExpect(MockMvcResultMatchers.model().attributeExists("selection"))
              .andExpect(MockMvcResultMatchers.status().isOk())
              .andExpect(MockMvcResultMatchers.view().name("owners/ownersList"));
        Mockito.verify(ownerService, Mockito.times(1)).findByLastNameContainsIgnoreCase(ArgumentMatchers.anyString());
    }

    @Test
    void displayOwner() throws Exception {
        Owner owner = Owner.builder().id(1L).build();
        Mockito.when(ownerService.findById(ArgumentMatchers.anyLong())).thenReturn(owner);
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
                .andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.model().attribute("owner", Matchers.hasProperty("id", Matchers.is(1L))))
                .andExpect(MockMvcResultMatchers.view().name("owners/ownerDetails"));
    }

    @Test
    void initCreateForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/create"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
                .andExpect(MockMvcResultMatchers.view().name("owners/ownerForm"));
    }

    @Test
    void procesCreateForm() throws Exception {
        //Given
        Owner owner = Owner.builder().id(1L).build();

        Mockito.when(ownerService.save(ArgumentMatchers.any())).thenReturn(owner);
        //When
        mockMvc.perform(MockMvcRequestBuilders.post("/owners/create"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/1"));

        //Then
        Mockito.verify(ownerService, Mockito.times(1)).save(ArgumentMatchers.any(Owner.class));
    }

    @Test
    void initUpdateForm() throws Exception {
        // Given
        Owner owner = Owner.builder().id(1L).build();

        Mockito.when(ownerService.findById(ArgumentMatchers.anyLong())).thenReturn(owner);
        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/1/update"))
              .andExpect(MockMvcResultMatchers.status().isOk())
              .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
              .andExpect(MockMvcResultMatchers.view().name("owners/ownerForm"));

        //Then
        Mockito.verify(ownerService, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
    }

    @Test
    void processUpdateForm() throws Exception {
        //Given
        Owner owner = Owner.builder().id(1L).build();

        Mockito.when(ownerService.save(ArgumentMatchers.any())).thenReturn(owner);
        //When
        mockMvc.perform(MockMvcRequestBuilders.post("/owners/1/update"))
               .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
               .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/1"));

        //Then
        Mockito.verify(ownerService, Mockito.times(1)).save(ArgumentMatchers.any(Owner.class));
    }
}
package pl.piomin.samples.spring.bluegreen.person.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.piomin.samples.spring.bluegreen.person.model.Person;
import pl.piomin.samples.spring.bluegreen.person.repository.PersonRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonControllerUnitTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonController controller;

    private Person testPerson;

    @BeforeEach
    void setUp() {
        testPerson = new Person();
        testPerson.setId(1L);
        testPerson.setFirstName("John");
        testPerson.setLastName("Doe");
        testPerson.setAge(30);
    }

    @Test
    void add_ShouldSaveAndReturnPerson() {
        when(repository.save(any(Person.class))).thenReturn(testPerson);

        Person result = controller.add(testPerson);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        verify(repository, times(1)).save(any(Person.class));
    }

    @Test
    void update_ShouldSaveAndReturnUpdatedPerson() {
        testPerson.setFirstName("Jane");
        when(repository.save(any(Person.class))).thenReturn(testPerson);

        Person result = controller.update(testPerson);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        verify(repository, times(1)).save(any(Person.class));
    }

    @Test
    void findById_WhenPersonExists_ShouldReturnPerson() {
        when(repository.findById(1L)).thenReturn(Optional.of(testPerson));

        Person result = controller.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenPersonDoesNotExist_ShouldReturnNull() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Person result = controller.findById(99L);

        assertNull(result);
        verify(repository, times(1)).findById(99L);
    }

    @Test
    void findAll_ShouldReturnAllPersons() {
        Person person2 = new Person();
        person2.setId(2L);
        person2.setFirstName("Jane");
        person2.setLastName("Smith");
        person2.setAge(25);

        when(repository.findAll()).thenReturn(Arrays.asList(testPerson, person2));

        Iterable<Person> result = controller.findAll();

        assertNotNull(result);
        int count = 0;
        for (Person p : result) {
            count++;
        }
        assertEquals(2, count);
        verify(repository, times(1)).findAll();
    }

    @Test
    void findAll_WhenEmpty_ShouldReturnEmptyList() {
        when(repository.findAll()).thenReturn(Arrays.asList());

        Iterable<Person> result = controller.findAll();

        assertNotNull(result);
        assertFalse(result.iterator().hasNext());
        verify(repository, times(1)).findAll();
    }
}

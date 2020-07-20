package de.schauderhaft.javaaktuell.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class SpringDataJpaTests {

	@Autowired
	PersonRepository persons;

	Person paul = createPerson("Paul");
	Person leto = createPerson("Leto");

	@BeforeEach
	void beforeEach() {

		paul.setGender(Gender.MALE);
		paul.setHobbies(Collections.singleton(createHobby("Worm riding")));
		paul.setAddress(createAddress("Arakeen"));

		leto.setHobbies(Collections.singleton(createHobby("Bull riding")));
		leto.setAddress(createAddress("Cladan"));
	}

	private Address createAddress(String city) {
		Address paulsAddress = new Address();
		paulsAddress.setCity(city);
		return paulsAddress;
	}

	@Test
	public void executeCrudFunctions() {

		persons.save(paul);

		Person reloaded = persons.findById(paul.id).get();

		assertThat(reloaded).isNotNull();

		persons.delete(reloaded);

		assertThat(persons.findAll()).isEmpty();
	}

	@Test
	public void executeQueryDerivation() {

		persons.saveAll(Arrays.asList(paul, leto));

		Person leto = persons.findByFirstNameIgnoreCase("leto");

		assertThat(leto).isNotNull();

	}

	@Test
	public void executeExplicitQuery() {

		persons.saveAll(Arrays.asList(paul, leto));

		List<Person> wormRidersFromArakeen = persons.findByHobbyOnArrakis("Worm riding");

		assertThat(wormRidersFromArakeen).containsExactly(paul);

	}

	@Test
	public void executeQBE() {

		persons.saveAll(Arrays.asList(paul, leto));

		Person firstNameL = createPerson("L");
		Example<Person> example = Example.of(firstNameL, ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.STARTING));

		Iterable<Person> startingWithL = persons.findAll(example);

		assertThat(startingWithL).containsExactly(leto);

	}

	private Person createPerson(String firstName) {

		Person person = new Person();
		person.firstName = firstName;
		return person;

	}

	private Hobby createHobby(String name) {

		Hobby hobby = new Hobby();
		hobby.setName(name);
		return hobby;
	}
}

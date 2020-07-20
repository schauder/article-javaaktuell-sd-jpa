package de.schauderhaft.javaaktuell.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class SpringDataJpaTests {


	@Autowired
	PersonRepository persons;


	@Test
	public void executeCrudFunctions(){

		Person p = createPerson("Paul");

		persons.save(p);

		Person reloaded = persons.findById(p.id).get();

		assertThat(reloaded).isNotNull();

		persons.delete(reloaded);

		assertThat(persons.findAll()).isEmpty();
	}


	@Test
	public void executeQueryDerivation() {

		persons.saveAll(Arrays.asList(createPerson("Paul"), createPerson("Leto")));

		Person leto = persons.findByFirstNameIgnoreCase("leto");

		assertThat(leto).isNotNull();

	}

	private Person createPerson(String firstName) {

		Person person = new Person();
		person.firstName = firstName;
		return person;

	}
}

package de.schauderhaft.javaaktuell.jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.data.domain.ExampleMatcher.StringMatcher.*;

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
		paul.setAddress(createAddress("Arrakeen"));

		leto.setHobbies(Collections.singleton(createHobby("Bull riding")));
		leto.setAddress(createAddress("Caladan"));
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
		Example<Person> example = Example.of(firstNameL,
				ExampleMatcher.matching().withStringMatcher(STARTING));

		Iterable<Person> startingWithL = persons.findAll(example);

		assertThat(startingWithL).containsExactly(leto);

	}

	@Test
	public void executeSpecification() {

		persons.saveAll(Arrays.asList(paul, leto));


		Iterable<Person> paulOrLeto = persons.findAll(byName("Leto").or(byCity("Arrakeen")));

		assertThat(paulOrLeto).containsExactlyInAnyOrder(paul, leto);

	}

	@Test
	public void executeCustomMethod() {

		Pair<Person, Person> kids = persons.spawnKids();


		Iterable<Person> all = persons.findAll();

		assertThat(all).extracting(Person::getFirstName).containsExactlyInAnyOrder("Ghanima", "Leto II");

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

	private Specification byName(String name) {

		return (Specification) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("firstName"), name);
	}
	private Specification byCity(String name) {

		return (Specification) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("address").get("city"), name);
	}
}

package de.schauderhaft.javaaktuell.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@DataJpaTest
class SpringDataJpaTests {


	@Autowired
	PersonRepository persons;


	@Test
	public void executeCrudFunctions(){

		Person p = new Person();

		persons.save(p);

		Person reloaded = persons.findById(p.id).get();

		persons.delete(reloaded);

		System.out.println("done");
	}
}

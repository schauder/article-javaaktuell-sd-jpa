package de.schauderhaft.javaaktuell.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@DataJpaTest
@Transactional
class JpaTests {

	@Autowired
	EntityManager em;

	@Autowired
	JdbcTemplate template;

	@Test
	public void testPersistenceBehaviour() {

		Person p = new Person();
		em.persist(p);

		System.out.println("First test " + template.queryForObject("select count(*) from Person", Integer.class));

		Person reloaded = em.find(Person.class, p.getId());

		System.out.println("Second test " + template.queryForObject("select count(*) from Person", Integer.class));

		Person reloadedAgain = em
				.createQuery("SELECT p  FROM Person p " +
						"WHERE p.id = :id", Person.class)
				.setParameter("id", p.getId())
				.getSingleResult();

		System.out.println("Third test " + template.queryForObject("select count(*) from Person", Integer.class));

	}
}

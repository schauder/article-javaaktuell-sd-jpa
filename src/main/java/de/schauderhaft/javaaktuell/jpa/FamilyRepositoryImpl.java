/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.schauderhaft.javaaktuell.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;

import javax.persistence.EntityManager;

public class FamilyRepositoryImpl implements FamilyRepository{
	@Autowired
	EntityManager em;


	@Override
	public Pair<Person, Person> spawnKids() {

		Person ghanima = new Person();
		ghanima.setFirstName("Ghanima");
		Person leto2 = new Person();
		leto2.setFirstName("Leto II");

		em.persist(ghanima);
		em.persist(leto2);

		return Pair.of(ghanima, leto2);
	}
}

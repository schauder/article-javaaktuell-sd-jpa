package de.schauderhaft.javaaktuell.jpa;/*
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

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

public interface PersonRepository extends
		CrudRepository<Person, Long>, // the default interface to extend
		QueryByExampleExecutor, // for query by example
		JpaSpecificationExecutor, // for specifications
		FamilyRepository
{
	Person findByFirstNameIgnoreCase(String name);

	@Query("select p from Person p join p.hobbies h where h.name = :hobby and p.address.city = 'Arrakeen'")
	List<Person> findByHobbyOnArrakis(String hobby);
}

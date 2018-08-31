package com.xcy.springboot;

import com.xcy.dao.UserDao;
import com.xcy.domain.UserElasticSearch;
import com.xcy.domain.UserMongo;
import com.xcy.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Autowired
	UserService userService;

	@Autowired
	MongoRepository<UserMongo, String> userDaoMongo;

//	@Autowired
//	ElasticsearchRepository<UserElasticSearch, String> userDaoEs;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testAdvice() {

		userService.addUser();

	}

	@Test
	public void testUpdate() {


		UserMongo user = new UserMongo();
		user.setId("5b8680c353aa6614a6696ea7");
		user.setName("张三三");
		user.setAddress("三里河东路");

		final UserMongo userNew = userDaoMongo.save(user);

		System.out.println(userNew);

	}

	@Test
	public void testQuery() {

		final List<UserMongo> all = userDaoMongo.findAll();

		System.out.println(all);

		final UserMongo user = userDaoMongo.findOne("5b8680c353aa6614a6696ea7");

		System.out.println(user);

		UserMongo userExample = new UserMongo();
		userExample.setName("三$");
		userExample.setAddress("三里河");
		Example<UserMongo> example = Example.of(userExample, ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.REGEX));
		final List<UserMongo> exampleAll = userDaoMongo.findAll(example);
		System.out.println(exampleAll);

	}

	/**
	 * ES插入
	 */
	public void testInsertEs() {



	}

}

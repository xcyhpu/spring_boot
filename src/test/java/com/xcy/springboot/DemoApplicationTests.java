package com.xcy.springboot;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.xcy.domain.UserMongo;
import com.xcy.elastic.User;
import com.xcy.mybatis.dao.GeneratorCardTaskInfoMoMapper;
import com.xcy.mybatis.mo.GeneratorCardTaskInfoMo;
import com.xcy.mybatis.mo.GeneratorCardTaskInfoMoExample;
import com.xcy.rocketmq.MessageListener;
import com.xcy.service.UserService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.xcy.mybatis.dao")
public class DemoApplicationTests {

	@Autowired
	UserService userService;

//	@Autowired
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


	@Autowired
	private JestClient client;


	@Test
	public void testCRUD() {


		final String index = "base_info";
		final String type = "users_2";

		try {

			DeleteIndex deleteIndex = new DeleteIndex.Builder(index).build();
			JestResult result = client.execute(deleteIndex);
			System.out.println("Delete result : " + result.getJsonString());

			CreateIndex createIndex = new CreateIndex.Builder(index).build();
			result = client.execute(createIndex);
			System.out.println("Create result : " + result.getJsonString());

			Bulk.Builder builder = new Bulk.Builder();
			for (int i=0;i < 10; i++) {

				User user = new User((long)(i+1), "用户"+(i+1));

				builder.addAction(new Index.Builder(user).index(index).type(type).build());

			}

			final Bulk bulk = builder.build();

			result = client.execute(bulk);

			System.out.println("Insert batch result : "+result.getJsonString());


		} catch (Exception e) {
			e.printStackTrace();
		}


	}


//	@Test
//	public void testCreateIndex() {
//
//		CreateIndex createIndex = new CreateIndex.Builder("users").build();
//
//		try {
//			final JestResult result = client.execute(createIndex);
//			System.out.println(result);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//
//	}

//	@Test
//	public void testCreateType() {
//
//
//
//	}

	@Test
	public void testQuery2() {

		//查询表达式
		String json = "{\n" +
				"    \"query\" : {\n" +
				"        \"match\" : {\n" +
				"            \"content\" : \"张三\"\n" +
				"        }\n" +
				"    }\n" +
				"}";
		//构建搜索功能
		Search search = new Search.Builder(json).addIndex("base_info").addType("users").build();

		try {
			SearchResult result = client.execute(search);
			System.out.println(result.getJsonString());
			final List<User> objectList = result.getSourceAsObjectList(User.class, false);
			System.out.println(objectList);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

//	/**
//	 * ES插入
//	 */
//	public void testInsertEs() {
//
//
//
//	}

	@Autowired
	private ApplicationContext context;

	@Test
	public void testRocketMQ() throws InterruptedException, RemotingException,
			MQClientException, MQBrokerException {


		DefaultMQProducer producer = context.getBean(DefaultMQProducer.class);

		final Message message = new Message();

		message.setTopic("TEST");
		message.setTags("TEST_2");
		message.setKeys("xuchunyang");
		message.setBody(("xcy from springboot："+new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS").format(new Date())).getBytes());

		final SendResult result = producer.send(message);

		System.out.println(result);

		Thread.sleep(10000);

	}

	@Test
	public void testRocketMQ2() throws InterruptedException, RemotingException,
			MQClientException, MQBrokerException {


		String topic = "TEST_HAHA_TOPIC2";
		String tags = "TEST_HAHA_TAGS2";

		DefaultMQProducer producer = context.getBean(DefaultMQProducer.class);

		final Message message = new Message();

		message.setTopic(topic);
		message.setTags(tags);
		message.setKeys("xuchunyang");
		message.setBody(("xcy from springboot："+new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS").format(new Date())).getBytes());

		final SendResult result = producer.send(message);

		System.out.println(result);

		/** 先send，保证topic和tag被创建，然后才能subscribe */

		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
		consumer.setNamesrvAddr(namesrvAddr);
		consumer.setConsumeThreadMin(consumeThreadMin);
		consumer.setConsumeThreadMax(consumeThreadMax);
		consumer.setMessageListener(messageListener);

		try {
			consumer.subscribe(topic, tags);
			consumer.start();
			System.out.println("Consumer start successfully!!!"
					+ "  groupName : " + groupName
					+ "  namesrvAddr : " + namesrvAddr
					+ "  topic : " + topic
					+ "  tags : " + tags);
		} catch (MQClientException e) {
			e.printStackTrace();
		}

	}

	@Value("${rocketmq.consumer.groupName}")
	private String groupName;

	@Value("${rocketmq.consumer.namesrvAddr}")
	private String namesrvAddr;

	@Value("${rocketmq.consumer.consumeThreadMin}")
	private int consumeThreadMin;

	@Value("${rocketmq.consumer.consumeThreadMax}")
	private int consumeThreadMax;

	@Autowired
	private MessageListener messageListener;

	@Test
	public void testMybatisGenerator() {

		List<String> warnings = new ArrayList<>();
		boolean overwrite = true;
		//如果这里出现空指针，直接写绝对路径即可。
		String genCfg = "/mbgConfiguration.xml";
		File configFile = new File(this.getClass().getResource(genCfg).getFile());
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = null;
		try {
			config = cp.parseConfiguration(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLParserException e) {
			e.printStackTrace();
		}
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = null;
		try {
			myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		try {
			myBatisGenerator.generate(null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}


	@Resource
	private GeneratorCardTaskInfoMoMapper generatorCardTaskInfoMoMapper;

	@Test
	public void testMybatisDao() {

		GeneratorCardTaskInfoMoExample example = new GeneratorCardTaskInfoMoExample();

		example.createCriteria().andDisplayIdEqualTo("AP1810150001");

		List<GeneratorCardTaskInfoMo> list = generatorCardTaskInfoMoMapper.selectByExample(example);

		System.out.println(JSON.toJSONString(list, true));

	}

}

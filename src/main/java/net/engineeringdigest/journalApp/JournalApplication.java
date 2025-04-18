package net.engineeringdigest.journalApp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class JournalApplication {

	public static void main(String[] args) {
//		ConfigurableApplicationContext context =
				SpringApplication.run(JournalApplication.class, args);

//		ConfigurableEnvironment environment = context.getEnvironment();
//		System.out.println(environment.getActiveProfiles()[0]);

	}
	@Bean
	public PlatformTransactionManager addPlatformTransactionManager(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}

	@Bean
	public RestTemplate restTemplate(){
		return  new RestTemplate();
	}

//	@Bean
//	public CommandLineRunner testRedis(RedisTemplate<String, String> redisTemplate) {
//		return args -> {
//			try {
//				redisTemplate.opsForValue().set("t1", "going into the db");
//				String value = redisTemplate.opsForValue().get("name");
//				System.out.println("🔑 Fetched value for 't1': " + value);
//
//				// Log connection info
//				Object connectionFactory = redisTemplate.getConnectionFactory();
//				if (connectionFactory instanceof org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory lettuceConnectionFactory) {
//					System.out.println("🚀 Redis Host: " + lettuceConnectionFactory.getHostName());
//					System.out.println("🚀 Redis Port: " + lettuceConnectionFactory.getPort());
//					System.out.println("🚀 Redis DB Index: " + lettuceConnectionFactory.getDatabase());
//					System.out.println("🚀 Redis Password: " + lettuceConnectionFactory.getPassword());
//				} else {
//					System.out.println("⚠️ Redis Connection Factory is not LettuceConnectionFactory.");
//				}
//			} catch (Exception e) {
//				System.out.println("❌ Error while testing Redis connection: " + e.getMessage());
//				e.printStackTrace();
//			}
//		};
//	}



}

//PlatformTransactionManager ----> it an interface for the transaction manager--->manage like rollback,commit..etc
//MongoTransactionManager -----> PlatformTransactionManager will be implemented by this

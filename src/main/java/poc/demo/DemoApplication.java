package poc.demo;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import poc.cqrs.command.CommandBus;
import poc.cqrs.event.EventStore;
import poc.cqrs.event.impl.JdbcEventStore;
import poc.cqrs.spring.CqrsEsConfig;
import poc.demo.product.command.CreateProductCommand;

@SpringBootApplication
@Import(CqrsEsConfig.class)
public class DemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
	}

//	@Bean
	public CommandLineRunner lucene(EntityManager em) {
		return (args) -> {
			System.err.println("Reint lucene");
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
			fullTextEntityManager.createIndexer().startAndWait();
		};
	}
	
	@Bean
	public EventStore productStore(DataSource datasource) {
		return new JdbcEventStore(Aggregates.PRODUCTS, datasource);
	}
	
	@Bean
	public CommandLineRunner run(CommandBus commandBus) {
		return (args) -> {
			commandBus.send(new CreateProductCommand("Soyo"));
			commandBus.send(new CreateProductCommand("arbre"));
			commandBus.send(new CreateProductCommand("chat"));
			commandBus.send(new CreateProductCommand("noisety"));
			commandBus.send(new CreateProductCommand("nutella"));
			commandBus.send(new CreateProductCommand("arnaud"));
		};
	}
}

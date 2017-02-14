package com.iqmsoft.dw.boot.rs;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

import com.iqmsoft.dw.boot.model.Client;
import com.iqmsoft.dw.boot.model.Person;
import com.iqmsoft.dw.boot.repos.ClientRepository;


import reactor.core.publisher.Flux;

@Path("/people")
@Component
@EnableMongoRepositories(basePackages = "com.iqmsoft.dw.boot.repos")
@ComponentScan(basePackages = "com.iqmsoft.dw.boot")
@EnableAutoConfiguration
public class PeopleRestService {
	
	private final Random random = new Random();
	
	@Autowired
    private ClientRepository csrepos;
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/clients")
	public List<Client> getClients() {
		return csrepos.findAll();
	}
	
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Collection<Person> getPeople() {
    	
    	Client c = csrepos.save(new Client("toto", "toto"));
    	
        return Flux
        	.just(
        		new Person("a@b.com", "John", "Smith"), 
        		new Person("c@b.com", "Bob", "Bobinec"),
        		new Person("d@c.com", "Robert", "Williams")
        	)
        	.delayMillis(random.nextInt(1000))
        	.toStream()
        	.collect(Collectors.toList());
    }
}

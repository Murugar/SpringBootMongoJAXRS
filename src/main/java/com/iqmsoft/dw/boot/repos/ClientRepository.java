package com.iqmsoft.dw.boot.repos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.iqmsoft.dw.boot.model.Client;


public interface ClientRepository extends MongoRepository<Client, String> {

    List<Client> findByLastName(String lastName);
}
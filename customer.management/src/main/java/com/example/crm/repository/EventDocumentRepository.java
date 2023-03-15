package com.example.crm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.crm.domain.EventDocument;

public interface EventDocumentRepository extends MongoRepository<EventDocument, String>{

}

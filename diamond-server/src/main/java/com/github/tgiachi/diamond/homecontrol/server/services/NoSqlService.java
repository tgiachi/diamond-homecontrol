package com.github.tgiachi.diamond.homecontrol.server.services;

import com.github.tgiachi.diamond.homecontrol.api.annotations.EventEntity;
import com.github.tgiachi.diamond.homecontrol.api.impl.services.AbstractDiamondService;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.entities.IBaseEntity;
import com.github.tgiachi.diamond.homecontrol.api.interfaces.services.INoSqlService;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Service
public class NoSqlService extends AbstractDiamondService implements INoSqlService {

    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    @Value("${mongodb.url}")
    private String mongoDbUrl;

    @Override
    public void onStart() {
        var connectionString = new ConnectionString(mongoDbUrl);

        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().register("com.github.tgiachi").build();
        CodecRegistry defaultCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        CodecRegistry pojoCodecRegistry = fromRegistries(defaultCodecRegistry, fromProviders(pojoCodecProvider));
        mongoClient = MongoClients.create(mongoDbUrl);
        mongoDatabase = mongoClient.getDatabase(connectionString.getDatabase());
        mongoDatabase = mongoDatabase.withCodecRegistry(pojoCodecRegistry);
    }

    @Override
    public <TEntity extends IBaseEntity> TEntity insert(TEntity entity, Class<TEntity> entityClass) {
        entity.setId(ObjectId.get());
        entity.setCreatedDateTime(LocalDateTime.now());
        entity.setUpdatedDateTime(LocalDateTime.now());
        var collection = mongoDatabase.getCollection(getCollectionName(entity.getClass()), entityClass);
        collection.insertOne(entity);
        return entity;
    }

    @Override
    public <TEntity extends IBaseEntity> TEntity update(TEntity entity, Class<TEntity> entityClass) {
        var collection = mongoDatabase.getCollection(getCollectionName(entity.getClass()), entityClass);
        return null;
    }

    @Override
    public <TEntity extends IBaseEntity> List<TEntity> findAll(Class<TEntity> entityClass) {
        return null;
    }

    private String getCollectionName(Class<?> entityClass) {
        var annotation = entityClass.getAnnotation(EventEntity.class);
        if (annotation == null) {
            return entityClass.getSimpleName();
        }
        return annotation.name();
    }
}

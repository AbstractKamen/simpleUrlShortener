package com.abstractkamen.simpleurlshortener.dao;

public interface Repository<Entity, Identity> {

    Entity findByIdentity(Identity id);

    long count();

    boolean isPresent(Identity id);

    Entity save(Entity saved);
}

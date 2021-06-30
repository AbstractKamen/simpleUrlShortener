package com.abstractkamen.simpleurlshortener.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ShortenedUrlRepositoryImpl extends RepositoryAbstract<ShortenedUrl, String> implements ShortenedUrlRepository {

    @Override
    protected Class<ShortenedUrl> getEntityClass() {
        return ShortenedUrl.class;
    }

    @Override
    protected Class<String> getIdentityClass() {
        return String.class;
    }

    @Override
    protected String getIdentity(ShortenedUrl shortenedUrl) {
        return shortenedUrl.getId();
    }

    @Autowired
    public ShortenedUrlRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}

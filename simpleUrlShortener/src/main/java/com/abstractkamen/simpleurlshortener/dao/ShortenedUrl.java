package com.abstractkamen.simpleurlshortener.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shortened_urls")
public class ShortenedUrl {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "url_whole")
    private String url;

    public static ShortenedUrlBuilder builder() {
        return new ShortenedUrlBuilder();
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    private void setId(String id) {
        this.id = id;
    }

    private void setUrl(String url) {
        this.url = url;
    }

    public static class ShortenedUrlBuilder {
        private ShortenedUrlBuilder() {
        }

        private String id;
        private String url;

        public ShortenedUrlBuilder id(String id) {
            this.id = id;
            return this;
        }

        public ShortenedUrlBuilder url(String url) {
            this.url = url;
            return this;
        }

        public ShortenedUrl build() {
            final ShortenedUrl shortenedUrl = new ShortenedUrl();
            shortenedUrl.setId(id);
            shortenedUrl.setUrl(url);
            return shortenedUrl;
        }
    }
}

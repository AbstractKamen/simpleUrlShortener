package com.abstractkamen.simpleurlshortener.web.rest;

import com.abstractkamen.simpleurlshortener.dao.Repository;
import com.abstractkamen.simpleurlshortener.dao.ShortenedUrl;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("api")
public class UrlShortenerControllerRest {
    private final Repository<ShortenedUrl, String> repository;

    @Autowired
    public UrlShortenerControllerRest(Repository<ShortenedUrl, String> repository) {
        this.repository = repository;
    }

    @PostMapping("/shortenUrl")
    public ResponseEntity<ShortenedUrl> shortenUrl(@RequestBody final String url) {
        final String sha = Base64.getEncoder().encodeToString(DigestUtils.sha1(url));
        final ShortenedUrl shortenedUrl = ShortenedUrl.builder()
                .id(sha.substring(0, 5))
                .url(url)
                .build();
        repository.save(shortenedUrl);
        return new ResponseEntity<>(shortenedUrl, HttpStatus.OK);
    }

    @RequestMapping("/{shortUrl}")
    public ResponseEntity<String> findUrl(@PathVariable String shortUrl) {
        final ShortenedUrl shortenedUrl = repository.findByIdentity(shortUrl);
        return new ResponseEntity<>(shortenedUrl != null ? shortenedUrl.getUrl() : "no such url", HttpStatus.OK);
    }

    @RequestMapping("/count")
    public ResponseEntity<Long> findUrl() {
        final Long count = repository.count();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}

package com.url.shortner.respository;

import com.url.shortner.model.URL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URLRepository extends JpaRepository<URL, Long> {
    URL findByShortenedURL(String shortenedURL);
    URL findByOriginalURL(String originalURL);
}

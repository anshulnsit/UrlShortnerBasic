package com.url.shortner.service;

import com.url.shortner.model.URL;

public interface URLService {
    void save(URL url);
    String getOriginalURL(String shortenedURL);
    URL getURL(String originalURL);
}

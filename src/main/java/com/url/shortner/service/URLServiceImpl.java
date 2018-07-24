package com.url.shortner.service;

import com.url.shortner.model.URL;
import com.url.shortner.respository.URLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class URLServiceImpl implements URLService {

    @Autowired
    private URLRepository urlRepository;

    @Override
    public void save(URL url) {
        urlRepository.save(url);
    }

    @Override
    public String getOriginalURL(String shortenedURL) {
        URL url = urlRepository.findByShortenedURL(shortenedURL);
        return url != null ? url.getOriginalURL() : null;
    }

    @Override
    public URL getURL(String originalURL) {
        return urlRepository.findByOriginalURL(originalURL);
    }

}

package com.url.shortner.model;

import javax.persistence.*;

@Entity
@Table(name = "url")
public class URL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originalURL;
    private String shortenedURL;

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getOriginalURL(){
        return this.originalURL;
    }

    public void setOriginalURL(String originalURL){
        this.originalURL = originalURL;
    }

    public String getShortenedURL(){
        return this.shortenedURL;
    }

    public void setShortenedURL(String shortenedURL){
        this.shortenedURL = shortenedURL;
    }

}

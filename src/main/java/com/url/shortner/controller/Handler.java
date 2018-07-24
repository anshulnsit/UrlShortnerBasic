package com.url.shortner.controller;

import java.util.*;
import com.url.shortner.model.URL;
import com.url.shortner.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class Handler {
    @Autowired
    private URLService urlService;

    private static List<Character> indexToCharTable;

    public Handler(){
        initializeIndexToCharTable();
    }

    @GetMapping("/{url}")
    public RedirectView redirect(@PathVariable("url") String shortenedURL){
        shortenedURL = "http://localhost:8080/" + shortenedURL;
        System.out.println(shortenedURL);
        String originalURL = urlService.getOriginalURL(shortenedURL);
        System.out.println(originalURL);
        RedirectView redirectView = new RedirectView();
        if(originalURL == null) redirectView.setUrl("error");
        else  redirectView.setUrl("http://" + originalURL);
        return redirectView;
    }

    @PostMapping("/shorten")
    public ResponseEntity save(@RequestParam("url") String originalURL){
        originalURL = stripHeaderFromURL(originalURL);
        originalURL = stripWWW(originalURL);
        URL url = new URL();
        if(urlService.getURL(originalURL) != null){
            url = urlService.getURL(originalURL);
            return ResponseEntity.ok(url.getShortenedURL());
        }
        url.setOriginalURL(originalURL);
        urlService.save(url);
        url = urlService.getURL(url.getOriginalURL());
        Long id = url.getId();
        long num = id;
        Stack<Long> stack = new Stack<>();
        while(num != 0){
            long rem = num % 62;
            long quotient = num / 62;
            stack.push(rem);
            num = quotient;
        }
        StringBuilder s = new StringBuilder();
        while(!stack.isEmpty()){
            s.append(this.indexToCharTable.get(Math.toIntExact(stack.pop())));
        }
        String shortenedURL = "http://localhost:8080/" + s.toString();
        url.setShortenedURL(shortenedURL);
        urlService.save(url);
        return ResponseEntity.ok(shortenedURL);
    }

    private String stripHeaderFromURL(String s){
        String[] str = s.split("://");
        if(str.length > 1){
           return str[1];
        }
        return s;
    }

    private String stripWWW(String url){
        String[] str = url.split("www\\d*.");
        if(str.length > 1){
            return str[1];
        }
        return url;
    }

    private void initializeIndexToCharTable() {
        // 0->a, 1->b, ..., 25->z, ..., 52->0, 61->9
        indexToCharTable = new ArrayList<>();
        for (int i = 0; i < 26; ++i) {
            char c = 'a';
            c += i;
            indexToCharTable.add(c);
        }
        for (int i = 26; i < 52; ++i) {
            char c = 'A';
            c += (i-26);
            indexToCharTable.add(c);
        }
        for (int i = 52; i < 62; ++i) {
            char c = '0';
            c += (i - 52);
            indexToCharTable.add(c);
        }
    }

}

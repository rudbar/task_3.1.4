package com.yankachan.spring.rest;

import com.yankachan.spring.rest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.POST;

@Component
public class Communication {

    private RestTemplate restTemplate;
    private final HttpHeaders headers;

    @Autowired
    public Communication(RestTemplate restTemplate, HttpHeaders headers) {
        this.restTemplate = restTemplate;
        this.headers = headers;
        this.headers.set("Cookie", String.join(";", restTemplate.headForHeaders(URL).get("Set-Cookie")));
    }

    private final String URL = "http://91.241.64.178:7081/api/users";


    public String getAllUsers() {
        System.out.println("this.headers " + this.headers);

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<>(this.headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
        List<String> cookie = responseEntity.getHeaders().get("Cookie");
        System.out.println("Get all users " + cookie);

        return responseEntity.getBody();
    }

    public User getUser(Long id) {

        User user = restTemplate.getForObject(URL + "/" + id, User.class);

        return user;
    }

    public void saveUser(@RequestBody User user) {
        System.out.println(headers);

        this.headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        this.headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> requestBody = new HttpEntity<>(user, this.headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                requestBody,
                String.class
        );

        System.out.println(responseEntity.getBody());


    }

    public void editUser(@RequestBody User user) {
        System.out.println(headers);

        HttpEntity<User> entity = new HttpEntity<>(user, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                URL,
                HttpMethod.PUT,
                entity,
                String.class
        );

        System.out.println(responseEntity.getBody());

    }

    public void deleteUser(Long id) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                    URL + "/" + id,
                    HttpMethod.DELETE,
                    new HttpEntity<String>(headers),
                    String.class
            );

        List<String> cookie = responseEntity.getHeaders().get("Cookie");
        System.out.println("Delete user " + cookie);
        System.out.println(responseEntity.getBody());

    }
}

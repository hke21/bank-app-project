package com.hakob.uservalidatorservice.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class HttpClient {
    private Logger logger = LoggerFactory.getLogger(HttpClient.class);

    @Value(value = "${service.master-data-service.url}")
    private String masterDataServiceUrl;
    RestTemplate restTemplate = new RestTemplate();

    public Boolean postToMasterDataService(String phoneNumber, String email) {
        WebClient client = WebClient.builder()
                .baseUrl(masterDataServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();


//        Mono<User> createdUser = client.post()
//                .uri("/users")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(Mono.just(user), User.class)
//                .retrieve()
//                .bodyToMono(User.class);

        Map<String, String> map = new HashMap<>();
        map.put("phoneNumber", phoneNumber);
//        map.put("email", email);

        Mono<Boolean> createdUser = client.post()
                .uri("/usersExist")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(map), Boolean.class)
                .retrieve()
                .bodyToMono(Boolean.class);

        Boolean responseUserExists = createdUser.block();

//
//        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = client.method(HttpMethod.POST);
//
//        WebClient.RequestBodySpec bodySpec = uriSpec.uri("/user");
//
//        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue("data");
//
//        WebClient.ResponseSpec responseSpec = headersSpec.header(
//                        HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .accept(MediaType.APPLICATION_JSON)
//                .acceptCharset(StandardCharsets.UTF_8)
//                .ifNoneMatch("*")
//                .ifModifiedSince(ZonedDateTime.now())
//                .retrieve();
//
//        Mono<String> responseResult = headersSpec.exchangeToMono(response -> {
//            if (response.statusCode().equals(HttpStatus.OK)) {
//                return response.bodyToMono(String.class);
//            } else if (response.statusCode().is4xxClientError()) {
//                return Mono.just("Error response eeeeeyooo bro");
//            } else {
//                return response.createException()
//                        .flatMap(Mono::error);
//            }
//        });

        assert responseUserExists != null;
        return responseUserExists;
    }

}

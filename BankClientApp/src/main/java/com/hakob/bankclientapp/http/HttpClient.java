package com.hakob.bankclientapp.http;

import com.hakob.bankclientapp.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class HttpClient {
    private Logger logger = LoggerFactory.getLogger(HttpClient.class);

    @Value(value = "${service.validation.url}")
    private String validationServiceUrl;

    @Value(value = "${service.customer-risk-raiting.url}")
    private String customerRiskRatingServiceUrl;

    @Value(value = "${service.master-data-service.url}")
    private String masterDataServiceUrl;


    RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<Boolean> postUserToValidationService(User user) {
        WebClient client = WebClient.builder()
                .baseUrl(validationServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();


//        Mono<User> createdUser = client.post()
//                .uri("/users")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(Mono.just(user), User.class)
//                .retrieve()
//                .bodyToMono(User.class);

//        Mono<Boolean> createdUser = client.post()
//                .uri("/users")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(Mono.just(user), Boolean.class)
//                .retrieve()
//                .bodyToMono(Boolean.class);
        logger.info("Making a request to user-validator-service...");
        ResponseEntity<Boolean> responseUser = null;
        try {
            responseUser = client.post()
                    .uri("/validate")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(user))
                    .retrieve()
                    .toEntity(Boolean.class)
                    .block();
        } catch (WebClientResponseException exception) {
            return ResponseEntity.status(exception.getStatusCode()).build();
        }

        HttpHeaders headers = responseUser.getHeaders();

//        Boolean responseUser = createdUser.block();

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

        assert responseUser != null;
        return responseUser;
    }
    public ResponseEntity<Boolean> postUserToValidationServiceWithPossibleError(User user) {
        WebClient client = WebClient.builder()
                .baseUrl(validationServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();


//        Mono<User> createdUser = client.post()
//                .uri("/users")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(Mono.just(user), User.class)
//                .retrieve()
//                .bodyToMono(User.class);

//        Mono<Boolean> createdUser = client.post()
//                .uri("/users")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(Mono.just(user), Boolean.class)
//                .retrieve()
//                .bodyToMono(Boolean.class);
        logger.info("Making a request to user-validator-service with possible 502 BAD_GATEWAY");
        ResponseEntity<Boolean> responseUser = null;
        try {
            responseUser = client.post()
                    .uri("/validate-possible-bad-gateway")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(user))
                    .retrieve()
                    .toEntity(Boolean.class)
                    .block();
        } catch (WebClientResponseException exception) {
            logger.info("user-validator-service responded with 502 BAD_GATEWAY");
            return ResponseEntity.status(exception.getStatusCode()).build();
        }

        logger.info("user-validator-service responded with 200 OK");

        HttpHeaders headers = responseUser.getHeaders();

//        Boolean responseUser = createdUser.block();

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

        assert responseUser != null;
        return responseUser;
    }


    public ResponseEntity<Boolean> postUserToValidationServiceWithError(User user) {
        WebClient client = WebClient.builder()
                .baseUrl(validationServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();


//        Mono<User> createdUser = client.post()
//                .uri("/users")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(Mono.just(user), User.class)
//                .retrieve()
//                .bodyToMono(User.class);

//        Mono<Boolean> createdUser = client.post()
//                .uri("/users")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(Mono.just(user), Boolean.class)
//                .retrieve()
//                .bodyToMono(Boolean.class);
        logger.info("Making a request to user-validator-service 502 BAD_GATEWAY endpoint...");
        ResponseEntity<Boolean> responseUser = null;
        try {
            responseUser = client.post()
                    .uri("/validate-bad-gateway")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(user))
                    .retrieve()
                    .toEntity(Boolean.class)
                    .block();
        } catch (WebClientResponseException exception) {
            logger.info("user-validator-service responded with " + exception.getStatusCode());
            return ResponseEntity.status(exception.getStatusCode()).build();
        }

        HttpHeaders headers = responseUser.getHeaders();

//        Boolean responseUser = createdUser.block();

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

        assert responseUser != null;
        return responseUser;
    }

    public ResponseEntity<User> postUserToRiskRaitingServiceAndStore(User user, Map<String, String> headers) {
        WebClient client = WebClient.builder()
                .baseUrl(customerRiskRatingServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();


//        Mono<User> createdUser = client.post()
//                .uri("/users")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(Mono.just(user), User.class)
//                .retrieve()
//                .bodyToMono(User.class);

        logger.info("Sending user to rating service...");

        Mono<User> ratedUser = client.post()
                .uri("/rate")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("x-b3-traceid", headers.get("x-b3-traceid"))
                .header("x-b3-spanid", headers.get("x-b3-spanid"))
                .header("x-b3-parentspanid", headers.get("x-b3-parentspanid"))
                .header("x-b3-sampled", headers.get("x-b3-sampled"))
                .header("x-b3-flags", headers.get("x-b3-flags"))
                .header("x-request-id", headers.get("x-request-id"))
                .body(Mono.just(user), User.class)
                .retrieve()
                .bodyToMono(User.class);

        System.out.println();
        User responseRatedUser = ratedUser.block();

        ResponseEntity<User> responseEntityRatedUser = ResponseEntity.ok(responseRatedUser);

        assert responseRatedUser != null;
        logger.info("User rated and now will send to CMD...");
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

        client = WebClient.builder()
                .baseUrl(masterDataServiceUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();


        ResponseEntity<User> responseUser = null;
        try {
            Mono<User> createdUser = client.post()
                    .uri("/users")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header("x-b3-traceid", headers.get("x-b3-traceid"))
                    .header("x-b3-spanid", headers.get("x-b3-spanid"))
                    .header("x-b3-parentspanid", headers.get("x-b3-parentspanid"))
                    .header("x-b3-sampled", headers.get("x-b3-sampled"))
                    .header("x-b3-flags", headers.get("x-b3-flags"))
                    .header("x-request-id", headers.get("x-request-id"))
                    .body(Mono.just(user), User.class)
                    .retrieve()
                    .bodyToMono(User.class);

            User user1 = createdUser.block();

            responseUser = ResponseEntity.ok(user1);
        } catch (WebClientResponseException exception) {
            return ResponseEntity.status(exception.getStatusCode()).build();
        }

        logger.info("Stored User: ");

        return responseEntityRatedUser;
    }
}

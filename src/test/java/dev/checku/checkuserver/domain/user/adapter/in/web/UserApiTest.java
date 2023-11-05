package dev.checku.checkuserver.domain.user.adapter.in.web;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApiTest {

    @LocalServerPort
    public int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void pushToken을_가지고_로그인을_한다() {
        given().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                body(new LoginRequest("test-push-token")).
        when().
                post("/api/users").
        then().
                log().all().
                statusCode(HttpStatus.OK.value());
    }
}
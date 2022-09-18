package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.courier.Courier;
import model.courier.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient{

    private static final String COURIER_PATH = "api/v1/courier/";


    @Step("Авторизация курьера в системе {credentials}")
    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .log().all()
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .log().all();
    }

    @Step("Создание курьера с параметрами {courier}")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .log().all()
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .log().all();
    }

    @Step("Удаление курьера с id {courierId}")
    public ValidatableResponse delete(int courierId) {
        return given()
                .spec(getBaseSpec())
                .log().all()
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .log().all();
    }

}


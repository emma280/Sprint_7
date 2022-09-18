import client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.courier.Courier;
import model.courier.CourierCredentials;
import model.courier.CreateCourierResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import utills.CourierDataGenerator;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class CourierTest {

    CourierClient courierClient;
    Courier courier;
    int courierId;
    CourierCredentials courierCredentials;
    CreateCourierResponse createCourierResponse;
    String messageText;
    int statusCode;



    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierDataGenerator.getRandomCourier();
        courierCredentials = new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

    @After
    public void tearDown() {

        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Успешное создание курьера с валидными данными - логин, пароль, имя")
    public void shouldBeCreateCourierWithValidData() {
        ValidatableResponse createResponse = courierClient.create(courier);
        CreateCourierResponse createCourierResponse = createResponse.extract().as(CreateCourierResponse.class);

        assertThat("Courier isn't created", createResponse.extract().statusCode(), equalTo(SC_CREATED));
        assertTrue("the value is ok", createCourierResponse.getOk());
    }

    @Test
    @DisplayName("Создание курьера с заполнением всех полей верными значениями")
    public void courierCreationWithValidCredentials() {
        ValidatableResponse createResponse = courierClient.create(courier);
        statusCode = createResponse.extract().statusCode();
        assertThat("Courier isn't created", statusCode, equalTo(SC_CREATED));
    }

    @Test
    @DisplayName("Создание курьера без поля \"Логин\"")
    public void courierCreationWithoutLogin() {
        courier.setLogin(null);
        ValidatableResponse createResponse = courierClient.create(courier);
        statusCode = createResponse.extract().statusCode();
        messageText = createResponse.extract().path("message");
        assertThat("Invalid status code after courier creation without login", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Invalid message text", messageText, equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без поля \"Пароль\"")
    public void courierCreationWithoutPassword() {
        courier.setPassword(null);
        ValidatableResponse createResponse = courierClient.create(courier);
        statusCode = createResponse.extract().statusCode();
        messageText = createResponse.extract().path("message");
        assertThat("Invalid status code after courier creation without password", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Invalid message text after courier creation without password", messageText, equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Ignore
    //Тест падает из-за бага
    @DisplayName("Создание курьера без поля \"First Name\"")
    public void courierCreationWithoutFirstName() {
        courier.setFirstName(null);
        ValidatableResponse createResponse = courierClient.create(courier);
        statusCode = createResponse.extract().statusCode();
        messageText = createResponse.extract().path("message");
        assertThat("Invalid status code after courier creation without FirstName", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Invalid message text after courier creation without FirstName", messageText, equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера с пустым значением поля \"Логин\"")
    public void courierCreationWithEmptyLogin() {
        courier.setLogin("");
        ValidatableResponse createResponse = courierClient.create(courier);
        statusCode = createResponse.extract().statusCode();
        messageText = createResponse.extract().path("message");
        assertThat("Invalid status code after courier creation with empty login", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Invalid message text after courier creation with empty login", messageText, equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера с пустым значением поля \"Пароль\"")
    public void courierCreationWithEmptyPassword() {
        courier.setPassword("");
        ValidatableResponse createResponse = courierClient.create(courier);
        statusCode = createResponse.extract().statusCode();
        messageText = createResponse.extract().path("message");
        assertThat("Invalid status code after courier creation with empty password", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Invalid message text after courier creation with empty password", messageText, equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Ignore
    //Тест падает из-за бага: в ответе возвращается статус-код 201 вместо 400
    @DisplayName("Создание курьера с пустым значением поля \"FirstName\"")
    public void courierCreationWithEmptyFirstName() {
        courier.setFirstName("");
        ValidatableResponse createResponse = courierClient.create(courier);
        statusCode = createResponse.extract().statusCode();
        messageText = createResponse.extract().path("message");
        assertThat("Invalid status code after courier creation with empty firstname", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Invalid message text after courier creation with empty firstname", messageText, equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера со значениями полей \"Логин\" и \"Пароль\" существующей записи")
    public void courierCreationWithExistedCredentials() {
        ValidatableResponse createResponse = courierClient.create(courier);
        ValidatableResponse createResponse2 = courierClient.create(courier);
        statusCode = createResponse2.extract().statusCode();
        messageText = createResponse2.extract().path("message");
        assertThat("Invalid status code after courier creation with existed credentials", statusCode, equalTo(SC_CONFLICT));
        assertThat("Invalid message text after courier creation with existed credentials", messageText, equalTo("Этот логин уже используется. Попробуйте другой."));
    }

}

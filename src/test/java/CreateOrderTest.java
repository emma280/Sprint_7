import client.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.orders.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    OrderClient orderClient;
    Order order;
    Integer track;
    private final List<String> scooterColor;

    public CreateOrderTest(List<String> scooterColor) {
        this.scooterColor = scooterColor;
    }

    @Parameterized.Parameters
    public static List<List<String>> getScooterColor() {

        return List.of(
                List.of("BLACK"),
                List.of("GREY"),
                List.of("BLACK", "GREY"),
                List.of());
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = new Order(scooterColor);
    }

    @After
    public void tearDown() {
        orderClient.cancelOrder(track.toString());
    }

    @Test
    @DisplayName("Создание заказа")
    public void orderCreation() {
        ValidatableResponse createResponse = orderClient.createOrder(order);
        int statusCode = createResponse.extract().statusCode();
        track = createResponse.extract().path("track");
        assertThat("Invalid status code after order creation", statusCode, equalTo(SC_CREATED));
        assertThat("Track number is absent after order creation", track, is(notNullValue()));
    }
}

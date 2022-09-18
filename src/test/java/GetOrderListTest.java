import client.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GetOrderListTest {
    OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Получение списка заказов")
    public void getOrderList() {
        ValidatableResponse orderListResponse = orderClient.getOrderList();
        int statusCode = orderListResponse.extract().statusCode();
        ArrayList<String> ordersList = orderListResponse.extract().path("orders");
        assertThat("Invalid status code", statusCode, equalTo(SC_OK));
        assertThat("Orders are absent in the list", ordersList, is(notNullValue()));
    }
}

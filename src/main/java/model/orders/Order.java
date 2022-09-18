package model.orders;

import java.util.List;

public class Order {
    private final String firstName = "Эмма";

    private final String lastName = "Миронова";

    private final String address = "Московская, 1";

    private final int metroStation = 4;

    private final String phone = "+71111111111";

    private final int rentTime = 4;

    private final String deliveryDate = "2022-04-09";

    private final String comment = "Как можно быстрее, пожалуйста, на 1-2-3";

    private List<String> scooterColor;

    public Order() {
    }

    public Order(List<String> scooterColor) {
        this.scooterColor = scooterColor;
    }

    public List<String> getScooterColor() {
        return scooterColor;
    }

    public void setScooterColor(List<String> scooterColor) {
        this.scooterColor = scooterColor;
    }}

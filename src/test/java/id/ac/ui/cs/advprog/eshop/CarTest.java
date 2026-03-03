package id.ac.ui.cs.advprog.eshop;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class CarTest {
    private Car car;

    @BeforeEach
    void setUp() {
        this.car = new Car();
        this.car.setCarName("Toyota Supra");
        this.car.setCarColor("Red");
        this.car.setCarQuantity(1);
    }

    @Test
    void testGetCarId() {
        assertNotNull(this.car.getId());
    }

    @Test
    void testGetCarName() {
        assertEquals("Toyota Supra", this.car.getCarName());
    }

    @Test
    void testGetCarColor() {
        assertEquals("Red", this.car.getCarColor());
    }

    @Test
    void testGetCarQuantity() {
        assertEquals(1, this.car.getCarQuantity());
    }

    @Test
    void testSetCarId() {
        UUID newId = UUID.randomUUID();
        this.car.setId(newId);
        assertEquals(newId, this.car.getId());
    }

    @Test
    void testSetCarName() {
        String newName = "Honda Civic";
        this.car.setCarName(newName);
        assertEquals(newName, this.car.getCarName());
    }

    @Test
    void testSetCarColor() {
        String newColor = "Black";
        this.car.setCarColor(newColor);
        assertEquals(newColor, this.car.getCarColor());
    }

    @Test
    void testSetCarQuantity() {
        int newQuantity = 10;
        this.car.setCarQuantity(newQuantity);
        assertEquals(newQuantity, this.car.getCarQuantity());
    }
}
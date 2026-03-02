package id.ac.ui.cs.advprog.eshop;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CarTest {
    private Car car;

    @BeforeEach
    void setUp() {
        this.car = new Car();
        this.car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.car.setCarName("Toyota Supra");
        this.car.setCarColor("Red");
        this.car.setCarQuantity(1);
    }

    @Test
    void testGetCarId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.car.getCarId());
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
        String newId = "new-uuid-123";
        this.car.setCarId(newId);
        assertEquals(newId, this.car.getCarId());
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
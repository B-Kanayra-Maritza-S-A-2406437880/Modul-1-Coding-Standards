package id.ac.ui.cs.advprog.eshop;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateAndFindAll() {
        Car car = new Car();
        car.setCarName("Toyota Supra");
        car.setCarColor("Red");
        car.setCarQuantity(1);

        carRepository.create(car);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals(car.getCarId(), savedCar.getCarId());
        assertEquals("Toyota Supra", savedCar.getCarName());
    }

    @Test
    void testCreateWithExistingId() {
        Car car = new Car();
        car.setCarId("manual-id-123");
        car.setCarName("Honda Civic");

        carRepository.create(car);

        assertEquals("manual-id-123", car.getCarId());
    }

    @Test
    void testCreateWithNullIdGeneratesUuid() {
        Car car = new Car();
        car.setCarId(null);

        carRepository.create(car);

        assertNotNull(car.getCarId());
        assertFalse(car.getCarId().isEmpty());
    }

    @Test
    void testFindByIdSuccess() {
        Car car = new Car();
        car.setCarName("Nissan GTR");
        carRepository.create(car);

        Car foundCar = carRepository.findById(car.getCarId());
        assertNotNull(foundCar);
        assertEquals(car.getCarId(), foundCar.getCarId());
    }

    @Test
    void testFindByIdNotFound() {
        Car foundCar = carRepository.findById("non-existent-id");
        assertNull(foundCar);
    }

    @Test
    void testUpdateSuccess() {
        Car car = new Car();
        car.setCarName("Old Name");
        carRepository.create(car);

        Car updatedCarData = new Car();
        updatedCarData.setCarName("New Name");
        updatedCarData.setCarColor("Blue");
        updatedCarData.setCarQuantity(5);

        Car result = carRepository.update(car.getCarId(), updatedCarData);

        assertNotNull(result);
        assertEquals("New Name", car.getCarName());
        assertEquals("Blue", car.getCarColor());
        assertEquals(5, car.getCarQuantity());
    }

    @Test
    void testUpdateNotFound() {
        Car updatedCarData = new Car();
        Car result = carRepository.update("invalid-id", updatedCarData);
        assertNull(result);
    }

    @Test
    void testDelete() {
        Car car = new Car();
        carRepository.create(car);
        String id = car.getCarId();

        carRepository.delete(id);

        Car foundCar = carRepository.findById(id);
        assertNull(foundCar);
    }
}
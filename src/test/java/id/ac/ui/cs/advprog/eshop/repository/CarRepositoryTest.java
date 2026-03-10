package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.UUID;

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
        assertEquals(car.getId(), savedCar.getId());
        assertEquals("Toyota Supra", savedCar.getCarName());
    }

    @Test
    void testCreateWithExistingId() {
        Car car = new Car();
        UUID existingId = UUID.randomUUID();
        car.setId(existingId);
        car.setCarName("Honda Civic");

        carRepository.create(car);

        assertEquals(existingId, car.getId());
    }

    @Test
    void testCreateAutoGeneratesUUID() {
        Car car = new Car();
        carRepository.create(car);
        assertNotNull(car.getId());
    }

    @Test
    void testFindByIdSuccess() {
        Car car = new Car();
        car.setCarName("Nissan GTR");
        carRepository.create(car);

        Car foundCar = carRepository.findById(car.getId());
        assertNotNull(foundCar);
        assertEquals(car.getId(), foundCar.getId());
    }

    @Test
    void testFindByIdNotFound() {
        Car foundCar = carRepository.findById(UUID.randomUUID());
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

        Car result = carRepository.update(car.getId(), updatedCarData);

        assertNotNull(result);
        assertEquals("New Name", car.getCarName());
        assertEquals("Blue", car.getCarColor());
        assertEquals(5, car.getCarQuantity());
    }

    @Test
    void testUpdateNotFound() {
        Car updatedCarData = new Car();
        Car result = carRepository.update(UUID.randomUUID(), updatedCarData);
        assertNull(result);
    }

    @Test
    void testDelete() {
        Car car = new Car();
        carRepository.create(car);
        carRepository.delete(car.getId());

        Car foundCar = carRepository.findById(car.getId());
        assertNull(foundCar);
    }
}
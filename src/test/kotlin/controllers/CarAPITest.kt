package controller

import ie.setu.Car
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import persistence.XMLSerializer  // or JSONSerializer
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CarAPITest {

    private var toyotaCorolla: Car? = null
    private var fordFocus: Car? = null
    private var hondaCivic: Car? = null
    private var teslaModel3: Car? = null
    private var bmwM3: Car? = null
    private var populatedCars: CarAPI? = null
    private var emptyCars: CarAPI? = null
    private val testFile = File("test_cars.xml") // Or use a mock file

    @BeforeEach
    fun setup() {
        // Create test car objects
        toyotaCorolla = Car("Toyota", "Corolla", 2010, 8000.00)
        fordFocus = Car("Ford", "Focus", 2015, 12000.00)
        hondaCivic = Car("Honda", "Civic", 2020, 22000.00)
        teslaModel3 = Car("Tesla", "Model 3", 2022, 35000.00)
        bmwM3 = Car("BMW", "M3", 2018, 55000.00)

        // Initialize serializer (could be XML or JSON serializer)
        val xmlSerializer = XMLSerializer(testFile)

        // Initialize populated and empty CarAPI instances with the serializer
        populatedCars = CarAPI(xmlSerializer)
        emptyCars = CarAPI(xmlSerializer)

        // Add cars to populated list
        populatedCars!!.add(toyotaCorolla!!)
        populatedCars!!.add(fordFocus!!)
        populatedCars!!.add(hondaCivic!!)
        populatedCars!!.add(teslaModel3!!)
        populatedCars!!.add(bmwM3!!)
    }

    @AfterEach
    fun tearDown() {
        toyotaCorolla = null
        fordFocus = null
        hondaCivic = null
        teslaModel3 = null
        bmwM3 = null
        populatedCars = null
        emptyCars = null
        testFile.delete() // Clean up after tests if necessary
    }

    @Test
    fun `adding a Car to an empty list adds to ArrayList`() {
        val newCar = Car("Mazda", "MX-5", 2021, 30000.00)

        // Add the car and check that the operation succeeded
        assertTrue(emptyCars!!.add(newCar))

        // Verify that the number of cars in the list is exactly 1
        assertEquals(1, emptyCars!!.count()) // Use a helper method to directly check the size

        // Confirm that the added car appears in the formatted list output
        val carsList = emptyCars!!.listAllCars()
        assertTrue(carsList.contains("Mazda MX-5")) // Check for specific car details in the output
    }

    @Test
    fun `listing all Cars in populated list returns correct format`() {
        val list = populatedCars!!.listAllCars()
        println(list) // Debugging output
        assertTrue(list.contains("Toyota Corolla"))
        assertTrue(list.contains("Ford Focus"))
        assertEquals(5, list.split("\n").size) // Ensure 5 cars are listed
    }

    @Test
    fun `listing all Cars in empty list returns 'No cars stored'`() {
        assertEquals("No cars stored", emptyCars!!.listAllCars())
    }

    @Test
    fun `updating a Car in the populated list updates it successfully`() {
        val updatedCar = Car("Toyota", "Supra", 2023, 60000.00)
        assertTrue(populatedCars!!.update(1, updatedCar)) // Update car at index 1 (1-based)

        // Verify update
        val list = populatedCars!!.listAllCars()
        println(list) // Debugging output
        assertTrue(list.contains("Toyota Supra"))
        assertFalse(list.contains("Toyota Corolla")) // Old car should be replaced
    }

    @Test
    fun `updating a Car with an invalid index fails`() {
        val updatedCar = Car("Toyota", "Supra", 2023, 60000.00)
        assertFalse(populatedCars!!.update(10, updatedCar)) // Invalid index
    }

    @Test
    fun `deleting a Car from the populated list removes it successfully`() {
        val initialListSize = populatedCars!!.listAllCars().split("\n").size
        assertTrue(populatedCars!!.delete(1)) // Delete the first car (1-based index)

        val updatedList = populatedCars!!.listAllCars()
        val updatedListSize = updatedList.split("\n").size

        println(updatedList) // Debugging output to verify results
        assertEquals(initialListSize - 1, updatedListSize) // Ensure size decreases
        assertFalse(updatedList.contains("Toyota Corolla")) // Ensure the deleted car is no longer in the list
    }

    @Test
    fun `deleting a Car with an invalid index fails`() {
        assertFalse(populatedCars!!.delete(10)) // Invalid index
    }
}

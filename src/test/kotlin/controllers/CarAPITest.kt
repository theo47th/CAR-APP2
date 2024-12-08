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
        // We're setting the vibe with some fresh car data before each test
        toyotaCorolla = Car("Toyota", "Corolla", 2010, 8000.00)
        fordFocus = Car("Ford", "Focus", 2015, 12000.00)
        hondaCivic = Car("Honda", "Civic", 2020, 22000.00)
        teslaModel3 = Car("Tesla", "Model 3", 2022, 35000.00)
        bmwM3 = Car("BMW", "M3", 2018, 55000.00)

        // We’re creating a virtual "car garage" with serializers
        val xmlSerializer = XMLSerializer(testFile)

        // One list with cars, one empty.
        populatedCars = CarAPI(xmlSerializer)
        emptyCars = CarAPI(xmlSerializer)

        // Filling up the "populatedCars" list with our whips
        populatedCars!!.add(toyotaCorolla!!)
        populatedCars!!.add(fordFocus!!)
        populatedCars!!.add(hondaCivic!!)
        populatedCars!!.add(teslaModel3!!)
        populatedCars!!.add(bmwM3!!)
    }

    @AfterEach
    fun tearDown() {
        // Clearing out the garage after each test
        toyotaCorolla = null
        fordFocus = null
        hondaCivic = null
        teslaModel3 = null
        bmwM3 = null
        populatedCars = null
        emptyCars = null
        testFile.delete() // looks fresh
    }

    @Test
    fun `adding a Car to an empty list adds to ArrayList`() {
        // Testing if we can add a new ride into an empty garage array i dont know but something
        // like that/
        val newCar = Car("Mazda", "MX-5", 2021, 30000.00)

        // Add it and check if it's in
        assertTrue(emptyCars!!.add(newCar))

        // Should now have 1 car in the list hopefully
        assertEquals(1, emptyCars!!.count())

        // Making sure the garage or array or data talks notices the  Mazda
        val carsList = emptyCars!!.listAllCars()
        assertTrue(carsList.contains("Mazda MX-5")) //it's there
    }

    @Test
    fun `listing all Cars in populated list returns correct format`() {
        // Let’s make sure all the cars are listed properly
        val list = populatedCars!!.listAllCars()
        println(list) // Debugging: Show off the garage lineup
        assertTrue(list.contains("Toyota Corolla")) // Check if it's got the classics
        assertTrue(list.contains("Ford Focus"))
        assertEquals(5, list.split("\n").size) // 5 cars in the garage
    }

    @Test
    fun `listing all Cars in empty list returns 'No cars stored'`() {
        // If there are no cars, the app better not lie to us
        assertEquals("No cars stored", emptyCars!!.listAllCars())
    }

    @Test
    fun `updating a Car in the populated list updates it successfully`() {
        // Verify that an empty CarAPI instance correctly indicates no cars are stored
        val updatedCar = Car("Toyota", "Supra", 2023, 60000.00)
        assertTrue(populatedCars!!.update(1, updatedCar)) // Update the first car (1-based index)

        // Double-check the Supra's there and the Corolla's out
        val list = populatedCars!!.listAllCars()
        println(list) // Debugging: Flex the updated garage
        assertTrue(list.contains("Toyota Supra"))
        assertFalse(list.contains("Toyota Corolla")) // Goodbye Corolla
    }

    @Test
    fun `updating a Car with an invalid index fails`() {
        // Test updating an existing car in the list
        val updatedCar = Car("Toyota", "Supra", 2023, 60000.00)
        assertFalse(populatedCars!!.update(10, updatedCar)) // 10? Bruh, there’s no car 10
    }

    @Test
    fun `deleting a Car from the populated list removes it successfully`() {
        // Test deleting a car from the populated list
        val initialListSize = populatedCars!!.listAllCars().split("\n").size
        assertTrue(populatedCars!!.delete(1)) // Delete the first car (1-based index)

        // Verify that the list size decreases by one and the car is removed
        val updatedList = populatedCars!!.listAllCars()
        val updatedListSize = updatedList.split("\n").size

        println(updatedList) // Debugging: Show the updated list
        assertEquals(initialListSize - 1, updatedListSize) // List size should shrink
        assertFalse(updatedList.contains("Toyota Corolla")) // Confirm it's gone
    }

    @Test
    fun `deleting a Car with an invalid index fails`() {
        // Test that attempting to delete a non-existent car index fails
        assertFalse(populatedCars!!.delete(10)) // 10 isn’t even a thing
    }
}

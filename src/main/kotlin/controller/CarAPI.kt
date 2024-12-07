package controller


import models.Car
import persistence.Serializer

class CarAPI(private val serializer: Serializer) {
    private var cars = ArrayList<Car>() // List to store cars


    fun add(car: Car): Boolean {
        return cars.add(car)
    }

    fun count(): Int {
        return cars.size
    }


    fun listAllCars(): String {
        return if (cars.isEmpty()) {
            "No cars stored"
        } else {
            cars.mapIndexed { index, car -> "${index + 1}: ${car.make} ${car.model} (${car.year}) - $${car.price}" }
                .joinToString("\n")
        }
    }


    fun update(index: Int, updatedCar: Car): Boolean {
        val zeroBasedIndex = index - 1 // Convert to 0-based index
        return if (zeroBasedIndex in cars.indices) {
            cars[zeroBasedIndex] = updatedCar
            true
        } else {
            false
        }
    }

    @Throws(Exception::class)
    fun load() {
        cars = serializer.read() as ArrayList<Car>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(cars)
    }



    fun delete(index: Int): Boolean {
        val zeroBasedIndex = index - 1 // Convert to 0-based index
        return if (zeroBasedIndex in cars.indices) {
            cars.removeAt(zeroBasedIndex)
            true
        } else {
            false
        }

    }


}

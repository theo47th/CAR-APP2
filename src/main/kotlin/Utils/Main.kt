package ie.setu
import controller.CarAPI
import utils.readIntNotNull
import java.io.File
import persistence.JSONSerializer


private val carAPI = CarAPI(JSONSerializer(File("cars.json")))

/*There will be the main menu for the app and also the
* user interface that a fan of cars like myself would likely love
* to have I am going to create data of cars with valid information,
* and we are going to call out the data whilst using the interface
* and also add new cars that the user really loves this would help grow .
* the community with knowledge about new models hopefully with the rewards
* system il have a way to store it for the user*/

fun main(args: Array<String>) {
    runMenu()


}

fun mainMenu(): Int {
    print(
        """ 
         > ----------------------------------
         > |        CAR LISTING APP         |
         > ----------------------------------
         > | MENU                           |
         > |   1) Add a car                 |
         > |   2) List all cars             |
         > |   3) Update a car              |
         > |   4) Delete a car              |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">")
    )
    return readIntNotNull("Select an option: ")
}
fun runMenu() {
    try {
        carAPI.load() // Load existing cars from the file
        println("Cars loaded successfully.")
    } catch (e: Exception) {
        println("No previous data found. Starting with an empty list.")
    }

    do {
        val option = mainMenu()
        when (option) {
            1 -> addCar(carAPI)
            2 -> listCars(carAPI)
            3 -> updateCar(carAPI)
            4 -> deleteCar(carAPI)
            0 -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}



fun addCar(carAPI: CarAPI) {
    val carMake = readNextLine("Enter the car make (e.g., Toyota): ")
    val carModel = readNextLine("Enter the car model (e.g., Corolla): ")
    val carYear = readNextInt("Enter the year of the car: ")
    val carPrice = readNextDouble("Enter the price of the car: ")
    val isAdded = carAPI.add(Car(carMake, carModel, carYear, carPrice))

    if (isAdded) {
        println("Car added successfully.")
    } else {
        println("Failed to add car.")
    }
}

fun listCars(carAPI: CarAPI) {
    println(carAPI.listAllCars())
}

fun updateCar(carAPI: CarAPI) {
    val carId = readNextInt("Enter the ID of the car to update: ")
    val carMake = readNextLine("Enter the updated car make: ")
    val carModel = readNextLine("Enter the updated car model: ")
    val carYear = readNextInt("Enter the updated year of the car: ")
    val carPrice = readNextDouble("Enter the updated price of the car: ")

    val isUpdated = carAPI.update(carId, Car(carMake, carModel, carYear, carPrice))
    if (isUpdated) {
        println("Car updated successfully.")
    } else {
        println("Car update failed.")
    }
}

fun deleteCar(carAPI: CarAPI) {
    val carId = readNextInt("Enter the ID of the car to delete: ")
    val isDeleted = carAPI.delete(carId)

    if (isDeleted) {
        println("Car deleted successfully.")
    } else {
        println("Car deletion failed.")
    }
}

fun exitApp() {
    println("Exiting application. Goodbye!")
}

fun readNextInt(prompt: String?): Int {
    do {
        try {
            print(prompt)
            return readln().toInt()
        } catch (e: NumberFormatException) {
            println("\tEnter a valid number.")
        }
    } while (true)
}

fun readNextDouble(prompt: String?): Double {
    do {
        try {
            print(prompt)
            return readln().toDouble()
        } catch (e: NumberFormatException) {
            println("\tEnter a valid decimal number.")
        }
    } while (true)
}

fun readNextLine(prompt: String?): String {
    print(prompt)
    return readln()
}
fun save(carAPI: CarAPI) {
    try {
        carAPI.store() // Call store() on the instance of CarAPI
        println("Data saved successfully.")
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load(carAPI: CarAPI) {
    try {
        carAPI.load() // Call load() on the instance of CarAPI
        println("Data loaded successfully.")
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

data class Car(
    val make: String,
    val model: String,
    val year: Int,
    val price: Double
)
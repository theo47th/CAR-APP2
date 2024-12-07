package ie.setu.Utils
import utils.readIntNotNull

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
package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import ie.setu.Car
import java.io.File
import java.io.FileReader
import java.io.FileWriter

// This class handles saving and loading objects as XML files using XStream
class XMLSerializer(private val file: File) : Serializer {

    /*
     * This function loads (reads) the object from the XML file.
     * It uses XStream to turn the XML back into a usable object (like "unpacking" a file).
     * Throws an error if something goes wrong.
     */
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(DomDriver()) // Setup XStream for XML handling
        xStream.allowTypes(arrayOf(Car::class.java)) // Allows the Car class to be read
        val inputStream = xStream.createObjectInputStream(FileReader(file)) // Open the file to read
        val obj = inputStream.readObject() as Any // Read the object from the XML
        inputStream.close() // Close the file after reading
        return obj // Return the object
    }

    /*
     * This function saves the object as an XML file.
     * It uses XStream to convert the object into XML and writes it to the file.
     * Throws an error if something goes wrong.
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(DomDriver()) // Setup XStream for XML handling
        val outputStream = xStream.createObjectOutputStream(FileWriter(file)) // Open the file to write
        outputStream.writeObject(obj) // Write the object as XML
        outputStream.close() // Close the file after writing
    }
}

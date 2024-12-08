package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import ie.setu.Car
import java.io.File
import java.io.FileReader
import java.io.FileWriter

// This class saves and loads data as XML files using XStream.
class XMLSerializer(private val file: File) : Serializer {

    /*
     * This function reads the object from an XML file.
     * It "unpacks" the XML back into an object (like opening a package).
     * If something breaks, it throws an error.
     */
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(DomDriver()) // Set up XStream to handle XML
        xStream.allowTypes(arrayOf(Car::class.java)) // Allow reading Car objects
        val inputStream = xStream.createObjectInputStream(FileReader(file)) // Open the file to read
        val obj = inputStream.readObject() as Any // Read the object from the XML
        inputStream.close() // Close the file after reading
        return obj // Return the object we read
    }

    /*
     * This function saves an object to an XML file.
     * It "packs" the object into XML format and saves it (like wrapping a gift).
     * Throws an error if anything goes wrong.
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(DomDriver()) // Set up XStream to handle XML
        val outputStream = xStream.createObjectOutputStream(FileWriter(file)) // Open the file to write
        outputStream.writeObject(obj) // Write the object as XML
        outputStream.close() // Close the file after writing
    }

}

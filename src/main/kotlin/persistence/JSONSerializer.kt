package persistence

// This is an interface called `Serializer` that defines how to save and load data.
interface Serializer {
    /**
     * This function takes an object and saves it (like saving a file).
     * It can throw an error if something goes wrong.
     */
    @Throws(Exception::class)
    fun write(obj: Any?)

    /**
     * This function loads (or reads) the saved object and brings it back.
     * It can also throw an error if something goes wrong.
     */
    @Throws(Exception::class)
    fun read(): Any?
}

package persistence

// This is an interface that defines how to save and load data.
interface Serializer {

    /*
     * This function takes an object and saves it somewhere (like saving a file).
     * If anything goes wrong, it can throw an error.
     */
    @Throws(Exception::class)
    fun write(obj: Any?)

    /*
     * This function loads (or reads) a saved object and brings it back.
     * It can also throw an error if something goes wrong.
     */
    @Throws(Exception::class)
    fun read(): Any?
}

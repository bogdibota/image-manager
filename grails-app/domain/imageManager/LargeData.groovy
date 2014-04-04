package imageManager

class LargeData {

    byte[] data

    static constraints = {
        data maxSize: 1024*1024*500
    }
}

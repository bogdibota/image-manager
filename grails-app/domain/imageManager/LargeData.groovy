package imageManager

class LargeData {

    byte[] data

    static constraints = {
        data blank: false, maxSize: 1024*1024*500
    }
}

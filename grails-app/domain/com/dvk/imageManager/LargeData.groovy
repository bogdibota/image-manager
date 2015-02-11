package com.dvk.imageManager

class LargeData {

    byte[] data

    static constraints = {
        data maxSize: 1024*1024*500
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof LargeData)) return false

        LargeData largeData = (LargeData) o

        if (!Arrays.equals(data, largeData.data)) return false
        if (id != largeData.id) return false

        return true
    }

    int hashCode() {
        int result
        result = Arrays.hashCode(data)
        result = 31 * result + id.hashCode()
        return result
    }
}

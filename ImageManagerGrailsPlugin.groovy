import imageManager.utils.ImageFilesManagementUtils

class ImageManagerGrailsPlugin {
    def version = "1.0"
    def grailsVersion = "2.0 > *"
    def title = "Image Manager"
    def description = 'A simple, yet effective, plugin for managing and using images in DB'
    def documentation = "http://grails.org/plugin/image-manager"
    def license = "APACHE"
    def developers = [
        [name: 'Bogdan Bota', email: 'bogdibota@yahoo.com']
    ]
    def issueManagement = [system: 'GITHUB', url: 'https://github.com/bogdibota/image-manager/issues']
    def scm = [url: 'https://github.com/bogdibota/image-manager']

    def onShutdown = { event ->
        ImageFilesManagementUtils.deleteAllTemporaryFiles()
    }
}

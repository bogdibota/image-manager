import com.dvk.imageManager.utils.ImageFilesManagementUtils

class DvkImageManagerGrailsPlugin {
    def version = "1.0"
    def grailsVersion = "2.0 > *"
    def title = "DVK Image Manager"
    def description = 'A simple, yet effective, plugin for managing and using images in DB'
    def documentation = "https://github.com/bogdibota/image-manager/wiki"
    def license = "APACHE"
    def developers = [
            [name: 'Bogdan Bota', email: 'bogdibota@gmail.com']
    ]
    def issueManagement = [system: 'GITHUB', url: 'https://github.com/bogdibota/image-manager/issues']
    def scm = [url: 'https://github.com/bogdibota/image-manager']

    def onShutdown = { event ->
        ImageFilesManagementUtils.deleteAllTemporaryFiles()
    }
}

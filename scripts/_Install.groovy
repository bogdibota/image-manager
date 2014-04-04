import imageManager.ImageManagerPluginConstants

//create the tmp folder
ant.mkdir(dir: "${basedir}/${ImageManagerPluginConstants.TEMPORARY_FILES_LOCATION}")

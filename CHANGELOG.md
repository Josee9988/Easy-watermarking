<!-- markdownlint-disable MD024-->
# **Change Log**

## [0.5.4] - 2019-07-25

### Added

* Improved readme

## [0.5.3] - 2019-07-25

### Added

* Missing comments

### Changed

* Image controller will do all the work only when calling the constructor, now there is a method that will create and run the threads and will make all the process.

## [0.5.2] - 2019-07-24

### Added

* Fixed bug at pressing remove with nothing selected.
* Support from gif (without animation)
* Readme all the image file type supported

### Changed

* From public to private unnecesary methods

## [0.5.1] - 2019-07-24

### Added

* Tiff support
* BMP support

## [0.5.0] - 2019-07-24

### Added

* Threads at the creation of the image, it now will perform much better, about 75% faster (depends a lot on your cpu)

## [0.4.1] - 2019-07-24

### Changed

* The whole arraylist is now controlled by imagecontroller and importcontroller doesn't need anymore to make a new imagecontroller class for each image.

### Fixed

* Some bugs
* Modularity wasn't on point

## [0.4.0] - 2019-07-21

### Added

* Support for Windows.
* Images of support windows & linux added + compressed
* Folder of screenshots with all the images the readme will display.

## [0.3.2] - 2019-07-21

### Added

* Basic info to readme
* Missing documentation
* Icon of the project.

## [0.3.1] - 2019-07-21

### Added

* Documentation of the source java code

## [0.3.0] - 2019-07-21

### Added

* Improved responsiveness
* Menubar items.
* Menubar items link to internet pages (github)
* Added an alert dialog (informational) that displays the folder that have been created which contains the watermarked images.
* A remove button that lets you remove images from the left panel.
* License

### Fixed

* Some bugs

### Changed

* Name of apply watermark

## [0.2.1] - 2019-07-20

### Fixed

* Bug that made the watermark don't fill the original picture by it's width and height and was filling by its width x width.

## [0.2.0] - 2019-07-20

### Added

* It works but sometimes it adds some transparent space down the image. Don't know yet
* Some responsiveness
* An error label that will display errors.
* Changelog.md
* Image controller that resizes the water image every time to every picture without loosing quality
* And saves the files with the same name by default in a subdirectory where the image was selected (in new versions you will be able to select the folder)

### Fixed

* Importing files now has a filter that only let the user select images.

### Changed

* Button from 'Watermark all' to 'Select output folder and Watermark All'

## [0.1.0] - 2019-07-20

### Added

* Basic UI with three buttons.
* Two buttons with that import the images
* The images show in the tableviews

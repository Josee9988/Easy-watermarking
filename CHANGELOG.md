<!-- markdownlint-disable MD024-->
# **Change Log** 📜📝

All notable changes to the "**Easy Watermarking**" program will be documented in this file.

---

## [0.5.7] - 2019-07-27

### Fixed

* Spelling mistakes

## [0.5.6] - 2019-07-27

### Added

* Documentation images in readme.md

## [RELEASED] - 2019-07-26

## [0.5.5] - 2019-07-26

### Added

* Executable jar
* Readme link to the release and the executable

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

* From public to private unnecessary methods

## [0.5.1] - 2019-07-24

### Added

* Tiff support
* BMP support

## [0.5.0] - 2019-07-24

### Added

* Threads at the creation of the image, it now will perform much better, about 75% faster (depends a lot on your CPU)

## [0.4.1] - 2019-07-24

### Changed

* The whole ArrayList is now controlled by imageController and importController doesn't need anymore to make a new imageController class for each image.

### Fixed

* Some bugs
* Modularity wasn't on point

## [0.4.0] - 2019-07-21

### Added

* Support for Windows.
* Images of support windows & Linux added + compressed
* Folder of screenshots with all the images the readme will display.

## [0.3.2] - 2019-07-21

### Added

* Basic info to the readme.
* Missing documentation
* Icon of the project.

## [0.3.1] - 2019-07-21

### Added

* Documentation of the source java code

## [0.3.0] - 2019-07-21

### Added

* Improved responsiveness
* Menubar items.
* Menubar items link to internet pages (GitHub)
* Added an alert dialogue (informational) that displays the folder that has been created which contains the watermarked images.
* A remove button that lets you remove images from the left panel.
* License

### Fixed

* Some bugs

### Changed

* Name of apply watermark

## [0.2.1] - 2019-07-20

### Fixed

* Bug that made the watermark don't fill the original picture by its width and height and was filling by its width x width.

## [0.2.0] - 2019-07-20

### Added

* It works but sometimes it adds some transparent space down the image. Don't know yet
* Some responsiveness
* An error label that will display errors.
* Changelog.md
* Image controller that resizes the water image every time to every picture without losing quality
* And saves the files with the same name by default in a subdirectory where the image was selected (in new versions you will be able to select the folder)

### Fixed

* Importing files now have a filter that only lets the user select images.

### Changed

* Button from 'Watermark all' to 'Select output folder and Watermark All'

## [0.1.0] - 2019-07-20

### Added

* Basic UI with three buttons.
* Two buttons with that import the images
* The images show in the TableView

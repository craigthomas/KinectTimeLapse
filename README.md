# KinectTimeLapse

## What is it?

The project contains code to connect to an XBOX 360 Kinect and take pictures.
The original purpose was to create a simple camera that could take pictures
in both the night and day time for my [Machine Learning Deer Detector project](http://craigthomas.ca/blog/2014/08/04/deer-detection-with-machine-learning-part-1).
However, I swapped out the Kinect to a Raspberry Pi NOIR camera, since it 
is more sensitive to IR light in the 850 nm wavelength when compared to the
Kinect. However, this project remains to demonstrate how to take pictures
in Java with the Kinect.


## License

This project makes use of an MIT style license. Please see the file called
LICENSE for more information. Note that this project may make use of other
software that has separate license terms. See the section called `Third
Party Licenses and Attributions` below for more information on those
software components.


## Compiling

Simply copy the source files to a directory of your choice. In addition
to the source, you will need the following required software packages:

* [Java JDK 7](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 1.7.0 u51 or later
* [OpenKinect libfreenect](http://openkinect.org/wiki/Getting_Started)

To build the project, switch to the root of the source directory, and
type:

    ./gradlew build

On Windows, switch to the root of the source directory, and type:

    gradlew.bat build

The compiled Jar file will be placed in the `build/libs` directory.


## Running

### libfreenect Drivers

To actually connect to the Kinect, you will need to install the
libfreenect drivers for your platform. Most major Linux distributions
provide compiled binaries in their software repositories. See
[Open Kinect's Getting Started](http://openkinect.org/wiki/Getting_Started)
page for more detailed installation instructions.


### Command Line Help

There are several options available for the program. Running the jar
with the `-h` option will display a helpful description of the options:

    java -jar build/libs/KinectTimeLapse-0.1.jar -h


### Taking Pictures

To take pictures of various objects, you can specify the number of pictures
to take as well as a time delay between successive pictures. For example,
to take 10 pictures:

    java -jar build/libs/KinectTimeLapse-0.1.jar -n 10

To take 10 pictures with a delay of 5 seconds between each:

    java -jar build/libs/KinectTimeLapse-0.1.jar -n 10 -d 5

To take a picture using the infrared camera:

    java -jar build/libs/KinectTimeLapse-0.1.jar -i

Saving files to a different path:

    java -jar build/libs/KinectTimeLapse-0.1.jar -p /path/to/save/to


## Third Party Licenses and Attributions

### args4j

The project makes use of the args4j command-line argument parser,
which is available at https://github.com/kohsuke/args4j and is
licensed under the following terms:

    Copyright (c) 2013 Kohsuke Kawaguchi and other contributors

    Permission is hereby granted, free of charge, to any person obtaining a copy of
    this software and associated documentation files (the "Software"), to deal in
    the Software without restriction, including without limitation the rights to
    use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
    of the Software, and to permit persons to whom the Software is furnished to do
    so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.


### BoofCV 

This project makes use of BoofCV, which is licensed under the Apache License,
Version 2.0. The license can be downloaded from
http://www.apache.org/licenses/LICENSE-2.0.txt. The source code for this
software is available from https://github.com/lessthanoptimal/BoofCV

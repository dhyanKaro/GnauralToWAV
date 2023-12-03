# GnauralToWAV

## Introduction

GnauralToWAV is a Java application that converts [Gnaural](https://sourceforge.net/projects/gnaural/) schedule files to WAV audio files. It uses Gnaural's engine to generate binaural or isochronic beats based on the input schedule file. The output is a WAV audio file.

## Installation

To install and run this project, you have two options:

### Option 1: Using the JAR file

1. Go to the [releases](https://github.com/dhyanKaro/GnauralToWAV/releases/) page of the project repository.
2. Download the latest JAR file.
3. Save the JAR file somewhere convenient.

### Option 2: Compiling from source

1. Ensure you have Java installed.
2. Clone the repository to your local machine using the command `git clone https://github.com/dhyanKaro/GnauralToWAV`.
3. Navigate to the project directory using the command `cd GnauralToWAV`.
4. Compile the Java files using the command `javac *.java`.

## Usage

To use GnauralToWAV, follow these steps:

1. Open a terminal or command prompt.

### If you are using the JAR file:

2. Navigate to the location where you saved the JAR file.
3. Run the app using the command `java -jar GnauralToWAV.jar <input-file-path>`. Replace `<input-file-path>` with the path to your Gnaural schedule file.
   * Gnaural files are just XML files which can have any extension (.gnaural, .xml, etc.)

### If you compiled from source:

2. Navigate to the directory containing your compiled Java files.
3. Run the app using the command `java GnauralToWAV <input-file-path>`. Replace `<input-file-path>` with the path to your Gnaural schedule file.
    

The app will read the input file and generate a WAV audio file with the same name as the input file but with the `.wav` extension instead of `.gnaural`. The output WAV file will be located in the same directory as your input file.

## Contributing

Hey, feel free to pour through this decompiled java byte-code. Most of it's the same as Gnaural, except I added some cool ADSR envelope stuff and a new voice-type too! Good luck.

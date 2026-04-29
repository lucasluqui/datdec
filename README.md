# datdec
Simple tool that acts as a wrapper for [clyde](https://github.com/threerings/clyde)'s export tools, allowing interaction with such through both CLI and GUI.

## Building From Source
To build datdec follow these steps:

1. Prerequisites
    - Java 21 JDK installed.
    - [Maven](https://maven.apache.org/download.cgi) installed:
        - **Windows**: Download the ZIP from the link above, extract, then add `bin/` to your `PATH`.
        - **macOS (Homebrew)**: `brew install maven`.
        - **Linux (APT)**: `apt install maven`.
    - [Git](https://git-scm.com/downloads) installed:
        - **Windows**: Download the installer from [git-scm.com](https://git-scm.com/downloads) and follow the setup.
        - **macOS (Homebrew)**: `brew install git`.
        - **Linux (APT)**: `apt install git`.
2. Clone the repository.
    - `git clone https://github.com/lucasluqui/datdec.git`
3. Copy `projectx-pcode.jar` from your Spiral Knights `code` directory into the project's `lib` directory.
4. Validate all Maven dependencies.
    - `mvn validate`
5. Build the package using Maven.
    - `mvn clean package`
6. Copy the package built by Maven to your Spiral Knights folder and run it.
    - `java -jar datdec.jar`
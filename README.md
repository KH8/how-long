[![CircleCI](https://circleci.com/gh/krzysztofreczek/how-long/tree/master.svg?style=svg)](https://circleci.com/gh/krzysztofreczek/how-long/tree/master)

# How-Long application

Project `How-Long` is a simple command line time that stores acumulated data in lightweight file-based database.

## Modules

Project is split into few sub-modules:

- `how-long-app` main CLI application covering basic functionalites: time tracking, data display

- `how-long-admin` admin CLI application covering data management, reporting (tbd) and modification functionalities

- `how-long-engine` library with common components like low-level data access services, data repositories

- `how-long-utils` library of utils

## Build

This is `maven` multi-project, therefore in order to build it run:
```bash
mvn clean install
``` 

## Usage

There is a convenient shell script `run.sh` to run pre-build applications

- In order to run main CLI app run:
    ```bash
    sh run.sh app {ARGS}
    ```

- In order to run admin CLI app run:
    ```bash
    sh run.sh admin {ARGS}
    ```

Drill down into the modules to find more

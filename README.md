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

Drill down into the modules to find more

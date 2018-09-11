 # How-Long application - Main Application

Main application that updates records of current date to track time of activities and computes basic statistics. 
At the same time application provided various options to display accumulated data.

## Build
```bash
mvn clean install
``` 

## Usage

Every time the application is run actual timestamp is stored. If this is the first timestap of the day new record is initialized otherwise existing record of the day is updated with new end timestamp

The following arguments are available:

- _no argumets_
    
    Record of the day is updated
    Basic statistics are displated:
    - start time
    - end time
    - suggested end time (the time to compensate given work time goal)
    - elapsed and remaining time

- `list` {MONTH}
   
    Record of the day is updated
    List of all records for given month is displayed
    Month argument is optional, if not provided current month is taken

- `calendar` {MONTH}
    
    Record of the day is updated
    Calendar view for given month is displayed
    Month argument is optional, if not provided current month is taken

- `q` {MONTH}
    
    Record of the day is updated
    Simple single line statistics are displayed
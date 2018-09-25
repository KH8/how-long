 # How-Long application - Admin

Command line application to manage data collected by `how-long app`

## Build
```bash
mvn clean install
``` 

## Usage

The following arguments are available:

- `LIST` {MONTH}
    
    Lists all records for given month. 
    Month argument is optional, if not provided current month is taken

- `UPDATE` {MONTH} {DAY} {MODE: FULL | START | END} {START_TIME} {END_TIME}
    
    Updated record of given month and day
    If record does not exist new record is be created
    Modes:
    - `FULL`: both start and end date are updated
    - `START`: only start date is updated
    - `END`: only end date is updated
    
    Date format should be provided as `hh:mm:ss`

- `DELETE` {MONTH} {DAY}
    
    Deletes ecord of given month and day
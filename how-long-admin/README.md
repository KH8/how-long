 # How-Long application - Admin

Command line application to manage data collected by `how-long app`

## Build
```bash
mvn clean install
``` 

## Usage

The following arguments are available:

- `LIST` --month={MONTH}

    Lists all records for given month.
    Month argument is optional, if not provided current month is taken.

- `UPDATE` --month={MONTH} --day={DAY} --start-time={hh:mm:ss} --end-time={hh:mm:ss}

    Updates record of given month and day.
    If record does not exist new record is created.

- `DELETE` --month={MONTH} --day={DAY}

    Deletes record of given month and day.
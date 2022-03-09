# Commissions Application

### Before run
Create empty DB on your PostgreSQL server

```
username: postgres
password: postgres
url: jdbc:postgresql://localhost:5432/commissions
```

You can change these properties in the `application.yaml` file

### How to run
Execute in the project folder:

    mvn spring-boot:run

### Remarks
I tried to make the app as usable and extensible as possible.
I don't use R2DBC because it's not stable now and has a lot of issues with other libraries,
but the rest of the chain is reactive. If necessary, you can easily add tools to the application
to edit existing rules or add new ones. Adding new rule types is also pretty easy, and you don't need
to modify a single line of existing code. It's quite enough to add new service with its own logic, 
which implements `CommissionService` - and that's it! The commission calculated by this service will be
taken into account when calculating the final commission.

Please note that I have not used Kotlin for three years :)

BR,
Andrei


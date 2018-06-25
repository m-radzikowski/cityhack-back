# CityHack Backend

## Branches

- **master** - current production version.
- **beta** - current beta version with.
- **dev** - development version.

## Usage

Start containers with:

```bash
docker-compose up --build
```

After start the following services should be available:
- Spring Boot app on [localhost](http://localhost:80/)
- Swagger generated documentation on [localhost/swagger-ui.html](http://localhost:80/swagger-ui.html/)
- MS SQL database on localhost:1433

Start single services (with -d flag if should run in background)

```bash
docker-compose up --build cityhack

docker-compose up --build mssql
```

To start tests run

```bash
docker-compose -f docker-compose.yml -f docker-compose.test.yml up --build
```

# Other ways of using Docker
These are helpful commands if running the application without Docker Compose.

## Build application image locally using Maven
`mvn spring-boot:run`<br>
`mvn clean package`<br>
`docker build -t portal .`

## Start DATABASE ONLY using Docker
`docker run --name stude-db --publish 3308:3306 -e MARIADB_ROOT_PASSWORD=my-secret-pw -d mariadb:latest --port 3306`

Connect to the database manually and run migrations manually to create schema, application user and grants.

## Start APPLICATION ONLY using Docker
`docker run --publish 8081:8081 tvergilio/portal`

You will need to make changes to `application.properties`

## Publish to Docker Hub
1. Log in
   `docker login --username=XXXXXXX`

2. Get List of Images
   `docker images`

3. Tag Image
   `docker tag tvergilio/portal XXXXX/portal:1.3`<br/>
   (replace XXXXXXXX with your Docker Hub username; increment the version)

4. Push to Docker Hub
   `docker push tvergilio/portal`

## Run container from image stored in Docker Hub
`docker run --publish 8081:8081 XXXXXX/portal:1.3`<br/>
(replace XXXXXXXX with your Docker Hub username; check the version)

## To clean old images and containers
`docker system prune -a`<br/>

## To clean volumes
`docker system prune --volumes`
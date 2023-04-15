run:
	mvn spring-boot:run

db-container:
	docker compose -f docker-compose-dev.yml up

db-migrate:
	mvn clean flyway:migrate -Dflyway.configFiles=flyway.conf

db-clean:
	mvn clean flyway:clean -Dflyway.configFiles=flyway.conf
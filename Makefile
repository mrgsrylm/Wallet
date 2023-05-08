##### DEV
run-backend:
	cd poy-wallet-backend && ./mvnw clean spring-boot:run --spring.profile.active=dev

run-frontend:
	cd poy-wallet-frontend && npm run start

##### MIGRATION
db-migrate:
	cd poy-wallet-backend && ./mvnw clean flyway:migrate -Dflyway.configFiles=flyway.conf

db-clean:
	cd poy-wallet-backend && ./mvnw clean flyway:clean -Dflyway.configFiles=flyway.conf

##### CONTAINER
docker-dev-start:
	docker compose -f docker-compose-dev.yml up

docker-dev-stop:
	docker compose -f docker-compose-dev.yml down

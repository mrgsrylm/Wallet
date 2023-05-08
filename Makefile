##### DEV
run-backend:
	cd poy-wallet-backend && ./mvnw clean spring-boot:run

run-frontend:
	cd poy-wallet-frontend && npm run start

##### CONTAINER
docker-dev-start:
	docker compose -f docker-compose-dev.yml up

docker-dev-stop:
	docker compose -f docker-compose-dev.yml down
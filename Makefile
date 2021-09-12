.PHONY: deploy
deploy:
	docker-compose up --force-recreate -d

.PHONY: down
down:
	docker-compose down --remove-orphans

.PHONY: copy
copy:
	docker-compose down --remove-orphans


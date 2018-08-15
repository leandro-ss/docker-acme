# docker-acme

DOCKER-ACME
=================	
	
###Build

	Para Build da aplicação execute os seguintes passos:
	
		1 - No mesmo nivel de diretório do Dockerfile execute "docker build -t docker-acme ."
		
		2 - Execute o container com o seguinte comando: "docker container run -d -e ENV ACME_ACCESS_KEY_ID=<ACCESS_KEY>
		
		-e ACME_SECRET_ACCESS_KEY=<SECRET_KEY> -p 8081:8081 -t docker-acme"
		
	


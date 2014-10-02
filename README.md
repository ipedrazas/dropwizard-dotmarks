dropwizard-dotmarks
===================

dotmarks backend implemented using Dropwizard and Cassandra

To run the aplication you have to have a Docker instance running Cassandra and call:

	docker run -d --name c1 poklet/cassandra
	# extract the IP
	docker inspect c1
	# Add that IP to config.yml
	java -jar target/dotmarks-api-0.0.1-SNAPSHOT.jar server config.yml
  


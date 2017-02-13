#########################################################################
# Config vars
#########################################################################
# Set to the name of the Docker machine you want to use
#docker stop $CLIENT_CONTAINER $SERVER_CONTAINER
#docker rm $CLIENT_CONTAINER $SERVER_CONTAINER
DOCKER_MACHINE_NAME=default

# Set to the names of the Docker images you want to use
IMAGE=rmiserverclient

# Set the names of the Docker containers for corresponding images
SERVER_CONTAINER=server
CLIENT_CONTAINER=client

docker stop $CLIENT_CONTAINER $SERVER_CONTAINER
docker rm $CLIENT_CONTAINER $SERVER_CONTAINER
#docker network rm pingnetwork
# Set the local directories to the server and the client
LOCAL_DIR=$(pwd)

# Set the image directories
WORK_DIR='/root/RMI'

# Set the idNumber and the output file name
ID_NUM=100
#OUTPUT_FILE=clientOutput
docker build -t $IMAGE $LOCAL_DIR

#########################################################################
# Start running of 2 containers
#########################################################################
echo "-----------------------------------------------------------"
echo "Start running of 2 containers"
echo "-----------------------------------------------------------"

docker network create -d bridge pingnetwork
#docker run -itd --name $SERVER_CONTAINER -v $LOCAL_DIR:$WORK_DIR --net=pingnetwork $IMAGE bash $WORK_DIR/compile_and_runServer.sh
docker run -itd --name $SERVER_CONTAINER --net=pingnetwork $IMAGE bash $WORK_DIR/compile_and_runServer.sh
#SERVER_IP=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' $SERVER_CONTAINER)
SERVER_IP=($(docker exec $SERVER_CONTAINER hostname -I))
#docker run -itd --name $CLIENT_CONTAINER -v $LOCAL_DIR:$WORK_DIR --net=pingnetwork $IMAGE bash $WORK_DIR/compile_and_runClient.sh $SERVER_IP $ID_NUM

docker run -itd --name $CLIENT_CONTAINER --net=pingnetwork $IMAGE bash $WORK_DIR/compile_and_runClient.sh $SERVER_IP $ID_NUM
sleep 10
docker logs $CLIENT_CONTAINER
docker stop $SERVER_CONTAINER
docker stop $CLIENT_CONTAINER
docker network rm pingnetwork
#docker logs $CLIENT_CONTAINER > $OUTPUT_FILE
#docker stop $CLIENT_CONTAINER $SERVER_CONTAINER
#docker rm $CLIENT_CONTAINER $SERVER_CONTAINER

SERVER_IP=$1
ID_NUM=$2
make

java pingpongserverTest/PingPongClient $SERVER_IP $ID_NUM

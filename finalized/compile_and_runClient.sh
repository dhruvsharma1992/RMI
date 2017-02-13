SERVER_IP=$1
ID_NUM=$2
javac pingpongserverTest/*.java

java pingpongserverTest/PingPongClient $SERVER_IP $ID_NUM

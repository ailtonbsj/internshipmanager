#!/bin/bash

RED='\033[1;43m'
NC='\033[0m'
echo -e "${RED}Run project in Eclipse IDE for create bin folder and automatically build.${NC}"
read

echo -e "${RED}Create a runnable jar file named as 'InternshipManager.jar' using export option in Eclipse IDE.${NC}"
read

if [ ! -f webswing-examples-eval-21.2.2-distribution.zip ]; then
    wget https://dev.webswing.org/files/public/webswing-examples-eval-21.2.2-distribution.zip
fi

sudo docker build -t internship-manager .
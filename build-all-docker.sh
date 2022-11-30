#!/bin/bash

docker build -f cad-AuthService/Dockerfile -t cad-auth cad-AuthService/
docker build -f cad-GymService/Dockerfile -t cad-gym cad-GymService/
docker build -f cad-UserService/Dockerfile -t cad-user cad-UserService/
docker build -f cad-WorkoutService/Dockerfile -t cad-workout cad-WorkoutService/
docker build -f cad-Frontend/Dockerfile -t cad-frontend cad-Frontend/

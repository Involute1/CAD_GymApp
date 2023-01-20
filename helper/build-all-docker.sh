#!/bin/bash

docker build -t drescherflo/cad-gym-gym-service:latest cad-GymService/
docker build -t drescherflo/cad-gym-user-service:latest cad-UserService/
docker build -t drescherflo/cad-gym-workout-service:latest cad-WorkoutService/
docker build -t drescherflo/cad-gym-invoice-cronjob:latest cad-InvoiceCronjob/
docker build -t drescherflo/cad-gym-frontend:latest cad-Frontend/

docker build -t drescherflo/cad-gym-gym-service:latest cad-GymService\
docker build -t drescherflo/cad-gym-user-service:latest cad-UserService\
docker build -t drescherflo/cad-gym-workout-service:latest cad-WorkoutService\
docker build -t drescherflo/cad-gym-frontend-service:latest cad-Frontend\

cd terraform/environments/dev
terraform init
terraform apply -auto-approve

cd ../../../
helm install cad-gymapp ./cad-gymapp
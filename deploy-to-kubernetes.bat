docker build -t europe-west3-docker.pkg.dev/cad-project-368216/CAD-Project/cad-gym-gym-service:latest cad-GymService\
docker build -t europe-west3-docker.pkg.dev/cad-project-368216/CAD-Project/cad-gym-user-service:latest cad-UserService\
docker build -t europe-west3-docker.pkg.dev/cad-project-368216/CAD-Project/cad-gym-workout-service:latest cad-WorkoutService\
docker build -t europe-west3-docker.pkg.dev/cad-project-368216/CAD-Project/cad-gym-frontend:latest cad-Frontend\

docker push europe-west3-docker.pkg.dev/cad-project-368216/cad-repo-dev/cad-gym-gym-service:latest
docker push europe-west3-docker.pkg.dev/cad-project-368216/cad-repo-dev/cad-gym-user-service:latest
docker push europe-west3-docker.pkg.dev/cad-project-368216/cad-repo-dev/cad-gym-workout-service:latest
docker push europe-west3-docker.pkg.dev/cad-project-368216/cad-repo-dev/cad-gym-frontend:latest

cd terraform/environments/dev
terraform init
terraform apply -auto-approve

cd ../../../
helm install cad-gymapp ./cad-gymapp

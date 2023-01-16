cd terraform/environments/dev
terraform init
terraform apply -auto-approve

cd ../../../

docker build -t europe-west3-docker.pkg.dev/cad-gym-app/cad-repo-dev/cad-gym-gym-service:latest cad-GymService/
docker build -t europe-west3-docker.pkg.dev/cad-gym-app/cad-repo-dev/cad-gym-user-service:latest cad-UserService/
docker build -t europe-west3-docker.pkg.dev/cad-gym-app/cad-repo-dev/cad-gym-workout-service:latest cad-WorkoutService/
docker build -t europe-west3-docker.pkg.dev/cad-gym-app/cad-repo-dev/cad-gym-frontend:latest cad-Frontend/

docker push europe-west3-docker.pkg.dev/cad-gym-app/cad-repo-dev/cad-gym-gym-service:latest
docker push europe-west3-docker.pkg.dev/cad-gym-app/cad-repo-dev/cad-gym-user-service:latest
docker push europe-west3-docker.pkg.dev/cad-gym-app/cad-repo-dev/cad-gym-workout-service:latest
docker push europe-west3-docker.pkg.dev/cad-gym-app/cad-repo-dev/cad-gym-frontend:latest

helm upgrade --install ingress-nginx ingress-nginx \
  --repo https://kubernetes.github.io/ingress-nginx \
  --namespace ingress-nginx --create-namespace
helm upgrade --install gymapp ./cad-gymapp

#export USE_GKE_GCLOUD_AUTH_PLUGIN=True

cd ../terraform/environments/dev
terraform init
terraform apply -auto-approve

cd ../../../

docker build -t europe-west3-docker.pkg.dev/cad-gym-app/cad-repo-dev/cad-gym-gym-service:latest cad-GymService/
docker build -t europe-west3-docker.pkg.dev/cad-gym-app/cad-repo-dev/cad-gym-user-service:latest cad-UserService/
docker build -t europe-west3-docker.pkg.dev/cad-gym-app/cad-repo-dev/cad-gym-workout-service:latest cad-WorkoutService/
docker build -t europe-west3-docker.pkg.dev/cad-gym-app/cad-repo-dev/cad-gym-frontend:latest cad-Frontend/
docker build -t europe-west3-docker.pkg.dev/cad-gym-app/cad-repo-dev/cad-gym-invoice-cronjob:latest cad-InvoiceCronjob/

cd helm

helm upgrade --install ingress-nginx ingress-nginx \
  --repo https://kubernetes.github.io/ingress-nginx \
  --namespace ingress-nginx --create-namespace
helm upgrade --install postgresql-user postgresql-ha \
  --repo https://charts.bitnami.com/bitnami \
  --values=postgresql-user-values.yaml
helm upgrade --install postgresql-workout postgresql-ha \
  --repo https://charts.bitnami.com/bitnami \
  --values=postgresql-workout-values.yaml

sleep 10  # required for ingress to fully start up

helm upgrade --install gymapp cad-gymapp --values=gymapp-local-deployment.yaml

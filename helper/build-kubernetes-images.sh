docker build -t europe-west3-docker.pkg.dev/cad-gym-app/cad-repo-dev/cad-gym-gym-service:latest ../cad-GymService/
docker build -t europe-west3-docker.pkg.dev/cad-gym-app/cad-repo-dev/cad-gym-user-service:latest ../cad-UserService/
docker build -t europe-west3-docker.pkg.dev/cad-gym-app/cad-repo-dev/cad-gym-workout-service:latest ../cad-WorkoutService/
docker build -t europe-west3-docker.pkg.dev/cad-gym-app/cad-repo-dev/cad-gym-frontend:latest ../cad-Frontend/
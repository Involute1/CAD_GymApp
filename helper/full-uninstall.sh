helm uninstall gymapp
helm uninstall postgresql-user
helm uninstall postgresql-workout
kubectl delete pvc --all  # remove persistent volume claims of postgresql (including login credentials etc)


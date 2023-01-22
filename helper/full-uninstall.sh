helm uninstall gymapp
helm uninstall postgresql-user
helm uninstall postgresql-workout
helm uninstall ingress-nginx --namespace ingress-nginx
kubectl delete namespace ingress-nginx
kubectl delete pvc --all  # remove persistent volume claims of postgresql (including login credentials etc)


#!/bin/bash

if [[ "$1" == "" ]]; then
  echo "No release environment specified as first argument. Assuming dev."
  deployment_type="dev"
else
  echo "Specified release environment: $1"
  deployment_type="$1"
fi

if [[ "$2" == "" ]]; then
  echo "No namespace passed as second argument. Assuming default."
  namespace="default"
else
  echo "Specified namespace: $2"
  namespace="$2"
fi

gcloud container clusters get-credentials cluster-"$deployment_type" --zone europe-west3-c

kubectl -n "$namespace" run mycurlpod --image=curlimages/curl -i --tty -- sh
kubectl -n "$namespace" delete pod mycurlpod

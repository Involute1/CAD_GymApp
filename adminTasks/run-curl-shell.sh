#!/bin/bash

if [[ "$1" == "" ]]; then
  echo "No namespace passed as argument. Assuming default."
  namespace="default"
else
  echo "Specified namespace: $1"
  namespace="$1"
fi

kubectl -n "$namespace" default run mycurlpod --image=curlimages/curl -i --tty -- sh
kubectl -n "$namespace" delete pod mycurlpod

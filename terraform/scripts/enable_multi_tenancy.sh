#!/bin/bash
curl --request PATCH \
  "https://identitytoolkit.googleapis.com/v2/projects/$1/config?updateMask=multiTenant" \
  --header "Authorization: Bearer $(gcloud auth print-access-token)" \
  --header 'Accept: application/json' \
  --header 'Content-Type: application/json' \
  --header "X-Goog-User-Project: $1" \
  --data '{"multiTenant":{"allowTenants":true}}' \
  --compressed

#!/bin/bash

if [[ $(git status --porcelain) ]]; then
  # Changes
  echo "There are untracked changes, aborting!"
  exit 1
fi

if [[ $1 == "" ]]; then
    echo "You must specify a customer as the first command line argument!"
fi

if [[ $2 == "" ]]; then
    echo "You must specify a pipeline/corresponding branch to push to!"
fi

echo "Adding new enterprise customer \"$1\" and pushing to pipeline $2"
echo "You have 5 seconds to cancel now..."
sleep 5

git checkout dev
echo "$1" >> ../helm/enterprise-clients.txt
git commit -am "Add new enterprise customer $1"

git checkout $2
git merge dev

git push


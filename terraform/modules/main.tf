terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "4.46.0"
    }
  }
}

provider "google" {
  project = var.project_id
  region  = var.eu-region
}

#resource "google_storage_bucket" "gym" {
#  name          = ""
#  project       = var.
#  location      = var.
#  force_destroy = true
#}

//TODO 2x sql-db (gym, user), 1x nosql db(workouts), kubernetes deploy
// per environment name change dbs?
// create execute configs for each env
// add service account for ci/cd
// store state in bucket per env -> backend.tf
// google api´s to activate: kubernetes, google identity platform?, firestore(nosql), bucket, artifact registry?, Cloud SQL-Instanzen


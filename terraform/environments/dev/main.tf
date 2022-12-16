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
  region  = var.eu-location
}

module "gym-bucket" {
  source = "../../modules/storage"

  bucket-name = "dev"
  project_id  = var.project_id
  eu-location = var.eu-location
}

module "services" {
  source = "../../modules/services"

  service_account_display_name = "dev-terraform-service-account"
  service_account_id           = "dev123456789"
}

module "kubernetes" {
  source     = "../../modules/kubernetes"
  depends_on = [module.services]

  cluster-name          = "cluster-dev"
  eu-location           = var.eu-zone
  machine_type          = "c2d-standard-2"
  node-pool-name        = "pool-dev"
  service-account-email = module.services.service-account-email
}

//TODO kubernetes deploy
// add service account for ci/cd
// google api´s to activate: kubernetes, google identity platform?, firestore(nosql), bucket, artifact registry?, Cloud SQL-Instanzen
// put sensitive info somewhere safe

terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "4.49.0"
    }
  }
}

provider "google" {
  project = var.project_id
  region  = var.eu_location
}

module "project-services" {
  source  = "terraform-google-modules/project-factory/google//modules/project_services"
  version = "~> 12.0"

  enable_apis = var.enable_apis
  project_id  = var.project_id

  activate_apis = [
    "container.googleapis.com",
    "artifactregistry.googleapis.com",
    "identitytoolkit.googleapis.com",
    "secretmanager.googleapis.com",
    "apikeys.googleapis.com",
    "firestore.googleapis.com",
    "cloudresourcemanager.googleapis.com"
  ]

  disable_services_on_destroy = false
}

#module "storage" {
#  source     = "../../modules/storage"
#  depends_on = [module.project-services]
#
#  environment = local.environment
#  project_id  = var.project_id
#  eu_location = var.eu_location
#}

module "services" {
  source = "../../modules/services"

  project_id  = var.project_id
  environment = local.environment
}

#module "kubernetes" {
#  source     = "../../modules/kubernetes"
#  depends_on = [module.services, module.project-services]
#
#  cluster_name          = var.cluster_name
#  eu_location           = var.eu_zone
#  machine_type          = var.machine_type
#  node_pool_name        = var.node_pool_name
#  service_account_email = module.services.kubernetes_service_account
#}


module "monitoring" {
  source     = "../../modules/monitoring"
  depends_on = [module.services, module.project-services]

  project_id  = var.project_id
  environment = local.environment
}

terraform {
  #  https://registry.terraform.io/providers/hashicorp/helm/latest/docs
  required_providers {
    helm = {
      source  = "hashicorp/helm"
      version = "2.8.0"
    }
  }
}

provider "helm" {
  # Configuration options
}

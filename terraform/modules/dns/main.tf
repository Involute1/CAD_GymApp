terraform {
  #  https://registry.terraform.io/providers/hashicorp/google/latest/docs/resources/dns_managed_zone
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "4.49.0"
    }
  }
}

provider "google" {
  # Configuration options
}

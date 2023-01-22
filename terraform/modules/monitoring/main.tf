terraform {
  #  https://registry.terraform.io/providers/hashicorp/google/latest/docs/resources/monitoring_alert_policy
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "4.49.0"
    }
  }
}

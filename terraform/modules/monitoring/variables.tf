locals {
  uptime_url_type = "uptime_url"
  urls            = ["drescherflo.de", "premium.drescherflo.de", "eiglsperger-gym.drescherflo.de"]
}

variable "environment" {
  type = string
}

variable "project_id" {
  type = string
}

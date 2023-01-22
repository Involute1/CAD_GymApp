locals {
  //TODO change these
  basic_urls      = ["192.168.1.1", "192.168.1.2"]
  uptime_url_type = "uptime_url"
}

variable "environment" {
  type = string
}

variable "project_id" {
  type = string
}

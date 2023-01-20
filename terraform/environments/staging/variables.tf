locals {
  environment = "staging"
}

variable "project_id" {
  type = string
}

variable "eu_zone" {
  type = string
}

variable "eu_location" {
  type = string
}

variable "cluster_name" {
  type = string
}

variable "machine_type" {
  type = string
}

variable "node_pool_name" {
  type = string
}

variable "enable_apis" {
  type = bool
}

variable "monitor_type" {
  type = string
}

variable "k8n_ingress_namespace_name" {
  type = string
}

variable "k8n_ingress_service_name" {
  type = string
}

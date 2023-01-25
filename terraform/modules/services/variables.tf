locals {
  is_not_Windows = length(regexall("/home/", lower(abspath(path.root)))) > 0
}

variable "project_id" {
  type = string
}

variable "environment" {
  type = string
}

variable "eu_location" {
  type = string
}

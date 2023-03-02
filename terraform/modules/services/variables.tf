locals {
  is_linux = startswith(abspath(path.root), "/home/")
  is_macos = startswith(abspath(path.root), "/Users/")
  is_windows = startswith(abspath(path.root), "C:/Users/")
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

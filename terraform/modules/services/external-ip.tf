resource "google_compute_address" "default" {
  project = var.project_id
  name = "ingress-ip-${var.environment}"
  region = var.eu_location
}
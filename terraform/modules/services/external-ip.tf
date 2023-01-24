resource "google_compute_global_address" "default" {
  project = var.project_id
  name = "ingress-ip-${var.environment}"
}
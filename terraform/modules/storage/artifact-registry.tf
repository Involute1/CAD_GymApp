resource "google_artifact_registry_repository" "cad_docker_artifact_repo" {
  location      = var.eu_location
  repository_id = "cad-repo-${var.environment}"
  description   = "cad Docker Repo for ${var.environment}"
  format        = "DOCKER"
}

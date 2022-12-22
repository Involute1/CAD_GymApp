resource "google_storage_bucket" "gym_bucket" {
  name          = "gym-bucket-${var.environment}"
  project       = var.project_id
  location      = var.eu_location
  force_destroy = true
  storage_class = "STANDARD"

  versioning {
    enabled = true
  }

}

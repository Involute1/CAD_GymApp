resource "random_id" "bucket_prefix" {
  byte_length = 8
}

resource "google_storage_bucket" "gym-bucket" {
  name          = "${random_id.bucket_prefix.hex}-gym-bucket-${var.environment}"
  project       = var.project_id
  location      = var.eu_location
  force_destroy = true
  storage_class = "STANDARD"

  versioning {
    enabled = true
  }

}

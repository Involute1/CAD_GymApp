resource "google_service_account" "tf_account" {
  account_id   = var.service_account_id
  display_name = var.service_account_display_name
  project      = var.project_id
}

//TODO set finer roles, i just gave admin because easier
resource "google_project_iam_binding" "tf_service_account_iam_binding_roles" {
  for_each = toset([
    "roles/identitytoolkit.admin", "roles/logging.admin", "roles/storage.admin", "roles/datastore.owner",
    "roles/storage.objectAdmin"
  ])
  members = ["serviceAccount:${google_service_account.tf_account.email}"]
  project = var.project_id
  role    = each.key
}

resource "google_service_account_key" "tf_account_key" {
  service_account_id = google_service_account.tf_account.name
  public_key_type    = "TYPE_X509_PEM_FILE"
}

//TODO create service account for every service, might be safer
resource "local_sensitive_file" "tf_account_key" {
  for_each = toset([
    "cad-GymService/src/main/resources/", "cad-UserService/src/main/resources/",
    "cad-WorkoutService/src/main/resources/"
  ])
  filename = "${path.root}/../../../${each.key}tf_service_account_key.json"
  content  = base64decode(google_service_account_key.tf_account_key.private_key)
}


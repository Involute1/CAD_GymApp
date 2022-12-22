resource "google_service_account" "tf_account" {
  account_id   = var.service_account_id
  display_name = var.service_account_display_name
  project      = var.project_id
}

resource "google_project_iam_binding" "tf_service_account_iam_binding_toolkit" {
  members = ["serviceAccount:${google_service_account.tf_account.email}"]
  project = var.project_id
  role    = "roles/identitytoolkit.admin"
}

resource "google_service_account_key" "tf_account_key" {
  service_account_id = google_service_account.tf_account.name
  public_key_type    = "TYPE_X509_PEM_FILE"
}

resource "local_sensitive_file" "tf_account_key" {
  filename = "${path.root}/../../../cad-AuthService/src/main/resources/tf_service_account_key.json"
  content  = base64decode(google_service_account_key.tf_account_key.private_key)
}


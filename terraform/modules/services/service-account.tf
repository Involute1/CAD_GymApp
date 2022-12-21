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

//use this for the key for auth
resource "google_service_account_key" "tf_account_key" {
  service_account_id = google_service_account.tf_account.name
  public_key_type    = "TYPE_X509_PEM_FILE"
}

resource "local_sensitive_file" "tf_account_key" {
  filename = "${path.root}/../../../cad-AuthService/src/main/resources/authKey.json"
  content  = jsonencode({
    "private_key"    = "-----BEGIN PRIVATE KEY-----\n${google_service_account_key.tf_account_key.private_key}\n-----END PRIVATE KEY-----\n",
    "type"           = "service_account",
    "private_key_id" = substr(google_service_account_key.tf_account_key.id, -40, -1),
    "client_email"   = google_service_account.tf_account.email,
    "client_id"      = google_service_account.tf_account.id
  })
  #  content  = "{\n\"type\":\"service_account\", \n\"project_id\":\"${var.project_id}\", \n\"private_key_id\":\"${substr(google_service_account_key.tf_account_key.id, -40, -1)}\", \n\"private_key\":\"-----BEGIN PRIVATE KEY-----${google_service_account_key.tf_account_key.private_key}\n-----END PRIVATE KEY-----\n\", \n\"client_email\":\"${google_service_account.tf_account.email}\", \n\"client_id\":\"${google_service_account.tf_account.id}\", \n\"auth_uri\":\"https://accounts.google.com/o/oauth2/auth\", \n\"token_uri\":\"https://oauth2.googleapis.com/token\", \n\"auth_provider_x509_cert_url\":\"https://www.googleapis.com/oauth2/v1/certs\", \n\"client_x509_cert_url\":\"https://www.googleapis.com/robot/v1/metadata/x509/dev123456789%40cad-project-368216.iam.gserviceaccount.com\"\n}"
}


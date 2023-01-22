resource "random_string" "random_string_for_service_accounts" {
  length  = 15
  special = false
  upper   = false
  lower   = true
}

resource "google_service_account" "tf_account_kubernetes" {
  account_id   = "tfk8s${random_string.random_string_for_service_accounts.result}"
  display_name = "tf_k8s_${var.environment}"
  project      = var.project_id
}

resource "google_project_iam_binding" "tf_k8n_service_account_iam_binding_roles" {
  for_each = toset([
    "roles/serviceusage.serviceUsageConsumer", "roles/artifactregistry.admin", "roles/container.clusterAdmin"
  ])
  members = ["serviceAccount:${google_service_account.tf_account_kubernetes.email}"]
  project = var.project_id
  role    = each.key
}

resource "google_service_account" "tf_account_gym_service" {
  account_id   = "tfgym${random_string.random_string_for_service_accounts.result}"
  display_name = "tf_gym_service_${var.environment}"
  project      = var.project_id
}

resource "google_project_iam_binding" "tf_gym_service_account_iam_binding_roles" {
  for_each = toset([
    "roles/storage.admin", "roles/datastore.owner", "roles/storage.objectAdmin"
  ])
  members = ["serviceAccount:${google_service_account.tf_account_gym_service.email}"]
  project = var.project_id
  role    = each.key
}

resource "google_service_account" "tf_account_user_service" {
  account_id   = "tfuser${random_string.random_string_for_service_accounts.result}"
  display_name = "tf_user_service_${var.environment}"
  project      = var.project_id
}

resource "google_project_iam_binding" "tf_user_service_account_iam_binding_roles" {
  for_each = toset([
    "roles/identitytoolkit.admin", "roles/logging.admin"
  ])
  members = ["serviceAccount:${google_service_account.tf_account_user_service.email}"]
  project = var.project_id
  role    = each.key
}

resource "google_service_account_key" "tf_gym_account_key" {
  service_account_id = google_service_account.tf_account_gym_service.name
  public_key_type    = "TYPE_X509_PEM_FILE"
}

resource "google_service_account_key" "tf_user_account_key" {
  service_account_id = google_service_account.tf_account_user_service.name
  public_key_type    = "TYPE_X509_PEM_FILE"
}

resource "local_sensitive_file" "tf_gym_account_key" {
  filename = "${path.root}/../../../cad-GymService/src/main/resources/tf_service_account_key.json"
  content  = base64decode(google_service_account_key.tf_gym_account_key.private_key)
}

resource "local_sensitive_file" "tf_user_account_key" {
  filename = "${path.root}/../../../cad-UserService/src/main/resources/tf_service_account_key.json"
  content  = base64decode(google_service_account_key.tf_gym_account_key.private_key)
}

//     "roles/identitytoolkit.admin", "roles/logging.admin", "roles/storage.admin", "roles/datastore.owner",
//    "roles/storage.objectAdmin", "roles/serviceusage.serviceUsageConsumer", "roles/artifactregistry.admin",
//    "roles/container.clusterAdmin"

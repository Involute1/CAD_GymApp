resource "google_service_account" "tf-account" {
  account_id   = var.service_account_id
  display_name = var.service_account_display_name
}

#resource "google_service_account_iam_member" "tf-account-iam" {
#  member             = "???"
#  role               = "roles/serviceusage.serviceUsageAdmin"
#  service_account_id = google_service_account.tf-account.account_id
#}
//TODO permissions for service account?

#resource "google_identity_platform_config" "default" {
#  project = var.project_id
#}

#resource "google_identity_platform_project_default_config" "default" {
#  sign_in {
#    allow_duplicate_emails = false
#
#    anonymous {
#      enabled = false
#    }
#
#    email {
#      enabled           = true
#      password_required = true
#    }
#
#    phone_number {
#      enabled = false
#    }
#  }
#}

#this is just a test tenant, dont think we need this
#resource "google_identity_platform_tenant" "tenant" {
#  display_name          = "tenant"
#  allow_password_signup = true
#}

#if we want tenants to auth with something else
#resource "google_identity_platform_tenant_default_supported_idp_config" "idp_config" {
#  enabled       = true
#  tenant        = google_identity_platform_tenant.tenant.name
#  idp_id        = "playgames.google.com"
#  client_id     = "my-client-id"
#  client_secret = "secret"
#}

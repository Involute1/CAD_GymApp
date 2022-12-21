resource "google_identity_platform_project_default_config" "default" {
  project = var.project_id
  sign_in {
    allow_duplicate_emails = false

    anonymous {
      enabled = false
    }

    email {
      enabled           = true
      password_required = true
    }

    phone_number {
      enabled = false
    }
  }
}

//TODO enable multi-tenacy

#resource "google_identity_platform_tenant" "default_tenant" {
#  project               = var.project_id
#  display_name          = "default"
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

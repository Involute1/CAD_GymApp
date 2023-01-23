#resource "google_identity_platform_config" "default" {
#  project = var.project_id
#
#
#}

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

resource "null_resource" "enable_multi_tenancy_script" {
  provisioner "local-exec" {
    command     = "enable_multi_tenancy.sh ${var.project_id}"
    working_dir = "../../scripts"
  }
}

resource "random_string" "random_string_for_api_key" {
  length  = 10
  special = false
  upper   = false
  lower   = true
}

resource "google_apikeys_key" "identity_platform_api_key" {
  name         = "tf${var.environment}apikey${random_string.random_string_for_api_key.result}"
  display_name = "tf_${var.environment}_identity_platform_api_key"
  project      = var.project_id
  restrictions {
    api_targets {
      service = "identitytoolkit.googleapis.com"
    }
  }
}

resource "local_sensitive_file" "identity_platform_api_key" {
  filename = "${path.root}/../../../cad-Frontend/src/environments/apiKey.json"
  content  = jsonencode({ "firebaseApiKey" = google_apikeys_key.identity_platform_api_key.key_string })
}

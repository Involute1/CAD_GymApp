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

#resource "null_resource" "enable_multi_tenacy_skript" {
#  provisioner "local-exec" {
#    #    PATCH https://identitytoolkit.googleapis.com/admin/v2/{config.name=projects/*/config}
#    #    {
#    #    "multiTenant": {
#    #      "allowTenants": true
#    #    }
#    #  }
#  }
#}
//TODO enable multi-tenacy

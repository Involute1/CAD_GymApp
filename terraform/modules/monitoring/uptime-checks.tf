resource "google_monitoring_uptime_check_config" "gym_service_uptime_check" {
  display_name = "Gym Service Uptime Check"
  timeout      = "60s"
  period       = "60s"

  http_check {
    path           = "/gym/healthcheck"
    port           = "80"
    request_method = "GET"

    accepted_response_status_codes {
      status_class = "STATUS_CLASS_2XX"
    }
  }

  monitored_resource {
    labels = {
      "project_id" : var.project_id,
      "location" : var.location,
      "cluster_name" : var.cluster_name,
      "namespace_name" : var.namespace_name,
      "service_name" : var.service_name
    }
    type = var.monitor_type
  }

}

resource "google_monitoring_uptime_check_config" "reporting_service_uptime_check" {
  display_name = "Reporting Service Uptime Check"
  timeout      = "60s"
  period       = "60s"

  http_check {
    path           = "/reporting/healthcheck"
    port           = "80"
    request_method = "GET"

    accepted_response_status_codes {
      status_class = "STATUS_CLASS_2XX"
    }
  }

  monitored_resource {
    labels = {
      "project_id" : var.project_id,
      "location" : var.location,
      "cluster_name" : var.cluster_name,
      "namespace_name" : var.namespace_name,
      "service_name" : var.service_name
    }
    type = var.monitor_type
  }
}

resource "google_monitoring_uptime_check_config" "user_service_uptime_check" {
  display_name = "User Service Uptime Check"
  timeout      = "60s"
  period       = "60s"

  http_check {
    path           = "/user/healthcheck"
    port           = "80"
    request_method = "GET"

    accepted_response_status_codes {
      status_class = "STATUS_CLASS_2XX"
    }
  }

  monitored_resource {
    labels = {
      "project_id" : var.project_id,
      "location" : var.location,
      "cluster_name" : var.cluster_name,
      "namespace_name" : var.namespace_name,
      "service_name" : var.service_name
    }
    type = var.monitor_type
  }
}

resource "google_monitoring_uptime_check_config" "workout_service_uptime_check" {
  display_name = "Workout Service Uptime Check"
  timeout      = "60s"
  period       = "60s"

  http_check {
    path           = "/workout/healthcheck"
    port           = "80"
    request_method = "GET"

    accepted_response_status_codes {
      status_class = "STATUS_CLASS_2XX"
    }
  }

  monitored_resource {
    labels = {
      "project_id" : var.project_id,
      "location" : var.location,
      "cluster_name" : var.cluster_name,
      "namespace_name" : var.namespace_name,
      "service_name" : var.service_name
    }
    type = var.monitor_type
  }
}

resource "google_monitoring_uptime_check_config" "frontend_uptime_check" {
  display_name = "Fronted Uptime Check"
  timeout      = "60s"
  period       = "60s"

  http_check {
    path           = "/"
    port           = "80"
    request_method = "GET"

    accepted_response_status_codes {
      status_class = "STATUS_CLASS_2XX"
    }
  }

  monitored_resource {
    labels = {
      "project_id" : var.project_id,
      "location" : var.location,
      "cluster_name" : var.cluster_name,
      "namespace_name" : var.namespace_name,
      "service_name" : var.service_name
    }
    type = var.monitor_type
  }
}

//TODO connect notification channel to uptime

#https://registry.terraform.io/providers/hashicorp/google/latest/docs/resources/monitoring_slo
resource "google_monitoring_notification_channel" "ln_email" {
  display_name = "Luis Email"
  type         = "email"

  labels = {
    email_address = "involute98@gmail.com"
  }
  project      = var.project_id
  force_delete = true
}

resource "google_monitoring_notification_channel" "dav_email" {
  display_name = "David Email"
  type         = "email"

  labels = {
    email_address = "davwol12@gmail.com"
  }
  project      = var.project_id
  force_delete = true
}

resource "google_monitoring_notification_channel" "flo_email" {
  display_name = "Flo Email"
  type         = "email"

  labels = {
    email_address = "florian.drescher@htwg-konstanz.de"
  }
  project      = var.project_id
  force_delete = true
}

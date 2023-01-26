resource "google_monitoring_uptime_check_config" "gym_service_uptime_check" {
  for_each     = toset(local.urls)
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
      "host" : each.key
    }
    type = local.uptime_url_type
  }

}

resource "google_monitoring_uptime_check_config" "reporting_service_uptime_check" {
  for_each     = toset(local.urls)
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
      "host" : each.key
    }
    type = local.uptime_url_type
  }
}

resource "google_monitoring_uptime_check_config" "user_service_uptime_check" {
  for_each     = toset(local.urls)
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
      "host" : each.key
    }
    type = local.uptime_url_type
  }
}

resource "google_monitoring_uptime_check_config" "workout_service_uptime_check" {
  for_each     = toset(local.urls)
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
      "host" : each.key
    }
    type = local.uptime_url_type
  }
}

resource "google_monitoring_uptime_check_config" "frontend_uptime_check" {
  for_each     = toset(local.urls)
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
      "host" : each.key
    }
    type = local.uptime_url_type
  }
}

resource "google_monitoring_uptime_check_config" "gateway_service_uptime_check" {
  for_each     = toset(local.urls)
  display_name = "Gateway Service Uptime Check"
  timeout      = "60s"
  period       = "60s"

  http_check {
    path           = "/api/healthcheck"
    port           = "8080"
    request_method = "GET"

    accepted_response_status_codes {
      status_class = "STATUS_CLASS_2XX"
    }
  }

  monitored_resource {
    labels = {
      "project_id" : var.project_id,
      "host" : each.key
    }
    type = local.uptime_url_type
  }
}

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

resource "google_monitoring_alert_policy" "alert_policy" {
  display_name          = "Uptime check Policy"
  notification_channels = [
    google_monitoring_notification_channel.ln_email.name, google_monitoring_notification_channel.dav_email.name,
    google_monitoring_notification_channel.flo_email.name
  ]
  combiner = "OR"
  conditions {
    display_name = "Uptime Check URL - Check passed"
    condition_threshold {
      filter          = "resource.type = \"uptime_url\" AND metric.type = \"monitoring.googleapis.com/uptime_check/check_passed\""
      duration        = "300s"
      comparison      = "COMPARISON_LT"
      threshold_value = 1
      aggregations {
        alignment_period     = "300s"
        per_series_aligner   = "ALIGN_COUNT_TRUE"
        cross_series_reducer = "REDUCE_NONE"
      }
      trigger {
        count = 1
      }
    }
  }

  alert_strategy {
    auto_close = "604800s"
  }
}

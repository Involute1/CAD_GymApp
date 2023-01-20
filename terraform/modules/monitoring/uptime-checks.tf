//https://cloud.google.com/monitoring/api/resources#tag_k8s_pod
//https://registry.terraform.io/providers/hashicorp/google/latest/docs/resources/monitoring_uptime_check_config#selected_regions
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
      "pod_name" : var.pod_name
    }
    type = "k8s_pod"

  }

  resource_group {
    resource_type = "INSTANCE"
    //RESOURCE_TYPE_UNSPECIFIED
  }
}

#resource "google_monitoring_uptime_check_config" "reporting_service_uptime_check" {
#  display_name = "Reporting Service Uptime Check"
#  timeout      = "60s"
#
#}
#
#resource "google_monitoring_uptime_check_config" "user_service_uptime_check" {
#  display_name = "User Service Uptime Check"
#  timeout      = "60s"
#
#}
#
#resource "google_monitoring_uptime_check_config" "workout_service_uptime_check" {
#  display_name = "Workout Service Uptime Check"
#  timeout      = "60s"
#
#}
#
#resource "google_monitoring_uptime_check_config" "frontend_uptime_check" {
#  display_name = "Fronted Uptime Check"
#  timeout      = "60s"
#
#}
#https://registry.terraform.io/providers/hashicorp/google/latest/docs/resources/monitoring_slo
resource "google_monitoring_notification_channel" "basic" {
  display_name = "Test Notification Channel"
  type         = "email"
  labels       = {
    email_address = "fake_email@blahblah.com"
  }
  project      = var.project_id
  force_delete = true
}

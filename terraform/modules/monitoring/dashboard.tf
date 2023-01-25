data "local_file" "dashboard_json" {
  filename = "${path.module}/dashboard.json"
}

resource "google_monitoring_dashboard" "dashboard" {
  dashboard_json = data.local_file.dashboard_json.content
  project = var.project_id
}

resource "google_container_cluster" "primary" {
  name     = var.cluster_name
  location = "europe-west3-c"

  # We can't create a cluster with no node pool defined, but we want to only use
  # separately managed node pools. So we create the smallest possible default
  # node pool and immediately delete it.
  remove_default_node_pool = true
  initial_node_count       = 1
  #enable_autopilot         = true
  #ip_allocation_policy {
  #}
  #node_config {
#    service_account = var.service_account_email
#    oauth_scopes    = [
#      "https://www.googleapis.com/auth/cloud-platform",
#      "https://www.googleapis.com/auth/devstorage.read_only",
#      "https://www.googleapis.com/auth/servicecontrol",
#      "https://www.googleapis.com/auth/service.management.readonly",
#      "https://www.googleapis.com/auth/trace.append"
#    ]
#  }

}

resource "google_container_node_pool" "primary_preemptible_nodes" {
  name       = var.node_pool_name
  location   = var.eu_location
  cluster    = google_container_cluster.primary.name
  autoscaling {
    max_node_count = 10
    min_node_count = 1
  }

  node_config {
    preemptible  = true
    machine_type = var.machine_type
    disk_size_gb = 20

    # Google recommends custom service accounts that have cloud-platform scope and permissions granted via IAM Roles.
    #    service_account = google_service_account.default.email
    service_account = var.service_account_email
    oauth_scopes    = [
      "https://www.googleapis.com/auth/cloud-platform",
      "https://www.googleapis.com/auth/devstorage.read_only",
      "https://www.googleapis.com/auth/servicecontrol",
      "https://www.googleapis.com/auth/service.management.readonly",
      "https://www.googleapis.com/auth/trace.append"
    ]
  }
}

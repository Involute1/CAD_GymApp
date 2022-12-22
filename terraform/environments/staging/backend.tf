terraform {
  backend "remote" {
    organization = "HTWG_CAD_GYM"

    workspaces {
      name = "CAD_GymApp_test"
    }
  }
}

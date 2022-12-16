terraform {
  cloud {
    organization = "HTWG_CAD_GYM"
    //TODO place this somewhere else
    token        = "7z0DFubhq7r0gA.atlasv1.1UvaZdFXgW6ksa2qyzNsZ7BrlyEzfAWqAklkN7vNNhMqPh6tnwv5dnWjLskIwTlGXrQ"

    workspaces {
      name = "CAD_GymApp_dev"
    }
  }
}

import { Component } from '@angular/core';
import { GymService } from '../../services/gym.service';
import { UserService } from '../../services/user.service';
import { WorkoutService } from '../../services/workout.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-health-checks',
  templateUrl: './health-checks.component.html',
  styleUrls: ['./health-checks.component.css'],
})
export class HealthChecksComponent {
  serviceHealthChecks: ServiceHealthCheck[] = [];
  displayedColumns: string[] = ['service', 'status'];

  constructor(
    private gymService: GymService,
    private userService: UserService,
    private workoutService: WorkoutService
  ) {
    this.serviceHealthChecks.push(this.gymHealthCheck());
    this.serviceHealthChecks.push(this.userHealthCheck());
    this.serviceHealthChecks.push(this.workoutHealthCheck());
  }

  gymHealthCheck(): ServiceHealthCheck {
    return {
      name: 'Gym-Service',
      status: this.gymService.gymHealthCheck(),
    };
  }

  userHealthCheck(): ServiceHealthCheck {
    return {
      name: 'User-Service',
      status: this.userService.userHealthCheck(),
    };
  }

  workoutHealthCheck(): ServiceHealthCheck {
    return {
      name: 'Workout-Service',
      status: this.workoutService.workoutHealthCheck(),
    };
  }
}

export interface ServiceHealthCheck {
  name: string;
  status: Observable<boolean>;
}

import {Component} from '@angular/core';
import {AuthService} from './services/auth.service';
import {Observable} from 'rxjs';
import {GymService} from './services/gym.service';
import {UserService} from './services/user.service';
import {WorkoutService} from './services/workout.service';

export interface ServiceHealthCheck {
  name: string;
  status: Observable<boolean>;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'cad-frontend';

  serviceHealthChecks: ServiceHealthCheck[] = [];
  displayedColumns: string[] = ['service', 'status'];

  constructor(
    private authService: AuthService,
    private gymService: GymService,
    private userService: UserService,
    private workoutService: WorkoutService
  ) {
    this.serviceHealthChecks.push(this.authHealthCheck());
    this.serviceHealthChecks.push(this.gymHealthCheck());
    this.serviceHealthChecks.push(this.userHealthCheck());
    this.serviceHealthChecks.push(this.workoutHealthCheck());
  }

  authHealthCheck(): ServiceHealthCheck {
    return {
      name: 'Auth-Service',
      status: this.authService.authHealthCheck(),
    };
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

  login(): void {
    this.authService.signIn()
  }
}

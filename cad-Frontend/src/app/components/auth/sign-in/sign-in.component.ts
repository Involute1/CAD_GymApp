import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../shared/services/auth.service';
import { Router } from '@angular/router';
import { map, Observable } from 'rxjs';
import { Gym, GymService } from '../../../shared/services/gym.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss'],
})
export class SignInComponent implements OnInit {
  gyms$: Observable<Gym[]>;

  constructor(
    public authService: AuthService,
    private router: Router,
    private gymService: GymService
  ) {
    let splitHostname = window.location.hostname.split('.');
    let level = splitHostname.at(0);
    this.gyms$ = this.gymService.getGyms().pipe(
      map((gyms) =>
        gyms.filter((gym) => {
          if (window.location.hostname.startsWith('premium')) {
            return gym.billingModel === 'STANDARD';
          } else if (
            splitHostname.length === 2 ||
            level === 'staging' ||
            level === 'dev'
          ) {
            return gym.billingModel === 'FREE';
          } else {
            // @ts-ignore
            return gym.name.toLowerCase() === level.replaceAll('-', '');
          }
        })
      )
    );
  }

  ngOnInit() {
    if (this.authService.isLoggedIn) {
      this.router.navigate(['/dashboard']);
    }
  }
}

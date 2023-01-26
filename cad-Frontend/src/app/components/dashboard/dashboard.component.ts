import { Component } from '@angular/core';
import { AuthService } from '../../shared/services/auth.service';
import { ActivatedRoute, Router, UrlSegment } from '@angular/router';
import { Gym, GymService } from '../../shared/services/gym.service';
import { Observable, tap } from 'rxjs';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent {
  isGymOwner: boolean;
  isUserOrTrainer: boolean;
  thumbnail: any;
  gym$: Observable<Gym>;
  imageUrl: string | undefined;

  constructor(
    public authService: AuthService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private gymService: GymService
  ) {
    this.isGymOwner = this.authService.hasRole(['GymOwner']);
    this.isUserOrTrainer = this.authService.hasRole(['User', 'Trainer']);
    this.activatedRoute.url.subscribe((value) => {
      this.routeToUser(value[value.length - 1]);
    });
    this.gym$ = this.gymService.getGym(this.authService.userData.tenantId).pipe(
      tap((gym) => {
        if (gym.billingModel === 'ENTERPRISE') {
          this.imageUrl = `${window.location.protocol}//${window.location.hostname}${environment.gymPort}/gym/${this.authService.userData.tenantId}/logo`;
        }
      })
    );
  }

  private routeToUser(url: UrlSegment) {
    if (!this.authService.isLoggedIn) {
      this.router.navigate(['/sign-in']);
    } else {
      if (url.path === 'dashboard') {
        this.router.navigate(['dashboard/user']);
      }
    }
  }
}

import { Component } from '@angular/core';
import { AuthService } from '../../shared/services/auth.service';
import { ActivatedRoute, Router, UrlSegment } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent {
  isGymOwner: boolean;
  isUserOrTrainer: boolean;

  constructor(
    public authService: AuthService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.isGymOwner = this.authService.hasRole(['GymOwner']);
    this.isUserOrTrainer = this.authService.hasRole(['User', 'Trainer']);
    this.activatedRoute.url.subscribe((value) => {
      this.routeToUser(value[value.length - 1]);
    });
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

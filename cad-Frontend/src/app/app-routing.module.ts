import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignInComponent } from './components/auth/sign-in/sign-in.component';
import { SignUpComponent } from './components/auth/sign-up/sign-up.component';
import { ForgotPasswordComponent } from './components/auth/forgot-password/forgot-password.component';
import { VerifyEmailComponent } from './components/auth/verify-email/verify-email.component';

// route guard
import { AuthGuard } from './shared/guard/auth.guard';
import { HomeComponent } from './components/dashboard/components/home/home.component';
import { UserProfileComponent } from './components/dashboard/components/user-profile/user-profile.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CaptureWorkoutComponent } from './components/dashboard/components/capture-workout/capture-workout.component';
import { WorkoutsComponent } from './components/dashboard/components/workouts/workouts.component';
import { WorkoutPlanComponent } from './components/dashboard/components/workout-plan/workout-plan.component';
import { RoleGuard } from './shared/guard/role.guard';
import { UsersComponent } from './components/dashboard/components/users/users.component';

const routes: Routes = [
  { path: '', redirectTo: '/sign-in', pathMatch: 'full' },
  { path: 'sign-in', component: SignInComponent },
  { path: 'register-user', component: SignUpComponent },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: 'home',
        component: HomeComponent,
        canActivate: [RoleGuard],
        data: { roles: ['User', 'Trainer'] },
      },
      {
        path: 'user',
        component: UserProfileComponent,
        canActivate: [AuthGuard],
      },
      {
        path: 'capture-workout',
        component: CaptureWorkoutComponent,
        canActivate: [RoleGuard],
        data: { roles: ['User', 'Trainer'] },
      },
      {
        path: 'workouts',
        component: WorkoutsComponent,
        canActivate: [RoleGuard],
        data: { roles: ['User', 'Trainer'] },
      },
      {
        path: 'workout-plan',
        component: WorkoutPlanComponent,
        canActivate: [RoleGuard],
        data: { roles: ['User', 'Trainer'] },
      },
      {
        path: 'users',
        component: UsersComponent,
        canActivate: [RoleGuard],
        data: { roles: ['GymOwner'] },
      },
    ],
  },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'verify-email-address', component: VerifyEmailComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

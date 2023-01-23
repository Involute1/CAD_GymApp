import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AngularFireModule } from '@angular/fire/compat';
import { AngularFireAuthModule } from '@angular/fire/compat/auth';
import { environment } from '../environments/environment';

import { DashboardComponent } from './components/dashboard/dashboard.component';
import { SignInComponent } from './components/auth/sign-in/sign-in.component';
import { SignUpComponent } from './components/auth/sign-up/sign-up.component';
import { ForgotPasswordComponent } from './components/auth/forgot-password/forgot-password.component';
import { VerifyEmailComponent } from './components/auth/verify-email/verify-email.component';
import { AppRoutingModule } from './app-routing.module';
import { AuthService } from './shared/services/auth.service';
import { HealthChecksComponent } from './components/dashboard/components/health-checks/health-checks.component';
import { MaterialModule } from './material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './components/dashboard/components/home/home.component';
import { UserProfileComponent } from './components/dashboard/components/user-profile/user-profile.component';
import { CaptureWorkoutComponent } from './components/dashboard/components/capture-workout/capture-workout.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TableModule } from 'primeng/table';
import { DropdownModule } from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { RippleModule } from 'primeng/ripple';
import { MessageModule } from 'primeng/message';
import { MessagesModule } from 'primeng/messages';
import { MessageService } from 'primeng/api';
import { WorkoutsComponent } from './components/dashboard/components/workouts/workouts.component';
import { WorkoutPlanComponent } from './components/dashboard/components/workout-plan/workout-plan.component';
import { firebase, firebaseui, FirebaseUIModule } from 'firebaseui-angular';
import { AngularFirestoreModule } from '@angular/fire/compat/firestore';
import { AngularFireDatabaseModule } from '@angular/fire/compat/database';
import { AngularFireStorageModule } from '@angular/fire/compat/storage';

const firebaseUiAuthConfig: firebaseui.auth.Config = {
  signInFlow: 'popup',
  signInOptions: [
    firebase.auth.GoogleAuthProvider.PROVIDER_ID,
    {
      scopes: ['public_profile', 'email', 'user_likes', 'user_friends'],
      customParameters: {
        auth_type: 'reauthenticate',
      },
      provider: firebase.auth.FacebookAuthProvider.PROVIDER_ID,
    },
    firebase.auth.TwitterAuthProvider.PROVIDER_ID,
    firebase.auth.GithubAuthProvider.PROVIDER_ID,
    {
      requireDisplayName: false,
      provider: firebase.auth.EmailAuthProvider.PROVIDER_ID,
    },
    firebase.auth.PhoneAuthProvider.PROVIDER_ID,
    firebaseui.auth.AnonymousAuthProvider.PROVIDER_ID,
  ],
  tosUrl: '<your-tos-link>',
  privacyPolicyUrl: '<your-privacyPolicyUrl-link>',
  credentialHelper: firebaseui.auth.CredentialHelper.GOOGLE_YOLO,
};

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    SignInComponent,
    SignUpComponent,
    ForgotPasswordComponent,
    VerifyEmailComponent,
    HealthChecksComponent,
    HomeComponent,
    UserProfileComponent,
    CaptureWorkoutComponent,
    WorkoutsComponent,
    WorkoutPlanComponent,
  ],
  imports: [
    BrowserModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFireAuthModule,
    AngularFirestoreModule,
    AngularFireStorageModule,
    AngularFireDatabaseModule,
    FirebaseUIModule.forRoot(firebaseUiAuthConfig),
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule,
    ReactiveFormsModule,
    TableModule,
    FormsModule,
    DropdownModule,
    ButtonModule,
    InputTextModule,
    RippleModule,
    MessageModule,
    MessagesModule,
  ],
  providers: [AuthService, MessageService],
  bootstrap: [AppComponent],
})
export class AppModule {}

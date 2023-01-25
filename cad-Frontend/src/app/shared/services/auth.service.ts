import { Injectable, NgZone } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { AngularFirestore } from '@angular/fire/compat/firestore';
import { Router } from '@angular/router';
import { UserService } from './user.service';
import { switchMap, take } from 'rxjs';
import firebase from 'firebase/compat/app';
import 'firebase/compat/auth';
import 'firebase/compat/firestore';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private currentUserData: any;

  constructor(
    public afs: AngularFirestore,
    public afAuth: AngularFireAuth,
    public router: Router,
    public ngZone: NgZone,
    private userService: UserService,
    private snackBar: MatSnackBar
  ) {
    this.currentUserData = JSON.parse(localStorage.getItem('user')!);
    /* Saving user data in localstorage when
    logged in and setting up null when logged out */
    this.afAuth.authState.subscribe((user) => {
      if (user) {
        this.currentUserData = user;
        this.userService
          .getUser(this.currentUserData.uid, this.currentUserData.tenantId)
          .pipe(take(1))
          .subscribe((user) => {
            this.currentUserData = user;
            localStorage.setItem('user', JSON.stringify(this.currentUserData));
            JSON.parse(localStorage.getItem('user')!);
            this.router.navigate(['dashboard']);
          });
        localStorage.setItem('user', JSON.stringify(this.currentUserData));
        JSON.parse(localStorage.getItem('user')!);
      } else {
        localStorage.setItem('user', 'null');
        JSON.parse(localStorage.getItem('user')!);
      }
    });
  }

  get userData() {
    if (!this.currentUserData) {
      this.router.navigate(['sign-in']);
    }
    return this.currentUserData;
  }

  // Sign in with email/password
  signIn(email: string, password: string, tenantId: string) {
    firebase.auth().tenantId = tenantId;
    return this.afAuth
      .signInWithEmailAndPassword(email, password)
      .catch((error) => {
        window.alert(error.message);
      });
  }

  // Sign up with email/password
  signUpNewTenant(user: SignUpUser, tenant: SignUpTenant) {
    this.userService
      .createTenant(tenant)
      .pipe(
        switchMap((tenant) => {
          user.tenantId = tenant.tenantId;
          return this.userService.createUser(user);
        }),
        take(1)
      )
      .subscribe({
        next: () => this.createdNewUser(),
        error: (error) => window.alert(error.message),
      });
  }

  signUp(user: SignUpUser) {
    this.userService
      .createUser(user)
      .pipe(take(1))
      .subscribe({
        next: () => this.createdNewUser(),
        error: (error) => window.alert(error.message),
      });
  }

  createdNewUser() {
    this.snackBar.open('Created new Account! Please Sign In!', 'Dismiss', {
      duration: 3000,
    });
    this.router.navigate(['sign-in']);
  }

  // Reset Forggot password
  forgotPassword(passwordResetEmail: string) {
    return this.afAuth
      .sendPasswordResetEmail(passwordResetEmail)
      .then(() => {
        window.alert('Password reset email sent, check your inbox.');
      })
      .catch((error) => {
        window.alert(error);
      });
  }

  // Returns true when user is logged in and email is verified
  get isLoggedIn(): boolean {
    const user = JSON.parse(localStorage.getItem('user')!);
    return user !== null && user.emailVerified !== false;
  }

  // Sign out
  signOut() {
    return this.afAuth.signOut().then(() => {
      localStorage.removeItem('user');
      this.router.navigate(['sign-in']);
    });
  }

  hasRole(roles: string[]) {
    return roles.some((role) => this.userData.role === role);
  }
}

export interface User {
  uid: string;
  email: string;
  displayName: string;
  isEmailVerified: boolean;
  role?: Roles;
}

export interface SignUpUser {
  email: string;
  password: string;
  role: Roles;
  displayName: string;
  tenantId?: string;
  gymName?: string;
  billingModel?: string;
}

export interface SignUpTenant {
  displayName: string;
  billingModel: string;
}

export enum Roles {
  USER = 'User',
  TRAINER = 'Trainer',
  GYMOWNER = 'GymOwner',
  ADMIN = 'Admin',
}

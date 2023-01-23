import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SignUpUser, User } from './auth.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  userUrl =
    `${window.location.protocol}//${window.location.hostname}:7082` + '/user';

  constructor(private http: HttpClient) {}

  userHealthCheck(): Observable<boolean> {
    return this.http.get<boolean>(this.userUrl + '/healthcheck');
  }

  createUser(user: SignUpUser): Observable<User> {
    return this.http.post<User>(`${this.userUrl}/user`, user);
  }

  getUser(userId: string): Observable<User> {
    return this.http.get<User>(`${this.userUrl}/user/${userId}`);
  }
}

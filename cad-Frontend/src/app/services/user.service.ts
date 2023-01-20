import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  userUrl =
    `${window.location.protocol}//${window.location.hostname}:80` + '/user';

  constructor(private http: HttpClient) {}

  userHealthCheck(): Observable<boolean> {
    return this.http.get<boolean>(this.userUrl + '/healthcheck');
  }
}

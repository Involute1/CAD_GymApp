import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {FirebaseApp, initializeApp} from "firebase/app";
import {getAuth, signInWithEmailAndPassword} from "firebase/auth";

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private authUrl = environment.authAPI;
  private firebaseApp?: FirebaseApp;

  constructor(private http: HttpClient) {
  }

  authHealthCheck(): Observable<boolean> {
    return this.http.get<boolean>(this.authUrl + '/healthcheck');
  }

  initFirebaseApp(): void {
    const config = {
      apiKey: "AIzaSyCJ3eFjVDUFAxMYaAThsfnmxwM1fJRMcrY",
      authDomain: "cad-project-368216.firebaseapp.com",
    };

    this.firebaseApp = initializeApp(config);
  }

  authorizeUser(): void {
    console.log("pressed")
    const auth = getAuth(this.firebaseApp);
    const email: string = 'test@test.de';
    const pw: string = '123456';

    signInWithEmailAndPassword(auth, email, pw)
      .catch((error) => {
        console.log(error.message)
      }).then((cred) => console.log(cred))
  }
}

import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {getAuth, signInWithEmailAndPassword} from 'firebase/auth';
import {initializeApp} from 'firebase/app';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private authUrl = environment.authAPI;

  private firebaseConfig = {
    apiKey: environment.firebaseApiKey,
    authDomain: "cad-gym-app.firebaseapp.com"
  };
  private app;

  constructor(private http: HttpClient) {
    this.app = initializeApp(this.firebaseConfig);
  }

  signIn(): void {
    console.log("login called...")
    console.log(this.firebaseConfig.apiKey)
    const auth = getAuth(this.app);
    const email = "test@test.de";
    const password = "123456";
    signInWithEmailAndPassword(auth, email, password)
      .catch((error) => console.log(error))
      .then((userCredential) => {
        if (userCredential != null) {
          console.log(userCredential);
          console.log(userCredential.user.getIdToken());
        }
      })
  }

  authHealthCheck(): Observable<boolean> {
    return this.http.get<boolean>(this.authUrl + '/healthcheck');
  }

}

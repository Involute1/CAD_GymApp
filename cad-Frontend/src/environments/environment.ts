// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

import apiKey from './apiKey.json';

export const environment = {
  production: false,
  authAPI: 'http://localhost:80/auth',
  gymAPI: 'http://localhost:7081/gym',
  userAPI: 'http://localhost:7082/user',
  workoutAPI: 'http://localhost:7083/workout',
  firebase: {
    apiKey: apiKey.firebaseApiKey,
    authDomain: 'cad-gym-app.firebaseapp.com',
    projectId: 'cad-gym-app',
  },
};

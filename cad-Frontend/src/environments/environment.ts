// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

import apiKey from './apiKey.json';

export const environment = {
  production: false,
  gymPort: ':8080',
  userPort: ':8080',
  workoutPort: ':8080',
  reportingPort: ':8080',
  firebase: {
    apiKey: apiKey.firebaseApiKey,
    authDomain: 'cad-gym-app.firebaseapp.com',
    projectId: 'cad-gym-app',
  },
};

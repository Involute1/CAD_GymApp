import apiKey from './apiKey.json';

export const environment = {
  production: true,
  gymPort: ':443',
  userPort: ':443',
  workoutPort: ':443',
  firebase: {
    apiKey: apiKey.firebaseApiKey,
    authDomain: 'cad-gym-app.firebaseapp.com',
    projectId: 'cad-gym-app',
  },
};

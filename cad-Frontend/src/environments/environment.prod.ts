import apiKey from './apiKey.json';

export const environment = {
  production: true,
  authAPI: 'http://localhost:80/auth',
  gymAPI: 'http://localhost:80/gym',
  userAPI: 'http://localhost:80/user',
  workoutAPI: 'http://localhost:80/workout',
  firebaseApiKey: apiKey.firebaseApiKey
};

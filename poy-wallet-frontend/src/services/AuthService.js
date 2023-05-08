import axios from './axios';

const login = (body) => {
  const url = '/auth/sign-in';
  return axios.post(url, body).then((response) => {
    localStorage.setItem('user', JSON.stringify(response.data.data));
    return response.data;
  });
};

const signup = (body) => {
  const url = '/auth/sign-up';
  return axios.post(url, body).then((response) => response.data);
};

const logout = () => {
  localStorage.removeItem('user');
};

const getCurrentUser = () => JSON.parse(localStorage.getItem('user'));

const AuthService = {
  login,
  signup,
  logout,
  getCurrentUser,
};

export default AuthService;

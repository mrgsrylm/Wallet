import axios from './axios';

const postWithoutAuth = (url, body) => {
  const request = axios.post(url, body);
  return request.then((response) => response.data);
};

const HttpService = {
  postWithoutAuth,
};

export default HttpService;

import store from '@/store/index.js'

export const getToken = () => {
  console.log('getToken', store.get('user'));

  return store.get('user')?.token || null;
};

export const clearToken = () => {
  store.remove('user');
};

export const checkLogin = () => {
  return !!store.get('user');
};

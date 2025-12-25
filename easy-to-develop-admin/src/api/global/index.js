import { generateCommonApis, buildPath, handleRequest } from '@/api/index.js';
import ApiClient from '@/api/index.js';
import common from '@/util/common';

export const globalApi = {
  ...(function () {
    const prefix = "/globalApi";
    const resources = [
      'users',
      'roles',
      'drinks',
      'categories',
      'add-ins',
      'coupons',
      'specs',
      'banners',
      'members',
      'member-cards',
      'orders',
      'locations',
      'stores',
      'user-coupons',
      'store-goods',
      'specs'
    ]

    // 动态注册 api 方法
    const apis = generateCommonApis(prefix, resources);

    // 特殊 api 方法
    apis['auth'] =  {
      login: async function (loginData = {}, success = null, failure = null){
        const url = buildPath(prefix, ['auth'], 'post', {}, 'login');
        return handleRequest(ApiClient.post(url, common.trimObj(loginData)), success, failure);
      },
      register: async function (registerData = {}, success = null, failure = null){
        const url = buildPath(prefix, ['auth'], 'post', {}, 'register');
        return handleRequest(ApiClient.post(url, common.trimObj(registerData)), success, failure);
      },
      logout: async function (logoutData = {}, success = null, failure = null){
        const url = buildPath(prefix, ['auth'], 'post', {}, 'logout');
        return handleRequest(ApiClient.post(url, common.trimObj(logoutData)), success, failure);
      },
    }
    apis['files'] = {
      getUploadUrl: async function (fileData = {}, success = null, failure = null){
        const url = buildPath(prefix, ['files'], 'post', {}, 'getUploadUrl');
        return handleRequest(ApiClient.post(url, fileData), success, failure);
      },
      callback: async function (callbackData = {}, success = null, failure = null){
        const url = buildPath(prefix, ['files'], 'post', {}, 'callback');
        return handleRequest(ApiClient.post(url, callbackData), success, failure);
      },
    }

    return apis;

  })()
}

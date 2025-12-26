import { generateCommonApis, buildPath, handleRequest } from '@/api/index.js';
import ApiClient from '@/api/index.js';
import common from '@/util/common';

export const adminApi = {
  ...(function () {
    const prefix = "/adminApi";
    const resources = [
      'menus',
    ]

    // 动态注册 api 方法
    const apis = generateCommonApis(prefix, resources);

    // 特殊 api
    apis['stats'] = {
      get: async function (statsData = {}, success = null, failure = null){
        const url = buildPath(prefix, ['stats'], 'get', {}, '');
        return handleRequest(ApiClient.get(url, { params: common.trimObj(statsData) }), success, failure);
      },
    }

    return apis;

  })()
}

import { generateCommonApis } from '@/api/index.js';

export const adminApi = {
  ...(function () {
    const prefix = "/adminApi";
    const resources = [
      'menus',
    ]

    // 动态注册 api 方法
    const apis = generateCommonApis(prefix, resources);

    // 特殊 api

    return apis;

  })()
}

import { createRouter, createWebHistory } from 'vue-router'
import admin from './admin'
import { ElNotification } from 'element-plus'
import { checkLogin } from '@/util/token'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    ...admin.routes,
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/auth/LoginView.vue'),
      meta: { hideLayout: true }
    },
    {
      path: '/register',
      name: 'Register',
      component: () => import('@/views/auth/RegisterView.vue'),
      meta: { hideLayout: true }
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'NotFound',
      component: () => import('@/views/error/404View.vue'),
      meta: { hideLayout: true }
    },
    {
      path: '/',
      redirect: '/admin/dashboard'
    },
  ]
})

//路由拦截白名单--迁移到对应路由下的whiteList中，方便管理
router.beforeEach(async (to, from, next) => {
  //获取具体对象
  let checkObj = null;
  if (to.path.startsWith(admin.rootPath)) {
    checkObj = admin;
  }else {
    next();
    return;//其他路由直接放行--404页面
  }

  //检查白名单
  for (let i = 0; i < checkObj.whiteList.length; i++) {
    if (to.path.match(new RegExp(checkObj.whiteList[i]))) {
      next();
      return;
    }
  }

  //检查token
  if (!checkLogin()) {
    ElNotification.warning("未登录，请先登录！");
    next({ name: 'Login' });
    return;
  }

  if (checkObj === admin) {
    const r = await admin.dynamicRoute(router, to);
    if (r) {
      next(r);
      return;
    }
  }
  next();
})

export default router

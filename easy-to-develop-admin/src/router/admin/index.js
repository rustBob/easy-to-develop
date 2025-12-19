const rootPath = import.meta.env.VITE_ROOT_PATH_ADMIN;

import { adminApi } from '@/api/admin/index.js'
import store from '@/store/index.js'
import { checkRole } from '@/util/perssion.js'

const viewComponents = import.meta.glob('@/views/**/*.vue', { eager: true  });

const componentPathMaps = {};
Object.entries(viewComponents).forEach(([path, viewComponent]) => {
  componentPathMaps[path.replace("/src/views", "")] = viewComponent.default;
});

export default{
  rootPath: rootPath,
  whiteList:[].map(item => rootPath + item),
  routes: [],

  dynamicRoute: async (router, to) => {
    let hasChange = false;
    let adminMenuCached = store.get("menus");
    if (!adminMenuCached) {
      hasChange = true;
      await adminApi["menus"].get(null, async data => {
        store.set("menus", data);
        adminMenuCached = data;
      });
    }

    // 角色权限过滤
    const permissionChecks = await Promise.all(adminMenuCached.map(item => item.roleId == 0 || checkRole(item.role.key)));
    adminMenuCached = adminMenuCached.filter((_, index) => permissionChecks[index]);

    if (!adminMenuCached) {
      return false;
    }
    let map = {}, roots = [];
    adminMenuCached.forEach(async item => {
      map[item.id] = {
        path: item.path,
        name: item.name,
        meta: {
          icon: item.icon,
          visible: !!item.visible
        },
        component: componentPathMaps[item.component],
        children: [],
      };
    });
    adminMenuCached.forEach(item => {
      let route = map[item.id];
      if (!map[item.parentId]) {
        route.meta.isRoot = true;
        roots.push(route);
      } else {
        map[item.parentId].children.push(route);
      }
    });
    let adminRoutes = {
      path: rootPath,
      name: "Admin",
      component: () => import('@/views/IndexView.vue'),
      children: [
      ],
    }
    roots.forEach(item => {
      if (!router.hasRoute(item.name)) {
        hasChange = true;
      }
      adminRoutes.children.push(item);
    });
    if (hasChange) {
      adminRoutes.redirect = adminRoutes.path + adminRoutes.children[0].path;
      router.removeRoute(adminRoutes.name);
      router.addRoute(adminRoutes);
      if (to.name === 'NotFound') {
        to.name = null;
      }
      return {...to, replace: true};
    }
    return false;
  }
}

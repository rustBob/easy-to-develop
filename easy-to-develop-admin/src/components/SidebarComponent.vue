<template>
  <div
    :class="[
      'fixed inset-y-0 left-0 z-30 w-64 bg-white border-r border-gray-200 transform transition-transform duration-300 ease-in-out',
      { 'translate-x-0': isOpen, '-translate-x-full': !isOpen },
      { 'lg:translate-x-0 lg:static lg:inset-auto': !isMobile },
    ]"
  >
    <div class="flex items-center justify-between h-16 px-6 border-b border-gray-200">
      <div class="flex items-center gap-2">
        <div class="w-8 h-8 bg-blue-500 rounded-lg flex items-center justify-center text-white font-bold text-lg">
          MS
        </div>
        <span class="text-xl font-bold text-gray-800">管理系统</span>
      </div>
      <button v-if="isMobile" @click="emit('toggle-sidebar')" class="p-1 text-gray-500 hover:text-gray-700">
        <el-icon :size="24"><Close /></el-icon>
      </button>
    </div>

    <el-menu
      :default-active="route.path"
      :router="true"
      @select="handleMenuSelect"
      class="border-r-0"
    >
      <component
        v-for="item in menus"
        :key="item.path"
        :is="SidebarItem"
        :item="item"
      />
    </el-menu>

    <div class="absolute bottom-0 w-full p-4 border-t border-gray-200">
      <button class="flex items-center gap-3 px-4 py-3 w-full text-left text-red-500 hover:bg-red-50 rounded-lg transition-colors"
        @click="handleLogout">
        <el-icon :size="20"><SwitchButton /></el-icon>
        <span class="text-lg font-medium">退出登录</span>
      </button>
    </div>
  </div>
  <div
    v-if="isMobile && isOpen"
    class="fixed inset-0 z-20 bg-black/50 transition-opacity"
    @click="emit('toggle-sidebar')"
  ></div>
</template>

<script setup>
import router from '@/router/index.js';
import { onMounted, ref, h, resolveComponent } from 'vue';
import { useRoute } from 'vue-router';
import { clearToken } from '@/util/token.js';
import { globalApi } from '@/api/global';
import store from '@/store';
import {
  SwitchButton,
  Close
} from '@element-plus/icons-vue';
import { ElMenu, ElSubMenu, ElMenuItem, ElIcon } from 'element-plus';

const { isMobile } = defineProps(['isOpen', 'isMobile']);
const emit = defineEmits(['toggle-sidebar']);

const route = useRoute();

const handleMenuSelect = () => {
  if (isMobile) {
    emit('toggle-sidebar');
  }
};

const handleLogout = () => {
  globalApi.auth.logout({ id: store.get('user').id }, () => {
    store.remove('store');
    clearToken();
  }, (error) => {
    console.log(error);
  });
  router.push('/login');
};

let menus = ref([]);

onMounted(() => {
  menus.value = router.getRoutes().filter( route => route.path.startsWith('/admin') && route.meta.isRoot && route.meta.visible);
});

const SidebarItem = {
  name: 'SidebarItem',
  props: {
    item: { type: Object, required: true }
  },
  setup(props) {
    const icon = props.item.meta?.icon
      ? h(ElIcon, { size: 20 }, () => h(resolveComponent(props.item.meta.icon)))
      : null;

    if (props.item.children && props.item.children.length > 0) {
      return () => h(ElSubMenu, { index: props.item.path }, {
        title: () => [ icon, h('span', { class: 'ml-2' }, props.item.name) ],
        default: () => props.item.children.map(child => {
          const newChild = {
            ...child,
            path: `${props.item.path}/${child.path}`
          };
          return h(SidebarItem, { item: newChild })
        })
      });
    }

    return () => h(ElMenuItem, { index: props.item.path }, {
      default: () => [ icon, h('span', { class: 'ml-2' }, props.item.name) ]
    });
  }
};
</script>

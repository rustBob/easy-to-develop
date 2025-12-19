<template>
  <header class="bg-white h-16 border-b border-gray-200 flex items-center justify-between px-4 lg:px-8 sticky top-0 z-10">
    <div class="flex items-center gap-4">
      <button
        @click="emit('toggle-sidebar')"
        class="p-2 rounded-md text-gray-500 hover:bg-gray-100 lg:hidden"
      >
        <el-icon :size="24"><Menu /></el-icon>
      </button>

      <!-- <div class="hidden md:flex items-center relative">
        <el-icon class="absolute left-8 text-gray-400" :size="18"><Search /></el-icon>
        <input
          type="text"
          placeholder="搜索..."
          class="pl-10 pr-4 py-2 border border-gray-200 rounded-full w-64 focus:outline-none focus:border-primary focus:ring-1 focus:ring-primary transition-all text-sm"
        />
      </div> -->
    </div>

    <div class="flex items-center gap-4">
      <button class="relative p-2 text-gray-500 hover:bg-gray-100 rounded-full transition-colors">
        <el-icon :size="20"><Bell /></el-icon>
        <span class="absolute top-1.5 right-1.5 w-2 h-2 bg-red-500 rounded-full border-2 border-white"></span>
      </button>

      <div class="flex items-center gap-3 pl-4 border-l border-gray-200">
        <div class="text-right hidden sm:block">
          <p class="text-sm font-medium text-gray-900">{{ user?.username || '用户' }}</p>
          <p class="text-xs text-gray-500">Administrator</p>
        </div>
        <div class="w-10 h-10 rounded-full bg-gray-200 flex items-center justify-center overflow-hidden border border-gray-300">
           <img src="https://picsum.photos/200/200" alt="User" class="w-full h-full object-cover" />
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>

import store from '@/store';
import { Bell, Menu } from '@element-plus/icons-vue';
import { ref, onMounted } from 'vue';

const emit = defineEmits(['toggle-sidebar']);

const user = ref({})

onMounted(() => {
  user.value = store.get('user')
})
</script>

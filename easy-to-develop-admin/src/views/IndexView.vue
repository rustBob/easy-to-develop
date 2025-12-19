<template>
  <div v-if="showLayout" class="flex h-screen bg-gray-100">
    <Sidebar :is-open="isSidebarOpen" :is-mobile="isMobile" @toggle-sidebar="toggleSidebar" />
    <div class="flex-1 flex flex-col overflow-hidden">
      <Header @toggle-sidebar="toggleSidebar" />
      <main class="flex-1 overflow-x-hidden overflow-y-auto bg-gray-100 p-4 lg:p-8">
        <router-view />
      </main>
    </div>
  </div>
  <router-view v-else />
</template>

<script setup>
import { ref, onMounted, onUnmounted, computed } from 'vue';
import { useRoute } from 'vue-router';
import Header from '@/components/HeaderComponent.vue';
import Sidebar from '@/components/SidebarComponent.vue';

const route = useRoute();
const showLayout = computed(() => !route.meta.hideLayout);

const screenWidth = ref(window.innerWidth);
const isSidebarOpen = ref(screenWidth.value > 1024);
const isMobile = computed(() => screenWidth.value <= 1024);

const toggleSidebar = () => {
  isSidebarOpen.value = !isSidebarOpen.value;
};

const handleResize = () => {
  screenWidth.value = window.innerWidth;
  if (screenWidth.value > 1024) {
    isSidebarOpen.value = true;
  } else {
    isSidebarOpen.value = false;
  }
};

onMounted(() => {
  window.addEventListener('resize', handleResize);
});

onUnmounted(() => {
  window.removeEventListener('resize', handleResize);
});
</script>

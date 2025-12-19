<template>
  <div class="min-h-screen bg-gray-50 flex flex-col justify-center items-center px-4 sm:px-6 lg:px-8">
    <div class="text-center">
      <h1 class="text-9xl font-bold text-gray-900 tracking-tighter">404</h1>
      <h2 class="mt-4 text-3xl font-extrabold text-gray-900 tracking-tight sm:text-4xl">
        页面未找到
      </h2>
      <p class="mt-4 text-lg text-gray-500">
        抱歉，您访问的页面不存在或已被移动。
      </p>
      
      <div class="mt-8 flex justify-center gap-4">
        <el-button type="primary" size="large" @click="goHome">
          返回首页
        </el-button>
        <el-button size="large" @click="goBack">
          返回上一页
        </el-button>
      </div>
      
      <div class="mt-10 max-w-md mx-auto">
        <el-input
          v-model="searchQuery"
          placeholder="搜索页面..."
          prefix-icon="Search"
          size="large"
          @keyup.enter="handleSearch"
        >
          <template #append>
            <el-button :icon="Search" @click="handleSearch" />
          </template>
        </el-input>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { Search } from '@element-plus/icons-vue';
import { ElNotification } from 'element-plus';

const router = useRouter();
const searchQuery = ref('');

const goHome = () => {
  router.push('/');
};

const goBack = () => {
  router.go(-1);
};

const handleSearch = () => {
  if (searchQuery.value.trim()) {
    ElNotification({
      title: '搜索功能',
      message: `正在搜索: ${searchQuery.value} (功能开发中)`,
      type: 'info',
    });
  }
};
</script>

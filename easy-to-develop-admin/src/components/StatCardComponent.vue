<template>
  <div class="bg-white p-6 rounded-xl border border-gray-100 shadow-sm hover:shadow-md transition-shadow">
    <div class="flex items-center justify-between mb-4">
      <div :class="[`p-3 rounded-lg`, color, 'bg-opacity-10']">
        <el-icon :class="color.replace('bg-', 'text-')" :size="24">
          <component :is="icon" />
        </el-icon>
      </div>
      <span :class="['flex items-center text-sm font-medium', trend >= 0 ? 'text-green-500' : 'text-red-500']">
        <el-icon :size="16" class="mr-1">
          <component :is="trend >= 0 ? ArrowUpBold : ArrowDownBold" />
        </el-icon>
        {{ Math.abs(trend) }}%
      </span>
    </div>
    <h3 class="text-gray-500 text-sm font-medium">{{ title }}</h3>
    <p class="text-2xl font-bold text-gray-900 mt-1">{{ value }}</p>
  </div>
</template>

<script setup>
import { ArrowUpBold , ArrowDownBold } from '@element-plus/icons-vue';

defineProps(['title', 'value', 'trend', 'icon', 'color']);
</script>

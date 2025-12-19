<template>
  <div class="max-w-4xl mx-auto space-y-8">
    <div>
      <h1 class="text-2xl font-bold text-gray-900">系统设置</h1>
      <p class="text-gray-500 text-sm mt-1">管理您的账户设置和系统偏好。</p>
    </div>

    <el-form :model="profileForm" label-position="top" class="bg-white rounded-xl border border-gray-200 shadow-sm overflow-hidden">
      <div class="p-6 border-b border-gray-200">
        <h3 class="text-lg font-medium text-gray-900">个人资料信息</h3>
        <p class="text-sm text-gray-500 mt-1">更新您的账户个人资料信息和电子邮件地址。</p>
      </div>
      <div class="p-6 space-y-6">
        <el-form-item label="用户名">
          <el-input v-model="profileForm.username" placeholder="用户名" />
        </el-form-item>
        <el-form-item label="电子邮件地址">
          <el-input v-model="profileForm.email" placeholder="电子邮件地址" />
        </el-form-item>
        <el-form-item label="个人简介">
          <el-input v-model="profileForm.bio" type="textarea" :rows="4" placeholder="个人简介" />
        </el-form-item>
      </div>
      <div class="bg-gray-50 px-6 py-4 flex justify-end">
        <el-button type="primary" :icon="Check">保存更改</el-button>
      </div>
    </el-form>

    <div class="bg-white rounded-xl border border-gray-200 shadow-sm overflow-hidden">
      <div class="p-6 border-b border-gray-200">
        <h3 class="text-lg font-medium text-gray-900">通知</h3>
        <p class="text-sm text-gray-500 mt-1">选择您希望收到的通信方式。</p>
      </div>
      <div class="p-6 space-y-4">
        <el-checkbox v-model="emailNotifications" label="Email Notifications" size="large">
          <div class="flex flex-col">
            <span class="text-sm font-medium text-gray-700">电子邮件通知</span>
            <span class="text-xs text-gray-500">当您不在线时，获取电子邮件以了解最新动态。</span>
          </div>
        </el-checkbox>
        <el-checkbox v-model="pushNotifications" label="Push Notifications" size="large">
          <div class="flex flex-col">
            <span class="text-sm font-medium text-gray-700">系统推送通知</span>
            <span class="text-xs text-gray-500">在您的浏览器中获取推送通知。</span>
          </div>
        </el-checkbox>
      </div>
      <div class="bg-gray-50 px-6 py-4 flex justify-end gap-2">
        <el-button>取消</el-button>
        <el-button type="primary" plain>保存偏好设置</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { ElForm, ElFormItem, ElInput, ElCheckbox, ElButton } from 'element-plus';
import { Check } from '@element-plus/icons-vue';

const profileForm = ref({
  username: 'admin',
  email: 'admin@nova.com',
  bio: '系统管理员',
});

const emailNotifications = ref(true);
const pushNotifications = ref(false);
</script>

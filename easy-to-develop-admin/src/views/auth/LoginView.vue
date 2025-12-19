<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-md w-full space-y-8 bg-white p-8 rounded-xl shadow-lg">
      <div>
        <h2 class="mt-6 text-center text-3xl font-extrabold text-gray-900">
          用户登录
        </h2>
        <p class="mt-2 text-center text-sm text-gray-600">
          欢迎回到 易开发
        </p>
      </div>
      <el-form
        ref="loginFormRef"
        :model="loginForm"
        :rules="loginRules"
        class="mt-8 space-y-6"
        @keyup.enter="handleLogin"
      >
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="用户名"
            prefix-icon="User"
            size="large"
          />
        </el-form-item>

        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="密码"
            prefix-icon="Lock"
            show-password
            size="large"
          />
        </el-form-item>

        <div class="flex items-center justify-between">
          <el-form-item prop="rememberMe" class="mb-0">
            <el-checkbox v-model="loginForm.rememberMe">记住我</el-checkbox>
          </el-form-item>

          <div class="text-sm">
            <a href="#" class="font-medium text-blue-600 hover:text-blue-500" @click.prevent="handleForgotPassword">
              忘记密码?
            </a>
          </div>
        </div>

        <div>
          <el-button
            type="primary"
            :loading="loading"
            class="w-full"
            size="large"
            @click="handleLogin"
          >
            登录
          </el-button>
        </div>

        <div class="text-center text-sm">
          <span class="text-gray-600">还没有账号? </span>
          <router-link to="/register" class="font-medium text-blue-600 hover:text-blue-500">
            立即注册
          </router-link>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { ElNotification } from 'element-plus';
import { globalApi } from '@/api/global';
import store from '@/store';

const router = useRouter();
const loginFormRef = ref(null);
const loading = ref(false);

const loginForm = reactive({
  username: '',
  password: '',
  rememberMe: false
});

const loginRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于 6 位', trigger: 'blur' }
  ]
};

const handleLogin = async () => {
  if (!loginFormRef.value) return;

  await loginFormRef.value.validate((valid) => {
    if (valid) {
      loading.value = true;

      globalApi.auth.login(loginForm, (res) => {
        store.set('user', res, !loginForm.rememberMe);

        ElNotification({
          title: '登录成功',
          message: '欢迎回来, ' + loginForm.username,
          type: 'success',
        });
        router.push('/admin/dashboard');
        loading.value = false;
      }, (error) => {
        loading.value = false;
        ElNotification({
          title: '登录失败',
          message: error.data.message || '登录失败，请稍后重试',
          type: 'error',
        });
      });

    } else {
      ElNotification({
        title: '验证失败',
        message: '请检查输入信息',
        type: 'warning',
      });
      return false;
    }
  });
};

const handleForgotPassword = () => {
  ElNotification({
    title: '提示',
    message: '请联系管理员重置密码',
    type: 'info',
  });
};
</script>

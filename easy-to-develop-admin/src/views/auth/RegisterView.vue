<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
    <div class="max-w-md w-full space-y-8 bg-white p-8 rounded-xl shadow-lg">
      <div>
        <h2 class="mt-6 text-center text-3xl font-extrabold text-gray-900">
          注册账号
        </h2>
        <p class="mt-2 text-center text-sm text-gray-600">
          加入 易开发
        </p>
      </div>
      <el-form
        ref="registerFormRef"
        :model="registerForm"
        :rules="registerRules"
        class="mt-8 space-y-6"
        label-position="top"
      >
        <el-form-item prop="username" label="用户名">
          <el-input
            v-model="registerForm.username"
            placeholder="请输入用户名"
            prefix-icon="User"
          />
        </el-form-item>

        <el-form-item prop="email" label="邮箱">
          <el-input
            v-model="registerForm.email"
            placeholder="请输入邮箱"
            prefix-icon="Message"
          />
        </el-form-item>

        <el-form-item prop="password" label="密码">
          <el-input
            v-model="registerForm.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            show-password
            @input="checkPasswordStrength"
          />
          <!-- 密码强度条 -->
          <div class="mt-2" v-if="registerForm.password">
            <div class="flex justify-between text-xs text-gray-500 mb-1">
              <span>密码强度: {{ strengthText }}</span>
            </div>
            <el-progress
              :percentage="passwordStrength"
              :status="strengthStatus"
              :show-text="false"
              :stroke-width="6"
            />
          </div>
        </el-form-item>

        <el-form-item prop="confirmPassword" label="确认密码">
          <el-input
            v-model="registerForm.confirmPassword"
            type="password"
            placeholder="请再次输入密码"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>

        <el-form-item prop="agreeTerms">
          <el-checkbox v-model="registerForm.agreeTerms">
            我已阅读并同意 <a href="#" class="text-blue-600" @click.prevent>服务条款</a> 和 <a href="#" class="text-blue-600" @click.prevent>隐私政策</a>
          </el-checkbox>
        </el-form-item>

        <div>
          <el-button
            type="primary"
            :loading="loading"
            class="w-full"
            size="large"
            @click="handleRegister"
          >
            立即注册
          </el-button>
        </div>

        <div class="text-center text-sm">
          <span class="text-gray-600">已有账号? </span>
          <router-link to="/login" class="font-medium text-blue-600 hover:text-blue-500">
            直接登录
          </router-link>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue';
import { useRouter } from 'vue-router';
import { ElNotification } from 'element-plus';
import { globalApi } from '@/api/global';

const router = useRouter();
const registerFormRef = ref(null);
const loading = ref(false);
const passwordStrength = ref(0);

const registerForm = reactive({
  username: '',
  email: '',
  password: '',
  confirmPassword: '',
  agreeTerms: false
});

const validatePass = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入密码'));
  } else {
    if (registerForm.confirmPassword !== '') {
      if (registerFormRef.value) {
        registerFormRef.value.validateField('confirmPassword');
      }
    }
    callback();
  }
};

const validatePass2 = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'));
  } else if (value !== registerForm.password) {
    callback(new Error('两次输入密码不一致!'));
  } else {
    callback();
  }
};

const validateTerms = (rule, value, callback) => {
  if (!value) {
    callback(new Error('请阅读并同意服务条款'));
  } else {
    callback();
  }
};

const registerRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' },
    // 模拟异步用户名检查
    {
      validator: (rule, value, callback) => {
        if (value === '用户某某') {
          callback(new Error('该用户名已被使用'));
        } else {
          callback();
        }
      },
      trigger: 'blur'
    }
  ],
  email: [
    { required: true, message: '请输入邮箱地址', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
  ],
  password: [
    { required: true, validator: validatePass, trigger: 'blur' },
    { min: 6, message: '密码长度不能少于 6 位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, validator: validatePass2, trigger: 'blur' }
  ],
  agreeTerms: [
    { validator: validateTerms, trigger: 'change' }
  ]
};

const strengthStatus = computed(() => {
  if (passwordStrength.value < 30) return 'exception';
  if (passwordStrength.value < 70) return 'warning';
  return 'success';
});

const strengthText = computed(() => {
  if (passwordStrength.value < 30) return '弱';
  if (passwordStrength.value < 70) return '中';
  return '强';
});

const checkPasswordStrength = (value) => {
  let score = 0;
  if (!value) {
    passwordStrength.value = 0;
    return;
  }
  if (value.length > 5) score += 20;
  if (value.length > 8) score += 20;
  if (/[A-Z]/.test(value)) score += 20;
  if (/[0-9]/.test(value)) score += 20;
  if (/[^A-Za-z0-9]/.test(value)) score += 20;

  passwordStrength.value = Math.min(100, score);
};

const handleRegister = async () => {
  if (!registerFormRef.value) return;

  await registerFormRef.value.validate((valid) => {
    if (valid) {
      loading.value = true;

      globalApi.auth.register(registerForm, (res) => {
        console.log(res);

        loading.value = false;

        ElNotification({
          title: '注册成功',
          message: '账号创建成功，请登录',
          type: 'success',
        });

        router.push('/login');
      }, (error) => {
        loading.value = false;

        ElNotification({
          title: '注册失败',
          message: error.message || '注册失败，请重试',
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
</script>

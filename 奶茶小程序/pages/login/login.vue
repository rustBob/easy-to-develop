<template>
  <view class="login-container">
    <view class="login-box">
      <text class="title">{{ isRegister ? '注册账号' : '欢迎回来' }}</text>
      
      <view class="form">
        <input class="input" v-model="form.username" placeholder="用户名/手机号" />
        <input v-if="isRegister" class="input" v-model="form.nickname" placeholder="昵称" />
        <input class="input" v-model="form.password" type="password" placeholder="密码" />
        
        <button class="login-btn" @click="handleSubmit">{{ isRegister ? '注册' : '登录' }}</button>
        
        <view class="toggle-box">
            <text class="toggle-text" @click="toggleMode">
                {{ isRegister ? '已有账号？去登录' : '没有账号？去注册' }}
            </text>
        </view>

        <text class="skip-btn" @click="goBack">暂不登录</text>
      </view>
    </view>
  </view>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { useUserStore } from '@/store/user';

const userStore = useUserStore();
const form = reactive({ username: '', password: '', nickname: '' });
const isRegister = ref(false);

const goBack = () => {
  uni.navigateBack();
};

const toggleMode = () => {
    isRegister.value = !isRegister.value;
    form.username = '';
    form.password = '';
    form.nickname = '';
};

const handleSubmit = async () => {
  if (!form.username) {
      uni.showToast({ title: '请输入用户名', icon: 'none' });
      return;
  }
  if (!form.password) {
      uni.showToast({ title: '请输入密码', icon: 'none' });
      return;
  }
  
  if (isRegister.value) {
      if (!form.nickname) {
          uni.showToast({ title: '请输入昵称', icon: 'none' });
          return;
      }
      try {
          await userStore.register(form.username, form.password, form.nickname);
          uni.showToast({ title: '注册成功，请登录' });
          isRegister.value = false;
      } catch (e) {
          uni.showToast({ title: '注册失败', icon: 'none' });
      }
  } else {
      try {
          await userStore.login(form.username, form.password);
          uni.navigateBack();
          uni.showToast({ title: '登录成功' });
      } catch (e) {
          uni.showToast({ title: '登录失败', icon: 'none' });
      }
  }
};
</script>

<style lang="scss" scoped>
.login-container {
  min-height: 100vh;
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40rpx;
}

.login-box {
  width: 100%;
  max-width: 600rpx;
  margin-top: -200rpx; // Shift up slightly
}

.title {
  font-size: 48rpx;
  font-weight: bold;
  text-align: center;
  margin-bottom: 60rpx;
  display: block;
  color: #333;
}

.input {
  background: #f9f9f9;
  border: 1px solid #eee;
  padding: 24rpx;
  border-radius: 12rpx;
  margin-bottom: 20rpx;
  font-size: 28rpx;
  height: 88rpx;
}

.login-btn {
  background: #333;
  color: #fff;
  border-radius: 12rpx;
  padding: 24rpx 0;
  font-weight: bold;
  margin-top: 20rpx;
  font-size: 30rpx;
}

.toggle-box {
    margin-top: 30rpx;
    text-align: center;
    
    .toggle-text {
        font-size: 28rpx;
        color: #ff6600;
    }
}

.skip-btn {
  display: block;
  text-align: center;
  color: #999;
  font-size: 26rpx;
  margin-top: 30rpx;
}
</style>
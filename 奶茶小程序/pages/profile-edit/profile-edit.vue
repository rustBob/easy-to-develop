<template>
  <view class="container">
    <view class="form-card">
      <view class="form-item">
        <text class="label">头像</text>
        <view class="avatar-wrap" @click="changeAvatar">
            <image :src="form.avatar" mode="aspectFill" class="avatar" />
        </view>
      </view>
      
      <view class="form-item">
        <text class="label">用户名</text>
        <input class="input disabled" :value="form.username" disabled />
      </view>

      <view class="form-item">
        <text class="label">昵称</text>
        <input class="input" v-model="form.nickname" placeholder="请输入昵称" />
      </view>

      <view class="form-item">
        <text class="label">手机号</text>
        <input class="input" v-model="form.phone" type="number" maxlength="11" placeholder="请输入手机号" />
      </view>
    </view>
    
    <view class="action-bar">
        <button class="btn cancel" @click="goBack">取消</button>
        <button class="btn save" @click="handleSave" :loading="loading">保存</button>
    </view>
  </view>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useUserStore } from '@/store/user';
import { globalApi } from '@/api/globalApi/index.js';
import { upload } from '@/api/file.js';

const userStore = useUserStore();
const loading = ref(false);

const form = reactive({
    id: '',
    username: '',
    nickname: '',
    phone: '',
    email: '',
    avatar: ''
});

let avatarFile = null;

onMounted(() => {
    if (userStore.userInfo) {
        form.id = userStore.userInfo.id;
        form.username = userStore.userInfo.username || userStore.userInfo.nickname; // Fallback
        form.nickname = userStore.userInfo.nickname;
        form.phone = userStore.userInfo.phone || '';
        form.email = userStore.userInfo.email || '';
        form.avatar = userStore.userInfo.avatar;
    }
});

const changeAvatar = () => {
    uni.chooseImage({
        count: 1,
        sizeType: ['original', 'compressed'],
        sourceType: ['album', 'camera'],
        success: (res) => {
            form.avatar = res.tempFilePaths[0];
            avatarFile = {
                ...res.tempFiles[0],
                name: Date.now() + '-avatar'
            };
        },
        fail: (err) => {
            console.error('选择图片失败：', err);
            uni.showToast({
                title: '选择图片失败或取消了选择',
                icon: 'none'
            });
        }
    });
};

const validate = () => {
    if (!form.nickname.trim()) {
        uni.showToast({ title: '请输入昵称', icon: 'none' });
        return false;
    }
    if (form.phone && !/^1[3-9]\d{9}$/.test(form.phone)) {
        uni.showToast({ title: '手机号格式不正确', icon: 'none' });
        return false;
    }
    return true;
};

const handleSave = async () => {
    if (!validate()) return;

    uni.showModal({
        title: '确认修改',
        content: '确定继续吗？',
        success: async (res) => {
            if (res.confirm) {
                if (avatarFile) {
                    uni.showLoading({ title: '上传图片中...', mask: true })
                    const res = await upload([avatarFile]);
                    
                    if (res.code !== 200) {
                        uni.hideLoading();
                        uni.showToast({
                            title: '文件上传失败',
                            icon: 'none'
                        });
                        return;
                    }
                    form.avatar = res.data[0];
                    uni.hideLoading()
                }
                submitData();
            }
        }
    });
};

const submitData = async () => {
    loading.value = true;
    try {
        const updateData = {
            id: form.id,
            nickname: form.nickname,
            phone: form.phone,
            avatar: form.avatar
        };
        
        // Call API
        await globalApi['users'].update(null, updateData, (res) => {
             userStore.updateUserInfo(updateData);
             uni.showToast({ title: '保存成功' });
             setTimeout(() => {
                 uni.navigateBack();
             }, 1500);
        }, (err) => {
             uni.showToast({ title: err.msg || '保存失败', icon: 'none' });
        });
        
    } catch (e) {
        uni.showToast({ title: '保存失败', icon: 'none' });
    } finally {
        loading.value = false;
    }
};

const goBack = () => {
    uni.navigateBack();
};
</script>

<style lang="scss" scoped>
.container {
  min-height: 100vh;
  background-color: #f8f8f8;
  padding: 30rpx;
  box-sizing: border-box;
}

.form-card {
    background: #fff;
    border-radius: 16rpx;
    padding: 0 30rpx;
    margin-bottom: 60rpx;
}

.form-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 30rpx 0;
    border-bottom: 1px solid #f5f5f5;
    
    &:last-child { border-bottom: none; }
    
    .label {
        font-size: 30rpx;
        color: #333;
        width: 160rpx;
    }
    
    .input {
        flex: 1;
        text-align: right;
        font-size: 30rpx;
        color: #333;
        
        &.disabled {
            color: #999;
        }
    }
    
    .avatar-wrap {
        display: flex;
        align-items: center;
        
        .avatar {
            width: 80rpx;
            height: 80rpx;
            border-radius: 50%;
            margin-right: 10rpx;
            background: #eee;
        }
        
        .arrow {
            color: #ccc;
            font-size: 30rpx;
        }
    }
}

.action-bar {
    display: flex;
    justify-content: space-between;
    gap: 30rpx;
    
    .btn {
        flex: 1;
        height: 88rpx;
        line-height: 88rpx;
        text-align: center;
        border-radius: 44rpx;
        font-size: 32rpx;
        font-weight: bold;
        
        &.cancel {
            background: #fff;
            color: #666;
            border: 1px solid #ddd;
        }
        
        &.save {
            background: #ff6600;
            color: #fff;
            border: none;
        }
    }
}
</style>
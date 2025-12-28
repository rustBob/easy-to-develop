<template>
  <view class="container">
    <view class="address-manage">
      <!-- Address List -->
      <view v-if="!isEditing" class="address-list">
        <!-- Mock Map -->
        <view class="map-placeholder">
          <text class="icon">📍</text>
          <text class="text">当前定位: 深圳市南山区科技园...</text>
        </view>

        <view v-for="addr in addresses" :key="addr.id" class="address-item" @click="selectAddress(addr)">
          <view class="info">
            <view class="location">{{ addr.position }} {{ addr.detail }}</view>
            <view class="contact">
                {{ addr.name }} 
                <text class="gender">{{ addr.gender === 0 ? '先生' : '女士' }}</text>
                {{ addr.phone }}
            </view>
          </view>
          <view class="actions">
            <text v-if="addr.tag" class="tag">{{ addr.tag }}</text>
            <view class="edit-btn" @click.stop="editAddress(addr)">
                <text class="edit-icon">✎</text>
            </view>
            <view class="delete-btn" @click.stop="deleteAddress(addr)">
                <text class="delete-icon">🗑</text>
            </view>
          </view>
        </view>
        
        <view class="add-btn" @click="addAddress">
          <text>+ 新增地址</text>
        </view>
      </view>

      <!-- Edit Form -->
      <view v-else class="edit-form">
        <view class="form-group">
            <view class="form-item">
                <text class="label">联系人</text>
                <input v-model="editForm.name" placeholder="请填写收货人姓名" class="input"/>
            </view>
            <view class="form-item">
                <text class="label"></text>
                <view class="tags-row">
                    <view class="radio-tag" :class="{active: editForm.gender === 0}" @click="editForm.gender = 0">先生</view>
                    <view class="radio-tag" :class="{active: editForm.gender === 1}" @click="editForm.gender = 1">女士</view>
                </view>
            </view>
            <view class="form-item">
                <text class="label">手机号</text>
                <input v-model="editForm.phone" placeholder="请填写收货人手机号" class="input" type="number" maxlength="11"/>
            </view>
            <view class="form-item">
                <text class="label">地址</text>
                <input v-model="editForm.position" placeholder="点击选择收货地址" class="input"/>
                <text class="arrow">></text>
            </view>
            <view class="form-item">
                <text class="label">门牌号</text>
                <input v-model="editForm.detail" placeholder="例：A座302" class="input"/>
            </view>
            <view class="form-item">
                <text class="label">标签</text>
                <view class="tags-row">
                    <view v-for="tag in ['家', '公司', '学校']" :key="tag" 
                          class="radio-tag" 
                          :class="{active: editForm.tag === tag}" 
                          @click="editForm.tag = tag">
                        {{ tag }}
                    </view>
                </view>
            </view>
        </view>

        <view class="action-bar">
             <button class="btn cancel" @click="cancelEdit">取消</button>
             <button class="btn save" @click="handleSave">保存</button>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useUserStore } from '@/store/user';
import { globalApi } from '@/api/globalApi';

// Get mode from page options (manage or select)
const mode = ref('manage');

const userStore = useUserStore();
const addresses = ref([]);
const isEditing = ref(false);
const editForm = reactive({
    userId: userStore.userInfo.id,
    gender: 0,
    phone: '',
    position: '',
    detail: '',
    tag: ''
});

onMounted(() => {
    // Get mode from query params (not directly accessible in setup, but we default to manage)
    // If opened from menu via navigateTo, we can't easily pass callback.
    // Usually use event channel or global store/event bus.
    // For now assume manage unless we find a way to detect select mode or just use manage always.
    // If select mode, clicking item should return.
    
    // We can check pages stack
    const pages = getCurrentPages();
    if (pages.length > 0) {
        const options = pages[pages.length - 1].options;
        if (options && options.mode) {
            mode.value = options.mode;
        }
    }
    
    loadAddresses();
});

const loadAddresses = async () => {
  if (!userStore.isLoggedIn) return;
  
  globalApi['locations'].get(null, { userId: userStore.userInfo.id }, (res) => {
    addresses.value = res;
  }, (err) => {
    uni.showToast({ title: err.msg || '获取地址失败', icon: 'none' });
  });
};

const cancelEdit = () => {
    isEditing.value = false;
};

const addAddress = () => {
    Object.assign(editForm, { userId: userStore.userInfo.id, name: '', gender: 0, phone: '', position: '', detail: '', tag: '' });
    isEditing.value = true;
};

const editAddress = (addr) => {
    Object.assign(editForm, addr);
    isEditing.value = true;
};

const deleteAddress = (addr) => {
    uni.showModal({
        title: '提示',
        content: '确定要删除该地址吗？',
        success: async (res) => {
            if (res.confirm) {
                uni.showLoading({ title: '删除中' });
                globalApi['locations'].delete({pk1: addr.id}, null, () => {
                    uni.hideLoading();
                    uni.showToast({ title: '删除成功' });
                    loadAddresses();
                }, (err) => {
                    uni.hideLoading();
                    uni.showToast({ title: err.msg || '删除失败', icon: 'none' });
                });
            }
        }
    });
};

const selectAddress = (addr) => {
    if (mode.value === 'select') {
        // Return selected address
        const eventChannel = getCurrentPages()[getCurrentPages().length - 1].getOpenerEventChannel();
        if (eventChannel) {
            eventChannel.emit('selectAddress', addr);
        }
        uni.navigateBack();
    }
};

const handleSave = async () => {
    if (!editForm.name || !editForm.phone || !editForm.position || !editForm.detail) {
        uni.showToast({ title: '请填写完整信息', icon: 'none' });
        return;
    }
    
    uni.showLoading({ title: '保存中' });
    globalApi['locations'].add(null, editForm, (res) => {
        console.log(res);
        uni.hideLoading();
        
        loadAddresses();
        isEditing.value = false;
        uni.showToast({ title: '保存成功' });
    }, (err) => {
        uni.hideLoading();
        uni.showToast({ title: err.msg || '保存失败', icon: 'none' });
    });
};
</script>

<style lang="scss" scoped>
.container {
  min-height: 100vh;
  background-color: #f8f8f8;
  padding: 30rpx;
  box-sizing: border-box;
}

.address-list {
  padding-bottom: 40rpx;
}

.map-placeholder {
  height: 200rpx;
  background: #e6f7ff;
  border: 2px dashed #91d5ff;
  border-radius: 16rpx;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-bottom: 30rpx;
  color: #1890ff;
  
  .icon { font-size: 48rpx; margin-bottom: 10rpx; }
  .text { font-size: 24rpx; }
}

.address-item {
  background: #fff;
  border-radius: 16rpx;
  padding: 24rpx;
  margin-bottom: 20rpx;
  display: flex;
  justify-content: space-between;
  align-items: center;
  
  .location { font-weight: bold; font-size: 30rpx; color: #333; margin-bottom: 8rpx; }
  .contact { 
      font-size: 24rpx; 
      color: #999; 
      .gender { margin: 0 10rpx; }
  }
  
  .actions {
    display: flex;
    align-items: center;
    gap: 16rpx;
    
    .tag {
      background: #e6f7ff;
      color: #1890ff;
      font-size: 20rpx;
      padding: 4rpx 12rpx;
      border-radius: 8rpx;
    }
    .edit-btn, .delete-btn {
        padding: 10rpx;
    }
    .edit-icon, .delete-icon { color: #999; font-size: 28rpx; }
    .delete-icon { color: #ff4d4f; }
  }
}

.add-btn {
  border: 2px dashed #ddd;
  padding: 30rpx;
  border-radius: 16rpx;
  display: flex;
  justify-content: center;
  color: #999;
  font-weight: bold;
  background: #fff;
}

/* Edit Form Styles */
.form-group {
    background: #fff;
    border-radius: 16rpx;
    padding: 0 24rpx;
    margin-bottom: 40rpx;
}

.form-item {
    display: flex;
    align-items: center;
    padding: 30rpx 0;
    border-bottom: 1px solid #f5f5f5;
    
    &:last-child { border-bottom: none; }
    
    .label {
        width: 120rpx;
        font-size: 28rpx;
        color: #333;
        font-weight: bold;
    }
    
    .input {
        flex: 1;
        font-size: 28rpx;
        color: #333;
    }
    
    .arrow { color: #ccc; }
}

.tags-row {
    display: flex;
    gap: 20rpx;
    
    .radio-tag {
        border: 1px solid #ddd;
        padding: 8rpx 30rpx;
        border-radius: 8rpx;
        font-size: 24rpx;
        color: #666;
        
        &.active {
            background: #fff5e6;
            border-color: #ff6600;
            color: #ff6600;
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
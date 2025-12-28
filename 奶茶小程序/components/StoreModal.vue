<template>
  <view v-if="visible" class="modal-mask">
    <view class="modal-content">
      <view class="modal-header">
        <view class="back-btn" @click="close"><image src="/static/back-arrow.png" class="back-icon" /></view>
        <text class="title">选择门店</text>
      </view>
      
      <scroll-view scroll-y class="store-list">
        <view v-for="store in stores" :key="store.id" class="store-item" :class="{active: selectedId === store.id}" @click="selectStore(store)">
          <view class="info">
            <text class="name">{{ store.name }}</text>
            <text class="address">{{ store.address }}</text>
            <view class="tags">
                <text class="distance">距离 {{ store.distance }}</text>
                <text class="status" :class="{closed: store.status !== '营业中'}">{{ store.status }}</text>
            </view>
          </view>
          <view class="action">
              <text class="select-btn" v-if="selectedId !== store.id">去点单</text>
              <text class="current-text" v-else>当前门店</text>
          </view>
        </view>
      </scroll-view>
    </view>
  </view>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { globalApi } from '@/api/globalApi';

const props = defineProps({ 
    visible: Boolean,
    selectedId: [String, Number]
});
const emit = defineEmits(['update:visible', 'select']);

const stores = ref([]);

onMounted(async () => {
    globalApi['stores'].get(null, null, (res) => {
        stores.value = res || [];
    }, (err) => {
        uni.showToast({ title: err.msg || '获取门店失败', icon: 'none' });
    });
});

const close = () => {
    emit('update:visible', false);
};

const selectStore = (store) => {
    if (store.status !== '营业中') {
        uni.showToast({ title: '该门店休息中', icon: 'none' });
        return;
    }
    emit('select', store);
    close();
};
</script>

<style lang="scss" scoped>
.modal-mask {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: #fff;
  z-index: 900;
  display: flex;
  flex-direction: column;
  padding: 80rpx 30rpx 30rpx;
}

.modal-header {
  padding: 20rpx;
  display: flex;
  align-items: center;
  border-bottom: 1px solid #f5f5f5;
  height: 88rpx;
  
  .back-btn {
    padding: 0 20rpx;
    display: flex;
    align-items: center;
    
    .back-icon {
        width: 40rpx;
        height: 40rpx;
    }
  }
  
  .title {
    font-size: 32rpx;
    font-weight: bold;
    margin-left: 20rpx;
  }
}

.store-list {
    flex: 1;
    padding: 30rpx;
    background: #f8f8f8;
}

.store-item {
    background: #fff;
    padding: 30rpx;
    border-radius: 16rpx;
    margin-bottom: 20rpx;
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-shadow: 0 2rpx 8rpx rgba(0,0,0,0.05);
    border: 2rpx solid transparent; // 预留边框位置，避免抖动
    
    .info {
        flex: 1;
        margin-right: 20rpx;
        
        .name { font-size: 30rpx; font-weight: bold; color: #333; display: block; margin-bottom: 8rpx; }
        .address { font-size: 24rpx; color: #999; display: block; margin-bottom: 12rpx; }
        
        .tags {
            display: flex;
            align-items: center;
            font-size: 22rpx;
            
            .distance { color: #666; margin-right: 16rpx; }
            .status { 
                color: #52c41a; 
                border: 1px solid #52c41a; 
                padding: 0 8rpx; 
                border-radius: 4rpx;
                
                &.closed {
                    color: #999;
                    border-color: #999;
                }
            }
        }
    }
    
    .select-btn {
        background: #fff;
        border: 1px solid #ff6600;
        color: #ff6600;
        font-size: 24rpx;
        padding: 8rpx 20rpx;
        border-radius: 30rpx;
    }
    
    .current-text {
        color: #ff6600;
        font-size: 24rpx;
        font-weight: bold;
    }
    
    &.active {
        border-color: #ff6600;
        background: #fff5e6;
    }
}
</style>
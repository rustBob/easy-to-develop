<template>
  <view class="container">
    <view class="coupon-list">
      <view v-for="coupon in coupons" :key="coupon.id" class="coupon-card">
        <view class="left">
          <text class="value" v-if="coupon.type === 'cash'">¥<text class="num">{{ coupon.value }}</text></text>
          <text class="value" v-else><text class="num">{{ coupon.value * 10 }}</text>折</text>
          <text class="type">{{ coupon.type === 'cash' ? '代金券' : '折扣券' }}</text>
        </view>
        <view class="middle">
          <text class="name">{{ coupon.name }}</text>
          <text class="desc">{{ coupon.desc }}</text>
          <text class="limit" v-if="coupon.minAmount > 0">满{{ coupon.minAmount }}元可用</text>
          <text class="limit" v-else>无门槛</text>
        </view>
        <view class="right">
          <button class="use-btn" @click="goUse">去使用</button>
        </view>
      </view>
    </view>
    
    <view v-if="coupons.length === 0" class="empty">
        <text>暂无可用优惠券</text>
    </view>
  </view>
</template>

<script setup>
import { computed } from 'vue';
import { useUserStore } from '@/store/user';

const userStore = useUserStore();
const coupons = computed(() => userStore.userInfo.couponList || []);

const goUse = () => {
    uni.switchTab({ url: '/pages/menu/menu' });
};
</script>

<style lang="scss" scoped>
.container {
  min-height: 100vh;
  background-color: #f8f8f8;
  padding: 30rpx;
  box-sizing: border-box;
}

.coupon-card {
    background: #fff;
    border-radius: 16rpx;
    margin-bottom: 24rpx;
    display: flex;
    overflow: hidden;
    box-shadow: 0 4rpx 12rpx rgba(0,0,0,0.05);
    height: 180rpx;
    
    .left {
        width: 180rpx;
        background: linear-gradient(135deg, #ff9a9e 0%, #ff6600 100%);
        color: #fff;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        
        .value {
            font-size: 28rpx;
            .num { font-size: 60rpx; font-weight: bold; }
        }
        
        .type { font-size: 22rpx; margin-top: 8rpx; opacity: 0.9; }
    }
    
    .middle {
        flex: 1;
        padding: 24rpx;
        display: flex;
        flex-direction: column;
        justify-content: center;
        
        .name { font-size: 30rpx; font-weight: bold; color: #333; margin-bottom: 8rpx; }
        .desc { font-size: 22rpx; color: #999; margin-bottom: 8rpx; }
        .limit { font-size: 20rpx; color: #ff6600; background: #fff5e6; padding: 2rpx 8rpx; border-radius: 4rpx; align-self: flex-start; }
    }
    
    .right {
        width: 140rpx;
        display: flex;
        align-items: center;
        justify-content: center;
        border-left: 1px dashed #eee;
        
        .use-btn {
            font-size: 24rpx;
            color: #ff6600;
            background: #fff;
            border: 1px solid #ff6600;
            border-radius: 30rpx;
            padding: 0 20rpx;
            height: 50rpx;
            line-height: 48rpx;
        }
    }
}

.empty {
    text-align: center;
    color: #999;
    padding-top: 100rpx;
}
</style>

<template>
  <view class="container" v-if="order">
    <!-- Status Header -->
    <view class="status-header" :class="order.orderType">
      <view class="status-text">{{ order.statusText }}</view>
      
      <!-- Status Description -->
      <view class="status-desc" v-if="order.status === 2">
          {{ order.orderType === 'pickup' ? '饮品制作中，请耐心等待' : '商家已接单，正在为您制作' }}
      </view>
      <view class="status-desc" v-if="order.status === 3">
          {{ order.orderType === 'pickup' ? '饮品已制作完成，请前往取餐' : '骑手已接单，正在配送中' }}
      </view>
      <view class="status-desc" v-if="order.status === 4">订单已完成，期待再次光临</view>
      
      <!-- Pickup Info -->
      <view class="pickup-code-box" v-if="order.orderType === 'pickup' && (order.status === 2 || order.status === 3)">
          <text class="label">取餐码</text>
          <text class="code">{{ order.pickupCode }}</text>
          <button v-if="order.status === 2" class="action-btn" @click="confirmArrival">我已到达</button>
      </view>

      <!-- Delivery Info -->
      <view class="delivery-status-box" v-if="order.orderType === 'delivery' && (order.status === 2 || order.status === 3)">
          <view class="time-box">
              <text class="label">预计送达</text>
              <text class="time">{{ formatTime(order.estimatedTime) }}</text>
          </view>
          <view class="rider-box" v-if="order.riderName">
              <view class="rider-info">
                  <text class="name">李明</text>
                  <text class="tag">配送员</text>
              </view>
              <view class="contact-btn" @click="contactRider">
                  <text class="icon">📞</text> 联系骑手
              </view>
          </view>
      </view>
    </view>

    <!-- Product List -->
    <view class="card">
      <view class="shop-name">{{ order.storeName || (order.store ? order.store.name : '云顶奶茶') }}</view>
      <view class="product-list">
        <view v-for="item in order.orderItems" :key="item.cartId" class="product-item">
          <image :src="item.image" mode="aspectFill" class="prod-img" />
          <view class="prod-info">
            <view class="name-row">
              <text class="name">{{ item.name }}</text>
              <text class="price">¥{{ item.price }}</text>
            </view>
            <view class="specs-row">
              <text class="specs">{{ item.specs }}</text>
              <text class="qty">x{{ item.drinksQuantity }}</text>
            </view>
          </view>
        </view>
      </view>
      
      <!-- Price Details -->
      <view class="price-details">
        <view class="row" v-if="order.levelDiscountAmount > 0">
            <text class="label">会员折扣</text>
            <text class="value highlight">-¥{{ order.levelDiscountAmount }}</text>
        </view>
        <view class="row" v-if="order.couponDiscountAmount > 0">
            <text class="label">优惠券抵扣</text>
            <text class="value highlight">-¥{{ order.couponDiscountAmount }}</text>
        </view>
        <view class="row" v-if="!order.levelDiscountAmount && !order.couponDiscountAmount && order.discountAmount > 0">
            <text class="label">优惠折扣</text>
            <text class="value highlight">-¥{{ order.discountAmount }}</text>
        </view>
        <view class="row" v-if="order.pointsDeduction > 0">
            <text class="label">积分抵扣</text>
            <text class="value highlight">-¥{{ order.pointsDeduction }}</text>
        </view>
        <view class="row total-row">
            <text class="label">实付</text>
            <text class="total-price">¥{{ order.finalAmount }}</text>
        </view>
      </view>
    </view>

    <!-- Order Info -->
    <view class="card">
        <view class="info-row">
            <text class="label">订单编号</text>
            <text class="value">{{ order.id }}</text>
        </view>
        <view class="info-row">
            <text class="label">下单时间</text>
            <text class="value">{{ formatDate(order.createTime) }}</text>
        </view>
        <view class="info-row">
            <text class="label">获得积分</text>
            <text class="value">{{ Math.floor(order.finalAmount) }} 积分</text>
        </view>
        <view class="info-row" v-if="order.remarks">
            <text class="label">订单备注</text>
            <text class="value">{{ order.remarks }}</text>
        </view>
    </view>

  </view>
</template>

<script setup>
import { ref } from 'vue';
import { onLoad } from '@dcloudio/uni-app';
import { useOrderStore } from '@/store/order';

const orderStore = useOrderStore();
const order = ref(null);

onLoad((options) => {
    if (options.id) {
        order.value = orderStore.orders.find(o => o.id === options.id);
    }
});

const formatDate = (timestamp) => {
  return new Date(timestamp).toLocaleString();
};

const formatTime = (timestamp) => {
    if (!timestamp) return '';
    const date = new Date(Number(timestamp));
    if (isNaN(date.getTime())) return '';
    return date.getHours().toString().padStart(2, '0') + ':' + date.getMinutes().toString().padStart(2, '0');
};

const contactRider = () => {
    uni.makePhoneCall({ phoneNumber: '13800138000' });
};

const confirmArrival = () => {
    uni.showToast({ title: '已通知店员', icon: 'none' });
};
</script>

<style lang="scss" scoped>
.container {
  min-height: 100vh;
  background-color: #f8f8f8;
  padding: 30rpx;
  box-sizing: border-box;
}

.status-header {
    padding: 40rpx 0;
    text-align: center;
    border-bottom: 20rpx solid #f8f8f8;
    background: #fff;
    margin: -30rpx -30rpx 30rpx -30rpx;
    
    &.pickup {
        background: linear-gradient(to bottom, #f6ffed, #fff);
    }
    
    &.delivery {
        background: linear-gradient(to bottom, #e6f7ff, #fff);
    }
    
    .status-text {
        font-size: 40rpx;
        font-weight: bold;
        color: #333;
        margin-bottom: 10rpx;
    }
    
    .status-desc {
        font-size: 24rpx;
        color: #666;
    }
}

.pickup-code-box {
    margin-top: 30rpx;
    background: #fff;
    padding: 30rpx;
    border-radius: 16rpx;
    display: inline-flex;
    flex-direction: column;
    align-items: center;
    box-shadow: 0 4rpx 16rpx rgba(0,0,0,0.05);
    min-width: 300rpx;
    
    .label {
        font-size: 24rpx;
        color: #999;
        margin-bottom: 10rpx;
    }
    
    .code {
        font-size: 60rpx;
        font-weight: bold;
        color: #333;
        font-family: monospace;
        letter-spacing: 4rpx;
        margin-bottom: 10rpx;
    }
    
    .counter {
        font-size: 24rpx;
        color: #666;
        margin-bottom: 20rpx;
    }
    
    .action-btn {
        background: #52c41a;
        color: #fff;
        font-size: 28rpx;
        border-radius: 40rpx;
        padding: 0 40rpx;
        line-height: 60rpx;
    }
}

.delivery-status-box {
    margin-top: 30rpx;
    width: 600rpx;
    margin-left: auto;
    margin-right: auto;
    
    .time-box {
        margin-bottom: 30rpx;
        
        .label { font-size: 24rpx; color: #999; display: block; margin-bottom: 8rpx; }
        .time { font-size: 48rpx; font-weight: bold; color: #1890ff; }
    }
    
    .rider-box {
        background: #fff;
        padding: 20rpx 30rpx;
        border-radius: 50rpx;
        display: flex;
        justify-content: space-between;
        align-items: center;
        box-shadow: 0 4rpx 16rpx rgba(0,0,0,0.05);
        border: 1px solid #e6f7ff;
        
        .rider-info {
            display: flex;
            align-items: center;
            
            .name { font-size: 28rpx; font-weight: bold; color: #333; margin-right: 12rpx; }
            .tag { font-size: 20rpx; color: #1890ff; background: #e6f7ff; padding: 2rpx 8rpx; border-radius: 4rpx; }
        }
        
        .contact-btn {
            display: flex;
            align-items: center;
            font-size: 26rpx;
            color: #333;
            
            .icon { margin-right: 8rpx; }
        }
    }
}

.card {
    background: #fff;
    border-radius: 16rpx;
    padding: 30rpx;
    margin-bottom: 24rpx;
}

.shop-name {
    font-size: 28rpx;
    color: #666;
    padding-bottom: 20rpx;
    border-bottom: 1px solid #f5f5f5;
    margin-bottom: 20rpx;
}

.product-item {
    display: flex;
    margin-bottom: 30rpx;
    
    .prod-img {
        width: 100rpx;
        height: 100rpx;
        border-radius: 8rpx;
        background: #f5f5f5;
        margin-right: 20rpx;
    }
    
    .prod-info {
        flex: 1;
        
        .name-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 8rpx;
            
            .name { font-size: 28rpx; font-weight: bold; color: #333; }
            .price { font-size: 28rpx; font-weight: bold; }
        }
        
        .specs-row {
            display: flex;
            justify-content: space-between;
            color: #999;
            font-size: 24rpx;
        }
    }
}

.price-details {
    border-top: 1px dashed #f5f5f5;
    padding-top: 20rpx;
    
    .row {
        display: flex;
        justify-content: space-between;
        margin-bottom: 10rpx;
        font-size: 26rpx;
        color: #666;
        
        .highlight { color: #ff6600; }
        
        &.total-row {
            margin-top: 20rpx;
            align-items: center;
            
            .label { font-size: 28rpx; color: #333; }
            .total-price { font-size: 36rpx; font-weight: bold; color: #333; }
        }
    }
}

.info-row {
    display: flex;
    justify-content: space-between;
    margin-bottom: 20rpx;
    font-size: 26rpx;
    
    .label { color: #999; }
    .value { color: #333; }
    
    .value-col {
        display: flex;
        flex-direction: column;
        align-items: flex-end;
        text-align: right;
        max-width: 70%;
        
        .sub-info { font-size: 22rpx; color: #999; margin-top: 4rpx; }
    }
    
    &:last-child { margin-bottom: 0; }
}
</style>

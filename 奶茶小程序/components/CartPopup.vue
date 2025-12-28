<template>
  <view v-if="visible" class="cart-mask" @click="close">
    <view class="cart-content" @click.stop>
      <!-- Header -->
      <view class="cart-header">
        <text class="title">购物袋</text>
        <view class="clear-btn" @click="clearCart">
          <text class="icon-trash">🗑</text>
          <text>清空</text>
        </view>
      </view>

      <!-- List -->
      <scroll-view scroll-y class="cart-list">
        <view v-for="(item, index) in cartStore.items" :key="index" class="cart-item">
          <view class="item-info">
            <text class="name">{{ item.name }}</text>
            <text class="specs">{{ item.specs }}</text>
          </view>
          <view class="item-action">
            <text class="price">¥{{ item.price * item.drinksQuantity }}</text>
            <view class="stepper">
              <view class="step-btn" @click="updateQty(index, -1)">-</view>
              <text class="qty">{{ item.drinksQuantity }}</text>
              <view class="step-btn" @click="updateQty(index, 1)">+</view>
            </view>
          </view>
        </view>
      </scroll-view>

      <!-- Footer Info -->
      <view class="cart-footer-info">
         <view v-if="cartStore.orderType === 'delivery'" class="discount-row">
            <text>配送费</text>
            <text class="amount">¥{{ cartStore.deliveryFee }}</text>
         </view>
         
         <view v-if="userStore.isLoggedIn" class="discount-row">
            <text>会员等级折扣 ({{ userStore.userInfo.levelName }})</text>
            <text class="amount">-¥{{ cartStore.levelDiscountAmount }}</text>
         </view>
         
         <view v-if="userStore.isLoggedIn && cartStore.couponDiscountAmount > 0" class="discount-row">
             <text>优惠券抵扣</text>
             <text class="amount">-¥{{ cartStore.couponDiscountAmount }}</text>
         </view>

         <view class="points-row">
            <text>积分抵扣 (可用{{ userStore.userInfo.points }})</text>
            <label class="checkbox-label">
                <checkbox :checked="cartStore.usePoints" @click="togglePoints" style="transform:scale(0.7)" color="#ff6600"/>
                <text>抵扣 ¥{{ cartStore.pointsDeduction }}</text>
            </label>
         </view>
      </view>

      <!-- Coupon Selector -->
      <scroll-view scroll-x class="coupon-scroll" v-if="userStore.isLoggedIn && coupons && coupons.length > 0">
          <view v-for="c in coupons" :key="c.id" 
                class="coupon-tag" 
                :class="{ active: cartStore.selectedCouponId === c.id, disabled: cartStore.originalTotal < c.minAmount }"
                @click="selectCoupon(c)">
              <text class="name">{{ c.name }}</text>
              <text class="tip" v-if="cartStore.originalTotal < c.minAmount">差¥{{ (c.minAmount - cartStore.originalTotal).toFixed(1) }}</text>
          </view>
      </scroll-view>

      <!-- Remarks -->
      <view class="remarks-section">
          <view class="section-header">
              <text class="title">备注</text>
              <text class="count">{{ remarks.length }}/200</text>
          </view>
          <textarea 
              v-model="remarks" 
              class="remarks-input" 
              placeholder="请输入特殊要求或备注信息（选填）" 
              maxlength="200"
              :disable-default-padding="true"
          />
      </view>

      <!-- Checkout Button -->
      <view class="checkout-bar" @click="submitOrder">
        <view class="total">
          <text class="symbol">¥</text>
          <text class="num">{{ cartStore.finalTotal }}</text>
        </view>
        <view class="pay-btn">
          <text>去支付</text>
          <image src="/static/enter-arrow.png" class="arrow" />
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { computed, watch, onUnmounted, ref } from 'vue';
import { useCartStore } from '@/store/cart';
import { useUserStore } from '@/store/user';

const props = defineProps({
  visible: Boolean
});

const emit = defineEmits(['update:visible', 'submit']);

const cartStore = useCartStore();
const userStore = useUserStore();

const remarks = ref('');

const coupons = computed(() => (userStore.userInfo && userStore.userInfo.couponList) ? userStore.userInfo.couponList : []);

// Watch visible to toggle TabBar
watch(() => props.visible, (val) => {
  if (val) {
    uni.hideTabBar();
  } else {
    uni.showTabBar();
  }
});

// Restore TabBar on unmount
onUnmounted(() => {
    uni.showTabBar();
});

const close = () => {
  emit('update:visible', false);
};

const clearCart = () => {
  cartStore.clearCart();
  remarks.value = '';
  close();
};

const updateQty = (index, delta) => {
  cartStore.updateQty(index, delta);
  if (cartStore.items.length === 0) {
    remarks.value = '';
    close();
  }
};

const togglePoints = () => {
  cartStore.usePoints = !cartStore.usePoints;
};

const selectCoupon = (coupon) => {
    if (cartStore.originalTotal < coupon.minAmount) return;
    cartStore.selectCoupon(coupon.id);
};

const submitOrder = () => {
  // Simple XSS filter or trim
  const cleanRemarks = remarks.value.trim().replace(/<[^>]*>?/gm, '');
  emit('submit', { remarks: cleanRemarks });
};
</script>

<style lang="scss" scoped>
.cart-mask {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 1002;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
}

.cart-content {
  background-color: #fff;
  border-top-left-radius: 24rpx;
  border-top-right-radius: 24rpx;
  padding: 30rpx;
  padding-bottom: calc(60rpx + env(safe-area-inset-bottom));
  max-height: 80vh;
  display: flex;
  flex-direction: column;
}

.cart-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 20rpx;
  border-bottom: 1px solid #eee;
  
  .title {
    font-size: 32rpx;
    font-weight: bold;
  }
  
  .clear-btn {
    font-size: 24rpx;
    color: #999;
    display: flex;
    align-items: center;
    gap: 8rpx;
  }
}

.cart-list {
  flex: 1;
  max-height: 500rpx;
  margin-bottom: 20rpx;
}

.cart-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20rpx 0;
  
  .item-info {
    flex: 1;
    
    .name {
      font-size: 30rpx;
      font-weight: bold;
      color: #333;
      display: block;
    }
    
    .specs {
      font-size: 22rpx;
      color: #999;
    }
  }
  
  .item-action {
    display: flex;
    align-items: center;
    gap: 20rpx;
    
    .price {
      font-weight: bold;
      font-size: 30rpx;
    }
  }
}

.stepper {
  display: flex;
  align-items: center;
  border: 1px solid #ddd;
  border-radius: 8rpx;
  
  .step-btn {
    width: 50rpx;
    height: 50rpx;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #666;
  }
  
  .qty {
    width: 40rpx;
    text-align: center;
    font-size: 24rpx;
  }
}

.cart-footer-info {
  background: #fff5e6;
  padding: 20rpx;
  border-radius: 12rpx;
  margin-bottom: 20rpx;
  font-size: 24rpx;
  
  .discount-row, .points-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10rpx;
    color: #666;
  }
  
  .amount {
    color: #ff6600;
    font-weight: bold;
  }
  
  .checkbox-label {
    display: flex;
    align-items: center;
  }
}

.coupon-scroll {
    white-space: nowrap;
    margin-bottom: 20rpx;
    
    .coupon-tag {
        display: inline-block;
        padding: 10rpx 20rpx;
        background: #f5f5f5;
        border: 1px solid #eee;
        border-radius: 8rpx;
        margin-right: 20rpx;
        font-size: 24rpx;
        color: #666;
        
        &.active {
            background: #fff5e6;
            border-color: #ff6600;
            color: #ff6600;
        }
        
        &.disabled {
            opacity: 0.5;
            background: #eee;
        }
        
        .tip {
            font-size: 20rpx;
            color: #ff4d4f;
            margin-left: 8rpx;
        }
    }
}

.remarks-section {
  margin-bottom: 30rpx;
  background: #f9f9f9;
  padding: 20rpx;
  border-radius: 12rpx;
  
  .section-header {
      display: flex;
      justify-content: space-between;
      margin-bottom: 12rpx;
      
      .title { font-size: 26rpx; font-weight: bold; color: #333; }
      .count { font-size: 22rpx; color: #999; }
  }
  
  .remarks-input {
      width: 100%;
      height: 120rpx;
      font-size: 26rpx;
      color: #333;
      line-height: 1.4;
  }
}

.checkout-bar {
  background: #ff6600;
  color: #fff;
  padding: 24rpx 40rpx;
  border-radius: 50rpx;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 4rpx 12rpx rgba(255, 102, 0, 0.3);
  
  .total {
    .symbol { font-size: 24rpx; }
    .num { font-size: 36rpx; font-weight: bold; }
  }
  
  .pay-btn {
    display: flex;
    align-items: center;
    font-weight: bold;
    font-size: 30rpx;
    
    .arrow { margin-left: 10rpx; width: 24rpx; height: 24rpx; }
  }
}
</style>

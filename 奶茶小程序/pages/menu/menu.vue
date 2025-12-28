<template>
  <view class="container">
    <!-- Store Header -->
    <view class="store-header">
      <!-- Toggle Switch -->
      <view class="order-type-switch">
          <view class="switch-item" :class="{active: cartStore.orderType === 'pickup'}" @click="setOrderType('pickup')">自取</view>
          <view class="switch-item" :class="{active: cartStore.orderType === 'delivery'}" @click="setOrderType('delivery')">外卖</view>
      </view>

      <view class="store-info" v-if="cartStore.orderType === 'pickup'" @click="showStoreModal = true">
        <view class="store-name-row">
           <text class="store-name">{{ cartStore.store ? cartStore.store.name : '云顶奶茶 (科技园店)' }}</text>
           <image src="/static/enter-arrow.png" class="arrow" />
        </view>
        <view class="store-desc">
            <text class="distance">{{ cartStore.store ? '距离您 ' + cartStore.store.distance : '距离您 1.2km' }}</text>
            <text class="separator">|</text>
            <text class="desc-text">{{ cartStore.store ? cartStore.store.address : '每日现煮，新鲜制作' }}</text>
        </view>
      </view>

      <view class="delivery-info" v-else>
          <view class="address-box" @click="openAddressSelect">
              <view v-if="cartStore.address" class="addr-content">
                  <text class="addr-text">配送至 · {{ cartStore.address.position }} {{ cartStore.address.detail }}</text>
                  <text class="user-text">{{ cartStore.address.name }} {{ cartStore.address.phone }}</text>
              </view>
              <view v-else class="no-addr">
                  <text class="plus">+</text>
                  <text>选择收货地址</text>
              </view>
              <image src="/static/enter-arrow.png" class="arrow" />
          </view>
          
          <view class="delivery-store" @click="showStoreModal = true">
              <text class="label">由</text>
              <text class="name">{{ cartStore.store ? cartStore.store.name : '选择门店' }}</text>
              <text class="label">配送</text>
              <image src="/static/enter-arrow.png" class="arrow-small" />
          </view>

          <view class="delivery-tip">
              <text>外卖配送 | 预计30分钟送达</text>
          </view>
      </view>

      <view class="bulletin">
            <text class="tag">公告</text>
            <text class="bulletin-text">新品"多肉葡萄冻"上市，欢迎品尝！</text>
      </view>
      
      <!-- Search Bar Entry -->
      <view class="search-entry" @click="showSearch = true">
          <text class="icon">🔍</text>
          <text class="placeholder">搜索商品，共{{ allProducts.length }}款好茶</text>
      </view>
    </view>
    <view class="menu-layout">
      <!-- Left: Categories -->
      <scroll-view scroll-y class="left-nav">
        <view 
          v-for="cat in categories" 
          :key="cat.id" 
          class="nav-item"
          :class="{ active: activeCategoryId === cat.id }"
          @click="switchCategory(cat.id)"
        >
          <text>{{ cat.name }}</text>
        </view>
      </scroll-view>

      <!-- Right: Products -->
      <scroll-view scroll-y class="right-content">
        <view class="category-title" v-if="activeCategory">
          <text>{{ activeCategory.name }}</text>
        </view>
        
        <view class="product-list">
          <view 
            v-for="prod in activeProducts" 
            :key="prod.id" 
            class="product-item"
            @click="openProduct(prod)"
          >
            <image :src="prod.image" mode="aspectFill" class="prod-img" />
            <view class="prod-info">
              <view>
                <text class="name">{{ prod.name }}</text>
                <text class="desc">{{ prod.description }}</text>
              </view>
              <view class="price-row">
                <text class="price">¥{{ prod.price }}</text>
                <view class="add-btn">
                  <text class="plus">+</text>
                </view>
              </view>
            </view>
          </view>
        </view>
      </scroll-view>
    </view>

    <!-- Floating Cart Bar -->
    <view 
      v-if="cartStore.totalCount > 0" 
      class="cart-bar"
      @click="showCart = true"
    >
      <view class="cart-icon-wrap">
        <text class="icon">👜</text>
        <view class="badge">{{ cartStore.totalCount }}</view>
      </view>
      <view class="price-info">
        <text class="total">¥{{ cartStore.finalTotal }}</text>
        <text class="discount" v-if="cartStore.discountAmount > 0">已省 ¥{{ cartStore.discountAmount }}</text>
      </view>
      <view class="settle-btn">去结算</view>
    </view>

    <!-- Components -->
    <ProductDetail 
      v-model:visible="showDetail" 
      :product="selectedProduct"
      @add-to-cart="handleAddToCart"
    />
    
    <CartPopup 
      v-model:visible="showCart" 
      @submit="handleSubmitOrder"
    />

    <StoreModal
        v-model:visible="showStoreModal"
        :selected-id="cartStore.store?.id"
        @select="handleStoreSelect"
    />
    
    <SearchOverlay
        v-model:visible="showSearch"
        :all-products="allProducts"
        @open-product="openProduct"
    />
  </view>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { onShow } from '@dcloudio/uni-app';
import { useCartStore } from '@/store/cart';
import { useUserStore } from '@/store/user';
import { globalApi } from '@/api/globalApi';
import ProductDetail from '@/components/ProductDetail.vue';
import CartPopup from '@/components/CartPopup.vue';
import StoreModal from '@/components/StoreModal.vue';
import SearchOverlay from '@/components/SearchOverlay.vue';

const cartStore = useCartStore();
const userStore = useUserStore();

const categories = ref([]);
const allProducts = ref([]);
const activeCategoryId = ref('');
const showDetail = ref(false);
const showCart = ref(false);
const showStoreModal = ref(false);
const showSearch = ref(false);
const selectedProduct = ref(null);

watch(() => cartStore.store, (newVal, oldVal) => {
    globalApi['drinks'].get(null, { storeId: newVal.id }, (res) => {
      allProducts.value = res;
    }, (err) => {
      uni.showToast({ title: err.msg || '获取商品失败', icon: 'none' });
    });
});

onShow(async () => {
  globalApi['categories'].get(null, null, (res) => {
    categories.value = res;
    activeCategoryId.value = res[0].id;
  }, (err) => {
    uni.showToast({ title: err.msg || '获取分类失败', icon: 'none' });
  });
});

const activeCategory = computed(() => categories.value.find(c => c.id === activeCategoryId.value));
const activeProducts = computed(() => allProducts.value.filter(p => p.categoryId === activeCategoryId.value));

const switchCategory = (id) => {
  activeCategoryId.value = id;
};

const setOrderType = (type) => {
    cartStore.setOrderType(type);
};

const handleAddressSelect = (addr) => {
    cartStore.setAddress(addr);
};

const openAddressSelect = () => {
    if (!userStore.isLoggedIn) {
        uni.navigateTo({ url: '/pages/login/login' });
        return;
    }
    uni.navigateTo({
        url: '/pages/address/address?mode=select',
        events: {
            selectAddress: (addr) => {
                handleAddressSelect(addr);
            }
        }
    });
};

const handleStoreSelect = (store) => {
    cartStore.setStore(store);
};

const openProduct = (prod) => {
  selectedProduct.value = prod;
  showDetail.value = true;
};

const handleAddToCart = (data) => {
  cartStore.addToCart(data.product, data.specs, data.qty);
};

const handleSubmitOrder = async (data) => {
  if (!userStore.isLoggedIn) {
    showCart.value = false;
    uni.navigateTo({ url: '/pages/login/login' });
    return;
  }

  // Validate Address for Delivery
  if (cartStore.orderType === 'delivery' && !cartStore.address) {
      uni.showToast({ title: '请选择收货地址', icon: 'none' });
      return;
  }

  // Submit Order Logic
  const orderData = {
    userId: userStore.userInfo.id,
    orderType: cartStore.orderType, // Ensure orderType is passed
    storeId: cartStore.store?.id, // Pass store info for both pickup and delivery
    locationId: cartStore.orderType === 'delivery' ? cartStore.address.id : null, // Pass address for delivery
    orderItems: JSON.parse(JSON.stringify(cartStore.items)), // Deep copy items
    totalAmount: cartStore.originalTotal,
    finalAmount: cartStore.finalTotal,
    discountAmount: cartStore.discountAmount,
    levelDiscountAmount: cartStore.levelDiscountAmount,
    couponDiscountAmount: cartStore.couponDiscountAmount,
    pointsDeduction: cartStore.pointsDeduction,
    pointsConsumption: cartStore.usePoints ? Math.floor(cartStore.pointsDeduction * 100) : 0,
    remarks: data && data.remarks ? data.remarks : ''
  };
  
  uni.showLoading({ title: '支付中...' });
  try {
    globalApi['orders'].add(null, orderData, (res) => {
      console.log(res);
      // Deduct points
      if (cartStore.usePoints) {
        userStore.updatePoints(userStore.userInfo.points - Math.floor(cartStore.pointsDeduction * 100));
      }
      // Add points from purchase
      const gainedPoints = Math.floor(orderData.finalAmount);
      userStore.updatePoints(userStore.userInfo.points + gainedPoints);
      userStore.updateBalance(userStore.userInfo.balance - orderData.finalAmount);

      cartStore.clearCart();
      
      // Close cart popup and restore TabBar
      showCart.value = false;
      uni.showTabBar();

      uni.hideLoading();
      uni.showToast({ title: '支付成功' });
      
      // Navigate to Orders
      uni.switchTab({ url: '/pages/orders/orders' });
    }, (err) => {
      uni.hideLoading();
      uni.showToast({ title: err.msg || '提交订单失败', icon: 'none' });
      return;
    }, 'make-order');
  } catch (e) {
    uni.hideLoading();
    uni.showToast({ title: '支付失败', icon: 'none' });
  }
};
</script>

<style lang="scss" scoped>
.container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #fff;
}

.store-header {
  padding: 80rpx 30rpx 30rpx; // Match index.vue top padding
  background: #fff;
  display: flex;
  flex-direction: column;
  box-shadow: 0 2rpx 10rpx rgba(0,0,0,0.05);
  z-index: 10;
  
  .order-type-switch {
    display: flex;
    background: #f5f5f5;
    border-radius: 40rpx;
    padding: 4rpx;
    margin-bottom: 20rpx;
    width: fit-content;
    
    .switch-item {
      padding: 10rpx 40rpx;
      border-radius: 36rpx;
      font-size: 28rpx;
      color: #666;
      transition: all 0.3s;
      
      &.active {
        background: #333;
        color: #fff;
        font-weight: bold;
        box-shadow: 0 2rpx 8rpx rgba(0,0,0,0.1);
      }
    }
  }

  .store-info {
    width: 100%;
    
    .store-name-row {
      display: flex;
      align-items: center;
      margin-bottom: 12rpx;
      
      .store-name { font-size: 36rpx; font-weight: bold; color: #333; }
      .arrow { margin-left: 10rpx; width: 24rpx; height: 24rpx; }
    }
    
    .store-desc {
      display: flex;
      align-items: center;
      font-size: 24rpx;
      color: #666;
      margin-bottom: 16rpx;
      
      .separator { margin: 0 12rpx; color: #eee; }
    }
  }
  
  .delivery-info {
      margin-bottom: 20rpx;
      
      .address-box {
          display: flex;
          align-items: center;
          margin-bottom: 10rpx;
          
          .no-addr {
              flex: 1;
              font-size: 32rpx;
              font-weight: bold;
              display: flex;
              align-items: center;
              color: #333;
              
              .plus { color: #ff6600; margin-right: 10rpx; font-size: 40rpx; }
          }
          
          .addr-content {
              flex: 1;
              display: flex;
              flex-direction: column;
              
              .addr-text { font-size: 32rpx; font-weight: bold; color: #333; margin-bottom: 6rpx; }
              .user-text { font-size: 24rpx; color: #666; }
          }
          
          .arrow { margin-left: 20rpx; width: 24rpx; height: 24rpx; }
      }
      
      .delivery-store {
          display: flex;
          align-items: center;
          margin-bottom: 12rpx;
          font-size: 26rpx;
          color: #666;
          
          .name {
              font-weight: bold;
              color: #333;
              margin: 0 8rpx;
          }
          
          .arrow-small {
              width: 20rpx;
              height: 20rpx;
              opacity: 0.5;
              margin-left: 4rpx;
          }
      }

      .delivery-tip {
          font-size: 22rpx;
          color: #999;
      }
  }
    
  .bulletin {
      display: flex;
      align-items: center;
      background: #fff9f0;
      padding: 10rpx 16rpx;
      border-radius: 8rpx;
      
      .tag { 
        background: #ff6600; 
        color: #fff; 
        font-size: 20rpx; 
        padding: 2rpx 8rpx; 
        border-radius: 4rpx; 
        margin-right: 12rpx;
      }
      .bulletin-text { font-size: 24rpx; color: #333; }
    }
    
    .search-entry {
        margin-top: 20rpx;
        background: #f5f5f5;
        height: 64rpx;
        border-radius: 32rpx;
        display: flex;
        align-items: center;
        padding: 0 24rpx;
        
        .icon { font-size: 28rpx; margin-right: 12rpx; opacity: 0.5; }
        .placeholder { font-size: 24rpx; color: #999; }
    }
  }

.menu-layout {
  flex: 1;
  display: flex;
  overflow: hidden;
}

.left-nav {
  width: 180rpx;
  background-color: #f8f8f8;
  height: 100%;
  padding-bottom: 200rpx;
  box-sizing: border-box;
  
  .nav-item {
    padding: 30rpx 20rpx;
    font-size: 28rpx;
    color: #666;
    text-align: center;
    border-left: 8rpx solid transparent;
    
    &.active {
      background-color: #fff;
      color: #ff6600;
      font-weight: bold;
      border-left-color: #ff6600;
    }
  }
}

.right-content {
  flex: 1;
  height: 100%;
  padding: 0 20rpx;
  background-color: #fff;
}

.category-title {
  padding: 20rpx 0;
  font-size: 24rpx;
  color: #999;
  position: sticky;
  top: 0;
  background: #fff;
  z-index: 10;
}

.product-item {
  display: flex;
  margin-bottom: 30rpx;
  
  .prod-img {
    width: 160rpx;
    height: 160rpx;
    border-radius: 12rpx;
    margin-right: 20rpx;
    background-color: #f5f5f5;
  }
  
  .prod-info {
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    padding: 4rpx 0;
    
    .name {
      font-size: 30rpx;
      font-weight: bold;
      color: #333;
      display: block;
      margin-bottom: 8rpx;
    }
    
    .desc {
      font-size: 22rpx;
      color: #999;
      display: -webkit-box;
      -webkit-line-clamp: 1;
      -webkit-box-orient: vertical;
      overflow: hidden;
    }
    
    .price-row {
      display: flex;
      justify-content: space-between;
      align-items: flex-end;
      
      .price {
        font-size: 32rpx;
        font-weight: bold;
        color: #333;
      }
      
      .add-btn {
        width: 48rpx;
        height: 48rpx;
        background-color: #ff6600;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        box-shadow: 0 4rpx 8rpx rgba(255, 102, 0, 0.3);
        
        .plus {
          color: #fff;
          font-size: 32rpx;
          line-height: 1;
          margin-top: -4rpx;
        }
      }
    }
  }
}

.cart-bar {
  position: fixed;
  bottom: 20rpx; // Adjusted to be closer to bottom
  left: 30rpx;
  right: 30rpx;
  background-color: #333;
  border-radius: 50rpx;
  height: 100rpx;
  display: flex;
  align-items: center;
  padding: 0 30rpx;
  z-index: 1000;
  box-shadow: 0 10rpx 20rpx rgba(0,0,0,0.2);
  
  .cart-icon-wrap {
    position: relative;
    margin-right: 30rpx;
    
    .icon { font-size: 40rpx; }
    
    .badge {
      position: absolute;
      top: -10rpx;
      right: -10rpx;
      background-color: #ff4d4f;
      color: #fff;
      font-size: 20rpx;
      padding: 0 10rpx;
      border-radius: 20rpx;
      min-width: 30rpx;
      text-align: center;
    }
  }
  
  .price-info {
    flex: 1;
    display: flex;
    flex-direction: column;
    
    .total { color: #fff; font-size: 32rpx; font-weight: bold; }
    .discount { color: #999; font-size: 20rpx; }
  }
  
  .settle-btn {
    background-color: #ff6600;
    color: #fff;
    padding: 12rpx 40rpx;
    border-radius: 40rpx;
    font-size: 28rpx;
    font-weight: bold;
  }
}
</style>

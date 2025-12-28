<template>
  <view class="container">
    <!-- Navbar -->
    <view class="nav-bar">
      <text class="nav-title">云顶奶茶</text>
    </view>

    <scroll-view scroll-y class="content">
      <!-- Banner -->
      <view class="banner-box">
        <swiper class="swiper" circular autoplay interval="5000" indicator-dots indicator-active-color="#fff">
          <swiper-item v-for="(b, i) in banners" :key="i">
            <image :src="b.img" mode="aspectFill" class="banner-img" />
          </swiper-item>
        </swiper>
        <view class="activity-tag">
          <view class="dot"></view>
          <text>精品优惠</text>
        </view>
      </view>

      <!-- Quick Entry -->
      <view class="quick-entry">
        <view class="entry-card" @click="toMenu('pickup')">
          <view class="text-group">
            <text class="title">门店自取</text>
            <text class="sub">下单免排队</text>
          </view>
          <text class="icon">🏬</text>
        </view>
        <view class="entry-card" @click="toMenu('delivery')">
          <view class="text-group">
            <text class="title">外卖配送</text>
            <text class="sub">无接触配送</text>
          </view>
          <text class="icon">🛵</text>
        </view>
      </view>

      <!-- Recommendations -->
      <view class="section" v-if="recommendations.length > 0">
        <view class="section-header">
          <text class="title">猜你喜欢</text>
          <text class="sub">根据最近点单推荐</text>
        </view>
        <scroll-view scroll-x class="recommend-scroll">
          <view class="recommend-list">
            <view v-for="item in recommendations" :key="item.id" class="recommend-item" @click="openProduct(item)">
              <image :src="item.image" mode="aspectFill" class="prod-img" />
              <text class="prod-name">{{ item.name }}</text>
              <text class="prod-price">¥{{ item.price }}</text>
            </view>
          </view>
        </scroll-view>
      </view>
    </scroll-view>

    <ProductDetail 
      v-model:visible="showDetail" 
      :product="selectedProduct" 
      @add-to-cart="handleAddToCart"
    />
  </view>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { onShow } from '@dcloudio/uni-app';
import { useCartStore } from '@/store/cart';
import { globalApi } from '@/api/globalApi';
import ProductDetail from '@/components/ProductDetail.vue';

const cartStore = useCartStore();

const banners = ref([]);
const products = ref([]);
const showDetail = ref(false);
const selectedProduct = ref(null);

const recommendations = computed(() => {
    if (products.value.length <= 3) return products.value;
    // Simple random shuffle and slice
    const shuffled = [...products.value].sort(() => 0.5 - Math.random());
    return shuffled.slice(0, 3);
});

onShow(async () => {
  globalApi['banners'].get(null, null, (res) => {
    banners.value = res || [];
  })

  globalApi['stores'].get(null, null, (res) => {
    cartStore.setStore(res[0]);
    globalApi['drinks'].get(null, { storeId: res[0].id }, (res) => {
      products.value = res || [];
    }, (err) => {
      uni.showToast({ title: err.msg || '获取商品失败', icon: 'none' });
    });
  }, (err) => {
    uni.showToast({ title: err.msg || '获取门店失败', icon: 'none' });
  });
});

const toProfile = () => uni.switchTab({ url: '/pages/profile/profile' });
const toMenu = (type) => {
    cartStore.setOrderType(type || 'pickup');
    uni.switchTab({ url: '/pages/menu/menu' });
};

const openProduct = (prod) => {
  selectedProduct.value = prod;
  showDetail.value = true;
};

const handleAddToCart = (data) => {
  cartStore.addToCart(data.product, data.specs, data.qty);
  uni.showToast({ title: '已加入购物袋', icon: 'none' });
};
</script>

<style lang="scss" scoped>
.container {
  min-height: 100vh;
  background-color: #f8f8f8;
  display: flex;
  flex-direction: column;
}

.nav-bar {
  background: #fff;
  padding: 80rpx 30rpx 20rpx; // Status bar padding
  display: flex;
  align-items: center;
  
  .nav-title {
    font-size: 36rpx;
    font-weight: bold;
  }
}

.content {
  flex: 1;
  padding: 30rpx;
  box-sizing: border-box;
}

.banner-box {
  position: relative;
  border-radius: 24rpx;
  overflow: hidden;
  box-shadow: 0 4rpx 16rpx rgba(0,0,0,0.1);
  margin-bottom: 30rpx;
  
  .swiper {
    height: 380rpx;
    width: 100%;
  }
  
  .banner-img {
    width: 100%;
    height: 100%;
  }
  
  .activity-tag {
    position: absolute;
    top: 20rpx;
    right: 20rpx;
    background: rgba(255, 255, 255, 0.9);
    padding: 8rpx 20rpx;
    border-radius: 30rpx;
    display: flex;
    align-items: center;
    color: #ff6600;
    font-size: 24rpx;
    font-weight: bold;
    
    .dot {
      width: 12rpx;
      height: 12rpx;
      background: red;
      border-radius: 50%;
      margin-right: 8rpx;
    }
  }
}

.section {
  margin-bottom: 30rpx;
  
  .section-header {
    display: flex;
    justify-content: space-between;
    align-items: baseline;
    margin-bottom: 20rpx;
    
    .title { font-size: 32rpx; font-weight: bold; color: #333; }
    .sub { font-size: 22rpx; color: #999; }
  }
}

.recommend-scroll {
  white-space: nowrap;
  width: 100%;
}

.recommend-list {
  display: flex;
  gap: 20rpx;
}

.recommend-item {
  width: 220rpx;
  background: #fff;
  border-radius: 16rpx;
  padding: 16rpx;
  display: inline-block;
  box-shadow: 0 2rpx 8rpx rgba(0,0,0,0.05);
  
  .prod-img {
    width: 100%;
    height: 180rpx;
    border-radius: 12rpx;
    margin-bottom: 12rpx;
  }
  
  .prod-name {
    font-size: 26rpx;
    font-weight: bold;
    color: #333;
    display: block;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  
  .prod-price {
    font-size: 24rpx;
    color: #ff6600;
    font-weight: bold;
  }
}

.quick-entry {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20rpx;
  margin-bottom: 40rpx;
  
  .entry-card {
    background: #fff;
    padding: 30rpx;
    border-radius: 24rpx;
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-shadow: 0 2rpx 8rpx rgba(0,0,0,0.05);
    
    .title { font-size: 30rpx; font-weight: bold; display: block; }
    .sub { font-size: 22rpx; color: #999; }
    .icon { font-size: 48rpx; opacity: 0.8; }
  }
}
</style>

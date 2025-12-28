<template>
  <view class="container">
    <!-- Top Tabs -->
    <view class="nav-tabs">
        <view 
            v-for="(tab, index) in tabs" 
            :key="index" 
            class="tab-item" 
            :class="{ active: activeTab === index }"
            @click="activeTab = index"
        >
            <text>{{ tab }}</text>
            <view class="line" v-if="activeTab === index"></view>
        </view>
    </view>

    <view class="content">
      <!-- Empty State -->
      <view v-if="!userStore.isLoggedIn" class="empty-state">
        <text class="icon">🔒</text>
        <text class="text">请先登录查看订单</text>
        <button class="login-btn" @click="toLogin">立即登录</button>
      </view>
      
      <view v-else-if="filteredOrders.length === 0" class="empty-state">
        <text class="icon">🧾</text>
        <text class="text">暂无{{ tabs[activeTab] === '全部' ? '' : tabs[activeTab] }}订单</text>
        <button class="go-menu-btn" @click="toMenu">去点单</button>
      </view>
      
      <!-- Order List -->
      <view v-else class="order-list">
        <view v-for="order in filteredOrders" :key="order.id" class="order-card" :class="[order.orderType, { rejected: order.status === 5 }]" 
          @click="toDetail(order.id)">
          <view class="card-header">
            <view class="header-left">
                <text class="type-tag" :class="order.orderType">{{ order.orderType === 'pickup' ? '自取' : '外送' }}</text>
                <text class="time">{{ formatDate(order.createTime) }}</text>
            </view>
            <text class="status" :class="'status-' + order.status">{{ order.statusText }}</text>
          </view>
          
          <view class="order-items">
            <view v-for="item in order.orderItems" :key="item.cartId" class="order-item">
              <text class="name">{{ item.name }} x{{ item.drinksQuantity }}</text>
              <text class="price">¥{{ (item.price * item.drinksQuantity).toFixed(2) }}</text>
            </view>
            <view v-if="order.remarks" class="remarks-row">
                <text class="label">备注：</text>
                <text class="text">{{ order.remarks }}</text>
            </view>
          </view>
          
          <!-- Type Specific Info -->
          <view class="type-info" v-if="order.status !== 4 && order.status !== 5">
              <view v-if="order.orderType === 'pickup'" class="pickup-info">
                  <text class="code">取餐码：{{ order.pickupCode }}</text>
                  <text class="addr">门店：{{ order.storeAddress || '云顶奶茶 (科技园店)' }}</text>
              </view>
              <view v-else-if="order.status === 2" class="delivery-info">
                  <text class="time">预计送达：{{ formatTime(order.estimatedTime) }}</text>
                  <text class="addr">配送至：{{ order.address + ' ' + order.detail }}</text>
              </view>
          </view>
          
          <!-- Reject Reason -->
          <view class="reject-info" v-if="order.status === 5">
              <text class="label">拒单原因：</text>
              <text class="reason">{{ order.rejectReason || '商家繁忙，无法接单' }}</text>
          </view>

          <view class="card-footer">
            <text class="count">共 {{ order.totalCount }} 件</text>
            <view class="total-box">
                <text class="total">实付 ¥{{ order.finalAmount }}</text>
                <text class="fee" v-if="order.deliveryFee">(含配送费 ¥{{ order.deliveryFee }})</text>
            </view>
          </view>
          
          <view class="actions">
            <!-- Status 1: Wait Accept -->
            <button v-if="order.status === 1" class="action-btn outline" @click.stop="cancelOrder(order)">取消订单</button>

            <!-- Status 2: Making -->
            <button v-if="order.status === 2" class="action-btn outline" @click.stop="remindOrder">催单</button>
            
            <!-- Status 4: Completed -->
            <button v-if="order.status === 4" class="action-btn outline" @click.stop="openAfterSales(order)">售后</button>
            
            <!-- Delivery Actions -->
            <button v-if="order.orderType === 'send-out' && (order.status === 2 || order.status === 3)" class="action-btn outline" @click.stop="contactRider">联系骑手</button>
            
            <!-- Pickup Actions -->
            <button v-if="order.orderType === 'pickup' && order.status === 3" class="action-btn primary" @click.stop="confirmArrival">我已到达</button>
            
            <!-- Common Reorder -->
            <button class="action-btn primary" @click.stop="reorder(order)">{{ order.status === 5 ? '重新下单' : '再来一单' }}</button>
          </view>
        </view>
      </view>
    </view>
    
  </view>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { onShow } from '@dcloudio/uni-app';
import { useUserStore } from '@/store/user';
import { useOrderStore } from '@/store/order';
import { useCartStore } from '@/store/cart';
import { globalApi } from '@/api/globalApi';

const userStore = useUserStore();
const orderStore = useOrderStore();
const cartStore = useCartStore();

const activeTab = ref(0);
const tabs = ['全部', '历史', '拒单'];
const loading = ref(false);

const loadOrders = async () => {
    if (!userStore.isLoggedIn) return;
    
    loading.value = true;
    let params = { userId: userStore.userInfo.id };

    if (activeTab.value === 1) {
        params.status = 4; // 历史 (已完成)
    } else if (activeTab.value === 2) {
        params.status = 5; // 拒单
    }
    
    try {
        await new Promise((resolve, reject) => {
             globalApi['orders'].get(null, params, (res) => {
              res.forEach(order => {
                order.statusText = getStatusText(order);
                order.totalCount = order.orderItems.reduce((acc, item) => acc + item.drinksQuantity, 0); 
              });
              const reversedRes = res.reverse();
              orderStore.setOrders(reversedRes);
              resolve(reversedRes);
            }, (err) => {
                uni.showToast({ title: '加载订单失败', icon: 'none' });
                reject(err);
            });
        });
    } catch (e) {
        console.error(e);
    } finally {
        loading.value = false;
    }
};

watch(() => userStore.isLoggedIn, (loggedIn) => {
    if (loggedIn) {
        loadOrders();
    } else {
        orderStore.setOrders([]); // Clear orders on logout
    }
}, { immediate: true });

watch(activeTab, () => {
    loadOrders();
});

onShow(() => {

});

const toLogin = () => {
    uni.navigateTo({ url: '/pages/login/login' });
};

const filteredOrders = computed(() => {
    return orderStore.orders;
});

const toMenu = () => uni.switchTab({ url: '/pages/menu/menu' });
const toDetail = (id) => uni.navigateTo({ url: `/pages/order-detail/order-detail?id=${id}` });

const formatDate = (timestamp) => {
  const date = new Date(timestamp);
  return date.toLocaleString();
};

const formatTime = (timestamp) => {
    if (!timestamp) return '';
    const date = new Date(Number(timestamp));
    if (isNaN(date.getTime())) return '';
    return date.getHours().toString().padStart(2, '0') + ':' + date.getMinutes().toString().padStart(2, '0');
};

const remindOrder = () => {
  uni.showToast({ title: '已催促店员制作', icon: 'none' });
};

const contactRider = () => {
    uni.makePhoneCall({ phoneNumber: '13800138000' });
};

const confirmArrival = () => {
    uni.showToast({ title: '已通知店员，请稍候', icon: 'none' });
};

const cancelOrder = (order) => {
    uni.showModal({
        title: '提示',
        content: '确定要取消订单吗？',
        success: (res) => {
            if (res.confirm) {
                const index = orderStore.orders.findIndex(o => o.id === order.id);
                if (index > -1) {
                    orderStore.orders.splice(index, 1);
                    uni.showToast({ title: '订单已取消' });
                }
            }
        }
    });
};

const reorder = (order) => {
    // Add items to cart
    order.items.forEach(item => {
        cartStore.addToCart(item, item.specs, item.qty);
    });
    uni.switchTab({ url: '/pages/menu/menu' });
};

const openAfterSales = (order) => {
    uni.navigateTo({
        url: `/pages/after-sales/after-sales?orderId=${order.id}`
    });
};

const getStatusText = (order) => {
    switch(order.status){
        case 0:
          return '已取消';
        case 1:
          return '待接单';
        case 2:
          return '制作中';
        case 3:
          return order.orderType === 'pickup' ? '待取餐' : '派送中';
        case 4:
          return '已完成';
        case 5:
          return '商家拒单';
      }
}
</script>

<style lang="scss" scoped>
.container {
  min-height: 100vh;
  background-color: #f8f8f8;
  box-sizing: border-box;
  padding-top: 220rpx; // Space for fixed header (188rpx + margin)
}

.nav-tabs {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    height: 188rpx;
    background: #fff;
    display: flex;
    align-items: center;
    justify-content: space-around;
    z-index: 100;
    box-shadow: 0 2rpx 10rpx rgba(0,0,0,0.05);
    
    .tab-item {
        font-size: 28rpx;
        color: #666;
        height: 100%;
        display: flex;
        align-items: flex-end; // Changed from center to flex-end
        padding-bottom: 20rpx; // Add some padding bottom
        position: relative;
        padding-left: 20rpx;
        padding-right: 20rpx;
        
        &.active {
            color: #333;
            font-weight: bold;
            font-size: 30rpx;
        }
        
        .line {
            position: absolute;
            bottom: 0;
            left: 50%;
            transform: translateX(-50%);
            width: 40rpx;
            height: 4rpx;
            background: #ff6600;
            border-radius: 2rpx;
        }
    }
}

.content {
  padding: 30rpx;
  padding-bottom: 40rpx;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding-top: 200rpx;
  
  .icon { font-size: 80rpx; margin-bottom: 30rpx; opacity: 0.5; }
  .text { color: #999; font-size: 28rpx; margin-bottom: 40rpx; }
  
  button {
    background: #ff6600;
    color: #fff;
    font-size: 28rpx;
    padding: 10rpx 60rpx;
    border-radius: 40rpx;
  }
}

.order-card {
  background: #fff;
  border-radius: 16rpx;
  padding: 30rpx;
  margin-bottom: 24rpx;
  border: 1px solid transparent; // Default border
  
  &.pickup {
      border-color: #52c41a;
      background: #f6ffed;
      
      .type-tag {
          background: #52c41a;
          color: #fff;
      }
  }
  
  &.delivery {
      border-color: #1890ff;
      background: #e6f7ff;
      
      .type-tag {
          background: #1890ff;
          color: #fff;
      }
  }
  
  &.rejected {
      border-color: #ccc;
      background: #f5f5f5;
      
      .type-tag {
          background: #999;
      }
      
      .status { color: #ff4d4f; }
  }
  
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20rpx;
    padding-bottom: 20rpx;
    border-bottom: 1px solid rgba(0,0,0,0.05);
    
    .header-left {
        display: flex;
        align-items: center;
        
        .type-tag {
            font-size: 20rpx;
            padding: 4rpx 12rpx;
            border-radius: 8rpx;
            margin-right: 16rpx;
            background: #999;
            color: #fff;
        }
        
        .time { font-size: 24rpx; color: #999; }
    }
    
    .status { font-size: 28rpx; font-weight: bold; }
    .status-0 { color: #ccc; } // 已取消
    .status-1 { color: #ff9900; } // 待接单
    .status-2 { color: #ff6600; } // 制作中
    .status-3 { color: #07c160; } // 待取餐
    .status-4 { color: #999; }    // 已完成
    .status-5 { color: #ff4d4f; } // 拒单
  }
}

.type-info {
    background: rgba(255,255,255,0.6);
    padding: 16rpx;
    border-radius: 8rpx;
    margin: 20rpx 0;
    
    .pickup-info, .delivery-info {
        display: flex;
        flex-direction: column;
        font-size: 26rpx;
        color: #333;
        
        .code { font-weight: bold; font-size: 32rpx; margin-bottom: 8rpx; }
        .time { font-weight: bold; color: #ff6600; margin-bottom: 8rpx; }
        .addr { font-size: 24rpx; color: #666; }
    }
}

.reject-info {
    background: #fff1f0;
    padding: 16rpx;
    border-radius: 8rpx;
    margin: 20rpx 0;
    font-size: 24rpx;
    
    .label { color: #ff4d4f; font-weight: bold; }
    .reason { color: #666; }
}

.order-items {
  margin-bottom: 20rpx;
  
  .order-item {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10rpx;
    font-size: 28rpx;
    color: #333;
    
    .price { font-weight: bold; }
  }
  
  .remarks-row {
      margin-top: 12rpx;
      padding-top: 12rpx;
      border-top: 1px dashed #eee;
      font-size: 24rpx;
      color: #666;
      display: flex;
      
      .label { color: #999; flex-shrink: 0; }
      .text { flex: 1; }
  }
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20rpx;
  padding-top: 20rpx;
  border-top: 1px dashed rgba(0,0,0,0.1);
  
  .count { font-size: 24rpx; color: #999; }
  
  .total-box {
      display: flex;
      flex-direction: column;
      align-items: flex-end;
      
      .total { font-size: 32rpx; font-weight: bold; color: #333; }
      .fee { font-size: 20rpx; color: #999; }
  }
}

.actions {
  display: flex;
  justify-content: flex-end;
  gap: 20rpx;
  margin-top: 24rpx;
  
  .action-btn {
    margin: 0;
    font-size: 24rpx;
    padding: 0 30rpx;
    height: 56rpx;
    line-height: 56rpx;
    border-radius: 28rpx;
    
    &.outline {
      background: #fff;
      border: 1px solid #ddd;
      color: #666;
    }
    
    &.primary {
      background: #fff5e6;
      color: #ff6600;
      border: 1px solid #ff6600;
    }
  }
}
</style>
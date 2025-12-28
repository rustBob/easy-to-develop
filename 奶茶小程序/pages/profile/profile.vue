<template>
  <view class="container">
    <!-- Header Background -->
    <view class="header-bg"></view>
    
    <view class="content">
      <!-- User Card -->
      <view class="user-card" @click="toProfileEdit">
        <view class="avatar-wrap">
          <image 
            :src="userStore.isLoggedIn ? userStore.userInfo.avatar : '/static/logo.png'" 
            class="avatar"
            mode="aspectFill"
          />
        </view>
        
        <view class="user-info">
          <block v-if="userStore.isLoggedIn">
            <text class="nickname">{{ userStore.userInfo.nickname }}</text>
            <text class="card-no">会员卡号: {{ userStore.userInfo.cardId }}</text>
          </block>
          <block v-else>
            <text class="nickname" @click.stop="toLogin">点击登录/注册</text>
            <text class="card-no">登录享受会员权益</text>
          </block>
        </view>
        <image v-if="userStore.isLoggedIn" src="/static/enter-arrow.png" class="arrow-right" />
      </view>

      <!-- Member Rights Card -->
      <view class="member-rights-card" v-if="userStore.isLoggedIn">
        <view class="rights-header">
            <text class="level-title">{{ userStore.userInfo.levelName }}</text>
            <text class="expiry">有效期至 2025-12-31</text>
        </view>
        <view class="rights-list">
            <view class="right-item">
                <text class="icon">👑</text>
                <text class="name">等级折扣</text>
                <text class="desc">{{ getLevelDiscountText(userStore.userInfo.level) }}</text>
            </view>
            <view class="right-item">
                <text class="icon">🎁</text>
                <text class="name">生日礼包</text>
                <text class="desc">专享好礼</text>
            </view>
            <view class="right-item">
                <text class="icon">🚀</text>
                <text class="name">极速点单</text>
                <text class="desc">优先制作</text>
            </view>
        </view>
        
        <!-- Level Rules -->
        <view class="level-rules">
            <view class="rule-item" :class="{active: userStore.userInfo.level === 0}">
                <text class="lvl">普通</text>
                <text class="benefit">无折扣</text>
            </view>
             <view class="rule-item" :class="{active: userStore.userInfo.level === 1}">
                <text class="lvl">黄金</text>
                <text class="benefit">9.5折</text>
            </view>
             <view class="rule-item" :class="{active: userStore.userInfo.level === 2}">
                <text class="lvl">钻石</text>
                <text class="benefit">9折</text>
            </view>
        </view>
      </view>
      
      <!-- Assets -->
      <view class="assets-card">
        <view class="asset-item" @click="toCoupons">
          <text class="num">{{ userStore.userInfo.coupons || 0 }}</text>
          <text class="label">优惠券</text>
        </view>
        <view class="asset-item">
          <text class="num">{{ userStore.userInfo.points || 0 }}</text>
          <text class="label">积分</text>
        </view>
        <view class="asset-item" @click="toRecharge">
          <text class="num">{{ userStore.userInfo.balance || '0.00' }}</text>
          <text class="label">余额</text>
        </view>
      </view>
      
      <!-- Menu List -->
      <view class="menu-list">
        <!-- Daily Share Button -->
        <button class="menu-item share-btn" open-type="share" v-if="userStore.isLoggedIn">
          <view class="left">
            <text class="icon">🎁</text>
            <text class="text">每日分享</text>
            <text class="tag" v-if="!userStore.hasSharedToday">领5元券</text>
          </view>
          <view class="right">
            <text class="tip" v-if="userStore.hasSharedToday">已领取</text>
            <image src="/static/enter-arrow.png" class="arrow" />
          </view>
        </button>

        <view class="menu-item" @click="toAddress">
          <view class="left">
            <text class="icon" style="color:#1890ff">📍</text>
            <text class="text">地址管理</text>
          </view>
          <image src="/static/enter-arrow.png" class="arrow" />
        </view>
        <view class="menu-item">
          <view class="left">
            <text class="icon" style="color:#52c41a">🎧</text>
            <text class="text">联系客服</text>
          </view>
          <image src="/static/enter-arrow.png" class="arrow" />
        </view>
      </view>
      
      <!-- Logout Button -->
    <button v-if="userStore.isLoggedIn" class="logout-btn" @click="handleLogout">退出登录</button>
    
    <!-- Delete Account Button -->
    <view v-if="userStore.isLoggedIn" class="delete-account-wrap" @click="handleDeleteAccount">
        <text class="text">注销账号</text>
    </view>
  </view>
  
</view>
</template>

<script setup>
import { useUserStore } from '@/store/user';
import { onShareAppMessage } from '@dcloudio/uni-app';

const userStore = useUserStore();

onShareAppMessage(() => {
    // Simulate reward
    if (!userStore.hasSharedToday) {
        setTimeout(() => {
            userStore.receiveShareCoupon();
            uni.showToast({ title: '分享成功，优惠券已到账', icon: 'none' });
        }, 2000);
    }

    return {
        title: '云顶奶茶，邀您共享美味',
        path: '/pages/index/index',
        imageUrl: 'https://api.dicebear.com/7.x/food/svg?seed=Tea'
    };
});

const toCoupons = () => uni.navigateTo({ url: '/pages/coupons/coupons' });
const toRecharge = () => uni.navigateTo({ url: '/pages/recharge/recharge' });
const toLogin = () => uni.navigateTo({ url: '/pages/login/login' });
const toProfileEdit = () => {
    if (userStore.isLoggedIn) {
        uni.navigateTo({ url: '/pages/profile-edit/profile-edit' });
    }
};
const toAddress = () => {
    if (userStore.isLoggedIn) {
        uni.navigateTo({ url: '/pages/address/address' });
    } else {
        toLogin();
    }
};

const getLevelDiscountText = (level) => {
    switch(level) {
        case 1: return '全场9.5折';
        case 2: return '全场9折';
        default: return '无折扣';
    }
};

const handleLogout = () => {
  uni.showModal({
    title: '提示',
    content: '确定要退出登录吗？',
    success: (res) => {
      if (res.confirm) {
        userStore.logout();
        uni.showToast({ title: '已退出' });
      }
    }
  });
};

const handleDeleteAccount = () => {
    uni.showModal({
        title: '危险操作',
        content: '确定要注销账号吗？注销后所有数据将无法恢复！',
        confirmColor: '#ff4d4f',
        success: async (res) => {
            if (res.confirm) {
                uni.showLoading({ title: '处理中' });
                await userStore.deleteAccount();
                uni.hideLoading();
            }
        }
    });
};
</script>

<style lang="scss" scoped>
.container {
  min-height: 100vh;
  background-color: #f8f8f8;
}

.header-bg {
  height: 300rpx;
  background: #ff6600;
  border-bottom-left-radius: 40rpx;
  border-bottom-right-radius: 40rpx;
}

.content {
  padding: 0 30rpx;
  margin-top: -160rpx;
}

.user-card {
  background: #fff;
  border-radius: 20rpx;
  padding: 40rpx;
  display: flex;
  align-items: center;
  box-shadow: 0 4rpx 16rpx rgba(0,0,0,0.08);
  margin-bottom: 30rpx;
  
  .avatar-wrap {
    position: relative;
    margin-right: 30rpx;
    
    .avatar {
      width: 120rpx;
      height: 120rpx;
      border-radius: 50%;
      border: 4rpx solid #fff;
      background: #eee;
    }
  }
  
  .user-info {
    flex: 1;
    display: flex;
    flex-direction: column;
    
    .nickname {
      font-size: 36rpx;
      font-weight: bold;
      color: #333;
      margin-bottom: 10rpx;
    }
    
    .card-no {
      font-size: 24rpx;
      color: #999;
    }
  }
  
  .arrow-right {
      width: 32rpx;
      height: 32rpx;
      opacity: 0.5;
  }
}

.member-rights-card {
  background: linear-gradient(135deg, #333 0%, #1a1a1a 100%);
  border-radius: 20rpx;
  padding: 30rpx;
  margin-bottom: 30rpx;
  color: #f1c40f;
  box-shadow: 0 4rpx 16rpx rgba(0,0,0,0.1);

  .rights-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 30rpx;
      
      .level-title { font-size: 32rpx; font-weight: bold; }
      .expiry { font-size: 22rpx; opacity: 0.8; }
  }
  
  .rights-list {
      display: flex;
      justify-content: space-between;
      margin-bottom: 30rpx;
      
      .right-item {
          display: flex;
          flex-direction: column;
          align-items: center;
          
          .icon { font-size: 40rpx; margin-bottom: 10rpx; }
          .name { font-size: 24rpx; font-weight: bold; margin-bottom: 6rpx; }
          .desc { font-size: 20rpx; opacity: 0.7; }
      }
  }
  
  .level-rules {
      display: flex;
      background: rgba(255,255,255,0.1);
      border-radius: 12rpx;
      padding: 4rpx;
      
      .rule-item {
          flex: 1;
          display: flex;
          flex-direction: column;
          align-items: center;
          padding: 16rpx 0;
          border-radius: 10rpx;
          opacity: 0.5;
          
          .lvl { font-size: 24rpx; font-weight: bold; margin-bottom: 4rpx; }
          .benefit { font-size: 20rpx; }
          
          &.active {
              background: #f1c40f;
              color: #333;
              opacity: 1;
              box-shadow: 0 2rpx 8rpx rgba(0,0,0,0.2);
          }
      }
  }
}

.assets-card {
  background: #fff;
  border-radius: 20rpx;
  padding: 30rpx;
  display: flex;
  justify-content: space-around;
  margin-bottom: 30rpx;
  box-shadow: 0 2rpx 8rpx rgba(0,0,0,0.05);
  
  .asset-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    
    .num { font-size: 36rpx; font-weight: bold; color: #333; margin-bottom: 8rpx; }
    .label { font-size: 24rpx; color: #999; }
  }
}

.menu-list {
  background: #fff;
  border-radius: 20rpx;
  overflow: hidden;
  box-shadow: 0 2rpx 8rpx rgba(0,0,0,0.05);
  margin-bottom: 40rpx;
  
  .menu-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 30rpx;
    border-bottom: 1px solid #f8f8f8;
    
    // Reset button styles
    &.share-btn {
        background: #fff;
        line-height: normal;
        margin: 0;
        border-radius: 0;
        
        &::after { border: none; }
    }
    
    &:last-child { border-bottom: none; }
    
    .left {
      display: flex;
      align-items: center;
      
      .icon { margin-right: 20rpx; font-size: 36rpx; }
      .text { font-size: 30rpx; color: #333; }
      
      .tag {
          font-size: 20rpx;
          color: #fff;
          background: #ff4d4f;
          padding: 2rpx 8rpx;
          border-radius: 8rpx;
          margin-left: 12rpx;
      }
    }
    
    .right {
        display: flex;
        align-items: center;
        
        .tip { font-size: 24rpx; color: #999; margin-right: 10rpx; }
    }
    
    .arrow { width: 32rpx; height: 32rpx; opacity: 0.5; }
  }
}

.logout-btn {
  background: #eee;
  color: #666;
  font-size: 30rpx;
  border: none;
  border-radius: 16rpx;
}

.delete-account-wrap {
    margin-top: 30rpx;
    text-align: center;
    padding-bottom: 30rpx;
    
    .text {
        font-size: 24rpx;
        color: #999;
        text-decoration: underline;
    }
}
</style>

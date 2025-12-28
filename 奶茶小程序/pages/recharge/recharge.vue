<template>
  <view class="container">
    <view class="balance-card">
        <text class="label">当前余额</text>
        <text class="amount">¥{{ userStore.userInfo.balance || '0.00' }}</text>
    </view>
    
    <view class="recharge-section">
        <text class="section-title">选择充值金额</text>
        <view class="grid">
            <view 
                v-for="item in rechargeOptions" 
                :key="item.amount"
                class="option-item"
                :class="{ active: selectedAmount === item.amount }"
                @click="selectAmount(item.amount)"
            >
                <text class="price">¥{{ item.amount }}</text>
                <text class="bonus" v-if="item.bonus > 0">送{{ item.bonus }}元</text>
            </view>
        </view>
        
        <view class="custom-amount">
            <text>其他金额：</text>
            <input 
                type="number" 
                v-model.number="customAmount" 
                placeholder="请输入金额" 
                @input="selectedAmount = null"
            />
        </view>
    </view>
    
    <button class="pay-btn" @click="handleRecharge">立即充值</button>
  </view>
</template>

<script setup>
import { ref } from 'vue';
import { useUserStore } from '@/store/user';
import { globalApi } from '@/api/globalApi';

const userStore = useUserStore();
const selectedAmount = ref(null);
const customAmount = ref(null);

const rechargeOptions = [
    { amount: 50, bonus: 0 },
    { amount: 100, bonus: 5 },
    { amount: 200, bonus: 15 },
    { amount: 500, bonus: 50 },
];

const selectAmount = (amount) => {
    selectedAmount.value = amount;
    customAmount.value = null;
};

const handleRecharge = async () => {
    const amount = selectedAmount.value || customAmount.value;
    if (!amount || amount <= 0) {
        uni.showToast({ title: '请输入有效金额', icon: 'none' });
        return;
    }
    
    // Calculate bonus if matching option
    let bonus = 0;
    const option = rechargeOptions.find(o => o.amount === amount);
    if (option) {
        bonus = option.bonus;
    }
    
    uni.showLoading({ title: '充值中' });
    try {
        const newBalance = userStore.userInfo.balance + amount + bonus
        await globalApi['users'].update(null, {
            id: userStore.userInfo.id,
            balance: newBalance
        }, () => {
            userStore.updateBalance(newBalance);
        })
        
        uni.hideLoading();
        uni.showToast({ title: '充值成功' });
        setTimeout(() => uni.navigateBack(), 1500);
    } catch (e) {
        uni.hideLoading();
        uni.showToast({ title: '充值失败', icon: 'none' });
    }
};
</script>

<style lang="scss" scoped>
.container {
    min-height: 100vh;
    background: #f8f8f8;
    padding: 30rpx;
}

.balance-card {
    background: linear-gradient(135deg, #ff9a9e 0%, #ff6600 100%);
    color: #fff;
    border-radius: 20rpx;
    padding: 60rpx;
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 40rpx;
    box-shadow: 0 8rpx 20rpx rgba(255, 102, 0, 0.2);
    
    .label { font-size: 28rpx; opacity: 0.9; margin-bottom: 10rpx; }
    .amount { font-size: 60rpx; font-weight: bold; }
}

.recharge-section {
    background: #fff;
    border-radius: 20rpx;
    padding: 30rpx;
    
    .section-title {
        font-size: 32rpx;
        font-weight: bold;
        margin-bottom: 30rpx;
        display: block;
    }
    
    .grid {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        
        .option-item {
            width: 48%;
            border: 1px solid #ddd;
            border-radius: 12rpx;
            padding: 30rpx 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-bottom: 24rpx;
            
            &.active {
                border-color: #ff6600;
                background: #fff5e6;
                color: #ff6600;
            }
            
            .price { font-size: 36rpx; font-weight: bold; margin-bottom: 6rpx; }
            .bonus { font-size: 24rpx; color: #ff6600; }
        }
    }
    
    .custom-amount {
        display: flex;
        align-items: center;
        margin-top: 20rpx;
        padding-top: 20rpx;
        border-top: 1px solid #f5f5f5;
        
        input {
            flex: 1;
            margin-left: 20rpx;
            font-size: 30rpx;
        }
    }
}

.pay-btn {
    margin-top: 60rpx;
    background: #07c160;
    color: #fff;
    border-radius: 50rpx;
    font-weight: bold;
}
</style>

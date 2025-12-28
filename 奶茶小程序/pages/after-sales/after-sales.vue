<template>
  <view class="container">
    <view class="order-info">
        <text class="label">订单编号：</text>
        <text class="value">{{ orderId }}</text>
    </view>

    <view class="status-bar" v-if="status">
        <text class="status-text">{{ getStatusText(status) }}</text>
        <text class="status-desc" v-if="status === 3">商家已拒绝，请修改后重新提交</text>
    </view>

    <view class="type-select" :class="{ disabled: status === 1 || status === 2 }">
        <view class="section-title">选择售后类型</view>
        <view class="options">
            <view class="option" :class="{active: type === 1}" @click="status !== 1 && status !== 2 && (type = 1)">
                <text class="icon">💰</text>
                <text>申请退款</text>
            </view>
            <view class="option" :class="{active: type === 2}" @click="status !== 1 && status !== 2 && (type = 2)">
                <text class="icon">📝</text>
                <text>投诉建议</text>
            </view>
        </view>
    </view>

    <view class="form-content">
        <view class="section-title">问题描述</view>
        <textarea 
            class="textarea" 
            v-model="reason" 
            placeholder="请详细描述您遇到的问题..." 
            maxlength="200"
            :disabled="status === 1 || status === 2"
        ></textarea>
        <text class="word-count">{{ reason.length }}/200</text>
        
        <view class="upload-box">
            <view class="upload-btn" @click="chooseImage" v-if="status !== 1 && status !== 2">
                <text class="plus">+</text>
                <text class="text">上传凭证</text>
            </view>
            <view v-for="(img, index) in images" :key="index" class="img-preview">
                <image :src="img" mode="aspectFill" class="img" />
                <text class="del" @click="removeImage(index)" v-if="status !== 1 && status !== 2">×</text>
            </view>
        </view>
    </view>

    <button class="submit-btn" @click="submit" v-if="!status || status === 3">提交申请</button>
    <button class="submit-btn cancel" @click="cancel" v-if="status === 1">取消申请</button>
  </view>
</template>

<script setup>
import { ref } from 'vue';
import { onLoad } from '@dcloudio/uni-app';
import { globalApi } from '@/api/globalApi';
import { upload } from '@/api/file.js';

const orderId = ref('');
const type = ref(1); // refund | complaint
const reason = ref('');
const images = ref([]);
const status = ref();
const recordId = ref();

onLoad((options) => {
    if (options.orderId) {
        orderId.value = options.orderId;
        globalApi['after-sales'].get(null, {
            orderId: orderId.value
        },(res) => {
            const data = res[0]
            if(data){
                recordId.value = data.id;
                type.value = data.type;
                images.value = data.images || [];
                reason.value = data.description || '';
                status.value = data.status; // 1: Pending, 2: Approved, 3: Rejected
            }
        })
    }
});

const getStatusText = (s) => {
    switch(s) {
        case 1: return '待审批';
        case 2: return '已同意';
        case 3: return '已拒绝';
        default: return '';
    }
};

const chooseImage = () => {
    if (status.value === 1 || status.value === 2) return;
    uni.chooseImage({
        count: 3 - images.value.length,
        sizeType: ['original', 'compressed'],
        sourceType: ['album', 'camera'],
        success: async (res) => {
            uni.showLoading({ title: '上传中...', mask: true });
            try {
                const uploadTasks = res.tempFiles.map(file => {
                    return upload([{
                        ...file,
                        name: Date.now() + '-aftersales'
                    }]);
                });
                
                const results = await Promise.all(uploadTasks);
                const newImages = results
                    .filter(r => r.code === 200 && r.data && r.data.length > 0)
                    .map(r => r.data[0]);
                
                if (newImages.length > 0) {
                    images.value = [...images.value, ...newImages];
                } else {
                    uni.showToast({ title: '图片上传失败', icon: 'none' });
                }
            } catch (e) {
                console.error(e);
                uni.showToast({ title: '上传异常', icon: 'none' });
            } finally {
                uni.hideLoading();
            }
        }
    });
};

const removeImage = (index) => {
    if (status.value === 1 || status.value === 2) return;
    images.value.splice(index, 1);
};

const cancel = () => {
    uni.showModal({
        title: '提示',
        content: '确定要取消售后申请吗？',
        success: (res) => {
            if (res.confirm) {
                globalApi['after-sales'].delete(null, {id: recordId.value}, () => {
                    uni.showToast({ title: '已取消' });
                    setTimeout(() => {
                        uni.navigateBack();
                    }, 1500);
                });
            }
        }
    });
};

const submit = () => {
    if (!reason.value.trim()) {
        uni.showToast({ title: '请输入问题描述', icon: 'none' });
        return;
    }
    
    uni.showLoading({ title: '提交中' });
    
    const data = {
        orderId: orderId.value,
        type: type.value,
        description: reason.value,
        images: images.value,
        status: 1 // Reset to pending
    };

    const apiCall = recordId.value 
        ? globalApi['after-sales'].update({pk1: recordId.value}, data) 
        : globalApi['after-sales'].add(null, data);

    apiCall.then(res => {
        uni.hideLoading();
        uni.showToast({ title: '提交成功' });
        setTimeout(() => {
            uni.navigateBack();
        }, 1500);
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

.status-bar {
    background: #e6f7ff;
    border: 1px solid #91d5ff;
    padding: 20rpx;
    border-radius: 12rpx;
    margin-bottom: 30rpx;
    display: flex;
    justify-content: space-between;
    align-items: center;
    
    .status-text { color: #1890ff; font-weight: bold; }
    .status-desc { font-size: 24rpx; color: #666; }
}

.order-info {
    background: #fff;
    padding: 24rpx;
    border-radius: 12rpx;
    margin-bottom: 30rpx;
    font-size: 28rpx;
    color: #666;
    
    .value { color: #333; font-weight: bold; margin-left: 10rpx; }
}

.section-title {
    font-size: 30rpx;
    font-weight: bold;
    color: #333;
    margin-bottom: 20rpx;
}

.type-select {
    background: #fff;
    padding: 30rpx;
    border-radius: 12rpx;
    margin-bottom: 30rpx;
    
    &.disabled { opacity: 0.7; pointer-events: none; }
    
    .options {
        display: flex;
        gap: 30rpx;
        
        .option {
            flex: 1;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 30rpx;
            border: 2px solid #eee;
            border-radius: 12rpx;
            color: #666;
            font-size: 28rpx;
            
            .icon { font-size: 48rpx; margin-bottom: 10rpx; }
            
            &.active {
                border-color: #ff6600;
                color: #ff6600;
                background: #fff5e6;
            }
        }
    }
}

.form-content {
    background: #fff;
    padding: 30rpx;
    border-radius: 12rpx;
    margin-bottom: 60rpx;
    
    .textarea {
        width: 100%;
        height: 200rpx;
        background: #f9f9f9;
        padding: 20rpx;
        border-radius: 8rpx;
        font-size: 28rpx;
        box-sizing: border-box;
    }
    
    .word-count {
        display: block;
        text-align: right;
        font-size: 24rpx;
        color: #999;
        margin: 10rpx 0 30rpx;
    }
    
    .upload-box {
        display: flex;
        flex-wrap: wrap;
        gap: 20rpx;
        
        .upload-btn {
            width: 160rpx;
            height: 160rpx;
            background: #f9f9f9;
            border: 1px dashed #ddd;
            border-radius: 8rpx;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            color: #999;
            
            .plus { font-size: 60rpx; line-height: 1; margin-bottom: 10rpx; }
            .text { font-size: 24rpx; }
        }
        
        .img-preview {
            width: 160rpx;
            height: 160rpx;
            position: relative;
            
            .img { width: 100%; height: 100%; border-radius: 8rpx; }
            .del {
                position: absolute;
                top: -10rpx;
                right: -10rpx;
                width: 36rpx;
                height: 36rpx;
                background: rgba(0,0,0,0.5);
                color: #fff;
                border-radius: 50%;
                text-align: center;
                line-height: 34rpx;
                font-size: 24rpx;
            }
        }
    }
}

.submit-btn {
    background: #ff6600;
    color: #fff;
    border-radius: 44rpx;
    font-size: 32rpx;
    font-weight: bold;
    
    &.cancel {
        background: #fff;
        color: #666;
        border: 1px solid #ddd;
        margin-top: 20rpx;
    }
}
</style>
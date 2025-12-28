<template>
  <view class="modal-mask" v-if="visible" @click.stop="close">
    <view class="modal-content" @click.stop>
      <!-- Top Image -->
      <view class="product-image-container">
        <image :src="product.image" mode="aspectFill" class="product-image" />
        <view class="close-btn" @click="close">
          <text class="icon-close">×</text>
        </view>
      </view>

      <!-- Content -->
      <scroll-view scroll-y class="product-info">
        <text class="product-name">{{ product.name }}</text>
        <text class="product-desc">{{ product.description }}</text>

        <!-- Specs -->
        <view class="specs-container">
          <view v-for="(spec, idx) in product.specs" :key="idx" class="spec-group">
            <text class="spec-title">{{ spec.name }}</text>
            <view class="spec-options">
              <view 
                v-for="opt in spec.option" 
                :key="opt"
                class="spec-option"
                :class="{ active: currentSpecs[spec.name] === opt }"
                @click="selectSpec(spec.name, opt)"
              >
                <text>{{ opt }}</text>
              </view>
            </view>
          </view>
        </view>
      </scroll-view>

      <!-- Footer Action -->
      <view class="action-bar">
        <view class="price-info">
          <text class="price">¥{{ calculatePrice }}</text>
          <text class="selected-specs">{{ selectedSpecString }}</text>
        </view>
        <view class="action-right">
          <view class="stepper">
            <view class="step-btn" @click="changeQty(-1)">-</view>
            <text class="qty">{{ qty }}</text>
            <view class="step-btn" @click="changeQty(1)">+</view>
          </view>
          <view class="add-btn" @click="handleAddToCart">加入购物袋</view>
        </view>
      </view>
    </view>
  </view>
</template>

<script setup>
import { ref, computed, watch, reactive } from 'vue';

const props = defineProps({
  visible: Boolean,
  product: Object
});

const emit = defineEmits(['update:visible', 'add-to-cart']);

const qty = ref(1);
const currentSpecs = reactive({});

// Watch product change to reset specs
watch(() => props.product, (newVal) => {
  if (newVal && newVal.specs) {
    qty.value = 1;
    newVal.specs.forEach(s => {
      s.option = JSON.parse(s.option);
      currentSpecs[s.name] = s.option[0];
    });
  }
}, { immediate: true });

const selectedSpecString = computed(() => Object.values(currentSpecs).join('/'));
const calculatePrice = computed(() => props.product ? (props.product.price * qty.value).toFixed(2) : 0);

const close = () => {
  emit('update:visible', false);
};

const selectSpec = (name, opt) => {
  currentSpecs[name] = opt;
};

const changeQty = (delta) => {
  const newVal = qty.value + delta;
  if (newVal >= 1) qty.value = newVal;
};

const handleAddToCart = () => {
  emit('add-to-cart', {
    product: props.product,
    specs: selectedSpecString.value,
    qty: qty.value
  });
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
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 1001; // Above CartBar (1000)
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
}

.modal-content {
  background-color: #fff;
  border-top-left-radius: 24rpx;
  border-top-right-radius: 24rpx;
  overflow: hidden;
  height: 80vh;
  display: flex;
  flex-direction: column;
}

.product-image-container {
  position: relative;
  height: 400rpx;
  
  .product-image {
    width: 100%;
    height: 100%;
  }
  
  .close-btn {
    position: absolute;
    top: 20rpx;
    left: 20rpx;
    width: 60rpx;
    height: 60rpx;
    background: rgba(0,0,0,0.3);
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    
    .icon-close {
      color: #fff;
      font-size: 40rpx;
      line-height: 1;
    }
  }
}

.product-info {
  flex: 1;
  padding: 30rpx;
  box-sizing: border-box;
}

.product-name {
  font-size: 36rpx;
  font-weight: bold;
  color: #333;
  display: block;
}

.product-desc {
  font-size: 24rpx;
  color: #999;
  margin-top: 10rpx;
  display: block;
}

.specs-container {
  margin-top: 40rpx;
}

.spec-group {
  margin-bottom: 30rpx;
  
  .spec-title {
    font-size: 28rpx;
    font-weight: bold;
    color: #666;
    margin-bottom: 20rpx;
    display: block;
  }
  
  .spec-options {
    display: flex;
    flex-wrap: wrap;
    gap: 20rpx;
  }
  
  .spec-option {
    padding: 10rpx 30rpx;
    background: #f5f5f5;
    border: 1px solid #eee;
    border-radius: 8rpx;
    font-size: 26rpx;
    color: #666;
    
    &.active {
      background: #fff5e6;
      border-color: #ff6600;
      color: #ff6600;
      font-weight: bold;
    }
  }
}

.action-bar {
  padding: 20rpx 30rpx 40rpx; // Extra padding for safe area
  background: #fff;
  border-top: 1px solid #eee;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.price-info {
  .price {
    font-size: 40rpx;
    font-weight: bold;
    color: #ff6600;
    display: block;
  }
  .selected-specs {
    font-size: 22rpx;
    color: #999;
  }
}

.action-right {
  display: flex;
  align-items: center;
  gap: 20rpx;
}

.stepper {
  display: flex;
  align-items: center;
  background: #f5f5f5;
  border-radius: 8rpx;
  
  .step-btn {
    width: 60rpx;
    height: 60rpx;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 30rpx;
    color: #666;
  }
  
  .qty {
    width: 40rpx;
    text-align: center;
    font-weight: bold;
    font-size: 28rpx;
  }
}

.add-btn {
  background: #ff6600;
  color: #fff;
  padding: 16rpx 40rpx;
  border-radius: 40rpx;
  font-size: 28rpx;
  font-weight: bold;
}
</style>

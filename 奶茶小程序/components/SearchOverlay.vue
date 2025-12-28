<template>
  <view v-if="visible" class="search-overlay">
    <!-- Search Header -->
    <view class="search-header">
      <view class="search-box">
        <text class="search-icon">🔍</text>
        <input 
            v-model="keyword" 
            class="search-input" 
            placeholder="搜索商品" 
            confirm-type="search"
            :focus="true"
            @confirm="onSearch"
            @input="onInput"
        />
        <text v-if="keyword" class="clear-icon" @click="clearKeyword">✕</text>
      </view>
      <text class="cancel-btn" @click="close">取消</text>
    </view>

    <scroll-view scroll-y class="search-content">
        <!-- Search Results -->
        <view v-if="keyword && showResults" class="result-list">
            <view v-if="loading" class="loading-state">
                <text>搜索中...</text>
            </view>
            <view v-else-if="results.length === 0" class="empty-result">
                <text>未找到相关商品</text>
            </view>
            <view v-else class="product-list">
                 <view 
                    v-for="prod in results" 
                    :key="prod.id" 
                    class="product-item"
                    @click="openProduct(prod)"
                  >
                    <image :src="prod.image" mode="aspectFill" class="prod-img" />
                    <view class="prod-info">
                      <view>
                        <rich-text :nodes="highlightName(prod.name)" class="name"></rich-text>
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
        </view>

        <!-- Search History & Hot Search -->
        <view v-else>
            <!-- History -->
            <view class="section" v-if="history.length > 0">
                <view class="section-header">
                    <text class="title">历史搜索</text>
                    <text class="delete-icon" @click="clearHistory">🗑️</text>
                </view>
                <view class="tags">
                    <view v-for="(item, index) in history" :key="index" class="tag-item" @click="quickSearch(item)">
                        {{ item }}
                    </view>
                </view>
            </view>

            <!-- Hot Search -->
            <view class="section">
                <view class="section-header">
                    <text class="title">热门搜索</text>
                </view>
                <view class="tags">
                    <view v-for="(item, index) in hotSearches" :key="index" class="tag-item hot" @click="quickSearch(item)">
                        <text class="fire-icon" v-if="index < 3">🔥</text>
                        {{ item }}
                    </view>
                </view>
            </view>
        </view>
    </scroll-view>
  </view>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { api } from '@/api/mock';

const props = defineProps({
    visible: Boolean,
    allProducts: {
        type: Array,
        default: () => []
    }
});

const emit = defineEmits(['update:visible', 'open-product']);

const keyword = ref('');
const history = ref([]);
const hotSearches = ref([]);
const results = ref([]);
const showResults = ref(false);
const loading = ref(false);

onMounted(() => {
    loadHistory();
    loadHotSearches();
});

// Auto refresh hot searches every 2 hours
setInterval(() => {
    loadHotSearches();
}, 2 * 60 * 60 * 1000);

const loadHistory = () => {
    const saved = uni.getStorageSync('search_history');
    if (saved) {
        history.value = JSON.parse(saved);
    }
};

const loadHotSearches = async () => {
    try {
        const data = await api.getHotSearches();
        hotSearches.value = data.slice(0, 8);
    } catch (e) {
        console.error(e);
    }
};

const saveHistory = (kw) => {
    if (!kw) return;
    let newHistory = [...history.value];
    // Remove if exists
    const index = newHistory.indexOf(kw);
    if (index > -1) {
        newHistory.splice(index, 1);
    }
    // Add to front
    newHistory.unshift(kw);
    // Keep 10
    if (newHistory.length > 10) {
        newHistory = newHistory.slice(0, 10);
    }
    history.value = newHistory;
    uni.setStorageSync('search_history', JSON.stringify(newHistory));
};

const clearHistory = () => {
    uni.showModal({
        title: '提示',
        content: '确定清空历史记录吗？',
        success: (res) => {
            if (res.confirm) {
                history.value = [];
                uni.removeStorageSync('search_history');
            }
        }
    });
};

const close = () => {
    emit('update:visible', false);
    keyword.value = '';
    showResults.value = false;
};

const clearKeyword = () => {
    keyword.value = '';
    showResults.value = false;
};

const onInput = (e) => {
    // Real-time search debounce could be added here
    const val = e.detail.value;
    if (!val) {
        showResults.value = false;
        return;
    }
    performSearch(val);
};

const onSearch = () => {
    if (!keyword.value.trim()) return;
    saveHistory(keyword.value.trim());
    performSearch(keyword.value);
};

const quickSearch = (kw) => {
    keyword.value = kw;
    saveHistory(kw);
    performSearch(kw);
};

const performSearch = (kw) => {
    if (!kw) return;
    loading.value = true;
    showResults.value = true;
    
    // Simulate network delay
    setTimeout(() => {
        const lowerKw = kw.toLowerCase();
        results.value = props.allProducts.filter(p => 
            p.name.toLowerCase().includes(lowerKw) || 
            p.description.toLowerCase().includes(lowerKw)
        );
        loading.value = false;
    }, 300);
};

const openProduct = (prod) => {
    emit('open-product', prod);
};

const addToCart = (prod) => {
    // For direct add to cart from search list
    // Since we need to open detail to choose specs, let's just open product
    // Or if we want to bypass, we need default specs. 
    // Usually search list item click opens detail.
    // The user said "search products cannot be added to cart", implies maybe the button + doesn't work or clicking item doesn't work.
    // The current template has @click="openProduct(prod)" on .product-item.
    // But .add-btn inside doesn't have stopPropagation.
    // Let's add @click.stop="openProduct(prod)" to add-btn as well, or just let it bubble.
    // Wait, the user said "cannot return", let's fix z-index first.
    // And "cannot add to cart".
    // If clicking the item opens the detail popup, then user can add to cart from there.
    // If the detail popup is BEHIND the search overlay, then user can't interact.
    // SearchOverlay z-index is 999.
    // ProductDetail needs to be higher or SearchOverlay needs to close.
    // If we close search overlay, user loses search context.
    // Better to make ProductDetail z-index higher (e.g. 1000).
};

const highlightName = (name) => {
    if (!keyword.value) return [{ type: 'text', text: name }];
    
    const parts = name.split(new RegExp(`(${keyword.value})`, 'gi'));
    return parts.map(part => {
        if (part.toLowerCase() === keyword.value.toLowerCase()) {
            return {
                name: 'span',
                attrs: { style: 'color: #ff6600; font-weight: bold;' },
                children: [{ type: 'text', text: part }]
            };
        }
        return { type: 'text', text: part };
    });
};

</script>

<style lang="scss" scoped>
.search-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: #fff;
    z-index: 999;
    display: flex;
    flex-direction: column;
}

.search-header {
    display: flex;
    align-items: center;
    padding: 20rpx 30rpx;
    padding-right: 200rpx; // Avoid overlapping with Mini Program capsule button
    padding-top: calc(20rpx + var(--status-bar-height)); // Adapt to status bar
    border-bottom: 1px solid #f5f5f5;
    background: #fff;
    
    .search-box {
        flex: 1;
        height: 72rpx;
        background: #f5f5f5;
        border-radius: 36rpx;
        display: flex;
        align-items: center;
        padding: 0 24rpx;
        margin-right: 20rpx;
        
        .search-icon { font-size: 28rpx; color: #999; margin-right: 12rpx; }
        
        .search-input {
            flex: 1;
            font-size: 28rpx;
            color: #333;
        }
        
        .clear-icon { font-size: 28rpx; color: #999; padding: 10rpx; }
    }
    
    .cancel-btn {
        font-size: 30rpx;
        color: #666;
        padding: 20rpx; // Add click area
    }
}

.search-content {
    flex: 1;
    padding: 30rpx;
    box-sizing: border-box;
}

.section {
    margin-bottom: 50rpx;
    
    .section-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 24rpx;
        
        .title { font-size: 30rpx; font-weight: bold; color: #333; }
        .delete-icon { font-size: 32rpx; opacity: 0.6; }
    }
    
    .tags {
        display: flex;
        flex-wrap: wrap;
        gap: 20rpx;
        
        .tag-item {
            padding: 12rpx 24rpx;
            background: #f5f5f5;
            border-radius: 30rpx;
            font-size: 26rpx;
            color: #666;
            
            &.hot {
                color: #333;
                .fire-icon { margin-right: 8rpx; font-size: 24rpx; }
            }
        }
    }
}

.product-item {
  display: flex;
  margin-bottom: 30rpx;
  
  .prod-img {
    width: 140rpx;
    height: 140rpx;
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
      font-size: 28rpx;
      font-weight: bold;
      color: #333;
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
        width: 44rpx;
        height: 44rpx;
        background-color: #ff6600;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        
        .plus {
          color: #fff;
          font-size: 30rpx;
          line-height: 1;
          margin-top: -4rpx;
        }
      }
    }
  }
}

.loading-state, .empty-result {
    padding-top: 100rpx;
    text-align: center;
    color: #999;
    font-size: 28rpx;
}
</style>
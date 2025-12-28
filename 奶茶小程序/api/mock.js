// 模拟数据源
const MockDB = {
    user: {
        id: 1,
        username: "user001",
        nickname: "奶茶达人",
        avatar: "https://api.dicebear.com/7.x/notionists/svg?seed=Felix",
        level: 2, // 0:普通, 1:黄金, 2:钻石
        levelName: "钻石会员",
        points: 1250,
        balance: 58.00,
        coupons: 3,
        couponList: [
            { id: 1, type: 'discount', value: 0.8, name: '8折优惠券', desc: '全场通用，最高抵扣20元', minAmount: 0 },
            { id: 2, type: 'cash', value: 5, name: '5元代金券', desc: '满30元可用', minAmount: 30 },
            { id: 3, type: 'cash', value: 10, name: '10元新人券', desc: '无门槛', minAmount: 0 }
        ]
    },
    categories: [
        { id: 'c1', name: '人气必喝' },
        { id: 'c2', name: '鲜果茶' },
        { id: 'c3', name: '纯茶' },
        { id: 'c4', name: '咖啡' }
    ],
    products: [
        { 
            id: 'p1', catId: 'c1', name: '烤黑糖波波牛乳', desc: '每日现煮黑糖波波，浓郁牛乳', price: 19, 
            image: 'https://images.unsplash.com/photo-1576092768241-dec231844f66?ixlib=rb-4.0.3&auto=format&fit=crop&w=300&q=80',
            specs: [
                { name: '温度', options: ['正常冰', '少冰', '去冰', '热'] },
                { name: '糖度', options: ['标准糖', '七分糖', '三分糖', '不另加糖'] }
            ]
        },
        { 
            id: 'p2', catId: 'c1', name: '多肉葡萄冻', desc: '手剥新鲜葡萄，清爽茶底', price: 28, 
            image: 'https://images.unsplash.com/photo-1623592576505-18b62569e578?ixlib=rb-4.0.3&auto=format&fit=crop&w=300&q=80',
            specs: [
                { name: '温度', options: ['正常冰', '少冰'] },
                { name: '糖度', options: ['标准糖', '少糖'] }
            ]
        },
        { id: 'p3', catId: 'c2', name: '满杯红柚', desc: '满满维C，清爽解腻', price: 22, image: 'https://images.unsplash.com/photo-1546173159-315724a31696?ixlib=rb-4.0.3&auto=format&fit=crop&w=300&q=80', specs: [{ name: '糖度', options: ['标准', '半糖'] }] },
        { id: 'p4', catId: 'c3', name: '高山四季春', desc: '清新回甘，高山原叶', price: 12, image: 'https://images.unsplash.com/photo-1627435601361-ec25f5b1d0e5?ixlib=rb-4.0.3&auto=format&fit=crop&w=300&q=80', specs: [{ name: '温度', options: ['冰', '热'] }] }
    ],
    addresses: [
        { id: 1, name: '张三', phone: '138****0000', location: '腾讯大厦', detail: '20楼前台', tag: '公司' },
        { id: 2, name: '张三', phone: '138****0000', location: '万象天地', detail: 'A座302', tag: '家' }
    ],
    banners: [
        { img: 'https://images.unsplash.com/photo-1563823253396-8d6c4c969324?ixlib=rb-4.0.3&auto=format&fit=crop&w=600&q=80' },
        { img: 'https://images.unsplash.com/photo-1497534446932-c925b458314e?ixlib=rb-4.0.3&auto=format&fit=crop&w=600&q=80' },
        { img: 'https://images.unsplash.com/photo-1556679343-c7306c1976bc?ixlib=rb-4.0.3&auto=format&fit=crop&w=600&q=80' }
    ],
    stores: [
        { id: 1, name: '云顶奶茶 (科技园店)', address: '南山区科技园南区W1-B栋', distance: '1.2km', status: '营业中' },
        { id: 2, name: '云顶奶茶 (万象天地店)', address: '南山区深南大道9668号', distance: '3.5km', status: '营业中' },
        { id: 3, name: '云顶奶茶 (海岸城店)', address: '南山区文心五路33号', distance: '5.8km', status: '营业中' },
        { id: 4, name: '云顶奶茶 (世界之窗店)', address: '南山区深南大道9028号', distance: '4.2km', status: '休息中' }
    ],
    hotSearches: [
        '波波', '葡萄', '红柚', '四季春', '奶茶', '咖啡', '新品', '优惠'
    ]
};

// 模拟 API
export const api = {
    getBanners: () => Promise.resolve(MockDB.banners),
    getCategories: () => Promise.resolve(MockDB.categories),
    getProducts: () => Promise.resolve(MockDB.products),
    getUserInfo: () => Promise.resolve(MockDB.user),
    getAddresses: () => Promise.resolve(MockDB.addresses),
    getStores: () => Promise.resolve(MockDB.stores),
    getHotSearches: () => Promise.resolve(MockDB.hotSearches),
    
    // 模拟登录
    login: (username, password) => {
        return new Promise((resolve, reject) => {
            setTimeout(() => {
                if (username) {
                    resolve(MockDB.user);
                } else {
                    reject('用户名不能为空');
                }
            }, 500);
        });
    },

    // 模拟提交订单
    submitOrder: (orderData) => {
        return new Promise((resolve) => {
            setTimeout(() => {
                const now = Date.now();
                const isPickup = orderData.orderType === 'pickup';
                
                resolve({
                    id: 'OD' + now,
                    ...orderData,
                    status: 0, // 0: 待接单
                    statusText: '待接单',
                    createTime: now,
                    // Pickup specific fields
                    pickupCode: isPickup ? Math.floor(Math.random() * 9000) + 1000 : null,
                    estimatedTime: now + (isPickup ? 15 * 60000 : 30 * 60000), // +15min or +30min
                    counterNo: isPickup ? 'A' + Math.floor(Math.random() * 10) : null,
                    storeAddress: isPickup ? (orderData.store ? orderData.store.address : '南山区科技园南区W1-B栋') : null,
                    // Delivery specific fields
                    riderName: !isPickup ? '李骑手' : null,
                    riderPhone: !isPickup ? '13800138000' : null,
                    deliveryStatus: !isPickup ? 'waiting_rider' : null // waiting_rider, delivering, arrived
                });
            }, 1000);
        });
    },

    // 获取历史订单 (模拟数据)
    getOrders: () => {
        return Promise.resolve([
            {
                id: 'OD_HIST_1',
                orderType: 'pickup',
                status: 3,
                statusText: '已完成',
                createTime: Date.now() - 86400000,
                finalAmount: 28.00,
                totalCount: 1,
                items: [{ name: '多肉葡萄冻', price: 28, qty: 1 }],
                storeAddress: '南山区科技园南区W1-B栋',
                storeName: '云顶奶茶 (科技园店)',
                pickupCode: 1001
            },
            {
                id: 'OD_REJ_1',
                orderType: 'delivery',
                status: 4,
                statusText: '商家拒单',
                createTime: Date.now() - 172800000,
                finalAmount: 52.00,
                totalCount: 2,
                items: [{ name: '烤黑糖波波牛乳', price: 19, qty: 1 }, { name: '满杯红柚', price: 22, qty: 1 }],
                address: { location: '腾讯大厦', detail: '20楼' },
                storeName: '云顶奶茶 (智慧云店)',
                rejectReason: '超出配送范围'
            }
        ]);
    },

    // 保存地址 (新增或更新)
    saveAddress: (addr) => {
        return new Promise((resolve) => {
            setTimeout(() => {
                if (addr.id) {
                    // Update
                    const index = MockDB.addresses.findIndex(a => a.id === addr.id);
                    if (index !== -1) {
                        MockDB.addresses[index] = { ...addr };
                    }
                } else {
                    // Create
                    MockDB.addresses.push({
                        id: Date.now(),
                        ...addr
                    });
                }
                resolve(true);
            }, 500);
        });
    },

    // 充值
    recharge: (amount, bonus) => {
        return new Promise((resolve) => {
            setTimeout(() => {
                const current = parseFloat(MockDB.user.balance);
                const addAmount = parseFloat(amount);
                const addBonus = parseFloat(bonus);
                MockDB.user.balance = parseFloat((current + addAmount + addBonus).toFixed(2));
                resolve(MockDB.user.balance);
            }, 1000);
        });
    },
    
    // 更新用户信息 (模拟)
    updateUserInfo: (data) => {
        return new Promise((resolve) => {
            setTimeout(() => {
                MockDB.user = { ...MockDB.user, ...data };
                resolve(MockDB.user);
            }, 500);
        });
    }
};

import { defineStore } from 'pinia';
import { globalApi } from '@/api/globalApi/index.js';
import Store from './index.js';

export const useUserStore = defineStore('user', {
    state: () => ({
        userInfo: {},
        isLoggedIn: false,
        token: null,
        hasSharedToday: false
    }),
    getters: {
        levelName: (state) => state.userInfo.levelName || '未登录',
        points: (state) => state.userInfo.points || 0
    },
    actions: {
        async deleteAccount() {
            if (!this.userInfo.id) return false;
            
            try {
                // Call DELETE /globalApi/users/:id
                await globalApi['users'].delete(null, { id: this.userInfo.id }, (res) => {
                    // 1. Clear local state
                    this.logout();
                    
                    // 2. Show toast
                    uni.showToast({ title: '账户已注销', icon: 'none' });
                    
                    // 3. Redirect to home or login
                    uni.reLaunch({ url: '/pages/index/index' });
                });
                return true;
            } catch (e) {
                console.error('Delete account failed:', e);
                uni.showToast({ title: '注销失败，请稍后重试', icon: 'none' });
                return false;
            }
        },
        async register(username, password, nickname) {
            try {
                await globalApi['auth'].register({username, password, nickname}, (res) => {
                });
                return true;
            } catch (e) {
                console.error(e);
                throw e; // Rethrow to handle in component
            }
        },
        async login(username, password) {
            try {
                await globalApi['auth'].login({username : username, password : password}, (res) => {
                    this.token = res.token;
                    this.isLoggedIn = true;
                    Store.set('user', res);
                    Store.set('easyToken', res.token);
                    
                    globalApi['users'].get(null, {id: res.id}, (res) => {
                        this.userInfo = {
                            ...res[0],
                            avatar: res[0].avatar || "https://api.dicebear.com/7.x/notionists/svg?seed=Felix",
                            levelName: res[0].memberCard.cardName,
                            points: res[0].totalPoints || 0,
                            level: res[0].memberCard.level || 0,
                            cardId: res[0].memberCard.id || 0,
                            coupons: res[0].couponList?.length || 0
                        };
                        this.isLoggedIn = true;
                    });
                });
            } catch (e) {
                console.error(e);
                return false;
            }
        },
        logout() {
            this.userInfo = {};
            this.isLoggedIn = false;
        },
        updateUserInfo(data) {
            if (this.userInfo) {
                this.userInfo = { ...this.userInfo, ...data };
                // Also update local storage if needed, or rely on next fetch
            }
        },
        updatePoints(points) {
            if (this.userInfo) {
                this.userInfo.points = points;
            }
        },
        updateBalance(balance) {
            if (this.userInfo) {
                this.userInfo.balance = balance;
            }
        },
        receiveShareCoupon() {
            if (!this.hasSharedToday && this.userInfo) {
                this.hasSharedToday = true;
                // Add coupon logic (mock)
                this.userInfo.coupons = (this.userInfo.coupons || 0) + 1;
                // In real app, call API to add coupon
            }
        }
    }
});

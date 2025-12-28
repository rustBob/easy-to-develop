"use strict";
const common_vendor = require("../common/vendor.js");
const api_globalApi_index = require("../api/globalApi/index.js");
const store_index = require("./index.js");
const useUserStore = common_vendor.defineStore("user", {
  state: () => ({
    userInfo: {},
    isLoggedIn: false,
    token: null,
    hasSharedToday: false
  }),
  getters: {
    levelName: (state) => state.userInfo.levelName || "未登录",
    points: (state) => state.userInfo.points || 0
  },
  actions: {
    async deleteAccount() {
      if (!this.userInfo.id)
        return false;
      try {
        await api_globalApi_index.globalApi["users"].delete(null, { id: this.userInfo.id }, (res) => {
          this.logout();
          common_vendor.index.showToast({ title: "账户已注销", icon: "none" });
          common_vendor.index.reLaunch({ url: "/pages/index/index" });
        });
        return true;
      } catch (e) {
        common_vendor.index.__f__("error", "at store/user.js:34", "Delete account failed:", e);
        common_vendor.index.showToast({ title: "注销失败，请稍后重试", icon: "none" });
        return false;
      }
    },
    async register(username, password, nickname) {
      try {
        await api_globalApi_index.globalApi["auth"].register({ username, password, nickname }, (res) => {
        });
        return true;
      } catch (e) {
        common_vendor.index.__f__("error", "at store/user.js:45", e);
        throw e;
      }
    },
    async login(username, password) {
      try {
        await api_globalApi_index.globalApi["auth"].login({ username, password }, (res) => {
          this.token = res.token;
          this.isLoggedIn = true;
          store_index.Store.set("user", res);
          store_index.Store.set("easyToken", res.token);
          api_globalApi_index.globalApi["users"].get(null, { id: res.id }, (res2) => {
            var _a;
            this.userInfo = {
              ...res2[0],
              avatar: res2[0].avatar || "https://api.dicebear.com/7.x/notionists/svg?seed=Felix",
              levelName: res2[0].memberCard.cardName,
              points: res2[0].totalPoints || 0,
              level: res2[0].memberCard.level || 0,
              cardId: res2[0].memberCard.id || 0,
              coupons: ((_a = res2[0].couponList) == null ? void 0 : _a.length) || 0
            };
            this.isLoggedIn = true;
          });
        });
      } catch (e) {
        common_vendor.index.__f__("error", "at store/user.js:71", e);
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
        this.userInfo.coupons = (this.userInfo.coupons || 0) + 1;
      }
    }
  }
});
exports.useUserStore = useUserStore;
//# sourceMappingURL=../../.sourcemap/mp-weixin/store/user.js.map

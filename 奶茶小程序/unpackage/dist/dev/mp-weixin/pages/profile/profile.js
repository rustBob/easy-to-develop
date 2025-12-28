"use strict";
const common_vendor = require("../../common/vendor.js");
const common_assets = require("../../common/assets.js");
const store_user = require("../../store/user.js");
const _sfc_main = {
  __name: "profile",
  setup(__props) {
    const userStore = store_user.useUserStore();
    common_vendor.onShareAppMessage(() => {
      if (!userStore.hasSharedToday) {
        setTimeout(() => {
          userStore.receiveShareCoupon();
          common_vendor.index.showToast({ title: "分享成功，优惠券已到账", icon: "none" });
        }, 2e3);
      }
      return {
        title: "云顶奶茶，邀您共享美味",
        path: "/pages/index/index",
        imageUrl: "https://api.dicebear.com/7.x/food/svg?seed=Tea"
      };
    });
    const toCoupons = () => common_vendor.index.navigateTo({ url: "/pages/coupons/coupons" });
    const toRecharge = () => common_vendor.index.navigateTo({ url: "/pages/recharge/recharge" });
    const toLogin = () => common_vendor.index.navigateTo({ url: "/pages/login/login" });
    const toProfileEdit = () => {
      if (userStore.isLoggedIn) {
        common_vendor.index.navigateTo({ url: "/pages/profile-edit/profile-edit" });
      }
    };
    const toAddress = () => {
      if (userStore.isLoggedIn) {
        common_vendor.index.navigateTo({ url: "/pages/address/address" });
      } else {
        toLogin();
      }
    };
    const getLevelDiscountText = (level) => {
      switch (level) {
        case 1:
          return "全场9.5折";
        case 2:
          return "全场9折";
        default:
          return "无折扣";
      }
    };
    const handleLogout = () => {
      common_vendor.index.showModal({
        title: "提示",
        content: "确定要退出登录吗？",
        success: (res) => {
          if (res.confirm) {
            userStore.logout();
            common_vendor.index.showToast({ title: "已退出" });
          }
        }
      });
    };
    const handleDeleteAccount = () => {
      common_vendor.index.showModal({
        title: "危险操作",
        content: "确定要注销账号吗？注销后所有数据将无法恢复！",
        confirmColor: "#ff4d4f",
        success: async (res) => {
          if (res.confirm) {
            common_vendor.index.showLoading({ title: "处理中" });
            await userStore.deleteAccount();
            common_vendor.index.hideLoading();
          }
        }
      });
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.unref(userStore).isLoggedIn ? common_vendor.unref(userStore).userInfo.avatar : "/static/logo.png",
        b: common_vendor.unref(userStore).isLoggedIn
      }, common_vendor.unref(userStore).isLoggedIn ? {
        c: common_vendor.t(common_vendor.unref(userStore).userInfo.nickname),
        d: common_vendor.t(common_vendor.unref(userStore).userInfo.cardId)
      } : {
        e: common_vendor.o(toLogin)
      }, {
        f: common_vendor.unref(userStore).isLoggedIn
      }, common_vendor.unref(userStore).isLoggedIn ? {
        g: common_assets._imports_0
      } : {}, {
        h: common_vendor.o(toProfileEdit),
        i: common_vendor.unref(userStore).isLoggedIn
      }, common_vendor.unref(userStore).isLoggedIn ? {
        j: common_vendor.t(common_vendor.unref(userStore).userInfo.levelName),
        k: common_vendor.t(getLevelDiscountText(common_vendor.unref(userStore).userInfo.level)),
        l: common_vendor.unref(userStore).userInfo.level === 0 ? 1 : "",
        m: common_vendor.unref(userStore).userInfo.level === 1 ? 1 : "",
        n: common_vendor.unref(userStore).userInfo.level === 2 ? 1 : ""
      } : {}, {
        o: common_vendor.t(common_vendor.unref(userStore).userInfo.coupons || 0),
        p: common_vendor.o(toCoupons),
        q: common_vendor.t(common_vendor.unref(userStore).userInfo.points || 0),
        r: common_vendor.t(common_vendor.unref(userStore).userInfo.balance || "0.00"),
        s: common_vendor.o(toRecharge),
        t: common_vendor.unref(userStore).isLoggedIn
      }, common_vendor.unref(userStore).isLoggedIn ? common_vendor.e({
        v: !common_vendor.unref(userStore).hasSharedToday
      }, !common_vendor.unref(userStore).hasSharedToday ? {} : {}, {
        w: common_vendor.unref(userStore).hasSharedToday
      }, common_vendor.unref(userStore).hasSharedToday ? {} : {}, {
        x: common_assets._imports_0
      }) : {}, {
        y: common_assets._imports_0,
        z: common_vendor.o(toAddress),
        A: common_assets._imports_0,
        B: common_vendor.unref(userStore).isLoggedIn
      }, common_vendor.unref(userStore).isLoggedIn ? {
        C: common_vendor.o(handleLogout)
      } : {}, {
        D: common_vendor.unref(userStore).isLoggedIn
      }, common_vendor.unref(userStore).isLoggedIn ? {
        E: common_vendor.o(handleDeleteAccount)
      } : {});
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-dd383ca2"]]);
_sfc_main.__runtimeHooks = 2;
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/profile/profile.js.map

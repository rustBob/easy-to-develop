"use strict";
const common_vendor = require("../../common/vendor.js");
const store_user = require("../../store/user.js");
const _sfc_main = {
  __name: "coupons",
  setup(__props) {
    const userStore = store_user.useUserStore();
    const coupons = common_vendor.computed(() => userStore.userInfo.couponList || []);
    const goUse = () => {
      common_vendor.index.switchTab({ url: "/pages/menu/menu" });
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.f(coupons.value, (coupon, k0, i0) => {
          return common_vendor.e({
            a: coupon.type === "cash"
          }, coupon.type === "cash" ? {
            b: common_vendor.t(coupon.value)
          } : {
            c: common_vendor.t(coupon.value * 10)
          }, {
            d: common_vendor.t(coupon.type === "cash" ? "代金券" : "折扣券"),
            e: common_vendor.t(coupon.name),
            f: common_vendor.t(coupon.desc),
            g: coupon.minAmount > 0
          }, coupon.minAmount > 0 ? {
            h: common_vendor.t(coupon.minAmount)
          } : {}, {
            i: common_vendor.o(goUse, coupon.id),
            j: coupon.id
          });
        }),
        b: coupons.value.length === 0
      }, coupons.value.length === 0 ? {} : {});
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-8d9323cd"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/coupons/coupons.js.map

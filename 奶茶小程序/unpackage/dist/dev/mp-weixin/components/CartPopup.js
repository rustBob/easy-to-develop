"use strict";
const common_vendor = require("../common/vendor.js");
const common_assets = require("../common/assets.js");
const store_cart = require("../store/cart.js");
const store_user = require("../store/user.js");
const _sfc_main = {
  __name: "CartPopup",
  props: {
    visible: Boolean
  },
  emits: ["update:visible", "submit"],
  setup(__props, { emit: __emit }) {
    const props = __props;
    const emit = __emit;
    const cartStore = store_cart.useCartStore();
    const userStore = store_user.useUserStore();
    const remarks = common_vendor.ref("");
    const coupons = common_vendor.computed(() => userStore.userInfo && userStore.userInfo.couponList ? userStore.userInfo.couponList : []);
    common_vendor.watch(() => props.visible, (val) => {
      if (val) {
        common_vendor.index.hideTabBar();
      } else {
        common_vendor.index.showTabBar();
      }
    });
    common_vendor.onUnmounted(() => {
      common_vendor.index.showTabBar();
    });
    const close = () => {
      emit("update:visible", false);
    };
    const clearCart = () => {
      cartStore.clearCart();
      remarks.value = "";
      close();
    };
    const updateQty = (index, delta) => {
      cartStore.updateQty(index, delta);
      if (cartStore.items.length === 0) {
        remarks.value = "";
        close();
      }
    };
    const togglePoints = () => {
      cartStore.usePoints = !cartStore.usePoints;
    };
    const selectCoupon = (coupon) => {
      if (cartStore.originalTotal < coupon.minAmount)
        return;
      cartStore.selectCoupon(coupon.id);
    };
    const submitOrder = () => {
      const cleanRemarks = remarks.value.trim().replace(/<[^>]*>?/gm, "");
      emit("submit", { remarks: cleanRemarks });
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: __props.visible
      }, __props.visible ? common_vendor.e({
        b: common_vendor.o(clearCart),
        c: common_vendor.f(common_vendor.unref(cartStore).items, (item, index, i0) => {
          return {
            a: common_vendor.t(item.name),
            b: common_vendor.t(item.specs),
            c: common_vendor.t(item.price * item.drinksQuantity),
            d: common_vendor.o(($event) => updateQty(index, -1), index),
            e: common_vendor.t(item.drinksQuantity),
            f: common_vendor.o(($event) => updateQty(index, 1), index),
            g: index
          };
        }),
        d: common_vendor.unref(cartStore).orderType === "delivery"
      }, common_vendor.unref(cartStore).orderType === "delivery" ? {
        e: common_vendor.t(common_vendor.unref(cartStore).deliveryFee)
      } : {}, {
        f: common_vendor.unref(userStore).isLoggedIn
      }, common_vendor.unref(userStore).isLoggedIn ? {
        g: common_vendor.t(common_vendor.unref(userStore).userInfo.levelName),
        h: common_vendor.t(common_vendor.unref(cartStore).levelDiscountAmount)
      } : {}, {
        i: common_vendor.unref(userStore).isLoggedIn && common_vendor.unref(cartStore).couponDiscountAmount > 0
      }, common_vendor.unref(userStore).isLoggedIn && common_vendor.unref(cartStore).couponDiscountAmount > 0 ? {
        j: common_vendor.t(common_vendor.unref(cartStore).couponDiscountAmount)
      } : {}, {
        k: common_vendor.t(common_vendor.unref(userStore).userInfo.points),
        l: common_vendor.unref(cartStore).usePoints,
        m: common_vendor.o(togglePoints),
        n: common_vendor.t(common_vendor.unref(cartStore).pointsDeduction),
        o: common_vendor.unref(userStore).isLoggedIn && coupons.value && coupons.value.length > 0
      }, common_vendor.unref(userStore).isLoggedIn && coupons.value && coupons.value.length > 0 ? {
        p: common_vendor.f(coupons.value, (c, k0, i0) => {
          return common_vendor.e({
            a: common_vendor.t(c.name),
            b: common_vendor.unref(cartStore).originalTotal < c.minAmount
          }, common_vendor.unref(cartStore).originalTotal < c.minAmount ? {
            c: common_vendor.t((c.minAmount - common_vendor.unref(cartStore).originalTotal).toFixed(1))
          } : {}, {
            d: c.id,
            e: common_vendor.unref(cartStore).selectedCouponId === c.id ? 1 : "",
            f: common_vendor.unref(cartStore).originalTotal < c.minAmount ? 1 : "",
            g: common_vendor.o(($event) => selectCoupon(c), c.id)
          });
        })
      } : {}, {
        q: common_vendor.t(remarks.value.length),
        r: remarks.value,
        s: common_vendor.o(($event) => remarks.value = $event.detail.value),
        t: common_vendor.t(common_vendor.unref(cartStore).finalTotal),
        v: common_assets._imports_0,
        w: common_vendor.o(submitOrder),
        x: common_vendor.o(() => {
        }),
        y: common_vendor.o(close)
      }) : {});
    };
  }
};
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-6a6a0634"]]);
wx.createComponent(Component);
//# sourceMappingURL=../../.sourcemap/mp-weixin/components/CartPopup.js.map

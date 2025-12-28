"use strict";
const common_vendor = require("../common/vendor.js");
const store_user = require("./user.js");
const useCartStore = common_vendor.defineStore("cart", {
  state: () => ({
    items: [],
    usePoints: false,
    selectedCouponId: null,
    orderType: "pickup",
    // pickup, delivery
    address: null,
    store: null
  }),
  getters: {
    totalCount: (state) => state.items.reduce((sum, item) => sum + item.drinksQuantity, 0),
    originalTotal: (state) => {
      return state.items.reduce((sum, item) => sum + item.price * item.drinksQuantity, 0);
    },
    // Delivery Fee
    deliveryFee: (state) => {
      return state.orderType === "delivery" ? 5 : 0;
    },
    // 1. Level Discount
    levelDiscountAmount: (state) => {
      const userStore = store_user.useUserStore();
      if (!userStore.isLoggedIn)
        return 0;
      const level = userStore.userInfo.level;
      const rate = level === 2 ? 0.1 : level === 1 ? 0.05 : 0;
      return Math.floor(state.originalTotal * rate * 10) / 10;
    },
    // 2. Coupon Discount
    couponDiscountAmount: (state) => {
      const userStore = store_user.useUserStore();
      if (!state.selectedCouponId || !userStore.isLoggedIn || !userStore.userInfo.couponList)
        return 0;
      const coupon = userStore.userInfo.couponList.find((c) => c.id === state.selectedCouponId);
      if (!coupon)
        return 0;
      if (state.originalTotal < coupon.minAmount)
        return 0;
      if (coupon.type === "cash") {
        return coupon.value;
      } else if (coupon.type === "DISCOUNT") {
        const discount = state.originalTotal * (1 - coupon.value);
        return Math.floor(Math.min(discount, 20) * 10) / 10;
      }
      return 0;
    },
    discountAmount: (state) => {
      return parseFloat((state.levelDiscountAmount + state.couponDiscountAmount).toFixed(2));
    },
    pointsDeduction: (state) => {
      const userStore = store_user.useUserStore();
      if (!state.usePoints || !userStore.isLoggedIn)
        return 0;
      const amountAfterDiscount = state.originalTotal - state.levelDiscountAmount - state.couponDiscountAmount;
      if (amountAfterDiscount <= 0)
        return 0;
      const maxDeduction = amountAfterDiscount * 0.5;
      const pointsValue = userStore.userInfo.points / 100;
      return Math.floor(Math.min(maxDeduction, pointsValue) * 100) / 100;
    },
    finalTotal: (state) => {
      let total = state.originalTotal - state.discountAmount - state.pointsDeduction + state.deliveryFee;
      return Math.max(0, parseFloat(total.toFixed(2)));
    }
  },
  actions: {
    setOrderType(type) {
      this.orderType = type;
    },
    setAddress(addr) {
      this.address = addr;
    },
    setStore(store) {
      this.store = store;
    },
    addToCart(product, specs, drinksQuantity = 1) {
      const existing = this.items.find((c) => c.id === product.id && c.specs === specs);
      if (existing) {
        existing.drinksQuantity += drinksQuantity;
      } else {
        this.items.push({
          ...product,
          specs,
          drinksQuantity,
          drinkId: product.id
        });
        common_vendor.index.__f__("log", "at store/cart.js:100", this.items);
      }
    },
    updateQty(index, delta) {
      const item = this.items[index];
      if (!item)
        return;
      item.drinksQuantity += delta;
      if (item.drinksQuantity <= 0) {
        this.items.splice(index, 1);
      }
    },
    selectCoupon(id) {
      if (this.selectedCouponId === id) {
        this.selectedCouponId = null;
      } else {
        this.selectedCouponId = id;
      }
    },
    clearCart() {
      this.items = [];
      this.usePoints = false;
      this.selectedCouponId = null;
    }
  }
});
exports.useCartStore = useCartStore;
//# sourceMappingURL=../../.sourcemap/mp-weixin/store/cart.js.map

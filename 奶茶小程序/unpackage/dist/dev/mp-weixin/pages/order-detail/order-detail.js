"use strict";
const common_vendor = require("../../common/vendor.js");
const store_order = require("../../store/order.js");
const _sfc_main = {
  __name: "order-detail",
  setup(__props) {
    const orderStore = store_order.useOrderStore();
    const order = common_vendor.ref(null);
    common_vendor.onLoad((options) => {
      if (options.id) {
        order.value = orderStore.orders.find((o) => o.id === options.id);
      }
    });
    const formatDate = (timestamp) => {
      return new Date(timestamp).toLocaleString();
    };
    const formatTime = (timestamp) => {
      if (!timestamp)
        return "";
      const date = new Date(Number(timestamp));
      if (isNaN(date.getTime()))
        return "";
      return date.getHours().toString().padStart(2, "0") + ":" + date.getMinutes().toString().padStart(2, "0");
    };
    const contactRider = () => {
      common_vendor.index.makePhoneCall({ phoneNumber: "13800138000" });
    };
    const confirmArrival = () => {
      common_vendor.index.showToast({ title: "已通知店员", icon: "none" });
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: order.value
      }, order.value ? common_vendor.e({
        b: common_vendor.t(order.value.statusText),
        c: order.value.status === 2
      }, order.value.status === 2 ? {
        d: common_vendor.t(order.value.orderType === "pickup" ? "饮品制作中，请耐心等待" : "商家已接单，正在为您制作")
      } : {}, {
        e: order.value.status === 3
      }, order.value.status === 3 ? {
        f: common_vendor.t(order.value.orderType === "pickup" ? "饮品已制作完成，请前往取餐" : "骑手已接单，正在配送中")
      } : {}, {
        g: order.value.status === 4
      }, order.value.status === 4 ? {} : {}, {
        h: order.value.orderType === "pickup" && (order.value.status === 2 || order.value.status === 3)
      }, order.value.orderType === "pickup" && (order.value.status === 2 || order.value.status === 3) ? common_vendor.e({
        i: common_vendor.t(order.value.pickupCode),
        j: order.value.status === 2
      }, order.value.status === 2 ? {
        k: common_vendor.o(confirmArrival)
      } : {}) : {}, {
        l: order.value.orderType === "delivery" && (order.value.status === 2 || order.value.status === 3)
      }, order.value.orderType === "delivery" && (order.value.status === 2 || order.value.status === 3) ? common_vendor.e({
        m: common_vendor.t(formatTime(order.value.estimatedTime)),
        n: order.value.riderName
      }, order.value.riderName ? {
        o: common_vendor.o(contactRider)
      } : {}) : {}, {
        p: common_vendor.n(order.value.orderType),
        q: common_vendor.t(order.value.storeName || (order.value.store ? order.value.store.name : "云顶奶茶")),
        r: common_vendor.f(order.value.orderItems, (item, k0, i0) => {
          return {
            a: item.image,
            b: common_vendor.t(item.name),
            c: common_vendor.t(item.price),
            d: common_vendor.t(item.specs),
            e: common_vendor.t(item.drinksQuantity),
            f: item.cartId
          };
        }),
        s: order.value.levelDiscountAmount > 0
      }, order.value.levelDiscountAmount > 0 ? {
        t: common_vendor.t(order.value.levelDiscountAmount)
      } : {}, {
        v: order.value.couponDiscountAmount > 0
      }, order.value.couponDiscountAmount > 0 ? {
        w: common_vendor.t(order.value.couponDiscountAmount)
      } : {}, {
        x: !order.value.levelDiscountAmount && !order.value.couponDiscountAmount && order.value.discountAmount > 0
      }, !order.value.levelDiscountAmount && !order.value.couponDiscountAmount && order.value.discountAmount > 0 ? {
        y: common_vendor.t(order.value.discountAmount)
      } : {}, {
        z: order.value.pointsDeduction > 0
      }, order.value.pointsDeduction > 0 ? {
        A: common_vendor.t(order.value.pointsDeduction)
      } : {}, {
        B: common_vendor.t(order.value.finalAmount),
        C: common_vendor.t(order.value.id),
        D: common_vendor.t(formatDate(order.value.createTime)),
        E: common_vendor.t(Math.floor(order.value.finalAmount)),
        F: order.value.remarks
      }, order.value.remarks ? {
        G: common_vendor.t(order.value.remarks)
      } : {}) : {});
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-71729483"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/order-detail/order-detail.js.map

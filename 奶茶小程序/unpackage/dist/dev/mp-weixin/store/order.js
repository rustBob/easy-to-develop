"use strict";
const common_vendor = require("../common/vendor.js");
const useOrderStore = common_vendor.defineStore("order", {
  state: () => ({
    orders: []
  }),
  actions: {
    addOrder(order) {
      this.orders.unshift(order);
    },
    setOrders(orders) {
      this.orders = orders;
    },
    updateOrderStatus(orderId, status, statusText) {
      const order = this.orders.find((o) => o.id === orderId);
      if (order) {
        order.status = status;
        order.statusText = statusText;
      }
    }
  }
});
exports.useOrderStore = useOrderStore;
//# sourceMappingURL=../../.sourcemap/mp-weixin/store/order.js.map

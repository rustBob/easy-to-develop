"use strict";
const common_vendor = require("../../common/vendor.js");
const store_user = require("../../store/user.js");
const store_order = require("../../store/order.js");
const store_cart = require("../../store/cart.js");
const api_globalApi_index = require("../../api/globalApi/index.js");
const _sfc_main = {
  __name: "orders",
  setup(__props) {
    const userStore = store_user.useUserStore();
    const orderStore = store_order.useOrderStore();
    const cartStore = store_cart.useCartStore();
    const activeTab = common_vendor.ref(0);
    const tabs = ["全部", "历史", "拒单"];
    const loading = common_vendor.ref(false);
    const loadOrders = async () => {
      if (!userStore.isLoggedIn)
        return;
      loading.value = true;
      let params = { userId: userStore.userInfo.id };
      if (activeTab.value === 1) {
        params.status = 4;
      } else if (activeTab.value === 2) {
        params.status = 5;
      }
      try {
        await new Promise((resolve, reject) => {
          api_globalApi_index.globalApi["orders"].get(null, params, (res) => {
            res.forEach((order) => {
              order.statusText = getStatusText(order);
              order.totalCount = order.orderItems.reduce((acc, item) => acc + item.drinksQuantity, 0);
            });
            const reversedRes = res.reverse();
            orderStore.setOrders(reversedRes);
            resolve(reversedRes);
          }, (err) => {
            common_vendor.index.showToast({ title: "加载订单失败", icon: "none" });
            reject(err);
          });
        });
      } catch (e) {
        common_vendor.index.__f__("error", "at pages/orders/orders.vue:150", e);
      } finally {
        loading.value = false;
      }
    };
    common_vendor.watch(() => userStore.isLoggedIn, (loggedIn) => {
      if (loggedIn) {
        loadOrders();
      } else {
        orderStore.setOrders([]);
      }
    }, { immediate: true });
    common_vendor.watch(activeTab, () => {
      loadOrders();
    });
    common_vendor.onShow(() => {
    });
    const toLogin = () => {
      common_vendor.index.navigateTo({ url: "/pages/login/login" });
    };
    const filteredOrders = common_vendor.computed(() => {
      return orderStore.orders;
    });
    const toMenu = () => common_vendor.index.switchTab({ url: "/pages/menu/menu" });
    const toDetail = (id) => common_vendor.index.navigateTo({ url: `/pages/order-detail/order-detail?id=${id}` });
    const formatDate = (timestamp) => {
      const date = new Date(timestamp);
      return date.toLocaleString();
    };
    const formatTime = (timestamp) => {
      if (!timestamp)
        return "";
      const date = new Date(Number(timestamp));
      if (isNaN(date.getTime()))
        return "";
      return date.getHours().toString().padStart(2, "0") + ":" + date.getMinutes().toString().padStart(2, "0");
    };
    const remindOrder = () => {
      common_vendor.index.showToast({ title: "已催促店员制作", icon: "none" });
    };
    const contactRider = () => {
      common_vendor.index.makePhoneCall({ phoneNumber: "13800138000" });
    };
    const confirmArrival = () => {
      common_vendor.index.showToast({ title: "已通知店员，请稍候", icon: "none" });
    };
    const cancelOrder = (order) => {
      common_vendor.index.showModal({
        title: "提示",
        content: "确定要取消订单吗？",
        success: (res) => {
          if (res.confirm) {
            const index = orderStore.orders.findIndex((o) => o.id === order.id);
            if (index > -1) {
              orderStore.orders.splice(index, 1);
              common_vendor.index.showToast({ title: "订单已取消" });
            }
          }
        }
      });
    };
    const reorder = (order) => {
      order.items.forEach((item) => {
        cartStore.addToCart(item, item.specs, item.qty);
      });
      common_vendor.index.switchTab({ url: "/pages/menu/menu" });
    };
    const openAfterSales = (order) => {
      common_vendor.index.navigateTo({
        url: `/pages/after-sales/after-sales?orderId=${order.id}`
      });
    };
    const getStatusText = (order) => {
      switch (order.status) {
        case 0:
          return "已取消";
        case 1:
          return "待接单";
        case 2:
          return "制作中";
        case 3:
          return order.orderType === "pickup" ? "待取餐" : "派送中";
        case 4:
          return "已完成";
        case 5:
          return "商家拒单";
      }
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.f(tabs, (tab, index, i0) => {
          return common_vendor.e({
            a: common_vendor.t(tab),
            b: activeTab.value === index
          }, activeTab.value === index ? {} : {}, {
            c: index,
            d: activeTab.value === index ? 1 : "",
            e: common_vendor.o(($event) => activeTab.value = index, index)
          });
        }),
        b: !common_vendor.unref(userStore).isLoggedIn
      }, !common_vendor.unref(userStore).isLoggedIn ? {
        c: common_vendor.o(toLogin)
      } : filteredOrders.value.length === 0 ? {
        e: common_vendor.t(tabs[activeTab.value] === "全部" ? "" : tabs[activeTab.value]),
        f: common_vendor.o(toMenu)
      } : {
        g: common_vendor.f(filteredOrders.value, (order, k0, i0) => {
          return common_vendor.e({
            a: common_vendor.t(order.orderType === "pickup" ? "自取" : "外送"),
            b: common_vendor.n(order.orderType),
            c: common_vendor.t(formatDate(order.createTime)),
            d: common_vendor.t(order.statusText),
            e: common_vendor.n("status-" + order.status),
            f: common_vendor.f(order.orderItems, (item, k1, i1) => {
              return {
                a: common_vendor.t(item.name),
                b: common_vendor.t(item.drinksQuantity),
                c: common_vendor.t((item.price * item.drinksQuantity).toFixed(2)),
                d: item.cartId
              };
            }),
            g: order.remarks
          }, order.remarks ? {
            h: common_vendor.t(order.remarks)
          } : {}, {
            i: order.status !== 4 && order.status !== 5
          }, order.status !== 4 && order.status !== 5 ? common_vendor.e({
            j: order.orderType === "pickup"
          }, order.orderType === "pickup" ? {
            k: common_vendor.t(order.pickupCode),
            l: common_vendor.t(order.storeAddress || "云顶奶茶 (科技园店)")
          } : order.status === 2 ? {
            n: common_vendor.t(formatTime(order.estimatedTime)),
            o: common_vendor.t(order.address + " " + order.detail)
          } : {}, {
            m: order.status === 2
          }) : {}, {
            p: order.status === 5
          }, order.status === 5 ? {
            q: common_vendor.t(order.rejectReason || "商家繁忙，无法接单")
          } : {}, {
            r: common_vendor.t(order.totalCount),
            s: common_vendor.t(order.finalAmount),
            t: order.deliveryFee
          }, order.deliveryFee ? {
            v: common_vendor.t(order.deliveryFee)
          } : {}, {
            w: order.status === 1
          }, order.status === 1 ? {
            x: common_vendor.o(($event) => cancelOrder(order), order.id)
          } : {}, {
            y: order.status === 2
          }, order.status === 2 ? {
            z: common_vendor.o(remindOrder, order.id)
          } : {}, {
            A: order.status === 4
          }, order.status === 4 ? {
            B: common_vendor.o(($event) => openAfterSales(order), order.id)
          } : {}, {
            C: order.orderType === "send-out" && (order.status === 2 || order.status === 3)
          }, order.orderType === "send-out" && (order.status === 2 || order.status === 3) ? {
            D: common_vendor.o(contactRider, order.id)
          } : {}, {
            E: order.orderType === "pickup" && order.status === 3
          }, order.orderType === "pickup" && order.status === 3 ? {
            F: common_vendor.o(confirmArrival, order.id)
          } : {}, {
            G: common_vendor.t(order.status === 5 ? "重新下单" : "再来一单"),
            H: common_vendor.o(($event) => reorder(order), order.id),
            I: order.id,
            J: common_vendor.n(order.orderType),
            K: common_vendor.n({
              rejected: order.status === 5
            }),
            L: common_vendor.o(($event) => toDetail(order.id), order.id)
          });
        })
      }, {
        d: filteredOrders.value.length === 0
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-1acc51a1"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/orders/orders.js.map

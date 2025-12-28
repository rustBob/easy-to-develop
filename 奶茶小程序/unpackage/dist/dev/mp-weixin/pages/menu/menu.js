"use strict";
const common_vendor = require("../../common/vendor.js");
const common_assets = require("../../common/assets.js");
const store_cart = require("../../store/cart.js");
const store_user = require("../../store/user.js");
const api_globalApi_index = require("../../api/globalApi/index.js");
if (!Math) {
  (ProductDetail + CartPopup + StoreModal + SearchOverlay)();
}
const ProductDetail = () => "../../components/ProductDetail.js";
const CartPopup = () => "../../components/CartPopup.js";
const StoreModal = () => "../../components/StoreModal.js";
const SearchOverlay = () => "../../components/SearchOverlay.js";
const _sfc_main = {
  __name: "menu",
  setup(__props) {
    const cartStore = store_cart.useCartStore();
    const userStore = store_user.useUserStore();
    const categories = common_vendor.ref([]);
    const allProducts = common_vendor.ref([]);
    const activeCategoryId = common_vendor.ref("");
    const showDetail = common_vendor.ref(false);
    const showCart = common_vendor.ref(false);
    const showStoreModal = common_vendor.ref(false);
    const showSearch = common_vendor.ref(false);
    const selectedProduct = common_vendor.ref(null);
    common_vendor.watch(() => cartStore.store, (newVal, oldVal) => {
      api_globalApi_index.globalApi["drinks"].get(null, { storeId: newVal.id }, (res) => {
        allProducts.value = res;
      }, (err) => {
        common_vendor.index.showToast({ title: err.msg || "获取商品失败", icon: "none" });
      });
    });
    common_vendor.onShow(async () => {
      api_globalApi_index.globalApi["categories"].get(null, null, (res) => {
        categories.value = res;
        activeCategoryId.value = res[0].id;
      }, (err) => {
        common_vendor.index.showToast({ title: err.msg || "获取分类失败", icon: "none" });
      });
    });
    const activeCategory = common_vendor.computed(() => categories.value.find((c) => c.id === activeCategoryId.value));
    const activeProducts = common_vendor.computed(() => allProducts.value.filter((p) => p.categoryId === activeCategoryId.value));
    const switchCategory = (id) => {
      activeCategoryId.value = id;
    };
    const setOrderType = (type) => {
      cartStore.setOrderType(type);
    };
    const handleAddressSelect = (addr) => {
      cartStore.setAddress(addr);
    };
    const openAddressSelect = () => {
      if (!userStore.isLoggedIn) {
        common_vendor.index.navigateTo({ url: "/pages/login/login" });
        return;
      }
      common_vendor.index.navigateTo({
        url: "/pages/address/address?mode=select",
        events: {
          selectAddress: (addr) => {
            handleAddressSelect(addr);
          }
        }
      });
    };
    const handleStoreSelect = (store) => {
      cartStore.setStore(store);
    };
    const openProduct = (prod) => {
      selectedProduct.value = prod;
      showDetail.value = true;
    };
    const handleAddToCart = (data) => {
      cartStore.addToCart(data.product, data.specs, data.qty);
    };
    const handleSubmitOrder = async (data) => {
      var _a;
      if (!userStore.isLoggedIn) {
        showCart.value = false;
        common_vendor.index.navigateTo({ url: "/pages/login/login" });
        return;
      }
      if (cartStore.orderType === "delivery" && !cartStore.address) {
        common_vendor.index.showToast({ title: "请选择收货地址", icon: "none" });
        return;
      }
      const orderData = {
        userId: userStore.userInfo.id,
        orderType: cartStore.orderType,
        // Ensure orderType is passed
        storeId: (_a = cartStore.store) == null ? void 0 : _a.id,
        // Pass store info for both pickup and delivery
        locationId: cartStore.orderType === "delivery" ? cartStore.address.id : null,
        // Pass address for delivery
        orderItems: JSON.parse(JSON.stringify(cartStore.items)),
        // Deep copy items
        totalAmount: cartStore.originalTotal,
        finalAmount: cartStore.finalTotal,
        discountAmount: cartStore.discountAmount,
        levelDiscountAmount: cartStore.levelDiscountAmount,
        couponDiscountAmount: cartStore.couponDiscountAmount,
        pointsDeduction: cartStore.pointsDeduction,
        pointsConsumption: cartStore.usePoints ? Math.floor(cartStore.pointsDeduction * 100) : 0,
        remarks: data && data.remarks ? data.remarks : ""
      };
      common_vendor.index.showLoading({ title: "支付中..." });
      try {
        api_globalApi_index.globalApi["orders"].add(null, orderData, (res) => {
          common_vendor.index.__f__("log", "at pages/menu/menu.vue:263", res);
          if (cartStore.usePoints) {
            userStore.updatePoints(userStore.userInfo.points - Math.floor(cartStore.pointsDeduction * 100));
          }
          const gainedPoints = Math.floor(orderData.finalAmount);
          userStore.updatePoints(userStore.userInfo.points + gainedPoints);
          userStore.updateBalance(userStore.userInfo.balance - orderData.finalAmount);
          cartStore.clearCart();
          showCart.value = false;
          common_vendor.index.showTabBar();
          common_vendor.index.hideLoading();
          common_vendor.index.showToast({ title: "支付成功" });
          common_vendor.index.switchTab({ url: "/pages/orders/orders" });
        }, (err) => {
          common_vendor.index.hideLoading();
          common_vendor.index.showToast({ title: err.msg || "提交订单失败", icon: "none" });
          return;
        }, "make-order");
      } catch (e) {
        common_vendor.index.hideLoading();
        common_vendor.index.showToast({ title: "支付失败", icon: "none" });
      }
    };
    return (_ctx, _cache) => {
      var _a;
      return common_vendor.e({
        a: common_vendor.unref(cartStore).orderType === "pickup" ? 1 : "",
        b: common_vendor.o(($event) => setOrderType("pickup")),
        c: common_vendor.unref(cartStore).orderType === "delivery" ? 1 : "",
        d: common_vendor.o(($event) => setOrderType("delivery")),
        e: common_vendor.unref(cartStore).orderType === "pickup"
      }, common_vendor.unref(cartStore).orderType === "pickup" ? {
        f: common_vendor.t(common_vendor.unref(cartStore).store ? common_vendor.unref(cartStore).store.name : "云顶奶茶 (科技园店)"),
        g: common_assets._imports_0,
        h: common_vendor.t(common_vendor.unref(cartStore).store ? "距离您 " + common_vendor.unref(cartStore).store.distance : "距离您 1.2km"),
        i: common_vendor.t(common_vendor.unref(cartStore).store ? common_vendor.unref(cartStore).store.address : "每日现煮，新鲜制作"),
        j: common_vendor.o(($event) => showStoreModal.value = true)
      } : common_vendor.e({
        k: common_vendor.unref(cartStore).address
      }, common_vendor.unref(cartStore).address ? {
        l: common_vendor.t(common_vendor.unref(cartStore).address.position),
        m: common_vendor.t(common_vendor.unref(cartStore).address.detail),
        n: common_vendor.t(common_vendor.unref(cartStore).address.name),
        o: common_vendor.t(common_vendor.unref(cartStore).address.phone)
      } : {}, {
        p: common_assets._imports_0,
        q: common_vendor.o(openAddressSelect),
        r: common_vendor.t(common_vendor.unref(cartStore).store ? common_vendor.unref(cartStore).store.name : "选择门店"),
        s: common_assets._imports_0,
        t: common_vendor.o(($event) => showStoreModal.value = true)
      }), {
        v: common_vendor.t(allProducts.value.length),
        w: common_vendor.o(($event) => showSearch.value = true),
        x: common_vendor.f(categories.value, (cat, k0, i0) => {
          return {
            a: common_vendor.t(cat.name),
            b: cat.id,
            c: activeCategoryId.value === cat.id ? 1 : "",
            d: common_vendor.o(($event) => switchCategory(cat.id), cat.id)
          };
        }),
        y: activeCategory.value
      }, activeCategory.value ? {
        z: common_vendor.t(activeCategory.value.name)
      } : {}, {
        A: common_vendor.f(activeProducts.value, (prod, k0, i0) => {
          return {
            a: prod.image,
            b: common_vendor.t(prod.name),
            c: common_vendor.t(prod.description),
            d: common_vendor.t(prod.price),
            e: prod.id,
            f: common_vendor.o(($event) => openProduct(prod), prod.id)
          };
        }),
        B: common_vendor.unref(cartStore).totalCount > 0
      }, common_vendor.unref(cartStore).totalCount > 0 ? common_vendor.e({
        C: common_vendor.t(common_vendor.unref(cartStore).totalCount),
        D: common_vendor.t(common_vendor.unref(cartStore).finalTotal),
        E: common_vendor.unref(cartStore).discountAmount > 0
      }, common_vendor.unref(cartStore).discountAmount > 0 ? {
        F: common_vendor.t(common_vendor.unref(cartStore).discountAmount)
      } : {}, {
        G: common_vendor.o(($event) => showCart.value = true)
      }) : {}, {
        H: common_vendor.o(handleAddToCart),
        I: common_vendor.o(($event) => showDetail.value = $event),
        J: common_vendor.p({
          product: selectedProduct.value,
          visible: showDetail.value
        }),
        K: common_vendor.o(handleSubmitOrder),
        L: common_vendor.o(($event) => showCart.value = $event),
        M: common_vendor.p({
          visible: showCart.value
        }),
        N: common_vendor.o(handleStoreSelect),
        O: common_vendor.o(($event) => showStoreModal.value = $event),
        P: common_vendor.p({
          ["selected-id"]: (_a = common_vendor.unref(cartStore).store) == null ? void 0 : _a.id,
          visible: showStoreModal.value
        }),
        Q: common_vendor.o(openProduct),
        R: common_vendor.o(($event) => showSearch.value = $event),
        S: common_vendor.p({
          ["all-products"]: allProducts.value,
          visible: showSearch.value
        })
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-388b40d3"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/menu/menu.js.map

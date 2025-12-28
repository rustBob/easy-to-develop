"use strict";
const common_vendor = require("../../common/vendor.js");
const store_cart = require("../../store/cart.js");
const api_globalApi_index = require("../../api/globalApi/index.js");
if (!Math) {
  ProductDetail();
}
const ProductDetail = () => "../../components/ProductDetail.js";
const _sfc_main = {
  __name: "index",
  setup(__props) {
    const cartStore = store_cart.useCartStore();
    const banners = common_vendor.ref([]);
    const products = common_vendor.ref([]);
    const showDetail = common_vendor.ref(false);
    const selectedProduct = common_vendor.ref(null);
    const recommendations = common_vendor.computed(() => {
      if (products.value.length <= 3)
        return products.value;
      const shuffled = [...products.value].sort(() => 0.5 - Math.random());
      return shuffled.slice(0, 3);
    });
    common_vendor.onShow(async () => {
      api_globalApi_index.globalApi["banners"].get(null, null, (res) => {
        banners.value = res || [];
      });
      api_globalApi_index.globalApi["stores"].get(null, null, (res) => {
        cartStore.setStore(res[0]);
        api_globalApi_index.globalApi["drinks"].get(null, { storeId: res[0].id }, (res2) => {
          products.value = res2 || [];
        }, (err) => {
          common_vendor.index.showToast({ title: err.msg || "获取商品失败", icon: "none" });
        });
      }, (err) => {
        common_vendor.index.showToast({ title: err.msg || "获取门店失败", icon: "none" });
      });
    });
    const toMenu = (type) => {
      cartStore.setOrderType(type || "pickup");
      common_vendor.index.switchTab({ url: "/pages/menu/menu" });
    };
    const openProduct = (prod) => {
      selectedProduct.value = prod;
      showDetail.value = true;
    };
    const handleAddToCart = (data) => {
      cartStore.addToCart(data.product, data.specs, data.qty);
      common_vendor.index.showToast({ title: "已加入购物袋", icon: "none" });
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.f(banners.value, (b, i, i0) => {
          return {
            a: b.img,
            b: i
          };
        }),
        b: common_vendor.o(($event) => toMenu("pickup")),
        c: common_vendor.o(($event) => toMenu("delivery")),
        d: recommendations.value.length > 0
      }, recommendations.value.length > 0 ? {
        e: common_vendor.f(recommendations.value, (item, k0, i0) => {
          return {
            a: item.image,
            b: common_vendor.t(item.name),
            c: common_vendor.t(item.price),
            d: item.id,
            e: common_vendor.o(($event) => openProduct(item), item.id)
          };
        })
      } : {}, {
        f: common_vendor.o(handleAddToCart),
        g: common_vendor.o(($event) => showDetail.value = $event),
        h: common_vendor.p({
          product: selectedProduct.value,
          visible: showDetail.value
        })
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-1cf27b2a"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/index/index.js.map

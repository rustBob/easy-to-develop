"use strict";
const common_vendor = require("../common/vendor.js");
const common_assets = require("../common/assets.js");
const api_globalApi_index = require("../api/globalApi/index.js");
const _sfc_main = {
  __name: "StoreModal",
  props: {
    visible: Boolean,
    selectedId: [String, Number]
  },
  emits: ["update:visible", "select"],
  setup(__props, { emit: __emit }) {
    const emit = __emit;
    const stores = common_vendor.ref([]);
    common_vendor.onMounted(async () => {
      api_globalApi_index.globalApi["stores"].get(null, null, (res) => {
        stores.value = res || [];
      }, (err) => {
        common_vendor.index.showToast({ title: err.msg || "获取门店失败", icon: "none" });
      });
    });
    const close = () => {
      emit("update:visible", false);
    };
    const selectStore = (store) => {
      if (store.status !== "营业中") {
        common_vendor.index.showToast({ title: "该门店休息中", icon: "none" });
        return;
      }
      emit("select", store);
      close();
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: __props.visible
      }, __props.visible ? {
        b: common_assets._imports_0$1,
        c: common_vendor.o(close),
        d: common_vendor.f(stores.value, (store, k0, i0) => {
          return common_vendor.e({
            a: common_vendor.t(store.name),
            b: common_vendor.t(store.address),
            c: common_vendor.t(store.distance),
            d: common_vendor.t(store.status),
            e: store.status !== "营业中" ? 1 : "",
            f: __props.selectedId !== store.id
          }, __props.selectedId !== store.id ? {} : {}, {
            g: store.id,
            h: __props.selectedId === store.id ? 1 : "",
            i: common_vendor.o(($event) => selectStore(store), store.id)
          });
        })
      } : {});
    };
  }
};
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-7d4eb3ee"]]);
wx.createComponent(Component);
//# sourceMappingURL=../../.sourcemap/mp-weixin/components/StoreModal.js.map

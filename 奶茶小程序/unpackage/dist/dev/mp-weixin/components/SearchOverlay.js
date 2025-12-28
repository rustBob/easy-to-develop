"use strict";
const common_vendor = require("../common/vendor.js");
const api_mock = require("../api/mock.js");
const _sfc_main = {
  __name: "SearchOverlay",
  props: {
    visible: Boolean,
    allProducts: {
      type: Array,
      default: () => []
    }
  },
  emits: ["update:visible", "open-product"],
  setup(__props, { emit: __emit }) {
    const props = __props;
    const emit = __emit;
    const keyword = common_vendor.ref("");
    const history = common_vendor.ref([]);
    const hotSearches = common_vendor.ref([]);
    const results = common_vendor.ref([]);
    const showResults = common_vendor.ref(false);
    const loading = common_vendor.ref(false);
    common_vendor.onMounted(() => {
      loadHistory();
      loadHotSearches();
    });
    setInterval(() => {
      loadHotSearches();
    }, 2 * 60 * 60 * 1e3);
    const loadHistory = () => {
      const saved = common_vendor.index.getStorageSync("search_history");
      if (saved) {
        history.value = JSON.parse(saved);
      }
    };
    const loadHotSearches = async () => {
      try {
        const data = await api_mock.api.getHotSearches();
        hotSearches.value = data.slice(0, 8);
      } catch (e) {
        common_vendor.index.__f__("error", "at components/SearchOverlay.vue:129", e);
      }
    };
    const saveHistory = (kw) => {
      if (!kw)
        return;
      let newHistory = [...history.value];
      const index = newHistory.indexOf(kw);
      if (index > -1) {
        newHistory.splice(index, 1);
      }
      newHistory.unshift(kw);
      if (newHistory.length > 10) {
        newHistory = newHistory.slice(0, 10);
      }
      history.value = newHistory;
      common_vendor.index.setStorageSync("search_history", JSON.stringify(newHistory));
    };
    const clearHistory = () => {
      common_vendor.index.showModal({
        title: "提示",
        content: "确定清空历史记录吗？",
        success: (res) => {
          if (res.confirm) {
            history.value = [];
            common_vendor.index.removeStorageSync("search_history");
          }
        }
      });
    };
    const close = () => {
      emit("update:visible", false);
      keyword.value = "";
      showResults.value = false;
    };
    const clearKeyword = () => {
      keyword.value = "";
      showResults.value = false;
    };
    const onInput = (e) => {
      const val = e.detail.value;
      if (!val) {
        showResults.value = false;
        return;
      }
      performSearch(val);
    };
    const onSearch = () => {
      if (!keyword.value.trim())
        return;
      saveHistory(keyword.value.trim());
      performSearch(keyword.value);
    };
    const quickSearch = (kw) => {
      keyword.value = kw;
      saveHistory(kw);
      performSearch(kw);
    };
    const performSearch = (kw) => {
      if (!kw)
        return;
      loading.value = true;
      showResults.value = true;
      setTimeout(() => {
        const lowerKw = kw.toLowerCase();
        results.value = props.allProducts.filter(
          (p) => p.name.toLowerCase().includes(lowerKw) || p.description.toLowerCase().includes(lowerKw)
        );
        loading.value = false;
      }, 300);
    };
    const openProduct = (prod) => {
      emit("open-product", prod);
    };
    const highlightName = (name) => {
      if (!keyword.value)
        return [{ type: "text", text: name }];
      const parts = name.split(new RegExp(`(${keyword.value})`, "gi"));
      return parts.map((part) => {
        if (part.toLowerCase() === keyword.value.toLowerCase()) {
          return {
            name: "span",
            attrs: { style: "color: #ff6600; font-weight: bold;" },
            children: [{ type: "text", text: part }]
          };
        }
        return { type: "text", text: part };
      });
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: __props.visible
      }, __props.visible ? common_vendor.e({
        b: common_vendor.o(onSearch),
        c: common_vendor.o([($event) => keyword.value = $event.detail.value, onInput]),
        d: keyword.value,
        e: keyword.value
      }, keyword.value ? {
        f: common_vendor.o(clearKeyword)
      } : {}, {
        g: common_vendor.o(close),
        h: keyword.value && showResults.value
      }, keyword.value && showResults.value ? common_vendor.e({
        i: loading.value
      }, loading.value ? {} : results.value.length === 0 ? {} : {
        k: common_vendor.f(results.value, (prod, k0, i0) => {
          return {
            a: prod.image,
            b: highlightName(prod.name),
            c: common_vendor.t(prod.description),
            d: common_vendor.t(prod.price),
            e: prod.id,
            f: common_vendor.o(($event) => openProduct(prod), prod.id)
          };
        })
      }, {
        j: results.value.length === 0
      }) : common_vendor.e({
        l: history.value.length > 0
      }, history.value.length > 0 ? {
        m: common_vendor.o(clearHistory),
        n: common_vendor.f(history.value, (item, index, i0) => {
          return {
            a: common_vendor.t(item),
            b: index,
            c: common_vendor.o(($event) => quickSearch(item), index)
          };
        })
      } : {}, {
        o: common_vendor.f(hotSearches.value, (item, index, i0) => {
          return common_vendor.e({
            a: index < 3
          }, index < 3 ? {} : {}, {
            b: common_vendor.t(item),
            c: index,
            d: common_vendor.o(($event) => quickSearch(item), index)
          });
        })
      })) : {});
    };
  }
};
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-a91236c5"]]);
wx.createComponent(Component);
//# sourceMappingURL=../../.sourcemap/mp-weixin/components/SearchOverlay.js.map

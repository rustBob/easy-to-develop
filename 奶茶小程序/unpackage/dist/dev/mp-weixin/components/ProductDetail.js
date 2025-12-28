"use strict";
const common_vendor = require("../common/vendor.js");
const _sfc_main = {
  __name: "ProductDetail",
  props: {
    visible: Boolean,
    product: Object
  },
  emits: ["update:visible", "add-to-cart"],
  setup(__props, { emit: __emit }) {
    const props = __props;
    const emit = __emit;
    const qty = common_vendor.ref(1);
    const currentSpecs = common_vendor.reactive({});
    common_vendor.watch(() => props.product, (newVal) => {
      if (newVal && newVal.specs) {
        qty.value = 1;
        newVal.specs.forEach((s) => {
          s.option = JSON.parse(s.option);
          currentSpecs[s.name] = s.option[0];
        });
      }
    }, { immediate: true });
    const selectedSpecString = common_vendor.computed(() => Object.values(currentSpecs).join("/"));
    const calculatePrice = common_vendor.computed(() => props.product ? (props.product.price * qty.value).toFixed(2) : 0);
    const close = () => {
      emit("update:visible", false);
    };
    const selectSpec = (name, opt) => {
      currentSpecs[name] = opt;
    };
    const changeQty = (delta) => {
      const newVal = qty.value + delta;
      if (newVal >= 1)
        qty.value = newVal;
    };
    const handleAddToCart = () => {
      emit("add-to-cart", {
        product: props.product,
        specs: selectedSpecString.value,
        qty: qty.value
      });
      close();
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: __props.visible
      }, __props.visible ? {
        b: __props.product.image,
        c: common_vendor.o(close),
        d: common_vendor.t(__props.product.name),
        e: common_vendor.t(__props.product.description),
        f: common_vendor.f(__props.product.specs, (spec, idx, i0) => {
          return {
            a: common_vendor.t(spec.name),
            b: common_vendor.f(spec.option, (opt, k1, i1) => {
              return {
                a: common_vendor.t(opt),
                b: opt,
                c: currentSpecs[spec.name] === opt ? 1 : "",
                d: common_vendor.o(($event) => selectSpec(spec.name, opt), opt)
              };
            }),
            c: idx
          };
        }),
        g: common_vendor.t(calculatePrice.value),
        h: common_vendor.t(selectedSpecString.value),
        i: common_vendor.o(($event) => changeQty(-1)),
        j: common_vendor.t(qty.value),
        k: common_vendor.o(($event) => changeQty(1)),
        l: common_vendor.o(handleAddToCart),
        m: common_vendor.o(() => {
        }),
        n: common_vendor.o(close)
      } : {});
    };
  }
};
const Component = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-4ae65b64"]]);
wx.createComponent(Component);
//# sourceMappingURL=../../.sourcemap/mp-weixin/components/ProductDetail.js.map

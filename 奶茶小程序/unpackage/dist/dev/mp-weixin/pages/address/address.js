"use strict";
const common_vendor = require("../../common/vendor.js");
const store_user = require("../../store/user.js");
const api_globalApi_index = require("../../api/globalApi/index.js");
const _sfc_main = {
  __name: "address",
  setup(__props) {
    const mode = common_vendor.ref("manage");
    const userStore = store_user.useUserStore();
    const addresses = common_vendor.ref([]);
    const isEditing = common_vendor.ref(false);
    const editForm = common_vendor.reactive({
      userId: userStore.userInfo.id,
      gender: 0,
      phone: "",
      position: "",
      detail: "",
      tag: ""
    });
    common_vendor.onMounted(() => {
      const pages = getCurrentPages();
      if (pages.length > 0) {
        const options = pages[pages.length - 1].options;
        if (options && options.mode) {
          mode.value = options.mode;
        }
      }
      loadAddresses();
    });
    const loadAddresses = async () => {
      if (!userStore.isLoggedIn)
        return;
      api_globalApi_index.globalApi["locations"].get(null, { userId: userStore.userInfo.id }, (res) => {
        addresses.value = res;
      }, (err) => {
        common_vendor.index.showToast({ title: err.msg || "获取地址失败", icon: "none" });
      });
    };
    const cancelEdit = () => {
      isEditing.value = false;
    };
    const addAddress = () => {
      Object.assign(editForm, { userId: userStore.userInfo.id, name: "", gender: 0, phone: "", position: "", detail: "", tag: "" });
      isEditing.value = true;
    };
    const editAddress = (addr) => {
      Object.assign(editForm, addr);
      isEditing.value = true;
    };
    const deleteAddress = (addr) => {
      common_vendor.index.showModal({
        title: "提示",
        content: "确定要删除该地址吗？",
        success: async (res) => {
          if (res.confirm) {
            common_vendor.index.showLoading({ title: "删除中" });
            api_globalApi_index.globalApi["locations"].delete({ pk1: addr.id }, null, () => {
              common_vendor.index.hideLoading();
              common_vendor.index.showToast({ title: "删除成功" });
              loadAddresses();
            }, (err) => {
              common_vendor.index.hideLoading();
              common_vendor.index.showToast({ title: err.msg || "删除失败", icon: "none" });
            });
          }
        }
      });
    };
    const selectAddress = (addr) => {
      if (mode.value === "select") {
        const eventChannel = getCurrentPages()[getCurrentPages().length - 1].getOpenerEventChannel();
        if (eventChannel) {
          eventChannel.emit("selectAddress", addr);
        }
        common_vendor.index.navigateBack();
      }
    };
    const handleSave = async () => {
      if (!editForm.name || !editForm.phone || !editForm.position || !editForm.detail) {
        common_vendor.index.showToast({ title: "请填写完整信息", icon: "none" });
        return;
      }
      common_vendor.index.showLoading({ title: "保存中" });
      api_globalApi_index.globalApi["locations"].add(null, editForm, (res) => {
        common_vendor.index.__f__("log", "at pages/address/address.vue:188", res);
        common_vendor.index.hideLoading();
        loadAddresses();
        isEditing.value = false;
        common_vendor.index.showToast({ title: "保存成功" });
      }, (err) => {
        common_vendor.index.hideLoading();
        common_vendor.index.showToast({ title: err.msg || "保存失败", icon: "none" });
      });
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: !isEditing.value
      }, !isEditing.value ? {
        b: common_vendor.f(addresses.value, (addr, k0, i0) => {
          return common_vendor.e({
            a: common_vendor.t(addr.position),
            b: common_vendor.t(addr.detail),
            c: common_vendor.t(addr.name),
            d: common_vendor.t(addr.gender === 0 ? "先生" : "女士"),
            e: common_vendor.t(addr.phone),
            f: addr.tag
          }, addr.tag ? {
            g: common_vendor.t(addr.tag)
          } : {}, {
            h: common_vendor.o(($event) => editAddress(addr), addr.id),
            i: common_vendor.o(($event) => deleteAddress(addr), addr.id),
            j: addr.id,
            k: common_vendor.o(($event) => selectAddress(addr), addr.id)
          });
        }),
        c: common_vendor.o(addAddress)
      } : {
        d: editForm.name,
        e: common_vendor.o(($event) => editForm.name = $event.detail.value),
        f: editForm.gender === 0 ? 1 : "",
        g: common_vendor.o(($event) => editForm.gender = 0),
        h: editForm.gender === 1 ? 1 : "",
        i: common_vendor.o(($event) => editForm.gender = 1),
        j: editForm.phone,
        k: common_vendor.o(($event) => editForm.phone = $event.detail.value),
        l: editForm.position,
        m: common_vendor.o(($event) => editForm.position = $event.detail.value),
        n: editForm.detail,
        o: common_vendor.o(($event) => editForm.detail = $event.detail.value),
        p: common_vendor.f(["家", "公司", "学校"], (tag, k0, i0) => {
          return {
            a: common_vendor.t(tag),
            b: tag,
            c: editForm.tag === tag ? 1 : "",
            d: common_vendor.o(($event) => editForm.tag = tag, tag)
          };
        }),
        q: common_vendor.o(cancelEdit),
        r: common_vendor.o(handleSave)
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-40ca010a"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/address/address.js.map

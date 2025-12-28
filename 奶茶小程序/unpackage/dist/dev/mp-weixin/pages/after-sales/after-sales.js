"use strict";
const common_vendor = require("../../common/vendor.js");
const api_globalApi_index = require("../../api/globalApi/index.js");
const api_file = require("../../api/file.js");
const _sfc_main = {
  __name: "after-sales",
  setup(__props) {
    const orderId = common_vendor.ref("");
    const type = common_vendor.ref(1);
    const reason = common_vendor.ref("");
    const images = common_vendor.ref([]);
    const status = common_vendor.ref();
    const recordId = common_vendor.ref();
    common_vendor.onLoad((options) => {
      if (options.orderId) {
        orderId.value = options.orderId;
        api_globalApi_index.globalApi["after-sales"].get(null, {
          orderId: orderId.value
        }, (res) => {
          const data = res[0];
          if (data) {
            recordId.value = data.id;
            type.value = data.type;
            images.value = data.images || [];
            reason.value = data.description || "";
            status.value = data.status;
          }
        });
      }
    });
    const getStatusText = (s) => {
      switch (s) {
        case 1:
          return "待审批";
        case 2:
          return "已同意";
        case 3:
          return "已拒绝";
        default:
          return "";
      }
    };
    const chooseImage = () => {
      if (status.value === 1 || status.value === 2)
        return;
      common_vendor.index.chooseImage({
        count: 3 - images.value.length,
        sizeType: ["original", "compressed"],
        sourceType: ["album", "camera"],
        success: async (res) => {
          common_vendor.index.showLoading({ title: "上传中...", mask: true });
          try {
            const uploadTasks = res.tempFiles.map((file) => {
              return api_file.upload([{
                ...file,
                name: Date.now() + "-aftersales"
              }]);
            });
            const results = await Promise.all(uploadTasks);
            const newImages = results.filter((r) => r.code === 200 && r.data && r.data.length > 0).map((r) => r.data[0]);
            if (newImages.length > 0) {
              images.value = [...images.value, ...newImages];
            } else {
              common_vendor.index.showToast({ title: "图片上传失败", icon: "none" });
            }
          } catch (e) {
            common_vendor.index.__f__("error", "at pages/after-sales/after-sales.vue:122", e);
            common_vendor.index.showToast({ title: "上传异常", icon: "none" });
          } finally {
            common_vendor.index.hideLoading();
          }
        }
      });
    };
    const removeImage = (index) => {
      if (status.value === 1 || status.value === 2)
        return;
      images.value.splice(index, 1);
    };
    const cancel = () => {
      common_vendor.index.showModal({
        title: "提示",
        content: "确定要取消售后申请吗？",
        success: (res) => {
          if (res.confirm) {
            api_globalApi_index.globalApi["after-sales"].delete(null, { id: recordId.value }, () => {
              common_vendor.index.showToast({ title: "已取消" });
              setTimeout(() => {
                common_vendor.index.navigateBack();
              }, 1500);
            });
          }
        }
      });
    };
    const submit = () => {
      if (!reason.value.trim()) {
        common_vendor.index.showToast({ title: "请输入问题描述", icon: "none" });
        return;
      }
      common_vendor.index.showLoading({ title: "提交中" });
      const data = {
        orderId: orderId.value,
        type: type.value,
        description: reason.value,
        images: images.value,
        status: 1
        // Reset to pending
      };
      const apiCall = recordId.value ? api_globalApi_index.globalApi["after-sales"].update({ pk1: recordId.value }, data) : api_globalApi_index.globalApi["after-sales"].add(null, data);
      apiCall.then((res) => {
        common_vendor.index.hideLoading();
        common_vendor.index.showToast({ title: "提交成功" });
        setTimeout(() => {
          common_vendor.index.navigateBack();
        }, 1500);
      });
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.t(orderId.value),
        b: status.value
      }, status.value ? common_vendor.e({
        c: common_vendor.t(getStatusText(status.value)),
        d: status.value === 3
      }, status.value === 3 ? {} : {}) : {}, {
        e: type.value === 1 ? 1 : "",
        f: common_vendor.o(($event) => status.value !== 1 && status.value !== 2 && (type.value = 1)),
        g: type.value === 2 ? 1 : "",
        h: common_vendor.o(($event) => status.value !== 1 && status.value !== 2 && (type.value = 2)),
        i: status.value === 1 || status.value === 2 ? 1 : "",
        j: status.value === 1 || status.value === 2,
        k: reason.value,
        l: common_vendor.o(($event) => reason.value = $event.detail.value),
        m: common_vendor.t(reason.value.length),
        n: status.value !== 1 && status.value !== 2
      }, status.value !== 1 && status.value !== 2 ? {
        o: common_vendor.o(chooseImage)
      } : {}, {
        p: common_vendor.f(images.value, (img, index, i0) => {
          return common_vendor.e({
            a: img
          }, status.value !== 1 && status.value !== 2 ? {
            b: common_vendor.o(($event) => removeImage(index), index)
          } : {}, {
            c: index
          });
        }),
        q: status.value !== 1 && status.value !== 2,
        r: !status.value || status.value === 3
      }, !status.value || status.value === 3 ? {
        s: common_vendor.o(submit)
      } : {}, {
        t: status.value === 1
      }, status.value === 1 ? {
        v: common_vendor.o(cancel)
      } : {});
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-47c44558"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/after-sales/after-sales.js.map

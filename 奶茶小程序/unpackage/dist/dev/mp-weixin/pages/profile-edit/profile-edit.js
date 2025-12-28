"use strict";
const common_vendor = require("../../common/vendor.js");
const store_user = require("../../store/user.js");
const api_globalApi_index = require("../../api/globalApi/index.js");
const api_file = require("../../api/file.js");
const _sfc_main = {
  __name: "profile-edit",
  setup(__props) {
    const userStore = store_user.useUserStore();
    const loading = common_vendor.ref(false);
    const form = common_vendor.reactive({
      id: "",
      username: "",
      nickname: "",
      phone: "",
      email: "",
      avatar: ""
    });
    let avatarFile = null;
    common_vendor.onMounted(() => {
      if (userStore.userInfo) {
        form.id = userStore.userInfo.id;
        form.username = userStore.userInfo.username || userStore.userInfo.nickname;
        form.nickname = userStore.userInfo.nickname;
        form.phone = userStore.userInfo.phone || "";
        form.email = userStore.userInfo.email || "";
        form.avatar = userStore.userInfo.avatar;
      }
    });
    const changeAvatar = () => {
      common_vendor.index.chooseImage({
        count: 1,
        sizeType: ["original", "compressed"],
        sourceType: ["album", "camera"],
        success: (res) => {
          form.avatar = res.tempFilePaths[0];
          avatarFile = {
            ...res.tempFiles[0],
            name: Date.now() + "-avatar"
          };
        },
        fail: (err) => {
          common_vendor.index.__f__("error", "at pages/profile-edit/profile-edit.vue:78", "选择图片失败：", err);
          common_vendor.index.showToast({
            title: "选择图片失败或取消了选择",
            icon: "none"
          });
        }
      });
    };
    const validate = () => {
      if (!form.nickname.trim()) {
        common_vendor.index.showToast({ title: "请输入昵称", icon: "none" });
        return false;
      }
      if (form.phone && !/^1[3-9]\d{9}$/.test(form.phone)) {
        common_vendor.index.showToast({ title: "手机号格式不正确", icon: "none" });
        return false;
      }
      return true;
    };
    const handleSave = async () => {
      if (!validate())
        return;
      common_vendor.index.showModal({
        title: "确认修改",
        content: "确定继续吗？",
        success: async (res) => {
          if (res.confirm) {
            if (avatarFile) {
              common_vendor.index.showLoading({ title: "上传图片中...", mask: true });
              const res2 = await api_file.upload([avatarFile]);
              if (res2.code !== 200) {
                common_vendor.index.hideLoading();
                common_vendor.index.showToast({
                  title: "文件上传失败",
                  icon: "none"
                });
                return;
              }
              form.avatar = res2.data[0];
              common_vendor.index.hideLoading();
            }
            submitData();
          }
        }
      });
    };
    const submitData = async () => {
      loading.value = true;
      try {
        const updateData = {
          id: form.id,
          nickname: form.nickname,
          phone: form.phone,
          avatar: form.avatar
        };
        await api_globalApi_index.globalApi["users"].update(null, updateData, (res) => {
          userStore.updateUserInfo(updateData);
          common_vendor.index.showToast({ title: "保存成功" });
          setTimeout(() => {
            common_vendor.index.navigateBack();
          }, 1500);
        }, (err) => {
          common_vendor.index.showToast({ title: err.msg || "保存失败", icon: "none" });
        });
      } catch (e) {
        common_vendor.index.showToast({ title: "保存失败", icon: "none" });
      } finally {
        loading.value = false;
      }
    };
    const goBack = () => {
      common_vendor.index.navigateBack();
    };
    return (_ctx, _cache) => {
      return {
        a: form.avatar,
        b: common_vendor.o(changeAvatar),
        c: form.username,
        d: form.nickname,
        e: common_vendor.o(($event) => form.nickname = $event.detail.value),
        f: form.phone,
        g: common_vendor.o(($event) => form.phone = $event.detail.value),
        h: common_vendor.o(goBack),
        i: common_vendor.o(handleSave),
        j: loading.value
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-b59caf64"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/profile-edit/profile-edit.js.map

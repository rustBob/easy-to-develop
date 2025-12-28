"use strict";
const common_vendor = require("../../common/vendor.js");
const store_user = require("../../store/user.js");
const _sfc_main = {
  __name: "login",
  setup(__props) {
    const userStore = store_user.useUserStore();
    const form = common_vendor.reactive({ username: "", password: "", nickname: "" });
    const isRegister = common_vendor.ref(false);
    const goBack = () => {
      common_vendor.index.navigateBack();
    };
    const toggleMode = () => {
      isRegister.value = !isRegister.value;
      form.username = "";
      form.password = "";
      form.nickname = "";
    };
    const handleSubmit = async () => {
      if (!form.username) {
        common_vendor.index.showToast({ title: "请输入用户名", icon: "none" });
        return;
      }
      if (!form.password) {
        common_vendor.index.showToast({ title: "请输入密码", icon: "none" });
        return;
      }
      if (isRegister.value) {
        if (!form.nickname) {
          common_vendor.index.showToast({ title: "请输入昵称", icon: "none" });
          return;
        }
        try {
          await userStore.register(form.username, form.password, form.nickname);
          common_vendor.index.showToast({ title: "注册成功，请登录" });
          isRegister.value = false;
        } catch (e) {
          common_vendor.index.showToast({ title: "注册失败", icon: "none" });
        }
      } else {
        try {
          await userStore.login(form.username, form.password);
          common_vendor.index.navigateBack();
          common_vendor.index.showToast({ title: "登录成功" });
        } catch (e) {
          common_vendor.index.showToast({ title: "登录失败", icon: "none" });
        }
      }
    };
    return (_ctx, _cache) => {
      return common_vendor.e({
        a: common_vendor.t(isRegister.value ? "注册账号" : "欢迎回来"),
        b: form.username,
        c: common_vendor.o(($event) => form.username = $event.detail.value),
        d: isRegister.value
      }, isRegister.value ? {
        e: form.nickname,
        f: common_vendor.o(($event) => form.nickname = $event.detail.value)
      } : {}, {
        g: form.password,
        h: common_vendor.o(($event) => form.password = $event.detail.value),
        i: common_vendor.t(isRegister.value ? "注册" : "登录"),
        j: common_vendor.o(handleSubmit),
        k: common_vendor.t(isRegister.value ? "已有账号？去登录" : "没有账号？去注册"),
        l: common_vendor.o(toggleMode),
        m: common_vendor.o(goBack)
      });
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-e4e4508d"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/login/login.js.map

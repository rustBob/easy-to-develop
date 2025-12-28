"use strict";
const common_vendor = require("../../common/vendor.js");
const store_user = require("../../store/user.js");
const api_globalApi_index = require("../../api/globalApi/index.js");
const _sfc_main = {
  __name: "recharge",
  setup(__props) {
    const userStore = store_user.useUserStore();
    const selectedAmount = common_vendor.ref(null);
    const customAmount = common_vendor.ref(null);
    const rechargeOptions = [
      { amount: 50, bonus: 0 },
      { amount: 100, bonus: 5 },
      { amount: 200, bonus: 15 },
      { amount: 500, bonus: 50 }
    ];
    const selectAmount = (amount) => {
      selectedAmount.value = amount;
      customAmount.value = null;
    };
    const handleRecharge = async () => {
      const amount = selectedAmount.value || customAmount.value;
      if (!amount || amount <= 0) {
        common_vendor.index.showToast({ title: "请输入有效金额", icon: "none" });
        return;
      }
      let bonus = 0;
      const option = rechargeOptions.find((o) => o.amount === amount);
      if (option) {
        bonus = option.bonus;
      }
      common_vendor.index.showLoading({ title: "充值中" });
      try {
        const newBalance = userStore.userInfo.balance + amount + bonus;
        await api_globalApi_index.globalApi["users"].update(null, {
          id: userStore.userInfo.id,
          balance: newBalance
        }, () => {
          userStore.updateBalance(newBalance);
        });
        common_vendor.index.hideLoading();
        common_vendor.index.showToast({ title: "充值成功" });
        setTimeout(() => common_vendor.index.navigateBack(), 1500);
      } catch (e) {
        common_vendor.index.hideLoading();
        common_vendor.index.showToast({ title: "充值失败", icon: "none" });
      }
    };
    return (_ctx, _cache) => {
      return {
        a: common_vendor.t(common_vendor.unref(userStore).userInfo.balance || "0.00"),
        b: common_vendor.f(rechargeOptions, (item, k0, i0) => {
          return common_vendor.e({
            a: common_vendor.t(item.amount),
            b: item.bonus > 0
          }, item.bonus > 0 ? {
            c: common_vendor.t(item.bonus)
          } : {}, {
            d: item.amount,
            e: selectedAmount.value === item.amount ? 1 : "",
            f: common_vendor.o(($event) => selectAmount(item.amount), item.amount)
          });
        }),
        c: common_vendor.o([common_vendor.m(($event) => customAmount.value = $event.detail.value, {
          number: true
        }), ($event) => selectedAmount.value = null]),
        d: customAmount.value,
        e: common_vendor.o(handleRecharge)
      };
    };
  }
};
const MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["__scopeId", "data-v-2984a38c"]]);
wx.createPage(MiniProgramPage);
//# sourceMappingURL=../../../.sourcemap/mp-weixin/pages/recharge/recharge.js.map
